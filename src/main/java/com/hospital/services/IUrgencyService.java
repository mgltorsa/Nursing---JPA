package com.hospital.services;

import java.time.LocalDate;
import java.util.List;

import com.hospital.model.UrgencyAttention;

/**
 * IUrgencyService
 */
public interface IUrgencyService {

    public void saveOrUpdate(UrgencyAttention urgencyAttention);
	public List<UrgencyAttention> findAll();
	public List<UrgencyAttention> findByPatient(String document);
	public void delete(UrgencyAttention urgency);
	public UrgencyAttention findById(Long id);

	//NOTE: Punto 2b)
	public List<UrgencyAttention> findBetweenDates(LocalDate since, LocalDate until);
}