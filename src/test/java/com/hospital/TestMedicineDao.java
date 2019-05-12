package com.hospital;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hospital.model.InventaryMedicine;
import com.hospital.model.Medicine;
import com.hospital.services.IMedicineService;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
@Log4j2
public class TestMedicineDao {

	@Autowired
	private IMedicineService medicineService;

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

		medicineService.saveOrUpdate(medicine);
		medicineService.saveOrUpdate(medicine2);

	}

	@Test
	public void testSave(){

		Medicine m = new Medicine(3l, "name", "genericName", "laboratory", "indications");

		medicineService.saveOrUpdate(m);

		Medicine newMedicine = medicineService.findById(3l);

		assertEquals(m.getConsecutive(),newMedicine.getConsecutive());
		assertEquals(m.getName(),newMedicine.getName());
		assertEquals(m.getGenericName(),newMedicine.getGenericName());
		assertEquals(m.getLaboratory(),newMedicine.getLaboratory());
	}

	@Test
	public void testMerge(){
		LocalDate expirationDate = LocalDate.now().plusYears(1);
		InventaryMedicine im = new InventaryMedicine(medicine, 14, "alaska", expirationDate);
		
		Medicine medicine = medicineService.findById(1l);
		medicine.getInventaries().add(im);

		medicineService.saveOrUpdate(medicine);

		Medicine newMedicine = medicineService.findById(medicine.getConsecutive());

		assertEquals(medicine.getConsecutive(),newMedicine.getConsecutive());
		assertEquals(medicine.getName(),newMedicine.getName());
		assertEquals(medicine.getGenericName(),newMedicine.getGenericName());
		assertEquals(medicine.getLaboratory(),newMedicine.getLaboratory());

	}

	@Test
	public void testFindAll() {

		List<Medicine> medicines = medicineService.findAll();

		assertNotNull(medicines);
		assertNotEquals(medicines.size(), 0);
		assertEquals(medicines.size(), 2);
	}

	@Test
	public void testFindById() {

		Medicine medicine = medicineService.findById(this.medicine.getConsecutive());

		assertNotNull("medicine was null", medicine);
		assertEquals(this.medicine.getConsecutive(), medicine.getConsecutive());
		assertEquals(this.medicine.getName(), medicine.getName());
		assertEquals(this.medicine.getGenericName(), medicine.getGenericName());
		assertEquals(this.medicine.getLaboratory(), medicine.getLaboratory());
		assertEquals(this.medicine.getIndications(), medicine.getIndications());
	}

	@Test
	public void testFindByName() {
		List<Medicine> medicines = medicineService.findByName(medicine.getName());

		assertNotNull("medicines was null", medicines);
		assertNotNull("array of medicines was null", medicines);
		assertEquals(2, medicines.size());
		assertTrue(medicines.stream().allMatch(item -> 
		
		medicine.getName().equals(item.getName())
		
		));

	}
	
	@Test
	public void testFindByGenericName() {
		List<Medicine> medicines = medicineService.findByGenericName(medicine.getGenericName());

		assertNotNull("medicines was null", medicines);
		assertNotNull("array of medicines was null", medicines);
		assertEquals(1, medicines.size());
		assertTrue(medicines.stream().allMatch(item -> 
		
		medicine.getName().equals(item.getName())
		
		));

	}
	
	@Test
	public void testFindByLaboratory() {
		List<Medicine> medicines = medicineService.findByLaboratory(medicine.getLaboratory());

		assertNotNull("medicines was null", medicines);
		assertNotNull("array of medicines was null", medicines);
		assertEquals(2, medicines.size());
		assertTrue(medicines.stream().anyMatch(item -> 
		
		medicine.getName().equals(item.getName())
		
		));

	}
	
	

	// NOTE: Consulta punto 2b)

	@Test
	public void testQuantityLessTen() {
		List<Medicine> medicines = medicineService.findByQuantityLessThan(10l);

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

		medicineService.saveOrUpdate(medicine);

	}

	@After
	public void destroy() {
		medicineService.delete(medicine);
	}
}
