package com.hospital;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
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
import com.hospital.model.Patient;
import com.hospital.model.Supply;
import com.hospital.services.IMedicineService;
import com.hospital.services.IPatientService;
import com.hospital.services.ISupplyService;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
@Log4j2
public class TestSupplyDao {

	@Autowired
	private ISupplyService supplyService;

	@Autowired
	private IPatientService patientService;

	@Autowired
	private IMedicineService medicineService;

	private Patient patient;

	private Medicine medicine;

	private Supply supply;

	private InventaryMedicine inventary;

	@Before
	public void init() {

		// CREATING OBJECTS

		patient = new Patient("123", "Miguel", "Torres");

		medicine = new Medicine(123l, "medicine", "generic-name-1", "laboratory", "indications");

		supply = new Supply(medicine, 20, patient, LocalDate.now(), "pathology");

		inventary = new InventaryMedicine(medicine, 10, "ubication", LocalDate.now());

		// CREATING RELATIONSHIPS
		medicine.getInventaries().add(inventary);
		medicine.getSupplies().add(supply);

		patient.getSupplies().add(supply);

		medicineService.saveOrUpdate(medicine);
		patientService.saveOrUpdate(patient);

//		funciona y trae suministros con sus ids : 
		supply = patientService.findByDocument(patient.getDocument()).getSupplies().get(0);
	}

	
	@Test
	public void testSave() {
		Supply oldSupply = new Supply(medicine, 2, patient, LocalDate.now(), "pathology-1");

		supplyService.saveOrUpdate(oldSupply);

		Supply newSupply = supplyService.findById(oldSupply.getConsecutive());

		supplyService.delete(oldSupply);

		assertNotNull(newSupply);
		assertEquals(oldSupply.getConsecutive(), newSupply.getConsecutive());
		assertEquals(oldSupply.getDate(), newSupply.getDate());
		assertEquals(oldSupply.getMedicine().getConsecutive(), newSupply.getMedicine().getConsecutive());
		assertEquals(oldSupply.getPathology(), newSupply.getPathology());
		assertEquals(oldSupply.getPatient().getDocument(), newSupply.getPatient().getDocument());

	}
	
	@Test
	public void initTestMerge() {
		
		Supply oldSupply = supply;
		oldSupply.setObservations("observations-1");
		supplyService.saveOrUpdate(oldSupply);

		Supply newSupply = supplyService.findById(oldSupply.getConsecutive());
		
		assertNotNull(newSupply);
		assertEquals("observations-1", newSupply.getObservations());
		assertEquals(oldSupply.getConsecutive(), newSupply.getConsecutive());
		assertEquals(oldSupply.getDate(), newSupply.getDate());
		assertEquals(oldSupply.getMedicine().getConsecutive(), newSupply.getMedicine().getConsecutive());
		assertEquals(oldSupply.getPathology(), newSupply.getPathology());
		assertEquals(oldSupply.getPatient().getDocument(), newSupply.getPatient().getDocument());
	}

	@Test
	public void testFindById() {
		Supply expected = supply;
		Supply actual = supplyService.findById(expected.getConsecutive());
		assertEquals(expected.getConsecutive(), actual.getConsecutive());
		assertEquals(expected.getDate(), actual.getDate());
		assertEquals(expected.getMedicine().getConsecutive(), actual.getMedicine().getConsecutive());
		assertEquals(expected.getPathology(), actual.getPathology());
		assertEquals(expected.getPatient().getDocument(), actual.getPatient().getDocument());

	}

	@Test
	public void testFindByMedicine() {
		List<Supply> actual = supplyService.findByMedicineName(supply.getMedicine().getName());
		
		assertNotNull(actual);
		assertNotEquals(0, actual);
		assertTrue(actual.stream().anyMatch(expected -> 
		
		expected.getConsecutive().equals(supply.getConsecutive()) &&
		expected.getDate().equals(supply.getDate()) &&
		expected.getMedicine().getConsecutive().equals(supply.getMedicine().getConsecutive())&&
		expected.getPathology().equals(supply.getPathology())&&
		expected.getPatient().getDocument().equals(supply.getPatient().getDocument())

				
		));
		
		
	}

	@Test
	public void testFindAll() {

		String debug = "findAll \n";

		List<Supply> supplies = supplyService.findAll();

		for (Supply supply : supplies) {

			debug += supply + "\n";
		}

		log.info(debug);
	}

	@Test
	public void testSupplyBetweenQuantity() {

		String debug = "by quantity \n";

		List<Supply> supplies = supplyService.findByQuantityRange(10, 30);

		for (Supply supply : supplies) {

			debug += supply + "\n";
		}

		log.info(debug);

		assertNotNull(supplies);
		assertEquals(1, supplies.size());
		assertTrue(supplies.stream()
				.anyMatch(sup -> supply.getConsecutive().equals(sup.getConsecutive())
						&& supply.getMedicine().getConsecutive().equals(sup.getMedicine().getConsecutive())
						&& supply.getDate().equals(sup.getDate()) && supply.getPathology().equals(sup.getPathology())

				));

	}

	@After
	public void destroy() {
		supplyService.delete(supply);
	}

}
