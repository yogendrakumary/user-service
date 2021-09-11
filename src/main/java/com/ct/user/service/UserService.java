package com.ct.user.service;

import java.util.List;

import com.ct.user.model.Patient;
import com.ct.user.model.Staff;
import com.ct.user.model.User;

public interface UserService {

	List<User> getAllUserFromPatient(List<Patient> patients);

	List<User> getAllUserFromStaff(List<Staff> staffs);

	List<User> getAllUsers();

	User authenicate(User user);

	User updateCredentials(User user);

}
