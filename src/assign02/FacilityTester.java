package assign02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for Facility.
 *
 * @author Eric Heisler, Bryan Munoz Barron, and Shawn Zhang
 * @version January 25, 2024
 */
public class FacilityTester {

	private Facility emptyFacility, verySmallFacility, smallFacility;
	private UHealthID uHID1, uHID2, uHID3;
	private GregorianCalendar date1, date2, date3;

	@BeforeEach
	void setUp() throws Exception {

		uHID1 = new UHealthID("AAAA-1111");
		uHID2 = new UHealthID("BCBC-2323");
		uHID3 = new UHealthID("HRHR-7654");

		date1 = new GregorianCalendar(2023, 0, 1);
		date2 = new GregorianCalendar(2023, 3, 17);
		date3 = new GregorianCalendar(2022, 8, 21);

		emptyFacility = new Facility();

		verySmallFacility = new Facility();
		verySmallFacility.addPatient(new CurrentPatient("Jane", "Doe", uHID1, 1010101, date1));
		verySmallFacility.addPatient(new CurrentPatient("Drew", "Hall", uHID2, 3232323, date2));
		verySmallFacility.addPatient(new CurrentPatient("Riley", "Nguyen", uHID3, 9879876, date3));

		smallFacility = new Facility();
		smallFacility.addAll("src/assign02/small_patient_list.txt");
	}

	// Empty Facility tests --------------------------------------------------------

	@Test
	public void testEmptyLookupUHID() {
		assertNull(emptyFacility.lookupByUHID(uHID1));
	}

	@Test
	public void testEmptyLookupPhysician() {
		ArrayList<CurrentPatient> patients = emptyFacility.lookupByPhysician(1010101);
		assertEquals(0, patients.size());
	}

	@Test
	public void testEmptySetVisit() {
		// ensure no exceptions thrown
		emptyFacility.setLastVisit(uHID2, date3);
	}

	@Test
	public void testEmptySetPhysician() {
		// ensure no exceptions thrown
		emptyFacility.setPhysician(uHID2, 1010101);
	}

	@Test
	public void testEmptyGetInactivePatients() {
		ArrayList<CurrentPatient> patients = emptyFacility.getInactivePatients(date3);
		assertEquals(0, patients.size());
	}

	@Test
	public void testEmptyPhysicianList(){
		var emptyRecord = new Facility();
		var emptyList = new ArrayList<>();
		assertEquals(emptyList.toString(),emptyRecord.getPhysicianList().toString());
	}

	// Very small facility tests ---------------------------------------------------

	@Test
	public void testVerySmallLookupUHID() {
		Patient expected = new Patient("Drew", "Hall", new UHealthID("BCBC-2323"));
		CurrentPatient actual = verySmallFacility.lookupByUHID(new UHealthID("BCBC-2323"));
		assertEquals(expected.getUHealthID(), actual.getUHealthID());
	}

	@Test
	public void testNotEqual(){
		Patient shouldNotEqual = new Patient("Aaron", "Bell", new UHealthID("BCBC-2323"));
		UHealthID uHID0 = new UHealthID("BRBM-2112");
		assertFalse(uHID0.equals(shouldNotEqual.getUHealthID()));
	}

	@Test
	public void testDoesEqual(){
		Patient shouldEqual = new Patient("Tom", "Raptor", new UHealthID("VRVR-1254"));
		assertTrue(shouldEqual.getUHealthID().equals(shouldEqual.getUHealthID()));
		assertTrue(shouldEqual.equals(shouldEqual));
	}

	@Test
	public void patientDoesNotExistLookupUHID(){
		UHealthID uHID0 = new UHealthID("BMBM-2112");
		assertNull(verySmallFacility.lookupByUHID(uHID0));
	}

	@Test
	public void testAddPatientTrue(){
		var uHID23 = new UHealthID("BBCO-0990");
		assertNull(smallFacility.lookupByUHID(uHID23));
		var patientToAdd = new CurrentPatient("Zeta", "D", uHID23, 1315191, date1);
		assertTrue(smallFacility.addPatient(patientToAdd));
	}

	@Test
	public void testAddPatientFalse(){
		var uHID27 = new UHealthID("AACO-0590");
		var patientInList = new CurrentPatient("Alpha", "Zulu", uHID27, 7615891, date1);
		assertNull(verySmallFacility.lookupByUHID(uHID27));
		verySmallFacility.addPatient(patientInList);
		assertEquals(patientInList, verySmallFacility.lookupByUHID(uHID27));
		assertFalse(verySmallFacility.addPatient(patientInList));
	}

	@Test
	public void testVerySmallLookupPhysicianCount() {
		ArrayList<CurrentPatient> actualPatients = verySmallFacility.lookupByPhysician(9879876);
		assertEquals(1, actualPatients.size());
	}

	@Test
	public void testGetPhysicianListDuplicate(){
		var duplicatePhysician = new CurrentPatient("Riley", "Nguyen", uHID3, 9999999, date3);
		var smallFacilityPhysList = smallFacility.getPhysicianList();
		var samePhysicianList = smallFacility.getPhysicianList();
		assertEquals(samePhysicianList.toString(), smallFacilityPhysList.toString());
		smallFacility.addPatient(duplicatePhysician);
		var expected = smallFacility.getPhysicianList();
		assertEquals(expected.toString(), smallFacilityPhysList.toString());
	}
	@Test
	public void updatePhysician(){
		var changePhysician = new CurrentPatient("Allan", "Curse", uHID3, 9999999, date3);
		assertEquals(9999999, changePhysician.getPhysician());
		changePhysician.updatePhysician(2121212);
		assertEquals(2121212, changePhysician.getPhysician());
	}

	@Test
	public void updatePhysicianNegative(){
		var negative = new CurrentPatient("Kelly", "Plum", uHID1, 9999999, date3);
		assertEquals(9999999, negative.getPhysician());
		negative.updatePhysician(-1);
		assertEquals(-1, negative.getPhysician());
	}

	@Test
	public void setPhysicianNoEffect(){
		var uHID89 = new UHealthID("BRBM-0022");
		var original = smallFacility;
		var expected = smallFacility;
		original.setPhysician(uHID89, 211212);
		assertEquals(original, expected);
	}

	@Test
	public void setPhysicianDoubleChange(){
		var uHID99 = new UHealthID("BLLL-1022");
		var physicianChange = new CurrentPatient("Delta", "Age", uHID99, 1234555, date3);
		smallFacility.addPatient(physicianChange);
		assertEquals(1234555, physicianChange.getPhysician());
		smallFacility.setPhysician(uHID99, 1112223);
		assertEquals(1112223, physicianChange.getPhysician());
		smallFacility.setPhysician(uHID99, 0000001);
		assertEquals(0000001, physicianChange.getPhysician());
	}

	@Test
	public void testSetLastVisit(){
		var uHID39 = new UHealthID("BLLL-1022");
		var changeVisit = new CurrentPatient("Gamma", "Rae", uHID39, 1233565, date3);
		smallFacility.addPatient(changeVisit);
		assertEquals(changeVisit.getLastVisit(), date3);
		smallFacility.setLastVisit(uHID39, date2);
		assertEquals(changeVisit.getLastVisit(), date2);
	}

	@Test
	public void testVerySmallLookupPhysicianPatient() {
		Patient expectedPatient = new Patient("Riley", "Nguyen", new UHealthID("HRHR-7654"));
		ArrayList<CurrentPatient> actualPatients = verySmallFacility.lookupByPhysician(9879876);
		assertEquals(expectedPatient.toString(), actualPatients.get(0).toString());
	}

	@Test
	public void testVerySmallAddNewPatient() {
		assertTrue(verySmallFacility.addPatient(new CurrentPatient("Jane", "Doe", new UHealthID("BBBB-2222"), 1010101, date1)));
	}

	@Test
	public void testVerySmallUpdatePhysician() {
		verySmallFacility.lookupByUHID(uHID1).updatePhysician(9090909);
		CurrentPatient patient = verySmallFacility.lookupByUHID(uHID1);
		assertEquals(9090909, patient.getPhysician());
	}

	// Small facility tests -------------------------------------------------------------------------

	@Test
	public void testSmallLookupPhysicianCount() {
		ArrayList<CurrentPatient> actualPatients = smallFacility.lookupByPhysician(8888888);
		assertEquals(2, actualPatients.size());
	}

	@Test
	public void testSmallLookupPhysicianPatient() {
		Patient expectedPatient1 = new Patient("Kennedy", "Miller", new UHealthID("QRST-3456"));
		Patient expectedPatient2 = new Patient("Taylor", "Miller", new UHealthID("UVWX-7890"));

		ArrayList<CurrentPatient> actualPatients = smallFacility.lookupByPhysician(8888888);
		assertTrue(actualPatients.contains(expectedPatient1) && actualPatients.contains(expectedPatient2));
	}

	@Test
	public void testSmallGetInactivePatients() {
		ArrayList<CurrentPatient> actual = smallFacility.getInactivePatients(new GregorianCalendar(2020, 0, 0));
		assertEquals(9, actual.size());
	}

	@Test
	public void testSmallGetPhysicianList() {
		ArrayList<Integer> actual = smallFacility.getPhysicianList();
		assertEquals(7, actual.size());
	}
}