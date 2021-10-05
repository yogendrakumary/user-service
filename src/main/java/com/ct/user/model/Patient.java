package com.ct.user.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "patient_details")
public class Patient extends User {

	private String race;
	private String ethnicity;
	private String languages;
	private String address;

}