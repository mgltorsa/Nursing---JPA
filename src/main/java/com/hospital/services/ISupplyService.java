package com.hospital.services;

import java.util.List;

import com.hospital.model.Supply;

/**
 * ISupplyService
 */
public interface ISupplyService {

    public void saveOrUpdate(Supply supply);
	public void delete(Supply supply);
	public List<Supply> findByMedicineName(String name);	
	public Supply findById(Long consecutive);

	//NOTE: Punto 1c)
	public List<Supply> findByQuantityRange(int minimum, int maximum);
	public List<Supply> findAll();
	public void deleteAll();
    
}