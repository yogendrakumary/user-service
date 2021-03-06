package com.ct.user.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

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

	void editPatientStatus(List<Patient> patientList);

	Map<String, Object> getAllPatient(Pageable paging);

	Map<String, Object> getFilteredPatientDetails(String filterValue, Pageable paging);


	List<Patient> getAllActivePatients();

}
