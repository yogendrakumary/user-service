package com.ct.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ct.user.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{

}
