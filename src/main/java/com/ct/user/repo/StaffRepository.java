package com.ct.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ct.user.model.Patient;
import com.ct.user.model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long>{

}
