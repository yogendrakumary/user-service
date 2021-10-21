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
import com.ct.user.repo.PatientRepository;

import com.ct.user.utility.EmailServiceImpl;


import lombok.extern.java.Log;

@Service
@Log
public class PatientServiceImpl extends UserServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@Override
	public Patient save(UserDto patient) {
		log.info("INSIDE save");
		Patient newPatient = new Patient();

		newPatient.setTitle(patient.getTitle());
		newPatient.setFirstName(patient.getFirstName());
		newPatient.setLastName(patient.getLastName());
		newPatient.setEmail(patient.getEmail());
		newPatient.setBirthDate(patient.getBirthDate());
		newPatient.setPassword(patient.getPassword());
		newPatient.setContactNo(patient.getContactNo());

		newPatient.setAttempt(0);

		newPatient = patientRepository.save(newPatient);

		String subject = "Welcome to CT General Hospital!";
		String body = String.format("Thanks for registering at CT General Hospital\r\n "
				+ "Hi, %s Thank you for creating your account at CT General hospital.Your accounts details as follows:\r\n "
				+ "Email Address : %s \r\n " + "Password :[The Password you specified]\r\n "
				+ "To Sign in to your account, please visit https://localhost:8080/ or Click here. \r\n\r\n "
				+ "CT General Hospital", newPatient.getFirstName(), newPatient.getEmail());

		emailServiceImpl.sendSimpleMessage(newPatient.getEmail(), subject, body);

		return newPatient;
	}

	@Override
	public List<Patient> getAllPatients() {
		log.info("Inside getAllPatients");
		return patientRepository.findAll();
	}

	@Override
	public Optional<Patient> getPatient(long id) {
		log.info("Inside getPatient");
		return patientRepository.findById(id);
	}

	@Override
	public Patient updatePatient(UserDto patient, Patient dbPatient) {
		log.info("Inside updatePatient");

		dbPatient.setTitle(patient.getTitle());
		dbPatient.setFirstName(patient.getFirstName());
		dbPatient.setLastName(patient.getLastName());
		dbPatient.setBirthDate(patient.getBirthDate());

		patientRepository.save(dbPatient);
		return dbPatient;
	}

	@Override
	public void disablePatient(Patient dbPatient) {
		log.info("Inside disablePatient");

		dbPatient.setDeleted(true);

		patientRepository.save(dbPatient);

	}

	@Override
	public List<Long> getPatientCount() {
		long totalPatient = patientRepository.count();

		long activePatient = patientRepository.countByStatus("active");
		long deactivePatient = patientRepository.countByStatus("deactive");
		deactivePatient += patientRepository.countByStatus("block");
		
		List<Long> countList = new ArrayList<>();
		countList.add(totalPatient);
		countList.add(activePatient);
		countList.add(deactivePatient);
		
		return countList;
	}



	@Override
	public void editPatientStatus(List<Patient> patientList) {
		Patient obj = new Patient();
		log.info("Inside User Service Mehod to edit status");
		for (Patient patient : patientList) {
			 obj = patientRepository.getById(patient.getUserId());
				obj.setUserId(patient.getUserId());
				obj.setStatus(patient.getStatus());	
				patientRepository.save(obj);
		}
	}

	@Override
	public Map<String, Object> getAllFilteredPatientDetails(Pageable paging) {
		Page<Patient> pagePatient = patientRepository.findAll(paging);
		List<Patient> patients = pagePatient.getContent();
		Map<String, Object> response = new HashMap<>();
		response.put("content", patients);
		response.put("currentPage", pagePatient.getNumber());
		response.put("totalItems", pagePatient.getTotalElements());
		response.put("totalPages", pagePatient.getTotalPages());
		response.put("last", pagePatient.isLast());
		response.put("first", pagePatient.isFirst());
		response.put("sort", pagePatient.getSort());
		response.put("numberOfElements", pagePatient.getNumberOfElements());
		response.put("number", pagePatient.getNumber());
		response.put("empty", pagePatient.isEmpty());
		response.put("totalElements", pagePatient.getTotalElements());
		response.put("page", pagePatient.getNumber());
		response.put("size", pagePatient.getSize());
		
		
		return response;
	}

}
