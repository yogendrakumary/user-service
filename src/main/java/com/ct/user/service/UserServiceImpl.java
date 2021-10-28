package com.ct.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ct.user.constant.FinalVariables;
import com.ct.user.exception.RoleNotFoundException;
import com.ct.user.exception.auth.EmailIdNotRegisteredException;
import com.ct.user.exception.auth.PasswordNotVerifiedException;
import com.ct.user.model.AuthDto;
import com.ct.user.model.Patient;
import com.ct.user.model.Role;
import com.ct.user.model.Staff;
import com.ct.user.model.User;
import com.ct.user.model.UserDto;
import com.ct.user.repo.UserRepository;
import com.ct.user.response.UserResponse;
import com.ct.user.utility.EmailServiceImpl;
import com.ct.user.utility.Utility;

import lombok.extern.java.Log;

@Service
@Log
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@Autowired
	protected BCryptPasswordEncoder passwordEncoder;

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
	public Optional<UserDto> authenticate(AuthDto authDto, User user) {
		log.info("INSIDE authenticate");

		UserDto responseUserDto = null;
		Integer attempt = user.getAttempt();

		boolean isAuthenticated = passwordEncoder.matches(authDto.getPassword(), user.getPassword());

		// If Incorrect Password or Attempt exceeds
		if (!isAuthenticated || user.getAttempt() >= FinalVariables.MAX_ATTEMPT) {

			// If incorrect increment attempt and persist
			attempt += 1;
			user.setAttempt(attempt);

			// Setting only limited Details
			responseUserDto = new UserDto();
			responseUserDto.setEmail(user.getEmail());
			responseUserDto.setAttempt(attempt);

		} else if (isAuthenticated) {
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
			throw new EmailIdNotRegisteredException("Blank email Id provided");
		}
		return userRepository.findByEmailId(email);
	}

	@Override
	public Optional<UserDto> resetUser(User user) {
		log.info("INSIDE resetUser");

		String resetPassword = Utility.generateOtp();

		user.setAttempt(-1);
		user.setPassword(resetPassword);

		String resetSubject = String.format("Reset Password - %s", user.getFirstName());
		String resetBody = String.format(
				"Dear %s, \r\n \r\n" + "Forgot your password ? \r\n"
						+ "We received a request to reset the password for your account. \r\n \r\n"
						+ "To reset your password, click on the link below : \r\nhttp://localhost:4200 \r\n \r\n"
						+ "To login, use your secret code! \r\n%s \r\n \r\n"
						+ "If you did not forget your password, you can ignore this email.",
				user.getFirstName(), resetPassword);

		// Generating token or otp to login

		// Sending mail with link to forget
		emailServiceImpl.sendSimpleMessage(user.getEmail(), resetSubject, resetBody);

		UserDto responseUserDto = this.mapUserToUserDto(user);

		return Optional.of(responseUserDto);
	}

	@Override
	public Optional<UserDto> updateCredentials(AuthDto userDto, User user) throws PasswordNotVerifiedException {
		log.info("INSIDE updateCredentials");

		String newPassoword = userDto.getNewPassword();
		String currentDbPassword = user.getPassword();

		if (user.getAttempt() != -1) {

			if (userDto.getOldPassword() == null || userDto.getOldPassword().trim().isEmpty()) {
				throw new PasswordNotVerifiedException("Old password is blank");
			}
			// To Verify that old password is matching other wise don't change password
			// To verify old password is matching with user entered old password
			if (!passwordEncoder.matches(userDto.getOldPassword(), currentDbPassword)) {
//				return Optional.empty();
				throw new PasswordNotVerifiedException("Old password is not matching");
			}

			// new Password should not match old password
			if (passwordEncoder.matches(newPassoword, currentDbPassword)) {
//				return Optional.empty();

				throw new PasswordNotVerifiedException("New password is same as old password");
			}
		}

		user.setPassword(newPassoword);
		user.setAttempt(0); // To Reset Attempt when password update
		user = userRepository.save(user);

		UserDto responseUserDto = this.mapUserToUserDto(user);

		return Optional.of(responseUserDto);
	}

	@Override
	public UserResponse getUserResponseFromUserDto(UserDto authenticatedUser) {
		UserResponse userResponse = new UserResponse();

		userResponse.setUserId(authenticatedUser.getUserId());
		userResponse.setTitle(authenticatedUser.getTitle());
		userResponse.setFirstName(authenticatedUser.getFirstName());
		userResponse.setLastName(authenticatedUser.getLastName());
		userResponse.setEmail(authenticatedUser.getEmail());
		userResponse.setBirthDate(authenticatedUser.getBirthDate());
		userResponse.setAttempt(authenticatedUser.getAttempt());
		userResponse.setRoleId(authenticatedUser.getRoleId());
		userResponse.setRoleName(authenticatedUser.getRoleName());
		userResponse.setContactNo(authenticatedUser.getContactNo());
		userResponse.setEmpId(authenticatedUser.getEmpId());

		return userResponse;
	}

}