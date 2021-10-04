package com.ct.user.service;

import java.util.List;
import java.util.Optional;

import com.ct.user.model.Patient;
import com.ct.user.model.UserDto;

public interface PatientService extends UserService {

	/**
	 * Returns All the patients
	 * 
	 * @return
	 */
	List<Patient> getAllPatients();

	/**
	 * Copy local Data to new model and persist that
	 * 
	 * @param patient
	 * @return
	 */
	Patient save(UserDto patient);

	/**
	 * Returns Patient From By Searching with User ID
	 * 
	 * @param id
	 * @return
	 */
	Optional<Patient> getPatient(long id);

	/**
	 * Update patient details with updated one
	 * 
	 * @param patient
	 * @param dbPatient
	 * @return
	 */
	Patient updatePatient(UserDto patient, Patient dbPatient);

	/**
	 * To change status of patient
	 * 
	 * @param dbPatient
	 */
	void disablePatient(Patient dbPatient);

	List<Long> getPatientCount();

	Patient editPatientStatus(Patient patient);

}
