package com.ct.users.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ct.user.model.User;
import com.ct.user.model.UserDto;
import com.ct.user.repo.UserRepository;
import com.ct.user.service.UserService;

@SpringBootTest
public class UserServiceImplTest {

	@Mock
	UserRepository userRepository;

	@MockBean
	UserService userService;
	
	User user =  null;

	@BeforeEach
	void initialUser() {
		User user = new User();
		user.setEmail("admin@admin");
		user.setPassword("Welcome@123");
		user.setAttempt(-1);
	}

	@Test
	void authenticateTest() {

		UserDto userDto = new UserDto();
		userDto.setEmail("admin@admin");
		userDto.setPassword("Welcome@123");
		
		assertEquals(userService.authenticate(userDto, user).get(), user);
		
	}

}
