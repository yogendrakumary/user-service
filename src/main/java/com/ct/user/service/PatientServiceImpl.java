package com.ct.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.user.model.Patient;
import com.ct.user.model.UserDto;
import com.ct.user.repo.PatientRepository;
import com.ct.user.repo.PatientRepositoryImpl;
import com.ct.user.utility.EmailServiceImpl;

import lombok.extern.java.Log;

@Service
@Log
public class PatientServiceImpl extends UserServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private PatientRepositoryImpl customPatientRepo;

	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@Override
	public Patient save(UserDto patient) {
		log.info("INSIDE save");
		Patient newPatient = new Patient();

		newPatient.setTitle(patient.getTitle());
		newPatient.setFirstName(patient.getFirstName());
		newPatient.setLastName(patient.getLastName());
		newPatient.setEmail(patient.getEmail());
		newPatient.setBirthDate(patient.getBirthDate());
		newPatient.setPassword(patient.getPassword());
		newPatient.setContactNo(patient.getContactNo());

		newPatient.setAttempt(0);

		newPatient = patientRepository.save(newPatient);

		String subject = "Welcome to CT General Hospital!";
		String body = String.format("Thanks for registering at CT General Hospital\r\n "
				+ "Hi, %s Thank you for creating your account at CT General hospital.Your accounts details as follows:\r\n "
				+ "Email Address : %s \r\n " + "Password :[The Password you specified]\r\n "
				+ "To Sign in to your account, please visit https://localhost:8080/ or Click here. \r\n\r\n "
				+ "CT General Hospital", newPatient.getFirstName(), newPatient.getEmail());

		emailServiceImpl.sendSimpleMessage(newPatient.getEmail(), subject, body);

		return newPatient;
	}

	@Override
	public List<Patient> getAllPatients() {
		log.info("Inside getAllPatients");
		return patientRepository.findAll();
	}

	@Override
	public Optional<Patient> getPatient(long id) {
		log.info("Inside getPatient");
		return patientRepository.findById(id);
	}

	@Override
	public Patient updatePatient(UserDto patient, Patient dbPatient) {
		log.info("Inside updatePatient");

		dbPatient.setTitle(patient.getTitle());
		dbPatient.setFirstName(patient.getFirstName());
		dbPatient.setLastName(patient.getLastName());
		dbPatient.setBirthDate(patient.getBirthDate());

		patientRepository.save(dbPatient);
		return dbPatient;
	}

	@Override
	public void disablePatient(Patient dbPatient) {
		log.info("Inside disablePatient");

		dbPatient.setDeleted(true);

		patientRepository.save(dbPatient);

	}

	@Override
	public List<Long> getPatientCount() {
		long totalPatient = patientRepository.count();
		long activePatient = customPatientRepo.countByStatus("active");
		long deactivePatient = customPatientRepo.countByStatus("deactive");
		deactivePatient += customPatientRepo.countByStatus("block");

		List<Long> countList = new ArrayList<>();
		countList.add(totalPatient);
		countList.add(activePatient);
		countList.add(deactivePatient);
		for (Long count : countList) {
			log.info(count.toString());
		}
		return countList;
	}

	@Override
	public Patient editPatientStatus(Patient patient) {
		log.info("Patient Status edited....!");
		patientRepository.save(patient);
		return patientRepository.getById(patient.getUserId());
	}

}
