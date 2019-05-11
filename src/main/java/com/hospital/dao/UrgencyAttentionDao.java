package com.hospital.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospital.model.UrgencyAttention;

@Repository
public class UrgencyAttentionDao implements IUrgencyAttentionDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public UrgencyAttention persist(UrgencyAttention attention) {
		entityManager.persist(attention);
		return attention;
	}

	@Override
	public UrgencyAttention merge(UrgencyAttention attention) {
		return entityManager.merge(attention);
	}

	@Override
	public UrgencyAttention detach(UrgencyAttention attention) {
		entityManager.detach(attention);
		return attention;
	}

	@Override
	public UrgencyAttention remove(UrgencyAttention attention) {
		entityManager.remove(attention);
		return attention;
	}

	@Override
	public UrgencyAttention findById(Long id) {
		return entityManager.find(UrgencyAttention.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UrgencyAttention> findAll() {
		String q = "select u from UrgencyAttention u";
		Query query = entityManager.createQuery(q);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UrgencyAttention> findByPatient(String document) {
		String q = "select u from UrgencyAttention u where u.patient.document = :document";
		Query query = entityManager.createQuery(q);
		query.setParameter("document", document);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UrgencyAttention> findBetweenDates(LocalDate since, LocalDate until) {
		String q = "select u from UrgencyAttention u where u.date between :since and :until";
		Query query = entityManager.createQuery(q);
		query.setParameter("since", since);
		query.setParameter("until", until);
		return query.getResultList();
	}

}
