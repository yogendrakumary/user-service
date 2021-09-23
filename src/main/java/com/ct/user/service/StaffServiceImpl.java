package com.ct.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.user.exception.StaffNotFoundException;
import com.ct.user.model.Staff;
import com.ct.user.repo.StaffRepository;
import com.ct.user.repo.StaffRepositoryImpl;

import lombok.extern.java.Log;

@Service
@Log
public class StaffServiceImpl extends UserServiceImpl implements StaffService {

	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private StaffRepositoryImpl customStaffRepo;

	@Override
	public Staff save(Staff staff) {
		log.info("Inside save");

		Staff newStaff = new Staff();

		newStaff.setTitle(staff.getTitle());
		newStaff.setFirstName(staff.getFirstName());
		newStaff.setLastName(staff.getLastName());
		newStaff.setEmail(staff.getEmail());
		newStaff.setRoleId(staff.getRoleId());
		newStaff.setPassword(staff.getPassword());
		newStaff.setEmpId(staff.getEmpId());
		newStaff.setBirthDate(staff.getBirthDate());

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
	public Staff getStaff(long id) {
		log.info("Inside getStaff");

		return staffRepository.findById(id).orElseThrow(() -> new StaffNotFoundException());
	}

	@Override
	public Staff updateStaff(long id, Staff updatedStaff) {
		log.info("Inside updateStaff");

		Staff dbStaff = this.getStaff(id);

		dbStaff.setFirstName(updatedStaff.getFirstName());
		dbStaff.setLastName(updatedStaff.getLastName());
		dbStaff.setEmail(updatedStaff.getEmail());

		return staffRepository.save(dbStaff);
	}

	@Override
	public void disableStaff(long id) {
		log.info("Inside disableStaff");

		Staff dbStaff = this.getStaff(id);

		dbStaff.setDeleted(true);
//		dbStaff.setActive(false);

		staffRepository.save(dbStaff);
	}
	@Override
	public List<Long> getStaffCount() {
		long totalEmployee = staffRepository.count();
		long activeEmployee = customStaffRepo.countByStatus("active");
		long deactiveEmployee = customStaffRepo.countByStatus("deactive");
		deactiveEmployee += customStaffRepo.countByStatus("block");
		
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
		// TODO Auto-generated method stub
		
	}

}
