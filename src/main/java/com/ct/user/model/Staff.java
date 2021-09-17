package com.ct.user.model;

import javax.persistence.Entity;
import javax.persistence.Table;

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
	private int roleId;
	private int empId;

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
