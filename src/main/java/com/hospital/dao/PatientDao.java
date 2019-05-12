package com.hospital.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import com.hospital.model.Patient;

@Repository
public class PatientDao implements IPatientDao {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	@Override
	public Patient persist(Patient patient) {
		entityManager.persist(patient);
		return patient;
	}

	@Override
	public Patient merge(Patient patient) {
		return entityManager.merge(patient);
	}

	@Override
	public Patient detach(Patient patient) {
		entityManager.detach(patient);
		return patient;
	}

	@Override
	public Patient remove(Patient patient) {
		entityManager.remove(patient);
		return patient;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> findAll() {
		String q = "select p from Patient p";
		Query query = entityManager.createQuery(q);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> findByName(String names) {
		String q = "select p from Patient p where p.names = :names";
		Query query = entityManager.createQuery(q);
		query.setParameter("names", names);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> findByLastName(String lastnames) {
		String q = "select p from Patient p where p.lastnames = :lastNames";
		Query query = entityManager.createQuery(q);
		query.setParameter("lastNames", lastnames);
		return query.getResultList();
	}

	@Override
	public Patient findByDocument(String document) {
		return entityManager.find(Patient.class, document);
	}

	@Override
	public List<Pair<Patient, Integer>> findAllPatientsByDocument() {
		List<Patient> patients = findAll();
		List<Pair<Patient, Integer>> list = new ArrayList<>();
		for (Patient p : patients) {
			list.add(Pair.of(p, p.getAttentions().size()));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> findByAttentionsMoreThanLastMonth(Long quantity) {		
		
		LocalDate lastMoth = LocalDate.now().minusDays(30);
		String q = "select t from Patient t where ( select count(u) from UrgencyAttention u where u.patient.document = t.document and u.date between :lastMonth and :currentDate ) > :quantity";
		Query query = entityManager.createQuery(q);
		query.setParameter("lastMonth", lastMoth);
		query.setParameter("currentDate", LocalDate.now());
		query.setParameter("quantity", quantity);
		
		return query.getResultList();
	}

}
