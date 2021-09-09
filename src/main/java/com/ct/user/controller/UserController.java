package com.ct.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.model.Patient;
import com.ct.user.model.Staff;
import com.ct.user.model.User;
import com.ct.user.service.PatientService;
import com.ct.user.service.StaffService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private PatientService patientService;

	@Autowired
	private StaffService staffService;

	@GetMapping("/users")
	public List<User> all() {

		List<Patient> patients = patientService.getAllPatients();
		List<Staff> staffs = staffService.getAllStaffDetails();

		List<User> users = new ArrayList<>();
		List<User> patientToUserList = patients.stream().map(patient -> {
			User user = new User();
			user.setTitle(patient.getTitle());
			user.setFirstName(patient.getFirstName());
			user.setLastName(patient.getLastName());
			user.setEmail(patient.getEmail());
			user.setRoleId(4);
			return user;
		}).collect(Collectors.toList());

		List<User> employeeToUserList = staffs.stream().map(patient -> {
			User user = new User();
			user.setTitle(patient.getTitle());
			user.setFirstName(patient.getFirstName());
			user.setLastName(patient.getLastName());
			user.setEmail(patient.getEmail());
			user.setRoleId(patient.getRoleId());
			return user;
		}).collect(Collectors.toList());

		users.addAll(employeeToUserList);
		users.addAll(patientToUserList);

		return users;
	}
}
