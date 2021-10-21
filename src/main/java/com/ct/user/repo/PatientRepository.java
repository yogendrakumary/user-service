package com.ct.user.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ct.user.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{
	@Query(value ="SELECT COUNT(*) FROM patient_details p WHERE p.status = :status", nativeQuery = true)
	long countByStatus(@Param("status") String status);

//	@Query(value = "select * from patient_details p where p.userId=:filterValue OR p.firstName=:filterValue OR p.lastName=:filterValue OR p.createdOn=:filterValue OR p.status=:filterValue")
//	Page<Patient> findAll(Pageable paging,@Param("filterValue") String filterValue);
}
