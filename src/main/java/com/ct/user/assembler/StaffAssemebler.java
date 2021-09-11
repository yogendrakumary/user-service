package com.ct.user.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.ct.user.controller.StaffController;
import com.ct.user.model.Staff;

@Component
public class StaffAssemebler implements RepresentationModelAssembler<Staff, EntityModel<Staff>> {

	@Override
	public EntityModel<Staff> toModel(Staff staff) {

		return EntityModel.of(staff,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StaffController.class).one(staff.getStaffId()))
						.withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StaffController.class).all()).withRel("employees"));
	}

}
