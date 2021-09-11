package com.ct.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.user.model.Patient;
import com.ct.user.model.Staff;
import com.ct.user.model.User;
import com.ct.user.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUserFromPatient(List<Patient> patients) {
		return patients.stream().map(patient -> {
			User user = new User();
			user.setTitle(patient.getTitle());
			user.setFirstName(patient.getFirstName());
			user.setLastName(patient.getLastName());
			user.setEmail(patient.getEmail());
			user.setRoleId(4);
			return user;
		}).collect(Collectors.toList());
	}

	@Override
	public List<User> getAllUserFromStaff(List<Staff> staffs) {
		return staffs.stream().map(patient -> {
			User user = new User();
			user.setTitle(patient.getTitle());
			user.setFirstName(patient.getFirstName());
			user.setLastName(patient.getLastName());
			user.setEmail(patient.getEmail());
			user.setRoleId(patient.getRoleId());
			return user;
		}).collect(Collectors.toList());
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.allUsers();
	}

	@Override
	public User authenicate(User user) {
		if (user != null && !user.getEmail().isEmpty() && !user.getPassword().isEmpty())
			return userRepository.authenticate(user.getEmail(), user.getPassword());

		return null;
	}

	@Override
	public User updateCredentials(User user) {

		return null;
	}
}
