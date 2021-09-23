package com.ct.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ct.user.model.Staff;

public interface StaffRepositoryImpl extends JpaRepository<Staff, Long> {
	@Query(value ="SELECT COUNT(*) FROM staff_details p WHERE p.status = :status", nativeQuery = true)
	long countByStatus(@Param("status") String status);
}