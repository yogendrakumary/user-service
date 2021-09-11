package com.ct.user.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Immutable
@Table(name = "vw_user_from_patient_staff")
@ToString
public class User {

	@Id
	private long userId;
	private String title;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	@Transient
	private String password;
	@Transient
	private String oldPassword;
	@Transient
	private String newPassword;
	private int roleId;
}
