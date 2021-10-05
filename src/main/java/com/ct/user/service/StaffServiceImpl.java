package com.ct.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.user.model.Patient;
import com.ct.user.model.Staff;
import com.ct.user.repo.StaffRepository;

import lombok.extern.java.Log;

@Service
@Log
public class StaffServiceImpl extends UserServiceImpl implements StaffService {

	@Autowired
	private StaffRepository staffRepository;


	@Override
	public Staff save(Staff staff) {
		log.info("Inside save");

		// TO fethc last empId
		Integer lastEmpId = staffRepository.getLastEmployeeId().orElse(0);

		Staff newStaff = new Staff();

		newStaff.setTitle(staff.getTitle());
		newStaff.setFirstName(staff.getFirstName());
		newStaff.setLastName(staff.getLastName());
		newStaff.setEmail(staff.getEmail());
		newStaff.setRoleId(staff.getRoleId());
		newStaff.setBirthDate(staff.getBirthDate());

		newStaff.setPassword(staff.getPassword());
		newStaff.setEmpId(lastEmpId + 1);

		newStaff.setAttempt(-1);

		newStaff = staffRepository.save(newStaff);

		return newStaff;
	}

	@Override
	public List<Staff> getAllStaffDetails() {
		log.info("Inside getAllStaffDetails");

		return staffRepository.findAll();
	}

	@Override
	public Optional<Staff> getStaff(long id) {
		log.info("Inside getStaff");

		return staffRepository.findById(id);
	}

	@Override
	public Staff updateStaff(Staff updatedStaff, Staff dbStaff) {
		log.info("Inside updateStaff");

		dbStaff.setFirstName(updatedStaff.getFirstName());
		dbStaff.setLastName(updatedStaff.getLastName());
		dbStaff.setEmail(updatedStaff.getEmail());

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
		for (Long count : countList) {
			log.info(count.toString());
		}
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
		}
		
	}

	@Override
	public List<Staff> getAllPhysicians() {
		return staffRepository.findAllByRoleId(2);
	}

}
