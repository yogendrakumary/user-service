package com.ct.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "staff_details")
public class Staff extends User {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "staff_id")
//	private long staffId;

	@Column(nullable = false)
//	@NotNull(message = "Please select a valid role.")
	private Integer roleId;

//	@SequenceGenerator(name = "empSeq", sequenceName = "emp_sequences	 ", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empSeq")
	private Integer empId;

//	private String title;
//	private String firstName;
//	private String lastName;
//	private String email;
//	private Date birthDate;

//	private String username;
//	private String password;
//	private boolean deleted;
//	private boolean active;

//	@CreationTimestamp
//	private java.util.Date createdOn;
//
//	@UpdateTimestamp
//	private java.util.Date updatedOn;
}
