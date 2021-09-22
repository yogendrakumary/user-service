package com.ct.user.service;

import java.util.List;
import java.util.Optional;

import com.ct.user.model.Staff;

public interface StaffService extends UserService {

	/**
	 * Create new staff model, set that model and persist in Database
	 * 
	 * @param staff
	 * @return
	 */
	Staff save(Staff staff);

	/**
	 * Returns All the Staff Details
	 * 
	 * @return
	 */
	List<Staff> getAllStaffDetails();

	/**
	 * Returns Staff details by searching with ID
	 * 
	 * @param id
	 * @return
	 */
	Optional<Staff> getStaff(long id);

	/**
	 * Update the database staff model with updateded Staff model and persist in
	 * Database
	 * 
	 * @param updatedStaff
	 * @param dbStaff
	 * @return
	 */
	Staff updateStaff(Staff updatedStaff, Staff dbStaff);

	/**
	 * Disable and Deactivate the Staff
	 * 
	 * @param dbStaff
	 */
	void disableStaff(Staff dbStaff);

}
