package com.ct.user.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ct.user.model.Patient;
import com.ct.user.model.Staff;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{
	@Query(value ="SELECT COUNT(*) FROM patient_details p WHERE p.status = :status", nativeQuery = true)
	long countByStatus(@Param("status") String status);
	public List<Patient> findAllByStatus(String string);
	@Query(value = "SELECT * FROM patient_details "
			+ "WHERE to_tsvector(coalesce(patient_details.user_id\\:\\:text)) \\@\\@ plainto_tsquery('?1') OR "
			+ "patient_details.first_name ILIKE %?1% OR "
			+ "patient_details.last_name ILIKE %?1% OR "
			+ "to_tsvector(coalesce(patient_details.created_on\\:\\:text)) \\@\\@ plainto_tsquery('?1') OR "
			+ "patient_details.status ILIKE %?1%", nativeQuery = true)
	Page<Patient> findAll(String filterValue,Pageable paging);

	
}
