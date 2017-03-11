package core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by harry on 11/03/2017.
 */
public class RegisterTest {

    private Register testRegister;

    @Before
    public void setUp() throws Exception {
        testRegister = new Register();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isNotEmpty() throws Exception {
        String nameFill = "Harry Parkinson";
        String nameEmpty = "";
        String uNameFill = "Harry123";
        String uNameEmpty = "";
        String pword1Fill = "Pass123";
        String pword1Empty = "";
        String pword2Fill = "Pass123";
        String pword2Empty = "";
        String addressFill = "123 Richmond Street";
        String addressEmpty = "";
        String phoneNoFill = "0412882192";
        String phoneNoEmpty = "";

        //Testing for false with all set to empty
        assertFalse(testRegister.isNotEmpty(nameEmpty, uNameEmpty, pword1Empty, pword2Empty, addressEmpty, phoneNoEmpty));

        //Testing for false with 3 set to empty and 2 set to filled
        assertFalse(testRegister.isNotEmpty(nameFill, uNameEmpty, pword1Fill, pword2Empty, addressFill, phoneNoEmpty));

        //Testing for false with 5 set to empty and 1 set to filled all with all different combinations
        assertFalse(testRegister.isNotEmpty(nameFill, uNameFill, pword1Fill, pword2Fill, addressFill, phoneNoEmpty));
        assertFalse(testRegister.isNotEmpty(nameFill, uNameFill, pword1Fill, pword2Fill, addressEmpty, phoneNoFill));
        assertFalse(testRegister.isNotEmpty(nameFill, uNameFill, pword1Fill, pword2Empty, addressFill, phoneNoFill));
        assertFalse(testRegister.isNotEmpty(nameFill, uNameFill, pword1Empty, pword2Fill, addressFill, phoneNoFill));
        assertFalse(testRegister.isNotEmpty(nameFill, uNameEmpty, pword1Fill, pword2Fill, addressFill, phoneNoFill));
        assertFalse(testRegister.isNotEmpty(nameEmpty, uNameFill, pword1Fill, pword2Fill, addressFill, phoneNoFill));

        //Testing for true with all set to filled.
        assertTrue(testRegister.isNotEmpty(nameFill, uNameFill, pword1Fill, pword2Fill, addressFill, phoneNoFill));
    }

    @Test
    public void pwordCriteria() throws Exception {
        String shortPword = "harry12";
        String noNumPword = "wEstcoast";
        String noCapsPword = "eastlyyyy";
        String noLowerPword = "EASTLY123";
        String correctPword = "H123abcZ";

        assertFalse(testRegister.pwordCriteria(shortPword));
        assertFalse(testRegister.pwordCriteria(noNumPword));
        assertFalse(testRegister.pwordCriteria(noCapsPword));
        assertFalse(testRegister.pwordCriteria(noLowerPword));
        assertTrue(testRegister.pwordCriteria(correctPword));
    }

    @Test
    public void pwordMatches() throws Exception {
        String pword1Correct = "12345";
        String pword1False = "1234";
        String pword2Correct = "12345";
        String pword2False = "1234";

        //Testing for false with the second password only wrong and the first password only wrong
        assertFalse(testRegister.pwordMatches(pword1Correct, pword2False));
        assertFalse(testRegister.pwordMatches(pword1False, pword2Correct));

        //Testing for true with both passwords matching
        assertTrue(testRegister.pwordMatches(pword1Correct, pword2Correct));
    }

    @Test
    public void uNameFree() throws Exception {
        String uNameExists = "Hazza203";
        String uNameFree = "JohnP_123";

        //Testing for false with a user name which exists
        assertFalse(testRegister.uNameFree(uNameExists));

        //Testing for true with a user name which is free
        assertTrue(testRegister.uNameFree(uNameFree));

    }

    @Test
    public void register() throws Exception {

    }

}