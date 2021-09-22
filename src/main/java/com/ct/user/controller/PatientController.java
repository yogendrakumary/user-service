package com.ct.user.controller;

import java.util.List;

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

import com.ct.user.model.Patient;
import com.ct.user.service.PatientService;

/**
 * Mapping for Patient
 * 
 * @author YogendrakumarY
 *
 */
@RestController
@CrossOrigin(origins = "*")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@GetMapping("/patients")
	public List<Patient> all() {
		List<Patient> patients = patientService.getAllPatients();
		return patients;
	}

	@PostMapping("/patients")
	public Patient newPatient(@RequestBody Patient patient) {
		return patientService.save(patient);
	}

	@GetMapping("/patients/{id}")
	public Patient one(@Valid @PathVariable long id) {
		return patientService.getPatient(id);
	}

	@PutMapping("/patients/{id}")
	public Patient replacePatient(@PathVariable long id, @RequestBody Patient patient) {
		return patientService.updatePatient(id, patient);
	}

	@DeleteMapping("/patients/{id}")
	void deletePatient(@PathVariable long id) {
		patientService.disablePatient(id);
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
