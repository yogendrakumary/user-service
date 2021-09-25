package com.ct.user.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.exception.StaffNotFoundException;
import com.ct.user.exception.auth.EmailIdAlreadyRegisteredException;
import com.ct.user.model.Staff;
import com.ct.user.model.User;
import com.ct.user.service.StaffService;

import lombok.extern.java.Log;

@RestController
@CrossOrigin(origins = "*")
@Log
public class StaffController {

	@Autowired
	private StaffService staffService;

	/**
	 * Returns all Employee Details
	 * 
	 * @return
	 */
	@GetMapping("/employees")
	public List<Staff> all() {
		log.info("INSIDE all()");

		return staffService.getAllStaffDetails();
	}

	/**
	 * Validating Data and Sending to DB
	 * 
	 * @param newStaff
	 * @return
	 */
	@PostMapping("/employees")
	public ResponseEntity<?> newStaff(@Valid @RequestBody Staff newStaff) {
		log.info("INSIDE newStaff()");

		// Need to verify email is already exists if exists send them user exists with
		// email id, you can forget
		staffService.getUserByEmailId(newStaff.getEmail()).ifPresent(s -> {
			throw new EmailIdAlreadyRegisteredException();
		});

		Staff dbStaff = staffService.save(newStaff);

		return ResponseEntity.ok(dbStaff);
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
	public ResponseEntity<?> replaceStaff(@PathVariable long id, @RequestBody Staff staff) {
		log.info("INSIDE replaceStaff()");

		Staff dbstaff = staffService.getStaff(id).orElseThrow(() -> new StaffNotFoundException(id));
		dbstaff = staffService.updateStaff(staff, dbstaff);

		return ResponseEntity.ok(dbstaff);
	}

	/**
	 * Set the Deactivate Status
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> removeStaff(@PathVariable long id) {
		log.info("INSIDE removeStaff()");

		Staff dbstaff = staffService.getStaff(id).orElseThrow(() -> new StaffNotFoundException(id));
		staffService.disableStaff(dbstaff);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/employee/employeecount")
	List<Long> employeeCount() {
		return staffService.getStaffCount();
	}

}
