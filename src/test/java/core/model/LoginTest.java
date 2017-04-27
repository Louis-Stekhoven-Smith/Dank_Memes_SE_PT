package core.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.sql.ResultSet;

import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.ArgumentMatchers.anyString;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by louie on 10/03/2017.
 */
@ExtendWith(MockitoExtension.class)
@Tag("LoginTests")
class LoginTest {

    @Mock Database mockDatabase;
    @Mock Session mockSession;
    @Mock ResultSet mockResultEmpty;
    @Mock ResultSet mockResultFull;

    /*valid user names are stored as all lower case */
    private String validUserName = "oldboismokey";
    private String validPassword = "Pass1234";
    private String invalidUserName ="Fake";
    private String invalidPassword = "not a password";
    private int result,failedAttempt = -1;
    private static final int CUSTOMER = 1, OWNER =2;

    private Login login;

    @BeforeEach
    public void setup() throws Exception{
        login = new Login(mockDatabase,mockSession);

        when(mockResultFull.next()).thenReturn(true);
        when(mockResultEmpty.next()).thenReturn(false);
        when(mockResultEmpty.getInt(anyString())).thenReturn(-1);
    }

    @DisplayName("Confirm an invalid username and password is caught")
    @Test
    void invalidLoginAttempt() throws Exception{
        setupForNegativeMatch(validUserName,validPassword);
        assertEquals(failedAttempt, login.validateAttempt(invalidUserName, invalidPassword));
    }

    @DisplayName("Confirm an invalid username is caught")
    @Test
    void validUsernameOnly() throws Exception{
        setupForNegativeMatch(validUserName,validPassword);
        assertEquals(failedAttempt,login.validateAttempt(validUserName, invalidPassword));
    }

    @DisplayName("Confirm an invalid password is caught")
    @Test
    void validPasswordOnly() throws Exception{
        setupForNegativeMatch(validUserName,validPassword);
        assertEquals(failedAttempt,  login.validateAttempt(invalidUserName, validPassword));
    }

    @DisplayName("Confirm correct customer login details result in successful validation")
    @Test
    void customerLoggedIn() throws Exception{
        setupForPositiveMatch(validUserName,validPassword);
        when(mockResultFull.getInt("type")).thenReturn(CUSTOMER);
        assertEquals(CUSTOMER,login.validateAttempt(validUserName,validPassword));
    }

    @DisplayName("Confirm correct owner login details result in successful validation")
    @Test
    void ownerLoggedIn() throws Exception{
        setupForPositiveMatch("homy","Homy1234");
        when(mockResultFull.getInt(anyString())).thenReturn(OWNER);
        assertEquals(OWNER,login.validateAttempt("homy","Homy1234"));
    }

    @DisplayName("Confirm user name used to log in inst case sensitive")
    @Test
    void usernameNotCaseSensitive() throws Exception{
        setupForPositiveMatch("homy","Homy1234");
        when(mockResultFull.getInt(anyString())).thenReturn(OWNER);
        result = login.validateAttempt("HOMY","Homy1234");
        assertEquals(OWNER,result);
    }

    /* Helpers */
    /** sets it so that by default the mock database will return false
     * but if username and password are found in SQL the mock database will return true.
     * @param validUserName
     * @param validPassword
     * @throws Exception
     */
    private void setupForPositiveMatch(String validUserName, String validPassword) throws Exception{
        String regex = regexMatchFor(validUserName,validPassword);
         /*Assume failure unless valid details are sent to mockDatabase */
        when(mockDatabase.queryDatabase(anyString())).thenReturn(mockResultEmpty);
        when(mockDatabase.queryDatabase(matches(regex))).thenReturn(mockResultFull);
    }

    private void setupForNegativeMatch(String validUserName, String validPassword) throws Exception {
        String regex = regexMatchFor(validUserName,validPassword);
        /*Assume success unless invalid details are sent in queryDatabase */
        when(mockDatabase.queryDatabase(anyString())).thenReturn(mockResultFull);
        when(mockDatabase.queryDatabase(matches(regex))).thenReturn(mockResultEmpty);

    }

    private String regexMatchFor(String validUserName,String validPassword){
        return  "(.*" +validUserName+ ".*" +validPassword+ ".*)|" +
                "(.*"+validPassword+".*"+validUserName+".*)";
    }
}