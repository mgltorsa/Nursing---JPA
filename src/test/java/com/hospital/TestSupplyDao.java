package com.hospital;

import static org.junit.Assert.assertEquals;
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
