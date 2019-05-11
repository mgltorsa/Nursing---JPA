package com.hospital.dao;


import java.util.List;

import com.hospital.model.Medicine;

public interface IMedicineDao {

	
	public Medicine persist(Medicine medicine);
	public Medicine merge(Medicine medicine);
	public Medicine detach(Medicine medicine);
	public Medicine remove(Medicine medicine);
	
	public Medicine findById(Long consecutive);
	public List<Medicine> findAll();
	public List<Medicine> findByName(String name);
	public List<Medicine> findByGenericName(String genericName);
	public List<Medicine> findByLaboratory(String laboratory);
	public List<Medicine> findByAdministrationType(String administrationType);
	
	
	public List<Medicine> findByQuantityLessThan(Long quantity);
	
}
