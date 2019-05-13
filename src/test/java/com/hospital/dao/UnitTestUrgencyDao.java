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

import com.hospital.dao.IUrgencyAttentionDao;
import com.hospital.model.InventaryMedicine;
import com.hospital.model.Medicine;
import com.hospital.model.Patient;
import com.hospital.model.Supply;
import com.hospital.model.UrgencyAttention;


import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
@Log4j2
public class UnitTestUrgencyDao {

	@Autowired
	private IUrgencyAttentionDao dao;
	

	private UrgencyAttention attention;

	private Patient patient;

	@Before
	public void init() {

		Medicine medicine = new Medicine(12l, "name", "genericName", "laboratory", "indications");
		InventaryMedicine inventary = new InventaryMedicine(medicine, 10, "ubication-1", LocalDate.now().plusDays(120));
		InventaryMedicine inventary2 = new InventaryMedicine(medicine, 15, "ubication-2",
				LocalDate.now().plusDays(150));
		medicine.getInventaries().add(inventary);
		medicine.getInventaries().add(inventary2);

		patient = new Patient("123", "miguel", "Torres");

		attention = new UrgencyAttention(patient, LocalDate.now(), "general-description-1", "procedure-1", true);
		UrgencyAttention attention2 = new UrgencyAttention(patient, LocalDate.now(), "generalDescription-2",
				"procedure-2", false);

		Supply supply = new Supply(medicine, 10, patient, LocalDate.now(), "pathology");
		medicine.getSupplies().add(supply);
		attention.getSupplies().add(supply);

		Supply supply2 = new Supply(medicine, 5, patient, LocalDate.now(), "pathology");
		medicine.getSupplies().add(supply2);
		attention.getSupplies().add(supply2);

		patient.getAttentions().add(attention);
		patient.getAttentions().add(attention2);

		dao.persist(attention);
		dao.persist(attention2);
	}

	@Test
	@Transactional
	public void testFindAll() {

		String debug = "findAll -> \n";

		List<UrgencyAttention> urgencies = dao.findAll();

		for (UrgencyAttention u : urgencies) {
			debug += u + "\n";
		}

		log.info(debug);

		assertEquals(2, urgencies.size());
	}

	@Test
	@Transactional
	public void testSave() {
		UrgencyAttention expected = new UrgencyAttention(patient, LocalDate.now(), "generalDescription-3",
				"procedure-3", true);

		dao.persist(expected);
		UrgencyAttention ur = dao.findById(expected.getConsecutive());

		assertNotNull(ur);
		assertEquals(expected.getConsecutive(), ur.getConsecutive());
		assertEquals(expected.getDate(), ur.getDate());
		assertEquals(expected.getPatient().getDocument(), ur.getPatient().getDocument());
		assertEquals(expected.getForwarded(), ur.getForwarded());
		assertEquals(expected.getGeneralDescription(), ur.getGeneralDescription());
		assertEquals(expected.getProcedure(), ur.getProcedure());
		assertEquals(expected.getForwardedPlace(), ur.getForwardedPlace());
	}
	
	@Test
	@Transactional
	public void testMerge() {
		UrgencyAttention expected = attention;
		expected.setProcedure("test-procedure");

		dao.persist(expected);
		UrgencyAttention ur = dao.findById(expected.getConsecutive());

		assertNotNull(ur);
		assertEquals(expected.getConsecutive(), ur.getConsecutive());
		assertEquals(expected.getDate(), ur.getDate());
		assertEquals(expected.getPatient().getDocument(), ur.getPatient().getDocument());
		assertEquals(expected.getForwarded(), ur.getForwarded());
		assertEquals(expected.getGeneralDescription(), ur.getGeneralDescription());
		assertEquals(expected.getProcedure(), ur.getProcedure());
		assertEquals(expected.getForwardedPlace(), ur.getForwardedPlace());
	}

	@Test
	@Transactional
	public void testFindById() {
		UrgencyAttention ur = dao.findById(attention.getConsecutive());

		assertNotNull(ur);
		assertEquals(attention.getConsecutive(), ur.getConsecutive());
		assertEquals(attention.getDate(), ur.getDate());
		assertEquals(attention.getPatient().getDocument(), ur.getPatient().getDocument());
		assertEquals(attention.getForwarded(), ur.getForwarded());
		assertEquals(attention.getGeneralDescription(), ur.getGeneralDescription());
		assertEquals(attention.getProcedure(), ur.getProcedure());
		assertEquals(attention.getForwardedPlace(), ur.getForwardedPlace());

	}

	@Test
	@Transactional
	public void testFindByPatient() {
		String debug = "findByPatient\n";

		List<UrgencyAttention> urgencies = dao.findByPatient(patient.getDocument());

		for (UrgencyAttention u : urgencies) {
			debug += u + "\n";
		}

		log.info(debug);

		assertEquals(2, urgencies.size());

		// REVIEW: List contains the attention in any order
		assertTrue(urgencies.stream()
				.anyMatch(ur -> attention.getConsecutive().equals(ur.getConsecutive())
						&& attention.getPatient().getDocument().equals(ur.getPatient().getDocument())
						&& attention.getForwarded().equals(ur.getForwarded())
						&& attention.getGeneralDescription().equals(ur.getGeneralDescription())));
	}

	// NOTE: Consulta punto 1b)
	@Test
	@Transactional
	public void testFindBetweenDates() {
		LocalDate since = LocalDate.now().minusDays(1);
		LocalDate until = LocalDate.now().plusDays(1);

		String debug = "findBetweenDates\n";

		List<UrgencyAttention> urgencies = dao.findBetweenDates(since, until);

		for (UrgencyAttention u : urgencies) {
			debug += u + "\n";
		}

		log.info(debug);

		assertEquals(2, urgencies.size());

		// REVIEW: List contains the attention in any order
		assertTrue(urgencies.stream()
				.anyMatch(ur -> attention.getConsecutive().equals(ur.getConsecutive())
						&& attention.getPatient().getDocument().equals(ur.getPatient().getDocument())
						&& attention.getForwarded().equals(ur.getForwarded())
						&& attention.getGeneralDescription().equals(ur.getGeneralDescription())));

	}

	@After
	public void destroy() {
		dao.remove(attention);
	}
}
