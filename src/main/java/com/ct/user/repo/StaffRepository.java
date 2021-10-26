package com.ct.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public Page<Staff> findAll(Pageable pageble);

	@Query(value = "SELECT * FROM staff_details "
			+ "WHERE to_tsvector(coalesce(staff_details.user_id\\:\\:text)) \\@\\@ plainto_tsquery(?1) OR "
			+ "staff_details.first_name ILIKE %?1% OR "
			+ "staff_details.last_name ILIKE %?1% OR "
			+ "to_tsvector(coalesce(staff_details.created_on\\:\\:text)) \\@\\@ plainto_tsquery(?1) OR "
			+ "staff_details.status ILIKE %?1%", nativeQuery = true)
	public Page<Staff> findAll(String filterValue, Pageable paging);


	public List<Staff> findAllByStatus(String string);


	/*
	 * @Query(value = "SELECT email from staff_details", nativeQuery = true) public
	 * List<String> findAllEmail();
	 * 
	 * @Query(value="SELECT email from staff_details WHERE status =:status"
	 * ,nativeQuery = true) public List<String> findAllByStatus(@Param("status")
	 * String string);
	 */
	
	/*
	 * "SELECT * FROM patient_details " +
	 * "WHERE to_tsvector(coalesce(patient_details.user_id\\:\\:text)) \\@\\@ plainto_tsquery('?1') OR "
	 * + "patient_details.first_name ILIKE %?1% OR " +
	 * "patient_details.last_name ILIKE %?1% OR " +
	 * "to_tsvector(coalesce(patient_details.created_on\\:\\:text)) \\@\\@ plainto_tsquery('?1') OR "
	 * + "patient_details.status ILIKE %?1%",
	 */

}
