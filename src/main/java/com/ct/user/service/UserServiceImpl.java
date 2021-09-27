package com.ct.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.user.exception.RoleNotFoundException;
import com.ct.user.exception.auth.EmailIdNotRegisteredException;
import com.ct.user.exception.auth.PasswordNotVerifiedException;
import com.ct.user.model.FinalVariables;
import com.ct.user.model.Patient;
import com.ct.user.model.Role;
import com.ct.user.model.Staff;
import com.ct.user.model.User;
import com.ct.user.model.UserDto;
import com.ct.user.repo.UserRepository;

import lombok.extern.java.Log;

@Service
@Log
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RolesService rolesService;

	@Override
	public List<UserDto> getAllUserFromPatient(List<Patient> patients) {
		log.info("INSIDE getAllUserFromPatient");

		return patients.stream().map(patient -> {
			UserDto user = new UserDto();
			user.setTitle(patient.getTitle());
			user.setFirstName(patient.getFirstName());
			user.setLastName(patient.getLastName());
			user.setEmail(patient.getEmail());
			user.setRoleId(4);
			return user;
		}).collect(Collectors.toList());
	}

	@Override
	public List<UserDto> getAllUserFromStaff(List<Staff> staffs) {
		log.info("INSIDE getAllUserFromStaff");

		return staffs.stream().map(patient -> {
			UserDto user = new UserDto();
			user.setTitle(patient.getTitle());
			user.setFirstName(patient.getFirstName());
			user.setLastName(patient.getLastName());
			user.setEmail(patient.getEmail());
			user.setRoleId(patient.getRoleId());
			return user;
		}).collect(Collectors.toList());
	}

	@Override
	public List<UserDto> getAllUsers() {
		log.info("INSIDE getAllUsers");

		return userRepository.allUsers().stream().map(this::mapUserToUserDto).collect(Collectors.toList());
	}

	private UserDto mapUserToUserDto(User user) throws RoleNotFoundException {
		log.info("INSIDE mapUserToUserDto");

		UserDto dto = new UserDto();

		dto.setUserId(user.getUserId());
		dto.setTitle(user.getTitle());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		dto.setAttempt(user.getAttempt());
		dto.setBirthDate(user.getBirthDate());

		int roleId = FinalVariables.PATIENT;
		if (user instanceof Staff) {
			dto.setEmpId(((Staff) user).getEmpId());
			roleId = ((Staff) user).getRoleId();
		}

		Role role = rolesService.getRole(roleId).orElseThrow(RoleNotFoundException::new);
		dto.setRoleName(role.getRoleName());

		dto.setRoleId(roleId);

		return dto;
	}

	@Override
	public Optional<UserDto> authenticate(UserDto userDto, User user) {
		log.info("INSIDE authenticate");

		UserDto responseUserDto = null;
		Integer attempt = user.getAttempt();

		// If Incorrect Password or Attempt exceeds
		if (!userDto.getPassword().equals(user.getPassword()) || user.getAttempt() >= FinalVariables.MAX_ATTEMPT) {

			// If incorrect increment attempt and persist
			attempt += 1;
			user.setAttempt(attempt);

			// Setting only limited Details
			responseUserDto = new UserDto();
			responseUserDto.setEmail(user.getEmail());
			responseUserDto.setAttempt(attempt);

		} else if (userDto.getPassword().equals(user.getPassword())) {
			// Check Password is correct

			if (user.getAttempt() >= 0 && user.getAttempt() <= FinalVariables.MAX_ATTEMPT) {
				// If login successful then reset Attempt
				user.setAttempt(0);
			}

			responseUserDto = this.mapUserToUserDto(user);
		}

		userRepository.save(user);

		return Optional.of(responseUserDto);
	}

	@Override
	public Optional<User> getUserByEmailId(String email) throws EmailIdNotRegisteredException {
		log.info("INSIDE getUserByEmailId");
		if (email == null || email.trim().isEmpty()) {
			throw new EmailIdNotRegisteredException("Blank Email Id Provided");
		}
		return userRepository.findByEmailId(email);
	}

	@Override
	public Optional<UserDto> resetUser(User user) {
		log.info("INSIDE resetUser");

		// Generating token or otp to login

		// Sending mail with link to forget

		UserDto responseUserDto = this.mapUserToUserDto(user);

		return Optional.of(responseUserDto);
	}

	@Override
	public Optional<UserDto> updateCredentials(UserDto userDto, User user) throws PasswordNotVerifiedException {
		log.info("INSIDE updateCredentials");

		String newPassoword = userDto.getNewPassword();
		String currentDbPassword = user.getPassword();

		if (user.getAttempt() != -1) {

			if (userDto.getOldPassword() == null || userDto.getOldPassword().trim().isEmpty()) {
				throw new PasswordNotVerifiedException("Old Password is Blank");
			}
			// To Verify that old password is matching other wise don't change password
			// To verify old password is matching with user entered old password
			if (!userDto.getOldPassword().equals(currentDbPassword)) {
//				return Optional.empty();
				throw new PasswordNotVerifiedException("Old Password is not matching");
			}

			// new Password should not match old password
			if (newPassoword.equals(currentDbPassword)) {
//				return Optional.empty();
				throw new PasswordNotVerifiedException("New Password is Same as Old Password");
			}
		}

		user.setPassword(newPassoword);
		user.setAttempt(0); // To Reset Attempt when password update
		user = userRepository.save(user);

		UserDto responseUserDto = this.mapUserToUserDto(user);

		return Optional.of(responseUserDto);
	}

}