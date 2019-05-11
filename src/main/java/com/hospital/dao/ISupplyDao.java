package com.hospital.dao;

import java.util.List;

import com.hospital.model.Supply;

public interface ISupplyDao {

	public Supply persist(Supply supply);
	public Supply merge(Supply supply);
	public Supply detach(Supply supply);
	public Supply remove(Supply supply);
	
	public List<Supply> findByMedicineName(String name);	
	public Supply findById(Long consecutive);

	//NOTE: Punto 1c)
	public List<Supply> findByQuantityRange(int minimum, int maximum);
	public List<Supply> findAll();
	
	
}
