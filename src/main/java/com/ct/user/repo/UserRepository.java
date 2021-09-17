package com.ct.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ct.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(nativeQuery = true, value = "select * from vw_user_from_patient_staff")
	public List<User> allUsers();

//	@Query(nativeQuery = true, value = "select * from users where email = :email and password = :password")
//	public Optional<User> authenticate(@Param("email") String email, @Param("password") String password);

	@Query(value = "select u from User u where email = :email and password = :password")
	public Optional<User> authenticate(@Param("email") String email, @Param("password") String password);

	@Query(value = "select u from User u where email = :email ")
	public Optional<User> findByEmailId(@Param("email") String email);
}
