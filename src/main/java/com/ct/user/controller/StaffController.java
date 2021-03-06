package com.ct.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.constant.Messages;
import com.ct.user.exception.StaffNotFoundException;
import com.ct.user.exception.auth.EmailIdAlreadyRegisteredException;
import com.ct.user.model.Staff;
import com.ct.user.model.UserDto;
import com.ct.user.model.validation.EmployeeInfo;
import com.ct.user.response.ResponseModel;
import com.ct.user.service.StaffService;

import lombok.extern.java.Log;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/users")
@Log
public class StaffController {

	@Autowired
	private StaffService staffService;

	/**
	 * Returns all Employee Details
	 * 
	 * @return
	 */
	@GetMapping(value = "/employees")
	public ResponseEntity<?> all() {
		log.info("INSIDE all()");
		try {

			return new ResponseEntity<List<Staff>>(staffService.getAllStaffDetails(), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Validating Data and Sending to DB
	 * 
	 * @param newStaff
	 * @return
	 */
	@PostMapping(path = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> newStaff(@Validated(value = EmployeeInfo.class) @RequestBody UserDto newStaff) {
		log.info("INSIDE newStaff()");

		// Need to verify email is already exists if exists send them user exists with
		// email id, you can forget
		staffService.getUserByEmailId(newStaff.getEmail()).ifPresent(s -> {
			throw new EmailIdAlreadyRegisteredException();
		});

		staffService.save(newStaff);

		return ResponseEntity.ok(new ResponseModel(Messages.EMPLOYEE_ADDED_SUCCESSFULLY));
	}

	/**
	 * Return Staff Based on their ID
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/employees/{id}")
	public Staff one(@PathVariable long id) {
		log.info("INSIDE one()");

		return staffService.getStaff(id).orElseThrow(() -> new StaffNotFoundException(id));
	}

	/**
	 * Update Staff Details by searching with id and data send
	 * 
	 * @param id
	 * @param staff
	 * @return
	 */
	@PutMapping("/employees/{id}")
	public ResponseEntity<?> replaceStaff(@PathVariable long id, @RequestBody UserDto staff) {
		log.info("INSIDE replaceStaff()");

		Staff dbstaff = staffService.getStaff(id).orElseThrow(() -> new StaffNotFoundException(id));

		staffService.updateStaff(staff, dbstaff);

		return ResponseEntity.ok(new ResponseModel(Messages.EMPLOYEE_UPDATED_SUCCESSFULLY));
	}

	/**
	 * Set the Deactivate Status
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<String> removeStaff(@PathVariable long id) {
		log.info("INSIDE removeStaff()");

		Staff dbstaff = staffService.getStaff(id).orElseThrow(() -> new StaffNotFoundException(id));
		staffService.disableStaff(dbstaff);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/employee/employeecount")
	public List<Long> employeeCount() {
		return staffService.getStaffCount();
	}

	/*
	 * this method is edit the employees Status it will received the list of
	 * employees and send it the staff service
	 */
	@PutMapping("employee/editstatus")
	public ResponseEntity<?> editStaffStatus(@RequestBody List<Staff> employeeList) {
		log.info(employeeList.toString());
		log.info("Inside User service Controller to edit status");
		try {
			staffService.editStaffStatus(employeeList);
			return new ResponseEntity<String>("Status Edited", HttpStatus.OK);
		}

		catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("employees/physicians")
	public ResponseEntity<?> getAllPhysicians() {
		try {
			return new ResponseEntity<List<Staff>>(staffService.getAllPhysicians(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "employees/user-list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> allEmployee(

			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "userId") String columnName,
			@RequestParam(defaultValue = "ASC") String direction) {
		try {
			Sort sort = Sort.by(Sort.Direction.fromString(direction), columnName);
			Pageable paging = PageRequest.of(page, size, sort);
			log.info(paging.toString());
			return new ResponseEntity<>(staffService.getAllEmployeeDetails(paging), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "employees/filteredstaff", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> FilteredEmployee(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "ASC") String direction,
			@RequestParam(defaultValue = "") String filterValue) {
		try {
			Sort sort = Sort.by(Sort.Direction.fromString(direction), "first_name");
			Pageable paging = PageRequest.of(page, size, sort);
			return new ResponseEntity<>(staffService.getFilteredEmployeeDetails(filterValue, paging), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "employees/allactiveemployees")
	public ResponseEntity<?> getAllActiveEmployeeEmail() {
		try {
			return new ResponseEntity<List<Staff>>(staffService.getAllActiveEmployees(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employees/employeeId/{id}")
	public Staff getEmployeeId(@PathVariable Integer id) {
		return staffService.getEmployeeId(id);
	}

}
