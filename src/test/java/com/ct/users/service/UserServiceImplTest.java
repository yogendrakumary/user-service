package com.ct.users.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ct.user.UserServiceApplication;
import com.ct.user.model.AuthDto;
import com.ct.user.model.User;
import com.ct.user.repo.UserRepository;
import com.ct.user.service.RolesService;
import com.ct.user.service.UserServiceImpl;
import com.ct.user.utility.EmailServiceImpl;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@ContextConfiguration(classes = UserServiceApplication.class)
public class UserServiceImplTest {

	@Mock
	UserRepository userRepository;

	@Mock
	private RolesService rolesService;

	@Mock
	private EmailServiceImpl emailServiceImpl;

	@InjectMocks
	UserServiceImpl userService;

	User user = null;

	@BeforeEach
	void initialUser() {
		user = new User();
		user.setUserId(11L);
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

	@Test
	void randomPasswordGeneraterTest() {
		String resetPassword = RandomStringUtils.randomAlphanumeric(6).concat("@C1c");
		System.out.println(RandomStringUtils.randomAscii(10));
		System.out.println(resetPassword);
	}

}
