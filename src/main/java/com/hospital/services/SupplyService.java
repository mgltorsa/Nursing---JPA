package com.hospital.services;

import java.util.List;

import javax.transaction.Transactional;

import com.hospital.dao.ISupplyDao;
import com.hospital.model.Supply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SupplyService
 */
@Service
public class SupplyService implements ISupplyService {

	@Autowired
	private ISupplyDao dao;

	@Override
	@Transactional
	public void saveOrUpdate(Supply supply) {

		if (supply.getConsecutive() == null || findById(supply.getConsecutive()) != null) {
			dao.persist(supply);
		} else {
			dao.merge(supply);
		}
	}

	@Override
	@Transactional
	public void delete(Supply supply) {
		Supply s = dao.findById(supply.getConsecutive());
		if (s != null) {
			dao.remove(s);
		} else {
			throw new IllegalStateException("entity doesn't exist in db");
		}
	}

	@Override
	@Transactional
	public List<Supply> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional
	public List<Supply> findByMedicineName(String name) {
		return dao.findByMedicineName(name);
	}

	@Override
	@Transactional
	public Supply findById(Long consecutive) {
		return dao.findById(consecutive);
	}

	@Override
	@Transactional
	public List<Supply> findByQuantityRange(int minimum, int maximum) {
		return dao.findByQuantityRange(minimum, maximum);
	}

}