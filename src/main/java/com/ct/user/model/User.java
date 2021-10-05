package com.ct.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@NoArgsConstructor
@Table(name = "users")
@Data
@ToString
public class User {

	@Id
	@SequenceGenerator(name = "myKeySeq", sequenceName = "user_sequences ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myKeySeq")
	private Long userId;

	@Column(length = 5, nullable = false)
	private String title;

	@Column(length = 50, nullable = false)
	private String firstName;

	@Column(length = 50, nullable = false)
	private String lastName;

	@Column(length = 50, nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private java.sql.Date birthDate;

	private String contactNo;

	private String password;

	@Column(length = 2)
	@ColumnDefault(value = "0")
	private Integer attempt;

	private boolean deleted;

	private String status;

	@CreationTimestamp
	private java.util.Date createdOn;

	@UpdateTimestamp
	private java.util.Date updatedOn;
}
