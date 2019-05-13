package com.hospital.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
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

import com.hospital.dao.ISupplyDao;
import com.hospital.model.InventaryMedicine;
import com.hospital.model.Medicine;
import com.hospital.model.Patient;
import com.hospital.model.Supply;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
@Log4j2
public class UnitTestSupplyDao {

	@Autowired
	private ISupplyDao dao;

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

		

//		funciona y trae suministros con sus ids : 
		dao.persist(supply);
	}

	
	@Test
	@Transactional
	public void testSave() {
		Supply oldSupply = new Supply(medicine, 2, patient, LocalDate.now(), "pathology-1");

		dao.persist(oldSupply);

		Supply newSupply = dao.findById(oldSupply.getConsecutive());

		assertNotNull(newSupply);
		assertEquals(oldSupply.getConsecutive(), newSupply.getConsecutive());
		assertEquals(oldSupply.getDate(), newSupply.getDate());
		assertEquals(oldSupply.getMedicine().getConsecutive(), newSupply.getMedicine().getConsecutive());
		assertEquals(oldSupply.getPathology(), newSupply.getPathology());
		assertEquals(oldSupply.getPatient().getDocument(), newSupply.getPatient().getDocument());

	}
	
	

	@Test
	@Transactional
	public void testFindById() {
		Supply actual = dao.findById(supply.getConsecutive());
		assertEquals(supply.getConsecutive(), actual.getConsecutive());
		assertEquals(supply.getDate(), actual.getDate());
		assertEquals(supply.getMedicine().getConsecutive(), actual.getMedicine().getConsecutive());
		assertEquals(supply.getPathology(), actual.getPathology());
		assertEquals(supply.getPatient().getDocument(), actual.getPatient().getDocument());

	}

	

	@Test
	@Transactional
	public void testFindAll() {

		String debug = "findAll \n";

		List<Supply> supplies = dao.findAll();

		for (Supply supply : supplies) {

			debug += supply + "\n";
		}

		log.info(debug);
	}

	@Test
	@Transactional
	public void testSupplyBetweenQuantity() {

		String debug = "by quantity \n";

		List<Supply> supplies = dao.findByQuantityRange(10, 30);

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
		dao.remove(supply);
	}

}
