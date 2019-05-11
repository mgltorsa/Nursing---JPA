package com.hospital.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import com.hospital.model.Supply;

@Repository
public class SupplyDao implements ISupplyDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Supply persist(Supply supply) {
		entityManager.persist(supply);
		return supply;
	}

	@Override
	public Supply merge(Supply supply) {
		return entityManager.merge(supply);
	}

	@Override
	public Supply detach(Supply supply) {
		entityManager.detach(supply);
		return supply;
	}

	@Override
	public Supply remove(Supply supply) {
		entityManager.remove(supply);
		return supply;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Supply> findAll() {
		String q = "select s from Supply s";
		Query query = entityManager.createQuery(q);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Supply> findByMedicineName(String name) {
		String q = "select s from Supply s where s.medicine.name = :name";
		Query query = entityManager.createQuery(q);
		query.setParameter("name", name);
		return query.getResultList();
	}

	@Override
	public Supply findById(Long consecutive) {
		return entityManager.find(Supply.class, consecutive);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Supply> findByQuantityRange(int minimum, int maximum) {
		String q = "select s from Supply s where s.quantity between :minimum and :maximum";
		Query query = entityManager.createQuery(q);
		query.setParameter("minimum", minimum);
		query.setParameter("maximum", maximum);
		return query.getResultList();
	}

}
