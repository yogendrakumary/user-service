package com.ct.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		return patientService.getAllPatients();
	}

	@PostMapping("/patients")
	public Patient newPatient(@RequestBody Patient patient) {
		return patientService.save(patient);
	}

	@GetMapping("/patients/{id}")
	public Patient one(@PathVariable long id) {
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
}
