package core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Created by louie on 10/03/2017.
 */
class LoginTest {


    private String vaildUserName = "OldBoiSmokey";
    private String vaildPassword = "Pass1234";
    private String invaildUserName ="Fake";
    private String invaildPassword = "not a password";

    Login login = new Login();

    @BeforeAll
    public static void setupDataBase(){
        Database db = new Database();
        db.setupDataBase();
    }

    @Test
    void validLoginAtempt(){assertEquals(true, login.validateAttempt(vaildUserName,vaildPassword));}

    @Test
    void invalidLoginAtempt(){assertNotEquals(true, login.validateAttempt(invaildUserName,invaildPassword));}

    @Test
    void vaildUsernameOnly(){assertNotEquals(true, login.validateAttempt(vaildUserName,invaildPassword));}

    @Test
    void vaildPasswordOnly(){assertNotEquals(true, login.validateAttempt(invaildUserName,vaildPassword));}

}