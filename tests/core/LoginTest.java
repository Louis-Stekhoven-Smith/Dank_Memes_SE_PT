package core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by louie on 10/03/2017.
 */
class LoginTest {


    private String vaildUserName = "louis";
    private String vaildPassword = "abc123";
    private String invaildUserName ="Fake";
    private String invaildPassword = "not a password";

    Login login = new Login();

    @Test
    void validLoginAtempt(){
        assertEquals(true, login.validateAttempt(vaildUserName,vaildPassword));
    }

    @Test
    void invalidLoginAtempt(){
        assertNotEquals(true, login.validateAttempt(invaildUserName,invaildPassword));
    }
}