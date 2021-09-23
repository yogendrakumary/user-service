package com.ct.users.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ct.user.repo.StaffRepository;

@SpringBootTest
public class StaffServiceTest {

	@Autowired
	StaffRepository repository;

	@Test
	public void testLastEmployeeID() {

		assertThat(repository.getLastEmployeeId()).isEqualTo(10);
	}
}
