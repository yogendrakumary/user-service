package com.ct.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.user.exception.PatientNotFoundException;
import com.ct.user.model.Patient;
import com.ct.user.repo.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public Patient save(Patient patient) {
		return patientRepository.save(patient);
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	@Override
	public Patient getPatient(long id) {
		return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
	}

	@Override
	public Patient updatePatient(long id, Patient patient) {
		Patient dbPatient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
		dbPatient.setPatientId(id);
		dbPatient.setFirstName(patient.getFirstName());
		dbPatient.setLastName(patient.getLastName());
		patientRepository.save(dbPatient);
		return dbPatient;
	}

	@Override
	public void disablePatient(long id) {
		Patient dbPatient = this.getPatient(id);

		dbPatient.setDeleted(true);
		dbPatient.setActive(false);

		patientRepository.save(dbPatient);

	}
}
