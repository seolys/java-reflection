package seol.refrection.entity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@MappedSuperclass
public abstract class BaseEntity {

	@SneakyThrows
	public <Entity extends BaseEntity> Entity update(Entity newEntity, String ...ignoreColumns) {
		// BaseEntity를 상속받았지만, this와 newEntity가 서로 다른 엔티티일 경우 에러 발생.
		if(!this.getClass().isAssignableFrom(newEntity.getClass())) {
			String message = String.format("Entity mismatch. (updateTarget: %s, newEntity: %s)", this.getClass().getName(),
					newEntity.getClass().getName());
			throw new RuntimeException(message);
		}
		List<String> ignoreColumnChecker = Arrays.asList(ignoreColumns);
		Field[] declaredFields = newEntity.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			if(ignoreColumnChecker.contains(field.getName())){
				continue;
			}
			if(isId(field)){
				continue;
			}
			if(!isColumns(field)) {
				continue;
			}
			field.setAccessible(true);
			log.debug("fieldName: {}, value: {}", field.getName(), field.get(newEntity));
			field.set(this, field.get(newEntity));
		}
		return (Entity) this;
	}

	private boolean isId(Field field) {
		return Arrays.stream(field.getAnnotations())
				.filter(annotation -> annotation instanceof Id)
				.count() > 0;
	}

	private boolean isColumns(Field field) {
		return Arrays.stream(field.getAnnotations())
				.filter(annotation -> annotation instanceof Column)
				.count() > 0;
	}

}
