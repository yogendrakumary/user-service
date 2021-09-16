package com.ct.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.user.exception.StaffNotFoundException;
import com.ct.user.model.Staff;
import com.ct.user.repo.StaffRepository;

@Service
public class StaffServiceImpl extends UserServiceImpl implements StaffService {

	@Autowired
	private StaffRepository staffRepository;

	@Override
	public Staff save(Staff staff) {

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
		return staffRepository.findAll();
	}

	@Override
	public Staff getStaff(long id) {
		return staffRepository.findById(id).orElseThrow(() -> new StaffNotFoundException());
	}

	@Override
	public Staff updateStaff(long id, Staff updatedStaff) {
		Staff dbStaff = this.getStaff(id);

		dbStaff.setFirstName(updatedStaff.getFirstName());
		dbStaff.setLastName(updatedStaff.getLastName());
		dbStaff.setEmail(updatedStaff.getEmail());

		return staffRepository.save(dbStaff);
	}

	@Override
	public void disableStaff(long id) {
		Staff dbStaff = this.getStaff(id);

		dbStaff.setDeleted(true);
//		dbStaff.setActive(false);

		staffRepository.save(dbStaff);
	}

}
