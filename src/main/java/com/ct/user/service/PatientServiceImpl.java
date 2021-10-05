package com.ct.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.user.model.Patient;
import com.ct.user.model.UserDto;
import com.ct.user.repo.PatientRepository;


import lombok.extern.java.Log;

@Service
@Log
public class PatientServiceImpl extends UserServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;
	

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

		return patientRepository.save(newPatient);
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
<<<<<<< HEAD
		long activePatient = patientRepository.countByStatus("active");
		long deactivePatient = patientRepository.countByStatus("deactive");
		deactivePatient += patientRepository.countByStatus("block");
		
=======
		long activePatient = customPatientRepo.countByStatus("active");
		long deactivePatient = customPatientRepo.countByStatus("deactive");
		deactivePatient += customPatientRepo.countByStatus("block");

>>>>>>> bb6f76fa02ffc7d60a1c4ee7a38ee3b13c6e9a80
		List<Long> countList = new ArrayList<>();
		countList.add(totalPatient);
		countList.add(activePatient);
		countList.add(deactivePatient);
		for (Long count : countList) {
			log.info(count.toString());
		}
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

}
