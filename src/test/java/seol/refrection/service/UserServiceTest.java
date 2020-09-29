package seol.refrection.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seol.refrection.entity.User;
import seol.refrection.repository.UserRepository;

@Slf4j
@SpringBootTest
@Transactional
class UserServiceTest {

	@Autowired UserRepository userRepository;
	@Autowired EntityManager entityManager;
	@Test
	void Test() {
		User user = User.builder()
				.userId("seolnavy")
				.userName("seol")
				.age(32)
				.etc("test")
				.build();
		userRepository.save(user);
		flushAndClear();

		User findUser = userRepository.findById("seolnavy").get();
	}

	private void flushAndClear() {
		entityManager.flush();
		entityManager.clear();
	}
}