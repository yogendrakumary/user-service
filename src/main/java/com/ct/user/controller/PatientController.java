package com.ct.user.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.exception.PatientNotFoundException;
import com.ct.user.model.Patient;
import com.ct.user.model.User;
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
	public ResponseEntity<?> newPatient(@Valid @RequestBody Patient newPatient) {
		log.info("INSIDE newPatient");

		// Need to verify email is already exists if exists send them user exists with
		// email id, you can forget
		Optional<User> optional = patientService.getUserByEmailId(newPatient.getEmail());
		if (optional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email Id Already Exist");
		}

		Patient dbPatient = patientService.save(newPatient);

		return ResponseEntity.status(HttpStatus.CREATED).body(dbPatient);
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
	public ResponseEntity<?> replacePatient(@PathVariable long id, @RequestBody Patient updatedPatient) {
		log.info("INSIDE replacePatient");

		Patient dbPatient = patientService.getPatient(id).orElseThrow(() -> new PatientNotFoundException(id));
		dbPatient = patientService.updatePatient(updatedPatient, dbPatient);

		return ResponseEntity.status(HttpStatus.CREATED).body(dbPatient);
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
	ResponseEntity<?> patientCount() {
		try {
			return new ResponseEntity<List<Long>>(patientService.getPatientCount(), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("patient/editstatus")
	public ResponseEntity<?> editPatientStatus(@RequestBody Patient patient ){
		
		try {
			return new ResponseEntity<Patient>(patientService.editPatientStatus(patient),HttpStatus.OK);
		}
		
		catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
