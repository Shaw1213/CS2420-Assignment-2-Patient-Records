package assign02;

import java.util.GregorianCalendar;

/**
 * This generic class represents a record of patients that have visited a UHealth
 *
 * @author Shawn Zhang and Bryan Munoz Barron
 * @version January 23, 2024
 */

public class CurrentPatientGeneric<Type> extends Patient {

	private Type physician;
	private GregorianCalendar lastVisit;


	public CurrentPatientGeneric(String firstName, String lastName, UHealthID uHealthID, Type physician, GregorianCalendar lastVisit) {
		super(firstName, lastName, uHealthID);
		this.physician = physician;
		this.lastVisit = lastVisit;
	}
	
	/**
	 * This returns the physician.
	 * @return - int physician
	 */
	public Type getPhysician() {
		return this.physician;
	}
	
	/**
	 * This returns the last visit.
	 * @return - GregorianCalendar lastVisit.
	 */
	public GregorianCalendar getLastVisit() {
		return this.lastVisit;
	}
	
	/**
	 * Updates the physician.
	 * @param newPhysician
	 */
	public void updatePhysician(Type newPhysician) {
		this.physician = newPhysician;
	}
	
	/**
	 * Updates the last visit .
	 * @param date
	 */
	public void updateLastVisit(GregorianCalendar date) {
		this.lastVisit = date;
	}

}
