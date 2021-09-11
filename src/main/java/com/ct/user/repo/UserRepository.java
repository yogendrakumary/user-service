package com.ct.user.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ct.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(nativeQuery = true, value = "select * from vw_user_from_patient_staff")
	public List<User> allUsers();

	@Query(nativeQuery = true, value = "select * from vw_user_from_patient_staff where email = :email and password = :password")
	public User authenticate(@Param("email") String email, @Param("password") String password);
}
