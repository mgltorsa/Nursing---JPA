package com.hospital.dao;

import java.time.LocalDate;
import java.util.List;

import com.hospital.model.UrgencyAttention;

public interface IUrgencyAttentionDao {

	public UrgencyAttention persist(UrgencyAttention attention);
	public UrgencyAttention merge(UrgencyAttention attention);
	public UrgencyAttention detach(UrgencyAttention attention);
	public UrgencyAttention remove(UrgencyAttention attention);
	
	public UrgencyAttention findById(Long id);
	
	public List<UrgencyAttention> findAll();
	public List<UrgencyAttention> findByPatient(String document);

	//NOTE: Punto 2b)
	public List<UrgencyAttention> findBetweenDates(LocalDate since, LocalDate until);
	
}
