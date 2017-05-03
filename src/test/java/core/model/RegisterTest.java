package core.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.sql.ResultSet;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;

/**
 * Created by harry on 11/03/2017.
 */
@ExtendWith(MockitoExtension.class)
@Tag("RegisterTest")
public class RegisterTest {

    @Mock
    Database mockDataBase;
    @Mock
    ResultSet mockResultEmpty;
    @Mock
    ResultSet mockResultFull;

    private Register testRegister;

    HashMap<String, String> allEmptyHMap = new HashMap<String, String>();
    HashMap<String, String> allFullHMap = new HashMap<String, String>();

    @BeforeEach
    public void setup() throws Exception{

        testRegister = new Register(mockDataBase);

        allEmptyHMap.put("name", "");
        allEmptyHMap.put("userName", "");
        allEmptyHMap.put("password1", "");
        allEmptyHMap.put("password2", "");
        allEmptyHMap.put("address", "");
        allEmptyHMap.put("phoneNo", "");

        allFullHMap.put("name", "CoolBean");
        allFullHMap.put("userName", "YesThatsMyRealName");
        allFullHMap.put("password1", "CoolBeansharry1yup");
        allFullHMap.put("password2", "CoolBeansharry1yup");
        allFullHMap.put("address", "0423457368");
        allFullHMap.put("phoneNo", "0423457368");

        when(mockResultEmpty.next()).thenReturn(false);
        when(mockResultFull.next()).thenReturn(true);

    }
    @DisplayName("Confirm error is given if all input is empty")
    @Test
    void isNotEmptyDetectsEmpty() throws Exception {
        assertFalse(testRegister.isNotEmpty(allEmptyHMap));
    }

    @DisplayName("Confirm true is given if input is full")
    @Test
    void isNotEmptyReturnsTrueIfNotEmpty() throws Exception {
        assertTrue(testRegister.isNotEmpty(allFullHMap));
    }

    @DisplayName("Confirm error is given if any input is empty")
    @Test
    void isNotEmptyDetectsIfOneIsEmpty() throws Exception {
        allFullHMap.put("name","");
        assertFalse(testRegister.isNotEmpty(allFullHMap));
    }

    @DisplayName("Confirm error if name is invalid")
    @Test
    void invalidName() throws Exception {
        allFullHMap.put("name", "test1234");
        assertFalse(testRegister.nameValidation(allFullHMap));
    }

    @DisplayName("Confirm success if name is valid")
    @Test
    void validName() throws Exception {
        allFullHMap.put("name", "test name");
        assertTrue(testRegister.nameValidation(allFullHMap));
    }

    @DisplayName("Confirm error given if passwords don't match")
    @Test
    void passwordsDoNotMatch() throws Exception {
        allFullHMap.put("password1", "CoolBeansharry1");
        allFullHMap.put("password2", "Coolbeansharry1");
        assertFalse(testRegister.passwordMatches(allFullHMap));
    }

    @DisplayName("Confirm success given if passwords match")
    @Test
    void passwordMatch() throws Exception {
        allFullHMap.put("password1", "CoolBeansharry1");
        allFullHMap.put("password2", "CoolBeansharry1");

        assertTrue(testRegister.passwordMatches(allFullHMap));
    }

    @DisplayName("Confirm error given if passwords too short")
    @Test
    void passwordCriteriaTooShort() throws Exception {

        allFullHMap.put("password1", "CoolBe1");
        allFullHMap.put("password2", "CoolBe1");
        assertFalse(testRegister.passwordCriteria(allFullHMap));

    }
    @DisplayName("Confirm error given if password has no number")
    @Test
    void passwordCriteriaNoNumber() throws Exception {
        allFullHMap.put("password1", "CoolBeansharry");
        allFullHMap.put("password2", "CoolBeansharry");
        assertFalse(testRegister.passwordCriteria(allFullHMap));

    }

    @DisplayName("Confirm error given if password has no caps")
    @Test
    void passwordCriteriaNoNumberNoCaps() throws Exception {
        allFullHMap.put("password1", "coolbeansharry1");
        allFullHMap.put("password2", "coolbeansharry1");
        assertFalse(testRegister.passwordCriteria(allFullHMap));
    }

    @DisplayName("Confirm error given if passwords have no lower case")
    @Test
    void passwordCriteriaNoNumberNoLower() throws Exception {
        allFullHMap.put("password1", "COOLBEANSHARRY1");
        allFullHMap.put("password2", "COOLBEANSHARRY1");
        assertFalse(testRegister.passwordCriteria(allFullHMap));
    }

    @DisplayName("Confirm success given if passwords meet all criteria ")
    @Test
    void passwordCriteriaCorrectPassword() throws Exception {
        assertTrue(testRegister.passwordCriteria(allFullHMap));
    }

    @DisplayName("Confirm success given if phone valid ")
    @Test
    void phoneNoCriteria1Success() throws Exception {
        allFullHMap.put("phoneNo","+61400123456");
        assertTrue(testRegister.phoneNoIsAus(allFullHMap));
    }

    @DisplayName("Confirm success given if phone valid ")
    @Test
    void phoneNoCriteria2Success() throws Exception {
        allFullHMap.put("phoneNo","0400123456");
        assertTrue(testRegister.phoneNoIsAus(allFullHMap));
    }

    @DisplayName("Confirm error given if phone number too short ")
    @Test
    void phoneNoCriteria3Tooshort() throws Exception {
        allFullHMap.put("phoneNo","040012345");
        assertFalse(testRegister.phoneNoIsAus(allFullHMap));
    }

    @DisplayName("Confirm error given if phone number doesn't char ")
    @Test
    void phoneNoCriteria4HasChar() throws Exception {
        allFullHMap.put("phoneNo","04234573b");
        assertFalse(testRegister.phoneNoIsAus(allFullHMap));
    }

    @DisplayName("Confirm error given if phone number is too long")
    @Test
    void phoneNoCriteria5ToLong() throws Exception {
        allFullHMap.put("phoneNo","04234573686");
        assertFalse(testRegister.phoneNoIsAus(allFullHMap));
    }

    @DisplayName("Confirm error given if username already exists ")
    @Test
     void userNameFreeTrue() throws Exception {
        String name = allFullHMap.get("userName");
        when(mockDataBase.queryDatabase(matches(".*"+name+".*"))).thenReturn(mockResultFull);
        assertFalse(testRegister.userNameFree(allFullHMap));
    }

    @DisplayName("Confirm successs given if username does not already exists ")
    @Test
    void userNameFree2False() throws Exception {
        String name = allFullHMap.get("userName");
        when(mockDataBase.queryDatabase(matches(".*"+name+".*"))).thenReturn(mockResultEmpty);
        assertTrue(testRegister.userNameFree(allFullHMap));
    }

    @DisplayName("Confirm error given if couldn't write customer to login table")
    @Test
    void writeNewCustomerWriteLoginFail() throws Exception {
        when(mockDataBase.updateDatabase(anyString())).thenReturn(false);
        assertFalse(testRegister.writeNewCustomer(allFullHMap));
    }

    @DisplayName("Confirm error given if couldn't write customer to customerDetails ")
    @Test
    void writeNewCustomerWriteDetailsFail() throws Exception {
        when(mockDataBase.updateDatabase(anyString())).thenReturn(true).thenReturn(false);
        when(mockDataBase.queryDatabase(anyString())).thenReturn(mockResultFull);
        when(mockResultFull.getInt(anyString())).thenReturn(1);

        assertFalse(testRegister.writeNewCustomer(allFullHMap));

    }

    @DisplayName("Confirm error given if could write customer to login Table but then can not query the ID ")
    @Test
    void writeNewCustomerWroteLoginButCanNotFindNewCustomersID() throws Exception {
        when(mockDataBase.updateDatabase(anyString())).thenReturn(true);
        when(mockDataBase.queryDatabase(anyString())).thenReturn(mockResultEmpty);

        assertFalse(testRegister.writeNewCustomer(allFullHMap));

    }

    @DisplayName("Confirm success given if could write customer to customerDetails ")
    @Test
    void writeNewCustomerSuccess() throws Exception {
        when(mockDataBase.updateDatabase(anyString())).thenReturn(true);
        when(mockDataBase.queryDatabase(anyString())).thenReturn(mockResultFull);
        when(mockResultFull.getInt(anyString())).thenReturn(1);

        assertTrue(testRegister.writeNewCustomer(allFullHMap));

    }

}