package assign02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for FacilityGeneric.
 *
 * @author Eric Heisler , Shawn Zhang, and Bryan Munoz Barron
 * @version January 25, 2024
 */
public class FacilityGenericTester {
	// Generic Facility
	private FacilityGeneric<Integer> uNIDFacility, emptyFacility, phase3Facility;
	private FacilityGeneric<UHealthID> uHIDFacility;
	private FacilityGeneric<String> nameFacility;
	// IDs, dates, names
	private UHealthID[] uHIDs1, uHIDs2;
	private GregorianCalendar[] dates;
	private String[] firstNames, lastNames, physicianNames;
	// For phase 3
	private UHealthID p3id1, p3id2, p3id3, p3id4;
	private GregorianCalendar p3date1, p3date2, p3date3, p3date4;

	// Don't change these numbers. It will affect some tests.
	int nPatients = 20;
	int nPhysicians = 8;

	// A private helper to generate UHIDs
	private UHealthID[] generateUHIDs(String prefix, int howMany) {
		UHealthID[] ids = new UHealthID[howMany];
		for (int i = 0; i < howMany; i++)
			ids[i] = new UHealthID(prefix + "-" + String.format("%04d", i));
		return ids;
	}

	// A private helper to generate dates
	private GregorianCalendar[] generateDates(int howMany) {
		GregorianCalendar[] dates = new GregorianCalendar[howMany];
		for (int i = 0; i < howMany; i++)
			dates[i] = new GregorianCalendar(2000 + i%22, i%12, i%28);
		return dates;
	}

	// A private helper to generate names
	private String[] generateNames(int howMany, int a, int b) {
		String[] names = new String[howMany];
		for (int i = 0; i < howMany; i++)
			names[i] = (char)('A' + (i+a) % 26) + "" + (char)('a' + (b*i) % 26);
		return names;
	}

	@BeforeEach
	 void setUp() throws Exception {

		uHIDs1 = generateUHIDs("PATS", nPatients); // for patients
		uHIDs2 = generateUHIDs("DOCS", nPhysicians); // for physicians

		dates = generateDates(nPatients);

		firstNames = generateNames(nPatients, 1, 2);
		lastNames = generateNames(nPatients, 5, 3);
		physicianNames = generateNames(nPhysicians, 10, 4);

		uNIDFacility = new FacilityGeneric<Integer>();
		uHIDFacility = new FacilityGeneric<UHealthID>();
		nameFacility = new FacilityGeneric<String>();
		emptyFacility = new FacilityGeneric<Integer>();
		phase3Facility = new FacilityGeneric<Integer>();

		for (int i = 0; i < nPatients; i++) {
			uNIDFacility.addPatient(new CurrentPatientGeneric<Integer>(
					firstNames[i], lastNames[i],
					uHIDs1[i], 1234567 + i%nPhysicians, dates[i]));
			uHIDFacility.addPatient(new CurrentPatientGeneric<UHealthID>(
					firstNames[i], lastNames[i],
					uHIDs1[i], uHIDs2[i%nPhysicians], dates[i]));
			nameFacility.addPatient(new CurrentPatientGeneric<String>(
					firstNames[i], lastNames[i],
					uHIDs1[i], physicianNames[i%nPhysicians], dates[i]));
		}

		p3id1 = new UHealthID("XXXX-1111");
		p3id2 = new UHealthID("BBBB-1111");
		p3id3 = new UHealthID("FFFF-1111");
		p3id4 = new UHealthID("BBBB-2222");
		p3date1 = new GregorianCalendar(2019, 1, 5);
		p3date2 = new GregorianCalendar(2019, 1, 4);
		p3date3 = new GregorianCalendar(2019, 1, 3);
		p3date4 = new GregorianCalendar(2019, 1, 2);

		phase3Facility.addPatient(new CurrentPatientGeneric<Integer>("A", "B", new UHealthID("XXXX-1111"), 7, new GregorianCalendar(2019, 1, 5)));
		phase3Facility.addPatient(new CurrentPatientGeneric<Integer>("A", "B", new UHealthID("BBBB-1111"), 7, new GregorianCalendar(2019, 1, 4)));
		phase3Facility.addPatient(new CurrentPatientGeneric<Integer>("A", "C", new UHealthID("FFFF-1111"), 7, new GregorianCalendar(2019, 1, 3)));
		phase3Facility.addPatient(new CurrentPatientGeneric<Integer>("R", "T", new UHealthID("BBBB-2222"), 7, new GregorianCalendar(2019, 1, 2)));

		// FILL IN -- Extend this tester to add more tests for the facilities above,
		// as well as to create and test other facilities.
	}

	// empty Facility tests --------------------------------------------------------

	@Test
	public void testEmptyLookupUHID() {
		assertNull(emptyFacility.lookupByUHID(uHIDs1[0]));
	}

	@Test
	public void testEmptyLookupPhysician() {
		ArrayList<CurrentPatientGeneric<Integer>> patients = emptyFacility.lookupByPhysician(1010101);
		assertEquals(0, patients.size());
	}

	@Test
	public void testEmptySetVisit() {
		// ensure no exceptions thrown
		emptyFacility.setLastVisit(uHIDs1[0], dates[3]);
	}

	@Test
	public void testEmptySetPhysician() {
		// ensure no exceptions thrown
		emptyFacility.setPhysician(uHIDs1[0], 1010101);
	}

	@Test
	public void testEmptyGetInactivePatients() {
		ArrayList<CurrentPatientGeneric<Integer>> patients = emptyFacility.getInactivePatients(dates[4]);
		assertEquals(0, patients.size());
	}

	// uNID Facility tests --------------------------------------------------------

	@Test
	public void testUNIDLookupPhysicianCount() {
		ArrayList<CurrentPatientGeneric<Integer>> actualPatients = uNIDFacility.lookupByPhysician(1234568);
		assertEquals(3, actualPatients.size());
	}

	@Test
	public void testIntegerLookupByPhysician(){
		var newPatient = new CurrentPatientGeneric<Integer>("A", "B", new UHealthID("XXLX-1111"), 11, new GregorianCalendar(2019, 1, 5));
		phase3Facility.addPatient(newPatient);
		assertEquals("[A B (XXLX-1111)]", phase3Facility.lookupByPhysician(11).toString());
	}

	@Test
	public void testIntegerLookupByPhysicianEmptyList(){
		assertEquals("[]", phase3Facility.lookupByPhysician(21).toString());
	}

	@Test
	public void testUNIDLookupPhysicianPatient() {
		Patient expectedPatient = new Patient(firstNames[1], lastNames[1], new UHealthID(uHIDs1[1].toString()));
		ArrayList<CurrentPatientGeneric<Integer>> actualPatients = uNIDFacility.lookupByPhysician(1234568);
		assertEquals(expectedPatient, actualPatients.get(0));
	}

	// UHealthID facility tests ---------------------------------------------------

	@Test
	public void testUHIDLookupUHID() {
		Patient expected = new Patient(firstNames[0], lastNames[0], new UHealthID(uHIDs1[0].toString()));
		CurrentPatientGeneric<UHealthID> actual = uHIDFacility.lookupByUHID(new UHealthID(uHIDs1[0].toString()));
		assertEquals(expected, actual);
	}

	@Test
	public void testUHIDLookupPhysicianCount() {
		ArrayList<CurrentPatientGeneric<UHealthID>> actualPatients = uHIDFacility.lookupByPhysician(uHIDs2[1]);
		assertEquals(3, actualPatients.size());
	}

	@Test
	public void testUHIDAddDuplicatePatient() {
		assertFalse(uHIDFacility.addPatient(new CurrentPatientGeneric<UHealthID>(firstNames[1], lastNames[1], new UHealthID(uHIDs1[1].toString()), uHIDs2[1], dates[1])));
	}

	@Test
	public void testUHIDAddNewPatient() {
		assertTrue(uHIDFacility.addPatient(new CurrentPatientGeneric<UHealthID>(firstNames[1], lastNames[1], new UHealthID("ZZZZ-9999"), uHIDs2[1], dates[1])));
	}

	@Test
	public void testUHIDUpdatePhysician() {
		uHIDFacility.lookupByUHID(uHIDs1[2]).updatePhysician(uHIDs2[0]);
		CurrentPatientGeneric<UHealthID> patient = uHIDFacility.lookupByUHID(uHIDs1[2]);
		assertEquals(uHIDs2[0], patient.getPhysician());
	}

	// name facility tests -------------------------------------------------------------------------

	@Test
	public void testNameLookupPhysician() {
		Patient expectedPatient1 = new Patient(firstNames[1], lastNames[1], new UHealthID(uHIDs1[1].toString()));
		Patient expectedPatient2 = new Patient(firstNames[9], lastNames[9], new UHealthID(uHIDs1[9].toString()));

		ArrayList<CurrentPatientGeneric<String>> actualPatients = nameFacility.lookupByPhysician(physicianNames[1]);
		assertTrue(actualPatients.contains(expectedPatient1) && actualPatients.contains(expectedPatient2));
	}

	@Test
	public void testLookupPhysicianIntegerCount() {
		ArrayList<CurrentPatientGeneric<Integer>> four = phase3Facility.lookupByPhysician(7);
		assertEquals(4, four.size());
	}

	@Test
	public void testNameGetInactivePatients() {
		ArrayList<CurrentPatientGeneric<String>> inactive = nameFacility.getInactivePatients(new GregorianCalendar(2010, 0, 0));
		assertEquals(10, inactive.size());
	}

	@Test
	public void testIntegerGetInactivePatients(){
		ArrayList<CurrentPatientGeneric<Integer>> intPatients = uNIDFacility.getInactivePatients(new GregorianCalendar(2010, 0, 4));
		assertEquals(10, intPatients.size());
	}

	@Test
	public void testUHealthIDGetInactivePatients(){
		ArrayList<CurrentPatientGeneric<UHealthID>> uHealthPatient = uHIDFacility.getInactivePatients(new GregorianCalendar(2010, 0, 0));
		assertEquals(10, uHealthPatient.size());
	}

	@Test
	public void testNameGetPhysicianList() {
		ArrayList<String> actual = nameFacility.getPhysicianList();
		assertEquals(8, actual.size());
	}

	@Test
	public void testNameGetPhysicianListInteger(){
		ArrayList<Integer> onlyOne = phase3Facility.getPhysicianList();
		assertEquals(1, onlyOne.size());
	}

	@Test
	public void testGetPhysicianListUHID(){
		ArrayList<UHealthID> byUHID = uHIDFacility.getPhysicianList();
		assertEquals(8, byUHID.size());
	}

	@Test
	public void testSetPhysician(){
		assertEquals("[7]", phase3Facility.getPhysicianList().toString());
		phase3Facility.setPhysician(p3id1,2);
		assertEquals("[2, 7]", phase3Facility.getPhysicianList().toString());
	}

	@Test
	public void testSetPhysicianString(){
		var stringPatient = new CurrentPatientGeneric<String>("C", "D", new UHealthID("XXXX-1111"), "7", new GregorianCalendar(2019, 1, 5));
		var emptyStringArray = new FacilityGeneric<String>();
		emptyStringArray.addPatient(stringPatient);
		assertEquals("[7]", emptyStringArray.getPhysicianList().toString());
		emptyStringArray.setPhysician(p3id1, "11");
		assertEquals("[11]", emptyStringArray.getPhysicianList().toString());
	}

	@Test
	public void testSetPhysicianUHealthID(){
		var uHealthIDPhys = new UHealthID("BBBE-1223");
		var uHealthIDToSet = new UHealthID("TTTW-1232");
		var uHealthIDPatient = new CurrentPatientGeneric<UHealthID>("D", "D", new UHealthID("XXXX-1111"), uHealthIDPhys, new GregorianCalendar(2019, 1, 5));
		var emptyUHealthIDRecord = new FacilityGeneric<UHealthID>();
		emptyUHealthIDRecord.addPatient(uHealthIDPatient);
		assertEquals("[BBBE-1223]", emptyUHealthIDRecord.getPhysicianList().toString());
		emptyUHealthIDRecord.setPhysician(p3id1,uHealthIDToSet);
		assertEquals("[TTTW-1232]", emptyUHealthIDRecord.getPhysicianList().toString());

	}

	@Test
	public void testGetOrderByUHealthIDInteger(){
		var first = new CurrentPatientGeneric<Integer>("First", "D", new UHealthID("XXXX-1111"), 9, new GregorianCalendar(2019, 1, 5));
		var second = new CurrentPatientGeneric<Integer>("Second", "P", new UHealthID("XXXX-1311"), 10, new GregorianCalendar(2019, 1, 5));
		var third = new CurrentPatientGeneric<Integer>("Third", "l", new UHealthID("XXXX-1411"), 11, new GregorianCalendar(2019, 1, 5));
		var emptyIntRecord = new FacilityGeneric<Integer>();
		var expectedOrder = "[First D (XXXX-1111), Second P (XXXX-1311), Third l (XXXX-1411)]";
		emptyIntRecord.addPatient(third);
		emptyIntRecord.addPatient(first);
		emptyIntRecord.addPatient(second);
		ArrayList<CurrentPatientGeneric<Integer>> integerGenericOrdered = emptyIntRecord.getOrderedByUHealthID();
		assertEquals(expectedOrder, integerGenericOrdered.toString());

	}

	@Test
	public void testGetOrderedByUHealthIDString(){
		var first = new CurrentPatientGeneric<String>("First", "D", new UHealthID("XXXX-1111"), "9", new GregorianCalendar(2019, 1, 5));
		var second = new CurrentPatientGeneric<String>("Second", "P", new UHealthID("XXXX-1311"), "10", new GregorianCalendar(2019, 1, 5));
		var third = new CurrentPatientGeneric<String>("Third", "l", new UHealthID("XXXX-1411"), "12", new GregorianCalendar(2019, 1, 5));
		var emptyStringRecord = new FacilityGeneric<String>();
		var expectedOrder = "[First D (XXXX-1111), Second P (XXXX-1311), Third l (XXXX-1411)]";
		emptyStringRecord.addPatient(second);
		emptyStringRecord.addPatient(first);
		emptyStringRecord.addPatient(third);
		ArrayList<CurrentPatientGeneric<String>> genericStringOrdered = emptyStringRecord.getOrderedByUHealthID();
		assertEquals(expectedOrder, genericStringOrdered.toString());

	}

	@Test
	public void testGetOrderedByUHealthID(){
		var first = new CurrentPatientGeneric<UHealthID>("First", "D", new UHealthID("XXXX-1011"), p3id2, new GregorianCalendar(2019, 1, 5));
		var second = new CurrentPatientGeneric<UHealthID>("Second", "P", new UHealthID("XXXX-1111"), p3id4, new GregorianCalendar(2019, 1, 5));
		var third = new CurrentPatientGeneric<UHealthID>("Third", "l", new UHealthID("XXXX-1211"), p3id1, new GregorianCalendar(2019, 1, 5));
		var fourth = new CurrentPatientGeneric<UHealthID>("Fourth", "l", new UHealthID("XXXX-1311"), p3id1, new GregorianCalendar(2019, 1, 5));
		var emptyUHealthIDRecord = new FacilityGeneric<UHealthID>();
		var expectedUHealthIDOrder = "[First D (XXXX-1011), Second P (XXXX-1111), Third l (XXXX-1211), Fourth l (XXXX-1311)]";
		emptyUHealthIDRecord.addPatient(fourth);
		emptyUHealthIDRecord.addPatient(second);
		emptyUHealthIDRecord.addPatient(first);
		emptyUHealthIDRecord.addPatient(third);
		ArrayList<CurrentPatientGeneric<UHealthID>> uHealthIDOrdered = emptyUHealthIDRecord.getOrderedByUHealthID();
		assertEquals(expectedUHealthIDOrder, uHealthIDOrdered.toString());

	}

	// phase 3 tests ---------------------------------------------------------------------------

	@Test
	public void testOrderedByUHIDCount() {
		ArrayList<CurrentPatientGeneric<Integer>> actual = phase3Facility.getOrderedByUHealthID();
		assertEquals(4, actual.size());
	}

	@Test
	public void testOrderedByUHIDOrder() {
		ArrayList<CurrentPatientGeneric<Integer>> actual = phase3Facility.getOrderedByUHealthID();
		assertTrue(actual.get(3).equals(new CurrentPatientGeneric<Integer>("A", "B", p3id1, 7, p3date1)) &&
				actual.get(0).equals(new CurrentPatientGeneric<Integer>("A", "B", p3id2, 7, p3date2)) &&
				actual.get(2).equals(new CurrentPatientGeneric<Integer>("A", "C", p3id3, 7, p3date3)) &&
				actual.get(1).equals(new CurrentPatientGeneric<Integer>("R", "T", p3id4, 7, p3date4)));
	}
}
