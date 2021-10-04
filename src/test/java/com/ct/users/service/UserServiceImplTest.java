package com.ct.users.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ct.user.model.AuthDto;
import com.ct.user.model.User;
import com.ct.user.repo.UserRepository;
import com.ct.user.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceImplTest {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserService userService;

	User user = null;

	@BeforeEach
	void initialUser() {
		User user = new User();
		user.setEmail("admin@admin");
		user.setPassword("Welcome@123");
		user.setAttempt(-1);
	}

	@Test
	void authenticateTest() {

		AuthDto userDto = new AuthDto();
		userDto.setEmail("admin@admin");
		userDto.setPassword("Welcome@123");

		assertEquals(userService.authenticate(userDto, user).get(), user);

	}

}
