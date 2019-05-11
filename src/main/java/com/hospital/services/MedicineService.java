package com.hospital.services;

import java.util.List;

import javax.transaction.Transactional;

import com.hospital.dao.IMedicineDao;
import com.hospital.model.Medicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MedicineService
 */
@Service
public class MedicineService implements IMedicineService {

	@Autowired
	private IMedicineDao dao;

	@Override
	@Transactional
	public void saveOrUpdate(Medicine medicine) {

		if (medicine.getConsecutive() == null || findById(medicine.getConsecutive())==null) {
			dao.persist(medicine);
		} else {
			dao.merge(medicine);
		}

	}

	@Override
	@Transactional
	public void delete(Medicine medicine) {

		Medicine m = dao.findById(medicine.getConsecutive());
		if (m != null) {
			dao.remove(m);

		} else {
			throw new IllegalStateException("entity doesn't exists in db");
		}
	}

	@Override
	@Transactional
	public Medicine findById(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional
	public List<Medicine> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional
	public List<Medicine> findByName(String name) {
		return dao.findByName(name);
	}

	@Override
	@Transactional
	public List<Medicine> findByGenericName(String genericName) {
		return dao.findByGenericName(genericName);
	}

	@Override
	@Transactional
	public List<Medicine> findByLaboratory(String laboratory) {
		return dao.findByLaboratory(laboratory);
	}

	@Override
	@Transactional
	public List<Medicine> findByAdministrationType(String administrationType) {
		return dao.findByAdministrationType(administrationType);
	}

	@Override
	@Transactional
	public List<Medicine> findByQuantityLessThan(Long quantity) {
		
		return dao.findByQuantityLessThan(quantity);
	}

}