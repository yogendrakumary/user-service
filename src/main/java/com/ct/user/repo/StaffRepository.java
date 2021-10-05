package com.ct.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ct.user.model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

	@Query(value = "select emp_id from staff_details order by created_on desc limit 1;", nativeQuery = true)
	public Optional<Integer> getLastEmployeeId();

	
	public List<Staff> findAllByRoleId(int i);
	
	@Query(value ="SELECT COUNT(*) FROM staff_details  WHERE status = :status", nativeQuery = true)
	long countByStatus(@Param("status") String status);

}
