package core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by louie on 10/03/2017.
 */
class LoginTest {


    String vaildUserName = "Louis";

    Login loginCorrect = new Login("louis", "abc123");
    Login loginInvalid = new Login("not vaild", "fake");

    @Test
    void initLoginAttempt(){
        /*TODO*/
    }

    @Test
    void validateUser(){
        assertEquals(true, loginCorrect.validateUsername());
        assertNotEquals(true, loginInvalid.validateUsername());
    }

    @Test
    void validatePassword(){
        assertEquals(true, loginCorrect.validatePassword());
        assertNotEquals(true, loginInvalid.validatePassword());
    }

    @Test
    void attemptLoginSuccess(){
        /*TODO*/
    }

    @Test
    void attemptLoginFailure(){
        /*TODO*/
    }
}