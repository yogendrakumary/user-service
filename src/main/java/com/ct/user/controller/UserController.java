package com.ct.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

//	@Autowired
//	private PatientService patientService;
//
//	@Autowired
//	private StaffService staffService;

	@Autowired
	private UserService userService;

//	@GetMapping("/users")
//	public List<User> all() {

//		List<Patient> patients = patientService.getAllPatients();
//		List<Staff> staffs = staffService.getAllStaffDetails();
//
//		List<User> users = new ArrayList<>();
//
//		users.addAll(userService.getAllUserFromPatient(patients));
//		users.addAll(userService.getAllUserFromStaff(staffs));

//		return userService.getAllUsers();
//	}
}
