package com.ct.user.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ct.user.model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

	@Query(value = "select emp_id from staff_details order by created_on desc limit 1;", nativeQuery = true)
	public Optional<Integer> getLastEmployeeId();

}
