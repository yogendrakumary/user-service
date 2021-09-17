package com.ct.user.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.ct.user.controller.PatientController;
import com.ct.user.model.Patient;

@Component
public class PatientAssembler implements RepresentationModelAssembler<Patient, EntityModel<Patient>> {

	@Override
	public EntityModel<Patient> toModel(Patient patient) {

		return EntityModel.of(patient,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PatientController.class).one(patient.getUserId()))
						.withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PatientController.class).all())
						.withRel("patients"));
	}

}
