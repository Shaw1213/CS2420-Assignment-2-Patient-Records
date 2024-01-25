package assign02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains test the PatientIndex
 * @author Bryan Munoz and Shawn Zhang
 * @version January 25, 2024
 */

class PatientIndexTest {

    @Test
    void addPatient() {
        var uHID23 = new UHealthID("FGFG-1121");
        var lily = new Patient("Lily", "Back",uHID23);
        var map = new PatientIndex();
        assertNull(map.getName(uHID23));
        map.addPatient(lily);
        assertEquals("Lily Back", map.getName(uHID23));
    }

    @Test
    void addPatientNameChange(){
        var uHID223 = new UHealthID("FKKG-1421");
        var boston = new Patient("Boston", "Alexander",uHID223);
        var map = new PatientIndex();
        assertNull(map.getName(uHID223));
        map.addPatient(boston);
        assertEquals("Boston Alexander",map.getName(uHID223));
        var jake = new Patient("Jake", "Nate",uHID223);
        map.addPatient(jake);
        assertEquals("Jake Nate", map.getName(uHID223));

    }

    @Test
    void removePatient() {
        var uHID44 = new UHealthID("NNFG-0121");
        var uHID54 = new UHealthID("VLVG-0121");
        var alex = new Patient("Alex", "Crook",uHID44);
        var brox = new Patient("Brox", "Chill",uHID54);
        var map = new PatientIndex();
        assertNull(map.getName(uHID44));
        map.addPatient(alex);
        map.addPatient(brox);
        assertEquals(map.getName(uHID44), "Alex Crook");
        assertEquals(map.getName(uHID54), "Brox Chill");
        map.removePatient(alex);
        map.removePatient(brox);
        assertNull(map.getName(uHID44));
        assertNull(map.getName(uHID54));
    }

    @Test
    void getName() {
        var uHID50 = new UHealthID("YYVG-0521");
        var dallas = new Patient("Dallas", "Whopper",uHID50);
        var map = new PatientIndex();
        map.addPatient(dallas);
        assertEquals("Dallas Whopper", map.getName(uHID50));
    }

    @Test
    void getNameNull(){
        var uHID990 = new UHealthID(("BBMN-0990"));
        var map = new PatientIndex();
        assertNull(map.getName(uHID990));
    }
}