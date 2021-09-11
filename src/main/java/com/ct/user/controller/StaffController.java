package com.ct.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.assembler.StaffAssemebler;
import com.ct.user.model.Staff;
import com.ct.user.service.StaffService;

@RestController
@CrossOrigin(origins = "*")
public class StaffController {

	@Autowired
	private StaffService staffService;

	@Autowired
	private StaffAssemebler assemebler;

	@GetMapping("/employees")
	public CollectionModel<EntityModel<Staff>> all() {
		List<EntityModel<Staff>> staffs = staffService.getAllStaffDetails().stream().map(assemebler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(staffs,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StaffController.class).all()).withSelfRel());
	}

	@PostMapping("/employees")
	public ResponseEntity<?> newStaff(@RequestBody Staff staff) {
		EntityModel<Staff> entityModel = assemebler.toModel(staffService.save(staff));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@GetMapping("/employees/{id}")
	public EntityModel<Staff> one(@PathVariable long id) {
		Staff staff = staffService.getStaff(id);

		return assemebler.toModel(staff);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<?> replaceStaff(@PathVariable long id, @RequestBody Staff staff) {

		EntityModel<Staff> entityModel = assemebler.toModel(staffService.updateStaff(id, staff));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> removeStaff(@PathVariable long id) {
		staffService.disableStaff(id);

		return ResponseEntity.noContent().build();
	}

}
