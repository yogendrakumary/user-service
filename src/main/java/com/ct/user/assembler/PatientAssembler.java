package com.ct.user.assembler;

//@Component
//public class PatientAssembler implements RepresentationModelAssembler<Patient, EntityModel<Patient>> {
//
//	@Override
//	public EntityModel<Patient> toModel(Patient patient) {
//
//		return EntityModel.of(patient,
//				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PatientController.class).one(patient.getUserId()))
//						.withSelfRel(),
//				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PatientController.class).all())
//						.withRel("patients"));
//	}
//
//}
