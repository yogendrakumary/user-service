package com.ct.user.service;

import java.util.List;

import com.ct.user.model.Staff;

public interface StaffService extends UserService {

	Staff save(Staff staff);

	List<Staff> getAllStaffDetails();

	Staff getStaff(long id);

	Staff updateStaff(long id, Staff updatedStaff);

	void disableStaff(long id);
	List<Long> getStaffCount();

	void editStaffStatus(List<Staff> employeeList);

}
