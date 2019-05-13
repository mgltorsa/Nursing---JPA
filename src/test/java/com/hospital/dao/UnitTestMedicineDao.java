package com.hospital.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hospital.dao.IMedicineDao;
import com.hospital.model.InventaryMedicine;
import com.hospital.model.Medicine;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
@Log4j2
public class UnitTestMedicineDao {

	@Autowired
	private IMedicineDao dao;	
	
	private Medicine medicine;

	private Medicine medicine2;

	@Before
	public void init() {

		LocalDate date = LocalDate.parse("2019-01-02", DateTimeFormatter.ISO_LOCAL_DATE);

		medicine = new Medicine(1l, "medicine", "generic-name-1", "laboratory", "indicators");

		medicine2 = new Medicine(2l, "medicine", "generic-name-2", "lab", "indicators");

		InventaryMedicine inventary = new InventaryMedicine(medicine, 5, "ubication", date);
		InventaryMedicine inventary2 = new InventaryMedicine(medicine2, 10, "ubication", date);

		medicine.getInventaries().add(inventary);
		medicine2.getInventaries().add(inventary2);

		dao.persist(medicine);
		dao.persist(medicine2);
		
		
				

	}
	
	@Test
	@Transactional
	public void testSave(){

		Medicine m = new Medicine(3l, "name", "genericName", "laboratory", "indications");

		dao.persist(m);

		Medicine newMedicine = dao.findById(3l);

		assertEquals(m.getConsecutive(),newMedicine.getConsecutive());
		assertEquals(m.getName(),newMedicine.getName());
		assertEquals(m.getGenericName(),newMedicine.getGenericName());
		assertEquals(m.getLaboratory(),newMedicine.getLaboratory());
	}
	
	@Test
	@Transactional
	public void testFindById() {

		Medicine medicine = dao.findById(this.medicine.getConsecutive());

		assertNotNull("medicine was null", medicine);
		assertEquals(this.medicine.getConsecutive(), medicine.getConsecutive());
		assertEquals(this.medicine.getName(), medicine.getName());
		assertEquals(this.medicine.getGenericName(), medicine.getGenericName());
		assertEquals(this.medicine.getLaboratory(), medicine.getLaboratory());
		assertEquals(this.medicine.getIndications(), medicine.getIndications());
	}
	
	@Test
	@Transactional
	public void testFindAll() {

		List<Medicine> medicines = dao.findAll();

		assertNotNull(medicines);
		assertNotEquals(medicines.size(), 0);
		assertEquals(medicines.size(), 2);
	}
	
	

	@Test
	@Transactional
	public void testFindByName() {
		List<Medicine> medicines = dao.findByName(medicine.getName());

		assertNotNull("medicines was null", medicines);
		assertNotNull("array of medicines was null", medicines);
		assertEquals(2, medicines.size());
		assertTrue(medicines.stream().allMatch(item -> 
		
		medicine.getName().equals(item.getName())
		
		));

	}
	
	@Test
	@Transactional
	public void testFindByGenericName() {
		List<Medicine> medicines = dao.findByGenericName(medicine.getGenericName());

		assertNotNull("medicines was null", medicines);
		assertNotNull("array of medicines was null", medicines);
		assertEquals(1, medicines.size());
		assertTrue(medicines.stream().allMatch(item -> 
		
		medicine.getName().equals(item.getName())
		
		));

	}
	
	@Test
	@Transactional
	public void testFindByLaboratory() {
		List<Medicine> medicines = dao.findByLaboratory(medicine.getLaboratory());

		assertNotNull("medicines was null", medicines);
		assertNotNull("array of medicines was null", medicines);
		assertEquals(2, medicines.size());
		assertTrue(medicines.stream().anyMatch(item -> 
		
		medicine.getName().equals(item.getName())
		
		));

	}
	
	

	// NOTE: Consulta punto 2b)

	@Test
	@Transactional
	public void testQuantityLessTen() {
		List<Medicine> medicines = dao.findByQuantityLessThan(10l);

		String debug = "less:\n";

		for (Medicine medicine : medicines) {
			debug += medicine + "\n";
		}

		log.info(debug);

		assertNotNull("array of medicines was null", medicines);
		assertEquals(String.format("size of array medicines was %s", medicines.size()), 1, medicines.size());
		assertTrue(medicines.stream().allMatch(item -> 
		
		medicine.getName().equals(item.getName())
		
		));

		dao.persist(medicine);

	}


	
	@After
	public void destroy() {
		dao.remove(medicine);
		dao.remove(medicine2);
	}
}
