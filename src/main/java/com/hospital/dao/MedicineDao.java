package com.hospital.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.hospital.model.Medicine;

@Repository
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MedicineDao implements IMedicineDao {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
	@Override
	public Medicine persist(Medicine medicine) {
		entityManager.persist(medicine);
		return medicine;
	}

	@Override
	public Medicine merge(Medicine medicine) {
		return entityManager.merge(medicine);
	}

	@Override
	public Medicine detach(Medicine medicine) {
		entityManager.detach(medicine);
		return medicine;
	}

	@Override
	public Medicine remove(Medicine medicine) {
		entityManager.remove(medicine);
		return medicine;
	}

	@Override
	public Medicine findById(Long consecutive) {
		
		return entityManager.find(Medicine.class, consecutive);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Medicine> findAll() {
		String q = "select m from Medicine m";
		Query query = entityManager.createQuery(q);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Medicine> findByName(String name) {
		String q = "select m from Medicine m where m.name = :name";
		Query query = entityManager.createQuery(q);
		query.setParameter("name", name);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Medicine> findByGenericName(String genericName) {
		String q = "select m from Medicine m where m.genericName = :genericName";
		Query query = entityManager.createQuery(q);
		query.setParameter("genericName", genericName);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Medicine> findByLaboratory(String laboratory) {
		String q = "select m from Medicine m where m.laboratory = :laboratory";
		Query query = entityManager.createQuery(q);
		query.setParameter("laboratory", laboratory);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Medicine> findByAdministrationType(String administrationType) {
		String q = "select m from Medicine m where m.administrationType = :administrationType";
		Query query = entityManager.createQuery(q);
		query.setParameter("administrationType", administrationType);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Medicine> findByQuantityLessThan(Long quantity) {
		// TODO: Consulta no funciona, suma todos los inventarios de
		// todas las medicinas
		String q = "SELECT t FROM Medicine t WHERE " + "( SELECT SUM(m.availableQuantity) "
				+ "FROM InventaryMedicine m " + "WHERE m.medicine.consecutive = t.consecutive " + ") < :quantity";

		Query query = entityManager.createQuery(q);
		query.setParameter("quantity", quantity);

		return query.getResultList();
	}

}
