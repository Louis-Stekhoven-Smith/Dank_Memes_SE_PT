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
    void vaildateUser(){
        assertEquals(true, loginCorrect.checkUserName());
        assertNotEquals(true, loginInvalid.checkUserName());
    }

    @Test
    void vaildatePassword(){
        assertEquals(true, loginCorrect.checkPassword());
        assertNotEquals(true, loginInvalid.checkPassword());
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