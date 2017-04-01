package core.model;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by louie on 10/03/2017.
 */
class LoginTest {


    private String vaildUserName = "OldBoiSmokey";
    private String vaildPassword = "Pass1234";
    private String invaildUserName ="Fake";
    private String invaildPassword = "not a password";
    private int result,failedAttempt = -1;


    Login login = new Login();

    @BeforeAll
    public static void setupDataBase(){
        Database db = new Database();
        db.setupDataBase();
    }

    @Test
    void invalidLoginAttempt(){assertEquals(failedAttempt, login.validateAttempt(invaildUserName,invaildPassword));}

    @Test
    void validUsernameOnly(){assertEquals(failedAttempt,login.validateAttempt(vaildUserName,invaildPassword));}

    @Test
    void validPasswordOnly(){assertEquals(failedAttempt,  login.validateAttempt(invaildUserName,vaildPassword));}

    @Test
    void customerLoggedIn(){
        result = login.validateAttempt("OldBoiSmokey","Pass1234");
        assertEquals(1,result);
    }
    @Test
    void ownerLoggedIn(){
        result = login.validateAttempt("homy","Homy1234");
        assertEquals(result,2);

    }
    @Test
    void usernameNotCaseSensitive(){
        result = login.validateAttempt("HOMY","Homy1234");
        assertEquals(result,2);
    }

}