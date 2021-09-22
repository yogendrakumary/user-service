package com.ct.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ct.user.model.Patient;

public interface PatientRepositoryImpl extends JpaRepository<Patient, Long> {
		
		@Query(value ="SELECT COUNT(*) FROM patient_details p WHERE p.status = :status", nativeQuery = true)
		long countByStatus(@Param("status") String status);
		
	}