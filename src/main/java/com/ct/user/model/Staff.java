package com.ct.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "staff_details")
public class Staff extends User {

	@Column(nullable = false)
	private Integer roleId;

	private Integer empId;

}
