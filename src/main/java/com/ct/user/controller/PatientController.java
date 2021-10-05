package com.ct.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.constant.Messages;
import com.ct.user.exception.PatientNotFoundException;
import com.ct.user.exception.auth.EmailIdAlreadyRegisteredException;
import com.ct.user.model.Patient;
import com.ct.user.model.UserDto;
import com.ct.user.model.validation.PatientInfo;
import com.ct.user.response.ResponseModel;
import com.ct.user.service.PatientService;

import lombok.extern.java.Log;

/**
 * Request Mapping for Patient
 * 
 * @author YogendrakumarY
 *
 */
@RestController
@CrossOrigin(origins = "*")
@Log
public class PatientController {

	@Autowired
	private PatientService patientService;

	/**
	 * Return All Patients
	 * 
	 * @return
	 */
	@GetMapping("/patients")
	public List<Patient> all() {
		log.info("INSIDE all()");

		return patientService.getAllPatients();
	}

	/**
	 * Validating and Persisting Patients Details
	 * 
	 * @param newPatient
	 * @return
	 */
	@PostMapping("/patients")
	public ResponseEntity<ResponseModel> newPatient(
			@Validated(value = PatientInfo.class) @RequestBody UserDto newPatient) {
		log.info("INSIDE newPatient");

		// Need to verify email is already exists if exists send them user exists with
		// email id, you can forget
		patientService.getUserByEmailId(newPatient.getEmail()).ifPresent(u -> {
			throw new EmailIdAlreadyRegisteredException();
		});

		patientService.save(newPatient);

		return ResponseEntity.ok(new ResponseModel(Messages.PATIENT_ADDED_SUCCESSFULLY));
	}

	/**
	 * Based on User ID getting patientDetails
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/patients/{id}")
	public Patient one(@PathVariable long id) {
		log.info("INSIDE one()");

		return patientService.getPatient(id).orElseThrow(() -> new PatientNotFoundException(id));
	}

	/**
	 * Update Patient Details passed with ID and Details
	 * 
	 * @param id
	 * @param patient
	 * @return
	 */
	@PutMapping("/patients/{id}")
	public ResponseEntity<String> replacePatient(@PathVariable long id, @RequestBody UserDto updatedPatient) {
		log.info("INSIDE replacePatient");

		Patient dbPatient = patientService.getPatient(id).orElseThrow(() -> new PatientNotFoundException(id));
		patientService.updatePatient(updatedPatient, dbPatient);

		return ResponseEntity.ok("Patient Details Updated Successfully");
	}

	/**
	 * To Deactive or disable patient
	 * 
	 * @param id
	 */
	@DeleteMapping("/patients/{id}")
	public void deletePatient(@PathVariable long id) {

		Patient dbPatient = patientService.getPatient(id).orElseThrow(() -> new PatientNotFoundException(id));

		patientService.disablePatient(dbPatient);
	}

	@GetMapping("/patients/patientcount")
	public ResponseEntity<?> patientCount() {
		try {
			return new ResponseEntity<>(patientService.getPatientCount(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("patient/editstatus")
<<<<<<< HEAD
	public ResponseEntity<?> editPatientStatus(@RequestBody List<Patient> patientList ){
		log.info("Inside User service Controller to edit status");
		try {
			patientService.editPatientStatus(patientList);
			return new ResponseEntity<Patient>(HttpStatus.OK);
=======
	public ResponseEntity<?> editPatientStatus(@RequestBody Patient patient) {

		try {
			return new ResponseEntity<>(patientService.editPatientStatus(patient), HttpStatus.OK);
>>>>>>> bb6f76fa02ffc7d60a1c4ee7a38ee3b13c6e9a80
		}

		catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
