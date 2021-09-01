package com.ct.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.model.Staff;
import com.ct.user.service.StaffService;

@RestController
public class StaffController {

	@Autowired
	private StaffService staffService;
	
	@GetMapping("/staffs")
	public List<Staff> all(){
		return staffService.getAllStaffDetails();
	}
	
	@PostMapping("/staffs")
	public Staff newStaff(@RequestBody Staff staff) {
		return staffService.save(staff);
	}
	
	@GetMapping("/staffs/{id}")
	public Staff one(@PathVariable long id) {
		return staffService.getStaff(id);
	}
	
	@PutMapping("/staffs/{id}")
	public Staff replaceStaff(@PathVariable long id, @RequestBody Staff staff) {
		return staffService.updateStaff(id, staff);
	}
	
	@DeleteMapping("/staffs/{id}")
	public void removeStaff(@PathVariable long id) {
		staffService.disableStaff(id);
	}
	

	
}
