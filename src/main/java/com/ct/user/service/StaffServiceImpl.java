package com.ct.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
