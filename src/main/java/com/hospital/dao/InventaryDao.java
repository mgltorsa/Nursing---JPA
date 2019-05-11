package com.hospital.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospital.model.InventaryMedicine;

@Repository
public class InventaryDao implements IInventaryDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public InventaryMedicine persist(InventaryMedicine inventary) {
		entityManager.persist(inventary);
		return inventary;

	}

	@Override
	public InventaryMedicine merge(InventaryMedicine inventary) {
		return entityManager.merge(inventary);
	}

	@Override
	public InventaryMedicine detach(InventaryMedicine inventary) {
		entityManager.detach(inventary);
		return inventary;
	}

	@Override
	public InventaryMedicine remove(InventaryMedicine inventary) {
		entityManager.remove(inventary);
		return inventary;
	}

	@Override
	public boolean contains(InventaryMedicine inventary) {
		return entityManager.contains(inventary);
	}
	
	@Override
	public InventaryMedicine findById(Long id) {
		return entityManager.find(InventaryMedicine.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InventaryMedicine> findByMedicine(Long id) {
		String q = "select m from Medicine m where m.medicine.consecutive = :id";
		Query query = entityManager.createQuery(q);
		query.setParameter("id", id);
		return query.getResultList();
	}



	
}
