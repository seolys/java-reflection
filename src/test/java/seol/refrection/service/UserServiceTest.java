package seol.refrection.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seol.refrection.entity.Product;
import seol.refrection.entity.System;
import seol.refrection.entity.User;
import seol.refrection.entity.UserStatus;
import seol.refrection.repository.UserRepository;

@Slf4j
@SpringBootTest
@Transactional
class UserServiceTest {

	@Autowired UserRepository userRepository;
	@Autowired EntityManager entityManager;

	@Test
	void test1() {
		User user = User.builder()
				.userId("seolnavy")
				.userName("seol")
				.age(32)
				.etc("test")
				.userStatus(UserStatus.A)
				.haveMoney(new BigDecimal(50000))
				.build();
		userRepository.save(user);
		flushAndClear();

		User managedUser = userRepository.findById("seolnavy").get();
		User newUser = User.builder()
				.userId("seolnavy")
				.userName("seolys")
				.age(30)
				.userStatus(UserStatus.B)
				.haveMoney(new BigDecimal(1000))
				.build();
		this.update(managedUser, newUser, "etc", "userName");
		flushAndClear();
	}

	@Test
	void test2() {
		User user = User.builder()
				.userId("seolnavy")
				.userName("seol")
				.age(32)
				.etc("test")
				.userStatus(UserStatus.A)
				.haveMoney(new BigDecimal(50000))
				.build();
		userRepository.save(user);
		flushAndClear();

		User managedUser = userRepository.findById("seolnavy").get();
		User newUser = User.builder()
				.userId("seolnavy")
				.userName("seolys")
				.age(30)
				.userStatus(UserStatus.B)
				.haveMoney(new BigDecimal(1000))
				.build();
		managedUser.update(newUser, "etc", "userName");
		flushAndClear();

		Product otherEntity1 = Product.builder().productId("pro").build();
		Exception exception = Assertions.assertThrows(Exception.class, () ->
				managedUser.update(otherEntity1, "etc", "userName"));
		log.info(exception.getMessage());

//		System otherEntity2 = System.builder().systemId("sys").build();
//		Assertions.assertThrows(Exception.class, () ->
//				managedUser.update(otherEntity2, "etc", "userName"));
	}

	@SneakyThrows
	public <Entity> void update(Entity managedEntity, Entity newEntity, String ...ignoreColumns) {
		List<String> ignoreColumnChecker = Arrays.asList(ignoreColumns);
		Field[] declaredFields = managedEntity.getClass().getDeclaredFields();
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
			field.set(managedEntity, field.get(newEntity));
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

	public static <E extends Number> void someMethod(List<E> numberList) {
		E number = numberList.get(0);
		numberList.add(number);
	}

	private void flushAndClear() {
		entityManager.flush();
		entityManager.clear();
	}
}