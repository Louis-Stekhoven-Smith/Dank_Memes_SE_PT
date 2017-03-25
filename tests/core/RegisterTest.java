package core;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by harry on 11/03/2017.
 */
public class RegisterTest {


    Register testRegister = new Register();


    @Test
    void isNotEmpty() throws Exception {
        Database db = new Database();
        db.setupDataBase();

        HashMap<String, String> allEmptyHMap = new HashMap<String, String>();
        HashMap<String, String> oneEmptyHMap = new HashMap<String, String>();
        HashMap<String, String> allFullHMap = new HashMap<String, String>();

        allEmptyHMap.put("name", "");
        allEmptyHMap.put("userName", "");
        allEmptyHMap.put("password1", "");
        allEmptyHMap.put("password2", "");
        allEmptyHMap.put("address", "");
        allEmptyHMap.put("phoneNo", "");

        oneEmptyHMap.put("name", "test");
        oneEmptyHMap.put("userName", "test");
        oneEmptyHMap.put("password1", "test");
        oneEmptyHMap.put("password2", "test2");
        oneEmptyHMap.put("address", "test");
        oneEmptyHMap.put("phoneNo", "");

        allFullHMap.put("name", "test");
        allFullHMap.put("userName", "test");
        allFullHMap.put("password1", "test");
        allFullHMap.put("password2", "test2");
        allFullHMap.put("address", "test");
        allFullHMap.put("phoneNo", "test");

        //Return of 0 means that a feild was empty

        //Testing for a return of 0 with all set to empty
        assertEquals(Register.attemptOutcome.EMPTY_FIELDS, testRegister.registerAttempt(allEmptyHMap));

        //Testing for a return of 0 with 5 set to empty and 1 set to filled
        assertEquals(Register.attemptOutcome.EMPTY_FIELDS, testRegister.registerAttempt(oneEmptyHMap));


        /*  Return of 1 means the fields weren't empty but failed the next test (password matching) this is expected
            Testing for a return of 1 with all set to filled. */
        assertEquals(Register.attemptOutcome.PASSWORDS_DIFFERENT,testRegister.registerAttempt(allFullHMap));
    }

    @Test
    void passwordMatches() throws Exception {
        Database db = new Database();
        db.setupDataBase();

        HashMap<String, String> unmatchingPasswords = new HashMap<String, String>();
        HashMap<String, String> matchingPasswords = new HashMap<String, String>();


        unmatchingPasswords.put("name", "test");
        unmatchingPasswords.put("userName", "test");
        unmatchingPasswords.put("password1", "harry1");
        unmatchingPasswords.put("password2", "harry12");
        unmatchingPasswords.put("address", "test");
        unmatchingPasswords.put("phoneNo", "test");

        matchingPasswords.put("name", "test");
        matchingPasswords.put("userName", "test");
        matchingPasswords.put("password1", "harry12");
        matchingPasswords.put("password2", "harry12");
        matchingPasswords.put("address", "test");
        matchingPasswords.put("phoneNo", "test");


        /*  Return of 1 means passwords dont match
            return of 2 means passwords match but fails password criteria*/

        //Testing for false with the unmatching passwords

        assertEquals(Register.attemptOutcome.PASSWORDS_DIFFERENT, testRegister.registerAttempt(unmatchingPasswords));

        //Testing for true with both passwords matching
        assertEquals(Register.attemptOutcome.PASSWORD_UNSATISFIED, testRegister.registerAttempt(matchingPasswords));
    }

    @Test
    void passwordCriteria() throws Exception {

        Database db = new Database();
        db.setupDataBase();

        HashMap<String, String> shortPassword = new HashMap<String, String>();
        HashMap<String, String> noNumPassword = new HashMap<String, String>();
        HashMap<String, String> noCapsPassword = new HashMap<String, String>();
        HashMap<String, String> noLowerPassword = new HashMap<String, String>();
        HashMap<String, String> correctPassword = new HashMap<String, String>();


        shortPassword.put("name", "test");
        shortPassword.put("userName", "OldBoiSmokey");
        shortPassword.put("password1", "harry12");
        shortPassword.put("password2", "harry12");
        shortPassword.put("address", "test");
        shortPassword.put("phoneNo", "test");

        noNumPassword.put("name", "test");
        noNumPassword.put("userName", "OldBoiSmokey");
        noNumPassword.put("password1", "wEstcoast");
        noNumPassword.put("password2", "wEstcoast");
        noNumPassword.put("address", "test");
        noNumPassword.put("phoneNo", "test");

        noCapsPassword.put("name", "test");
        noCapsPassword.put("userName", "OldBoiSmokey");
        noCapsPassword.put("password1", "eastly123");
        noCapsPassword.put("password2", "eastly123");
        noCapsPassword.put("address", "test");
        noCapsPassword.put("phoneNo", "test");

        noLowerPassword.put("name", "test");
        noLowerPassword.put("userName", "OldBoiSmokey");
        noLowerPassword.put("password1", "EASTLY123");
        noLowerPassword.put("password2", "EASTLY123");
        noLowerPassword.put("address", "test");
        noLowerPassword.put("phoneNo", "test");

        correctPassword.put("name", "test");
        correctPassword.put("userName", "OldBoiSmokey");
        correctPassword.put("password1", "H123abcZ");
        correctPassword.put("password2", "H123abcZ");
        correctPassword.put("address", "test");
        correctPassword.put("phoneNo", "test");

        //return of 2 means fails password criteria check, 3 means password passed.

        assertEquals(Register.attemptOutcome.PASSWORD_UNSATISFIED, testRegister.registerAttempt(shortPassword));
        assertEquals(Register.attemptOutcome.PASSWORD_UNSATISFIED, testRegister.registerAttempt(noNumPassword));
        assertEquals(Register.attemptOutcome.PASSWORD_UNSATISFIED, testRegister.registerAttempt(noCapsPassword));
        assertEquals(Register.attemptOutcome.PASSWORD_UNSATISFIED, testRegister.registerAttempt(noLowerPassword));
        assertEquals(Register.attemptOutcome.PHONENO_FAIL, testRegister.registerAttempt(correctPassword));
    }


    @Test
    void phoneNoCriteria() throws Exception {

        Database db = new Database();
        db.setupDataBase();

        HashMap<String, String> correctPhoneNo1 = new HashMap<String, String>();
        HashMap<String, String> correctPhoneNo2 = new HashMap<String, String>();
        HashMap<String, String> incorrectPhoneNo = new HashMap<String, String>();

        correctPhoneNo1.put("name", "test");
        correctPhoneNo1.put("userName", "OldBoiSmokey");
        correctPhoneNo1.put("password1", "H123abcZ");
        correctPhoneNo1.put("password2", "H123abcZ");
        correctPhoneNo1.put("address", "test");
        correctPhoneNo1.put("phoneNo", "0400123456");

        correctPhoneNo2.put("name", "test");
        correctPhoneNo2.put("userName", "OldBoiSmokey");
        correctPhoneNo2.put("password1", "H123abcZ");
        correctPhoneNo2.put("password2", "H123abcZ");
        correctPhoneNo2.put("address", "test");
        correctPhoneNo2.put("phoneNo", "+61400123456");

        incorrectPhoneNo.put("name", "test");
        incorrectPhoneNo.put("userName", "OldBoiSmokey");
        incorrectPhoneNo.put("password1", "H123abcZ");
        incorrectPhoneNo.put("password2", "H123abcZ");
        incorrectPhoneNo.put("address", "test");
        incorrectPhoneNo.put("phoneNo", "1234567890");

        assertEquals(Register.attemptOutcome.USERNAME_TAKEN, testRegister.registerAttempt(correctPhoneNo1));
        assertEquals(Register.attemptOutcome.USERNAME_TAKEN, testRegister.registerAttempt(correctPhoneNo2));
        assertEquals(Register.attemptOutcome.PHONENO_FAIL, testRegister.registerAttempt(incorrectPhoneNo));
    }

    @Test
     void userNameFree() throws Exception {

        Database db = new Database();
        db.setupDataBase();

        HashMap<String, String> userNameExists = new HashMap<String, String>();
        HashMap<String, String> userNameFree = new HashMap<String, String>();

        userNameExists.put("name", "test");
        userNameExists.put("userName", "OldBoiSmokey");
        userNameExists.put("password1", "H123abcZ");
        userNameExists.put("password2", "H123abcZ");
        userNameExists.put("address", "test");
        userNameExists.put("phoneNo", "0400123456");

        userNameFree.put("name", "test");
        userNameFree.put("userName", "YoungLasGrilled");
        userNameFree.put("password1", "H123abcZ");
        userNameFree.put("password2", "H123abcZ");
        userNameFree.put("address", "test");
        userNameFree.put("phoneNo", "0400123456");

        //Testing for a return of 3 meaning the username exists
        assertEquals(Register.attemptOutcome.USERNAME_TAKEN, testRegister.registerAttempt(userNameExists));

        //Testing for a return of 5 meaning the username is free
        assertEquals(Register.attemptOutcome.SUCCESS, testRegister.registerAttempt(userNameFree));
        String deleteSQL = "DELETE FROM customerDetails WHERE userName = 'YoungLasGrilled'";
        String deleteSQL1 = "DELETE FROM customerLogin WHERE userName = 'YoungLasGrilled'";

        db.updateDatabase(deleteSQL);
        db.updateDatabase(deleteSQL1);

    }

    @Test
    void register() throws Exception {

        Database db = new Database();
        db.setupDataBase();
        HashMap<String, String> registerCustomer = new HashMap<String, String>();

        registerCustomer.put("name", "Lady Sovreign");
        registerCustomer.put("userName", "YoungLasGrilled");
        registerCustomer.put("password1", "H123abcZ");
        registerCustomer.put("password2", "H123abcZ");
        registerCustomer.put("address", "123madeup St");
        registerCustomer.put("phoneNo", "+61488777666");

        assertEquals(Register.attemptOutcome.SUCCESS, testRegister.registerAttempt(registerCustomer));

        String deleteSQL3 = "DELETE FROM customerDetails WHERE userName = " + "'YoungLasGrilled'";
        String deleteSQL4 = "DELETE FROM customerLogin WHERE userName = " + "'YoungLasGrilled'";
        db.updateDatabase(deleteSQL3);
        db.updateDatabase(deleteSQL4);

    }

}