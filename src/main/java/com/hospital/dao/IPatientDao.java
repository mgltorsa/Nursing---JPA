package com.hospital.dao;

import java.util.List;

import org.springframework.data.util.Pair;

import com.hospital.model.Patient;

public interface IPatientDao {

	public Patient persist(Patient patient);
	public Patient merge(Patient patient);
	public Patient detach(Patient patient);
	public Patient remove(Patient patient);
	

	public List<Patient> findAll();	

	//NOTE: Punto 1a)
	public List<Patient> findByName(String names);
	public List<Patient> findByLastName(String lastnames);
	public Patient findByDocument(String document);

	//NOTE: Punto 2a)
	public List<Pair<Patient, Integer>> findAllPatientsByDocument();

	//NOTE: Punto 2c)
	public List<Patient> findByAttentionsMoreThanLastMonth(Long quantity);
}
