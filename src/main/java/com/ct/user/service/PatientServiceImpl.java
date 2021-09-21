package com.ct.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.user.exception.PatientNotFoundException;
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
	public Patient getPatient(long id) {
		log.info("Inside getPatient");
		return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
	}

	@Override
	public Patient updatePatient(long id, Patient patient) {
		log.info("Inside updatePatient");

		Patient dbPatient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
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
	public void disablePatient(long id) {
		log.info("Inside disablePatient");

		Patient dbPatient = this.getPatient(id);

		dbPatient.setDeleted(true);
//		dbPatient.setActive(false);

		patientRepository.save(dbPatient);

	}
}
