package com.ct.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.user.model.Patient;
import com.ct.user.repo.PatientRepository;

import lombok.extern.java.Log;

@Service
@Log
public class PatientServiceImpl extends UserServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public Patient save(Patient patient) {
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

//		newPatient.setActive(true);

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
	public Patient updatePatient(Patient patient, Patient dbPatient) {
		log.info("Inside updatePatient");

//		dbPatient.setPatientId(id);
		dbPatient.setTitle(patient.getTitle());
		dbPatient.setFirstName(patient.getFirstName());
		dbPatient.setLastName(patient.getLastName());
		dbPatient.setEmail(patient.getEmail());
		dbPatient.setBirthDate(patient.getBirthDate());

		patientRepository.save(dbPatient);
		return dbPatient;
	}

	@Override
	public void disablePatient(Patient dbPatient) {
		log.info("Inside disablePatient");

		dbPatient.setDeleted(true);
//		dbPatient.setActive(false);

		patientRepository.save(dbPatient);

	}
}
