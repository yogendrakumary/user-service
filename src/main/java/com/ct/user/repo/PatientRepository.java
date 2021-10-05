package com.ct.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ct.user.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{
	@Query(value ="SELECT COUNT(*) FROM patient_details p WHERE p.status = :status", nativeQuery = true)
	long countByStatus(@Param("status") String status);
}
