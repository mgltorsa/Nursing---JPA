package com.hospital.services;

import java.util.List;

import javax.transaction.Transactional;

import com.hospital.dao.IInventaryDao;
import com.hospital.model.InventaryMedicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * InventaryService
 */
@Service
public class InventaryService implements IInventaryService {

	@Autowired
	private IInventaryDao dao;

	@Override
	@Transactional
	public void save(InventaryMedicine inventaryMedicine) {

		if (inventaryMedicine.getId() == null) {
			dao.persist(inventaryMedicine);
		} else {
			throw new IllegalArgumentException("entity with id");
		}
	}

	@Override
	@Transactional
	public InventaryMedicine findById(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional
	public List<InventaryMedicine> findByMedicine(Long id) {
		return dao.findByMedicine(id);
	}

	@Override
	@Transactional
	public void delete(InventaryMedicine inventary) {
		InventaryMedicine inv = dao.findById(inventary.getId());
		if (inv != null) {
			dao.remove(inv);
		}else {
			throw new IllegalStateException("the entity doesn't exists in db");
		}
	}

}