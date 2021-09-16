package com.ct.user.service;

import java.util.List;
import java.util.Optional;

import com.ct.user.model.Patient;
import com.ct.user.model.Staff;
import com.ct.user.model.UserDto;

public interface UserService {

	List<UserDto> getAllUserFromPatient(List<Patient> patients);

	List<UserDto> getAllUserFromStaff(List<Staff> staffs);

	List<UserDto> getAllUsers();

	Optional<UserDto> authenticate(UserDto user);

	Optional<UserDto> updateCredentials(UserDto user);

	Optional<UserDto> resetUser(UserDto user);

}
