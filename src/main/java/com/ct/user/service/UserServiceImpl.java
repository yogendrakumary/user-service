package com.ct.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	private UserDto mapUserToUserDto(User user) {
		log.info("INSIDE mapUserToUserDto");

		List<Role> roles = rolesService.getAllRoles();

		UserDto dto = new UserDto();

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

		Role role = roles.get(roleId - 1);

		dto.setRoleName(role.getRoleName());
		dto.setRoleId(roleId);

		return dto;
	}

	@Override
	public Optional<UserDto> authenticate(UserDto userDto) {
		log.info("INSIDE authenticate");

		if (userDto != null && !userDto.getEmail().isBlank() && !userDto.getPassword().isBlank()) {
			Optional<User> optional = userRepository.authenticate(userDto.getEmail(), userDto.getPassword());

			if (optional.isPresent()) {
				User user = optional.get();
				UserDto responseUserDto = this.mapUserToUserDto(user);
				log.info("USER : " + user);
				log.info("Type Of Object : " + user.getClass());
				return Optional.of(responseUserDto);
			}
		}

		return Optional.empty();
	}

	@Override
	public Optional<UserDto> resetUser(UserDto userDto) {
		log.info("INSIDE resetUser");

		if (userDto != null && !userDto.getEmail().isBlank()) {
			Optional<User> optional = userRepository.findByEmailId(userDto.getEmail());
			if (optional.isPresent()) {

				// Generating token or otp to login

				// Sending mail with link to forget

				User user = optional.get();
				UserDto responseUserDto = this.mapUserToUserDto(user);

				return Optional.of(responseUserDto);
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<UserDto> updateCredentials(UserDto userDto) {
		log.info("INSIDE updateCredentials");

		if (userDto == null)
			return Optional.empty();

		if (!userDto.getEmail().isBlank() && !userDto.getNewPassword().isBlank()) {
			Optional<User> optional = userRepository.findByEmailId(userDto.getEmail());

			if (optional.isPresent()) {
				User user = optional.get();
				UserDto responseUserDto = this.mapUserToUserDto(user);

				String newPassoword = userDto.getNewPassword();
				String oldPassword = user.getPassword();

				// To Verify that old password is matching other wise don't change password
				if (!userDto.getOldPassword().isBlank()) {

					// To verify old password is matching with user entered old password
					if (!userDto.getOldPassword().equals(oldPassword)) {
						return Optional.empty();
					}

					// new Password should not match old password
					if (newPassoword.equals(oldPassword)) {
						return Optional.empty();
					}
				}
				user.setPassword(newPassoword);

				userRepository.save(user);

				return Optional.of(responseUserDto);
			}
		}
		return Optional.empty();
	}
};