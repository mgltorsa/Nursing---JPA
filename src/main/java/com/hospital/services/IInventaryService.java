package com.hospital.services;

import java.util.List;

import com.hospital.model.InventaryMedicine;

/**
 * IInventaryService
 */
public interface IInventaryService {

    public void save(InventaryMedicine inventaryMedicine);
	public InventaryMedicine findById(Long id);
	public List<InventaryMedicine> findByMedicine(Long id);
    public void delete(InventaryMedicine inventary);
    
}