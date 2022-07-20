package com.evilcorp.demo;

import com.evilcorp.demo.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(initializers = TestcontainersInitializer.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestcontainersSpringDemoApplicationTests {
	@Autowired
	UserRepository userRepository;
	private User createdUser;

	@Test
	@Order(1)
	void addUser() {
		createdUser = new User();
		createdUser.setName("Fry");
		userRepository.save(createdUser);
	}

	@Test
	@Order(2)
	void checkUserAdded() {
		final Optional<User> loadedUser = userRepository.findById(createdUser.getUserId());
		assertTrue(loadedUser.isPresent());
		assertEquals("Fry", loadedUser.get().getName());
		assertNotSame(createdUser, loadedUser.get());
	}
}
