package com.hospital.dao;

import java.util.List;

import com.hospital.model.InventaryMedicine;

public interface IInventaryDao {

	
	public InventaryMedicine persist(InventaryMedicine inventary);
	public InventaryMedicine merge(InventaryMedicine inventary);
	public InventaryMedicine detach(InventaryMedicine inventary);
	public InventaryMedicine remove(InventaryMedicine inventary);
	
	public boolean contains(InventaryMedicine inventary);
	
	public InventaryMedicine findById(Long id);
	public List<InventaryMedicine> findByMedicine(Long id);
	
}
