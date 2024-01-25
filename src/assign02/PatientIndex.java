package assign02;

import java.util.TreeMap;
import java.util.Comparator;

/**
 *An index of patients that have visited a UHealth facility.
 *
 * @author Shawn Zhang
 * @version January 25, 2024
 */

public class PatientIndex {

    private TreeMap<UHealthID, String> patientMap;

    public PatientIndex() {
        this.patientMap = new TreeMap<>(Comparator.comparing(UHealthID::toString));
    }

    /**
     * Add Patient's UHealth id and full name to the patientMap
     * @param p The patient to be added to the patientMap
     */
    public void addPatient(Patient p){
        String fullNameOfPatient = p.getFirstName() + " " + p.getLastName();
        this.patientMap.put(p.getUHealthID(), fullNameOfPatient);
    }

    /**
     * Remove the patient from patientMap
     * @param p The patient to be removed to the patientMap
     */
    public void removePatient(Patient p){
        this.patientMap.remove(p.getUHealthID());
    }

    /**
     * Return the name of patient by their UHealthID
     * @param id the UHealthID of patient
     * @return the full name of the patient by UHealthID
     */
    public String getName(UHealthID id){
        return this.patientMap.get(id);
    }
}

