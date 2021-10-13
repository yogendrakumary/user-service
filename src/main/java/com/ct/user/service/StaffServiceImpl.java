package com.ct.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.user.model.Staff;
import com.ct.user.model.UserDto;
import com.ct.user.repo.StaffRepository;
import com.ct.user.repo.StaffRepositoryImpl;
import com.ct.user.utility.EmailServiceImpl;

import lombok.extern.java.Log;

@Service
@Log
public class StaffServiceImpl extends UserServiceImpl implements StaffService {

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private StaffRepositoryImpl customStaffRepo;

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
		log.info("Inside getAllStaffDetails");

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

}
