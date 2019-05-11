package com.hospital;

import static org.junit.Assert.assertEquals;
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
import com.hospital.model.UrgencyAttention;
import com.hospital.services.IMedicineService;
import com.hospital.services.IPatientService;
import com.hospital.services.IUrgencyService;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
@Log4j2
public class TestUrgencyDao {

	@Autowired
	private IUrgencyService urgencyService;

	@Autowired
	private IPatientService patientService;

	@Autowired
	private IMedicineService medicineService;

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

		medicineService.saveOrUpdate(medicine);
		patientService.saveOrUpdate(patient);
	}

	@Test
	public void testFindAll() {

		String debug = "findAll -> \n";

		List<UrgencyAttention> urgencies = urgencyService.findAll();

		for (UrgencyAttention u : urgencies) {
			debug += u + "\n";
		}

		log.info(debug);

		assertEquals(2, urgencies.size());
	}

	// NOTE: Consulta punto 1b)
	@Test
	public void testFindBetweenDates() {
		LocalDate since = LocalDate.now().minusDays(1);
		LocalDate until = LocalDate.now().plusDays(1);

		String debug = "findBetweenDates\n";

		List<UrgencyAttention> urgencies = urgencyService.findBetweenDates(since, until);
		
		for(UrgencyAttention u : urgencies) {
			debug+=u+"\n";
		}

		
		log.info(debug);
		
		assertEquals(2, urgencies.size());
		
		//REVIEW: List contains the attention in any order
		assertTrue(urgencies.stream().anyMatch(ur -> 
				attention.getConsecutive().equals(ur.getConsecutive())&&
				attention.getPatient().getDocument().equals(ur.getPatient().getDocument())&&
				attention.getForwarded().equals(ur.getForwarded())&&
				attention.getGeneralDescription().equals(ur.getGeneralDescription())
				));
		

	}

	@After
	public void destroy() {
	}
}
