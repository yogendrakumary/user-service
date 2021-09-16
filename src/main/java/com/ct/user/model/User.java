package com.ct.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@MappedSuperclass
@Entity
//@Immutable
@NoArgsConstructor
//@Table(name = "vw_user_from_patient_staff")
@Table(name = "users")
@Data
@ToString
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	@Column(length = 5)
	private String title;

	@Column(length = 50)
	private String firstName;

	@Column(length = 50)
	private String lastName;

	@Column(length = 50)
	private String email;
	private java.sql.Date birthDate;

	private String contactNo;

	private String password;

	@Column(length = 2)
	@ColumnDefault(value = "0")
	private Integer attempt;

	private boolean deleted;

	private boolean active;

	@CreationTimestamp
	private java.util.Date createdOn;

	@UpdateTimestamp
	private java.util.Date updatedOn;
}
