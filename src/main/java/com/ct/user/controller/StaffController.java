package com.ct.user.controller;

import java.util.List;

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

import com.ct.user.model.Patient;
import com.ct.user.model.Staff;
import com.ct.user.service.StaffService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin( "*")
@Slf4j
public class StaffController {

	@Autowired
	private StaffService staffService;

	@GetMapping("/employees")
	public List<Staff> all() {
		List<Staff> staffs = staffService.getAllStaffDetails();

		return staffs;
	}

	@PostMapping("/employees")
	public ResponseEntity<?> newStaff(@Valid @RequestBody Staff staff) {

//		if (staff.getRoleId() == null) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Problem.create().withTitle("Roles Not Defined"));
//		}

		Staff entityModel = staffService.save(staff);

//		return ResponseEntity.ok("User is valid");
		return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
	}

	@GetMapping("/employees/{id}")
	public Staff one(@PathVariable long id) {
		Staff staff = staffService.getStaff(id);

		return staff;
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<?> replaceStaff(@PathVariable long id, @RequestBody Staff staff) {

		Staff entityModel = staffService.updateStaff(id, staff);

		return ResponseEntity.status(HttpStatus.OK).body(entityModel);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> removeStaff(@PathVariable long id) {
		staffService.disableStaff(id);

		return ResponseEntity.noContent().build();
	}
	@GetMapping("/employee/employeecount")
	List<Long> employeeCount() {
		return staffService.getStaffCount();
	}
	
	/* this method is edit the employees Status 
	 * it will received the list of employees
	 * and send it the staff service
	 * */
	@PutMapping("employee/editstatus")
	public ResponseEntity<?> editStaffStatus(@RequestBody List<Staff> EmployeeList ){
		log.info("Inside User service Controller to edit status");
		try {
			staffService.editStaffStatus(EmployeeList);
			return new ResponseEntity<Patient>(HttpStatus.OK);
		}
		
		catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
