package com.ct.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ct.user.model.Patient;
import com.ct.user.model.Staff;
import com.ct.user.model.UserDto;
import com.ct.user.repo.StaffRepository;


import com.ct.user.utility.EmailServiceImpl;


import lombok.extern.java.Log;

@Service
@Log
public class StaffServiceImpl extends UserServiceImpl implements StaffService {

	@Autowired
	private StaffRepository staffRepository;


	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@Override
	public Staff save(UserDto staff) {
		log.info("Inside save");

		// TO fethc last empId
		Integer lastEmpId = staffRepository.getLastEmployeeId().orElse(0);

		String otp = "Welcome@123";

		Staff newStaff = new Staff();

		newStaff.setTitle(staff.getTitle());
		newStaff.setFirstName(staff.getFirstName());
		newStaff.setLastName(staff.getLastName());
		newStaff.setEmail(staff.getEmail());
		newStaff.setRoleId(staff.getRoleId());
		newStaff.setBirthDate(staff.getBirthDate());

		newStaff.setPassword(otp);
		newStaff.setEmpId(lastEmpId + 1);

		newStaff.setAttempt(-1);

		newStaff = staffRepository.save(newStaff);

		String subject = "Welcome to CT General Hospital!";
		String body = String.format("Thanks for registering at CT General Hospital\r\n "
				+ "Hi, %s Thank you for creating your account at CT General hospital.Your accounts details as follows:\r\n "
				+ "Email Address : %s \r\n " + "One Time Password : %s\r\n "
				+ "Sign with this One Time Password and Reset Account\r\n"
				+ "To Sign in to your account, please visit https://localhost:8080/ or Click here. \r\n\r\n "
				+ "CT General Hospital", newStaff.getFirstName(), newStaff.getEmail(), otp);

		emailServiceImpl.sendSimpleMessage(newStaff.getEmail(), subject, body);

		return newStaff;
	}
	@Override
	public List<Staff> getAllStaffDetails() {
		return staffRepository.findAll();
	}

	@Override
	public Optional<Staff> getStaff(long id) {
		log.info("Inside getStaff");

		return staffRepository.findById(id);
	}

	@Override
	public Staff updateStaff(UserDto updatedStaff, Staff dbStaff) {
		log.info("Inside updateStaff");

		dbStaff.setTitle(updatedStaff.getTitle());
		dbStaff.setFirstName(updatedStaff.getFirstName());
		dbStaff.setLastName(updatedStaff.getLastName());
		dbStaff.setBirthDate(updatedStaff.getBirthDate());
		dbStaff.setRoleId(updatedStaff.getRoleId());
		dbStaff.setEmpId(updatedStaff.getEmpId());

		return staffRepository.save(dbStaff);
	}

	@Override
	public void disableStaff(Staff dbStaff) {
		log.info("Inside disableStaff");

		dbStaff.setDeleted(true);
		//		dbStaff.setActive(false);

		staffRepository.save(dbStaff);
	}

	@Override
	public List<Long> getStaffCount() {
		long totalEmployee = staffRepository.count();
		long activeEmployee = staffRepository.countByStatus("active");
		long deactiveEmployee = staffRepository.countByStatus("deactive");
		deactiveEmployee += staffRepository.countByStatus("block");

		List<Long> countList = new ArrayList<>();
		countList.add(totalEmployee);
		countList.add(activeEmployee);
		countList.add(deactiveEmployee);

		return countList;
	}

	@Override
	public void editStaffStatus(List<Staff> employeeList) {
		Staff obj = new Staff();
		log.info("Inside User Service Mehod to edit Employee status");
		for (Staff staff : employeeList) {
			obj = staffRepository.getById(staff.getUserId());
			obj.setUserId(staff.getUserId());
			obj.setStatus(staff.getStatus());	
			staffRepository.save(obj);
			editStatusEmail(obj);
		}

	}

	@Override
	public List<Staff> getAllPhysicians() {
		return staffRepository.findAllByRoleId(2);
	}

	public void editStatusEmail(Staff user) {
		String accountStatus = "";

		if(user.getStatus().equals("active"))
			accountStatus= "ACTIVATED";

		else if(user.getStatus().equals("deactive"))
			accountStatus= "DEACTIVATED";

		else if(user.getStatus().equals("block"))
			accountStatus= "BLOCKED";

		String subject ="YOUR ACCOUNT IS "+accountStatus+" !";

		String body = String.format(""
				+ "Hello"+user.getFirstName()+",\n"
				+ "This is an acknowledgement mail for your account with CITY GENERAL HOSPITAL "
				+ "We wanted to let you know that your account status has been changed\r\n "
				+ "Your account Status - "+accountStatus+"\n");
				
				if(user.getStatus().equals("active")) {
				body += "Sign in to your account, ";
				body += "please visit https://localhost:4200/ or Click here.\n";
				}
				body += "To see this and other security events for your account, please contact to admin by visiting to hospital";
				
		emailServiceImpl.sendSimpleMessage(user.getEmail(), subject, body);

	}
	@Override
	public Map<String, Object> getAllEmployeeDetails(Pageable paging) {
		Page<Staff> pageStaff = staffRepository.findAll(paging);
		List<Staff> staffs = pageStaff.getContent();
		Map<String, Object> response = new HashMap<>();
		response.put("content", staffs);
		response.put("currentPage", pageStaff.getNumber());
		response.put("totalItems", pageStaff.getTotalElements());
		response.put("totalPages", pageStaff.getTotalPages());
		response.put("last", pageStaff.isLast());
		response.put("first", pageStaff.isFirst());
		response.put("sort", pageStaff.getSort());
		response.put("numberOfElements", pageStaff.getNumberOfElements());
		response.put("number", pageStaff.getNumber());
		response.put("empty", pageStaff.isEmpty());
		response.put("totalElements", pageStaff.getTotalElements());
		response.put("page", pageStaff.getNumber());
		response.put("size", pageStaff.getSize());
		
		
		return response;

	}
	@Override
	public Map<String, Object> getFilteredEmployeeDetails(String filterValue, Pageable paging) {
		Page<Staff> pageStaff = staffRepository.findAll(filterValue,paging);
		log.info(filterValue);
		List<Staff> staffs = pageStaff.getContent();
		Map<String, Object> response = new HashMap<>();
		response.put("content", staffs);
		response.put("currentPage", pageStaff.getNumber());
		response.put("totalItems", pageStaff.getTotalElements());
		response.put("totalPages", pageStaff.getTotalPages());
		response.put("last", pageStaff.isLast());
		response.put("first", pageStaff.isFirst());
		response.put("sort", pageStaff.getSort());
		response.put("numberOfElements", pageStaff.getNumberOfElements());
		response.put("number", pageStaff.getNumber());
		response.put("empty", pageStaff.isEmpty());
		response.put("totalElements", pageStaff.getTotalElements());
		response.put("page", pageStaff.getNumber());
		response.put("size", pageStaff.getSize());
		return response;
	}
	
	@Override
	public List<Staff> getAllActiveEmployees() {
		return staffRepository.findAllByStatus("active");
		
	}



}
