package com.hospital.services;

import java.util.List;

import com.hospital.model.Patient;

import org.springframework.data.util.Pair;

/**
 * IPatientService
 */
public interface IPatientService {

	public void saveOrUpdate(Patient patient);

	public void delete(Patient patient);

	public List<Patient> findAll();

	// NOTE: Punto 1a)
	public List<Patient> findByName(String names);

	public List<Patient> findByLastName(String lastnames);

	public Patient findByDocument(String document);

	// NOTE: Punto 2a)
	public List<Pair<Patient, Integer>> findAllPatientsByDocument();

	// NOTE: Punto 2c)
	public List<Patient> findByAttentionsMoreThanLastMonth(Long quantity);
}