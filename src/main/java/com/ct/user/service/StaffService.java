package com.ct.user.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.ct.user.model.Staff;
import com.ct.user.model.UserDto;

public interface StaffService extends UserService {

	/**
	 * Create new staff model, set that model and persist in Database
	 * 
	 * @param newStaff
	 * @return
	 */
	Staff save(UserDto newStaff);

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
	Staff updateStaff(UserDto updatedStaff, Staff dbStaff);

	/**
	 * Disable and Deactivate the Staff
	 * 
	 * @param dbStaff
	 */
	void disableStaff(Staff dbStaff);

	List<Long> getStaffCount();

	void editStaffStatus(List<Staff> employeeList);

	List<Staff> getAllPhysicians();

	Map<String, Object> getAllEmployeeDetails(Pageable paging);

	Map<String, Object> getFilteredEmployeeDetails(String filterValue, Pageable paging);

	List<Staff> getAllActiveEmployees();
	
	Staff getEmployeeId(Integer id);

}
