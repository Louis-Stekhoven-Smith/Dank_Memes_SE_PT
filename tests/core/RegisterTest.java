package core;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by harry on 11/03/2017.
 */
public class RegisterTest {


    Register testRegister = new Register();


    @Test
    void isNotEmpty() throws Exception {
        String nameFill = "Harry Parkinson";
        String nameEmpty = "";
        String userNameFill = "Harry123";
        String userNameEmpty = "";
        String password1Fill = "Pass123";
        String password1Empty = "";
        String password2Fill = "Pass123";
        String password2Empty = "";
        String addressFill = "123 Richmond Street";
        String addressEmpty = "";
        String phoneNoFill = "0412882192";
        String phoneNoEmpty = "";

        //Testing for false with all set to empty
        assertFalse(testRegister.isNotEmpty(nameEmpty, userNameEmpty, password1Empty, password2Empty, addressEmpty, phoneNoEmpty));

        //Testing for false with 3 set to empty and 2 set to filled
        assertFalse(testRegister.isNotEmpty(nameFill, userNameEmpty, password1Fill, password2Empty, addressFill, phoneNoEmpty));

        //Testing for false with 5 set to empty and 1 set to filled all with all different combinations
        assertFalse(testRegister.isNotEmpty(nameFill, userNameFill, password1Fill, password2Fill, addressFill, phoneNoEmpty));
        assertFalse(testRegister.isNotEmpty(nameFill, userNameFill, password1Fill, password2Fill, addressEmpty, phoneNoFill));
        assertFalse(testRegister.isNotEmpty(nameFill, userNameFill, password1Fill, password2Empty, addressFill, phoneNoFill));
        assertFalse(testRegister.isNotEmpty(nameFill, userNameFill, password1Empty, password2Fill, addressFill, phoneNoFill));
        assertFalse(testRegister.isNotEmpty(nameFill, userNameEmpty, password1Fill, password2Fill, addressFill, phoneNoFill));
        assertFalse(testRegister.isNotEmpty(nameEmpty, userNameFill, password1Fill, password2Fill, addressFill, phoneNoFill));

        //Testing for true with all set to filled.
        assertTrue(testRegister.isNotEmpty(nameFill, userNameFill, password1Fill, password2Fill, addressFill, phoneNoFill));
    }

    @Test
    void passwordCriteria() throws Exception {
        String shortpassword = "harry12";
        String noNumpassword = "wEstcoast";
        String noCapspassword = "eastlyyyy";
        String noLowerpassword = "EASTLY123";
        String correctpassword = "H123abcZ";

        assertFalse(testRegister.passwordCriteria(shortpassword));
        assertFalse(testRegister.passwordCriteria(noNumpassword));
        assertFalse(testRegister.passwordCriteria(noCapspassword));
        assertFalse(testRegister.passwordCriteria(noLowerpassword));
        assertTrue(testRegister.passwordCriteria(correctpassword));
    }

    @Test
    void passwordMatches() throws Exception {
        String password1Correct = "12345";
        String password1False = "1234";
        String password2Correct = "12345";
        String password2False = "1234";

        //Testing for false with the second password only wrong and the first password only wrong
        assertFalse(testRegister.passwordMatches(password1Correct, password2False));
        assertFalse(testRegister.passwordMatches(password1False, password2Correct));

        //Testing for true with both passwords matching
        assertTrue(testRegister.passwordMatches(password1Correct, password2Correct));
    }

    @Test
     void userNameFree() throws Exception {
        String userNameExists = "OldBoiSmokey";
        String userNameFree = "JohnP_123";

        //Testing for false with a user name which exists
        assertFalse(testRegister.userNameFree(userNameExists));

        //Testing for true with a user name which is free
        assertTrue(testRegister.userNameFree(userNameFree));

    }

    @Test
    void register() throws Exception {
        String userName = "Hazza203";
        String name = "Harry Parkinson";
        String password = "123Password";
        String address = "123madeup St";
        String phoneNo = "12345678";

        assertTrue(testRegister.register(userName, name, password, address, phoneNo));
    }

}