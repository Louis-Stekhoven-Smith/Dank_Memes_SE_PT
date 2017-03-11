package core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by louie on 10/03/2017.
 */
class LoginTest {


    String vaildUserName = "louis";
    String vaildPassword = "abc123";
    String invaildUserName ="Fake";
    String invaildPassword = "not a password";

    Login login = new Login();
    @Test
    void initLoginAttempt(){
        /*TODO*/
    }

    @Test
    void validateUser(){
        assertEquals(true, login.validateAttempt(vaildUserName,vaildPassword));
        assertNotEquals(true, login.validateAttempt(invaildUserName,invaildPassword));
    }

}