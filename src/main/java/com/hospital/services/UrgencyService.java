package com.hospital.services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import com.hospital.dao.IUrgencyAttentionDao;
import com.hospital.model.UrgencyAttention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UrgencyService
 */
@Service
public class UrgencyService implements IUrgencyService {

	@Autowired
	private IUrgencyAttentionDao dao;

	@Override
	@Transactional
	public void saveOrUpdate(UrgencyAttention urgencyAttention) {
		if (urgencyAttention.getConsecutive() == null || findById(urgencyAttention.getConsecutive()) == null) {
			dao.persist(urgencyAttention);
		} else {
			dao.merge(urgencyAttention);
		}

	}

	@Override
	@Transactional
	public UrgencyAttention findById(Long id) {

		return dao.findById(id);
	}

	@Override
	@Transactional
	public List<UrgencyAttention> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional
	public List<UrgencyAttention> findByPatient(String document) {
		return dao.findByPatient(document);
	}

	@Override
	@Transactional
	public void delete(UrgencyAttention urgency) {
		UrgencyAttention u =dao.findById(urgency.getConsecutive());
		if ( u!= null) {
			dao.remove(u);
		}else {
			throw new IllegalStateException("entity doesn't exist in db");
		}
	}

	@Override
	@Transactional
	public List<UrgencyAttention> findBetweenDates(LocalDate since, LocalDate until) {
		return dao.findBetweenDates(since, until);
	}

}