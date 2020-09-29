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
	public <Entity extends BaseEntity> void update(Entity newEntity, String ...ignoreColumns) {
		if(!this.getClass().isAssignableFrom(newEntity.getClass())) {
			throw new RuntimeException();
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
