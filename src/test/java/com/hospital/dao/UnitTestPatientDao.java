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
import org.springframework.data.util.Pair;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hospital.dao.IPatientDao;
import com.hospital.model.Patient;
import com.hospital.model.UrgencyAttention;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
@Log4j2
public class UnitTestPatientDao {

	@Autowired
	private IPatientDao dao;

	private Patient patient;

	private UrgencyAttention attention;

	@Before
	public void init() {

		patient = new Patient("123", "miguel", "torres");
		attention = new UrgencyAttention(patient, LocalDate.now(), "general-description-1", "procedure-1", true);
		UrgencyAttention attention2 = new UrgencyAttention(patient, LocalDate.now(), "general-description-2",
				"procedure-2", false);
		UrgencyAttention attention3 = new UrgencyAttention(patient, LocalDate.now().minusDays(10), "general-description-3", "procedure", false);

		patient.getAttentions().add(attention);

		patient.getAttentions().add(attention2);
		
		patient.getAttentions().add(attention3);

		dao.persist(patient);

	}

	@Test
	@Transactional
	public void testSave() {
		Patient expected = new Patient("3312", "juan", "silva");
		
		dao.persist(expected);

		Patient newPatient = dao.findByDocument(expected.getDocument());

		String debug = "testSave -> " + newPatient;

		log.info(debug);

		assertNotNull(newPatient);
		assertEquals(expected.getDocument(), newPatient.getDocument());
		assertEquals(expected.getNames(), newPatient.getNames());
		assertEquals(expected.getLastnames(), newPatient.getLastnames());
		assertEquals(expected.getState(), newPatient.getState());
		
		dao.remove(newPatient);
	}

	@Test
	@Transactional
	public void testMerge() {
		Patient expected =patient;
		expected.setLastnames("sanchez");

		dao.persist(expected);
		
		Patient newPatient = dao.findByDocument(patient.getDocument());

		String debug = "testMerge -> " + newPatient;

		log.info(debug);

		assertNotNull(newPatient);
		assertEquals(expected.getDocument(), newPatient.getDocument());
		assertEquals(expected.getNames(), newPatient.getNames());
		assertEquals(expected.getLastnames(), newPatient.getLastnames());
		assertEquals(expected.getState(), newPatient.getState());
	}

	@Test
	@Transactional
	public void testFindByDocument() {

		Patient newPatient = dao.findByDocument(patient.getDocument());

		String debug = "Patient -> " + newPatient;

		log.info(debug);

		assertNotNull(newPatient);
		assertEquals(patient.getDocument(), newPatient.getDocument());
		assertEquals(patient.getNames(), newPatient.getNames());
		assertEquals(patient.getLastnames(), newPatient.getLastnames());
		assertEquals(patient.getState(), newPatient.getState());

	}

	@Test
	@Transactional
	public void testFindAllByDocument() {

		List<Pair<Patient, Integer>> list = dao.findAllPatientsByDocument();

		String debug = "List->\n";

		for (Pair<Patient, Integer> pair : list) {

			pair.getFirst().getAttentions();
			debug += pair + "\n";
		}

		log.info(debug);
		assertNotNull(list);
		assertEquals(1, list.size());
		assertTrue(list.stream().allMatch(item -> item.getSecond() == 3));

	}

	@Test
	@Transactional
	public void testFindByNames() {
		List<Patient> patients = dao.findByName(patient.getNames());
		String debug = "Patients -> \n";

		for (Patient patient : patients) {
			debug += patient + "\n";
		}

		log.info(debug);

		assertNotNull(patients);
		assertEquals(1, patients.size());
		assertTrue(patients.stream().anyMatch(p ->

		patient.getDocument().equals(p.getDocument()) && patient.getNames().equals(p.getNames())
				&& patient.getLastnames().equals(p.getLastnames()) && patient.getState().equals(p.getState())
				&& p.getAttentions().size() > 1

		));
	}

	@Test
	@Transactional
	public void testFindByLastNames() {
		List<Patient> patients = dao.findByLastName(patient.getLastnames());
		String debug = "Patients -> \n";

		for (Patient patient : patients) {
			debug += patient + "\n";
		}

		log.info(debug);

		assertNotNull(patients);
		assertEquals(1, patients.size());
		assertTrue(patients.stream().anyMatch(p ->

		patient.getDocument().equals(p.getDocument()) && patient.getNames().equals(p.getNames())
				&& patient.getLastnames().equals(p.getLastnames()) && patient.getState().equals(p.getState())
				&& p.getAttentions().size() > 1

		));
	}

	// NOTE: Test
	@Test
	@Transactional
	public void testFindByAttentionsMoreThanLastMonth() {

		List<Patient> patients = dao.findByAttentionsMoreThanLastMonth(1l);
		String debug = "Patients -> \n";

		for (Patient patient : patients) {
			debug += patient + "\n";
		}

		log.info(debug);

		assertNotNull(patients);
		assertEquals(1, patients.size());
		assertTrue(patients.stream().anyMatch(p ->

		patient.getDocument().equals(p.getDocument()) && patient.getNames().equals(p.getNames())
				&& patient.getLastnames().equals(p.getLastnames()) && patient.getState().equals(p.getState())
				&& p.getAttentions().size() == 3

		));

	}

	@After
	public void destroy() {
		dao.remove(patient);
	}

}
