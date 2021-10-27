package com.ct.user.service;

import java.util.List;
import java.util.Optional;

import com.ct.user.model.AuthDto;
import com.ct.user.model.Patient;
import com.ct.user.model.Staff;
import com.ct.user.model.User;
import com.ct.user.model.UserDto;
import com.ct.user.response.UserResponse;

public interface UserService {

	/**
	 * Only to Return All Patients
	 * 
	 * @param patients
	 * @return
	 */
	List<UserDto> getAllUserFromPatient(List<Patient> patients);

	/**
	 * Only to Return All Staff
	 * 
	 * @param staffs
	 * @return
	 */
	List<UserDto> getAllUserFromStaff(List<Staff> staffs);

	/**
	 * Return All the user from Staff and Patient
	 * 
	 * @return
	 */
	List<UserDto> getAllUsers();

	/**
	 * To Authenticate the Staff
	 * 
	 * @param userDto
	 * @param dbUser TODO
	 * @return
	 */
	Optional<UserDto> authenticate(AuthDto userDto, User dbUser);

	/**
	 * Update the credentials
	 * 
	 * @param userDto
	 * @param user TODO
	 * @return
	 */
	Optional<UserDto> updateCredentials(AuthDto userDto, User user);

	/**
	 * To Reset User Credentials
	 * 
	 * @param user
	 * @return
	 */
	Optional<UserDto> resetUser(User user);

	/**
	 * Returns Optional of User by searching with email
	 * 
	 * @param email
	 * @return
	 */
	Optional<User> getUserByEmailId(String email);

	UserResponse getUserResponseFromUserDto(UserDto authenticatedUser);

}
