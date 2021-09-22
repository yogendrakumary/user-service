package com.ct.user.service;

import java.util.List;

import com.ct.user.model.Patient;

public interface PatientService extends UserService {

	List<Patient> getAllPatients();

	Patient save(Patient patient);

	Patient getPatient(long id);

	Patient updatePatient(long id, Patient patient);

	void disablePatient(long id);

	List<Long> getPatientCount();

	Patient editPatientStatus(Patient patient);

}
