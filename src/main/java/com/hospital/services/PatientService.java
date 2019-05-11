package com.hospital.services;

import java.util.List;

import javax.transaction.Transactional;

import com.hospital.dao.IPatientDao;
import com.hospital.model.Patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/**
 * PatientService
 */
@Service
public class PatientService implements IPatientService {

	@Autowired
	private IPatientDao dao;

	@Override
	@Transactional
	public void saveOrUpdate(Patient patient) {

		if (patient.getDocument() == null) {
			throw new IllegalArgumentException("entity id must be declared");
		}

		if (findByDocument(patient.getDocument()) == null) {
			dao.persist(patient);
		} else {
			dao.merge(patient);
		}

	}

	@Override
	@Transactional
	public void delete(Patient patient) {
		Patient p = dao.findByDocument(patient.getDocument());
		if (p != null) {
			dao.remove(p);
		} else {
			throw new IllegalStateException("entity doesn't exist in db");
		}
	}

	@Override
	@Transactional
	public List<Patient> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional
	public List<Patient> findByName(String names) {
		return dao.findByName(names);
	}

	@Override
	@Transactional
	public List<Patient> findByLastName(String lastnames) {
		return dao.findByLastName(lastnames);
	}

	@Override
	@Transactional
	public Patient findByDocument(String document) {
		return dao.findByDocument(document);
	}

	@Override
	@Transactional
	public List<Pair<Patient, Integer>> findAllPatientsByDocument() {
		return dao.findAllPatientsByDocument();
	}

	@Override
	@Transactional
	public List<Patient> findByAttentionsMoreThanLastMonth(Long quantity) {
		return dao.findByAttentionsMoreThanLastMonth(quantity);
	}

}