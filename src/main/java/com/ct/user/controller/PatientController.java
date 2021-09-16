package com.ct.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.assembler.PatientAssembler;
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

	@Autowired
	private PatientAssembler assembler;

	@GetMapping("/patients")
	public CollectionModel<EntityModel<Patient>> all() {
		List<EntityModel<Patient>> patients = patientService.getAllPatients().stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(patients,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PatientController.class).all()).withSelfRel());
	}

	@PostMapping("/patients")
	public EntityModel<Patient> newPatient(@RequestBody Patient patient) {
		return assembler.toModel(patientService.save(patient));
	}

	@GetMapping("/patients/{id}")
	public EntityModel<Patient> one(@PathVariable long id) {
		return assembler.toModel(patientService.getPatient(id));
	}

	@PutMapping("/patients/{id}")
	public EntityModel<Patient> replacePatient(@PathVariable long id, @RequestBody Patient patient) {
		return assembler.toModel(patientService.updatePatient(id, patient));
	}

	@DeleteMapping("/patients/{id}")
	void deletePatient(@PathVariable long id) {
		patientService.disablePatient(id);
	}
}
