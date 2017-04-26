package core.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;

/**
 * Created by harry on 25/04/2017.
 */
@ExtendWith(MockitoExtension.class)
@Tag("AddServiceTests")
public class AddServiceTest {
    @Mock
    private Database mockDatabase;

    @Mock
    private ResultSet mockResultFull;

    @Mock
    private ResultSet mockResultEmpty;

    private AddService addService;
    private int result;
    private int length;
    private String name;
    private int FAILED_LENGTH = -3, FAIL_NAME_EXISTS = -2, FAILED_NAME = -1, FAILED_WRITE = 0, SUCCESS_WRITE = 1;

    @BeforeEach
    public void setup() throws Exception{
        addService = new AddService(mockDatabase);
        length = 15;
        name = "test";
        when(mockDatabase.updateDatabase(anyString())).thenReturn(true);
        when(mockDatabase.queryDatabase(anyString())).thenReturn(mockResultFull);
        when(mockResultFull.next()).thenReturn(true);
        when(mockResultEmpty.next()).thenReturn(false);
    }

    @DisplayName("Test for fail on the empty string")
    @Test
    void emptyName(){
        name = "";
        result = addService.addNewService(name, length);
        assertEquals(FAILED_NAME, result);
    }

    @DisplayName("Test for fail on role name with numbers")
    @Test
    void invalidName1(){
        name = "12@#";
        result = addService.addNewService(name, length);
        assertEquals(FAILED_NAME, result);
    }

    @DisplayName("Test for fail on role name with letters and numbers")
    @Test
    void invalidName2(){
        name = "Test12$%*";
        result = addService.addNewService(name, length);
        assertEquals(FAILED_NAME, result);
    }

    @DisplayName("Test for success with valid role name")
    @Test
    void validName() throws Exception{
        name = "TestSuccess";
        when(mockDatabase.queryDatabase(anyString())).thenReturn(mockResultEmpty);
        when(mockDatabase.queryDatabase(contains(name))).thenReturn(mockResultFull);
        when(mockResultFull.next()).thenReturn(false);
        result = addService.addNewService(name, length);
        assertEquals(SUCCESS_WRITE, result);
    }

    @DisplayName("Test for fail on invalid length")
    @Test
    void invalidLength1() throws Exception{
        length = 10;
        when(mockDatabase.queryDatabase(anyString())).thenReturn(mockResultEmpty);
        when(mockDatabase.queryDatabase(contains(name))).thenReturn(mockResultFull);
        when(mockResultFull.next()).thenReturn(false);
        result = addService.addNewService(name, length);
        assertEquals(FAILED_LENGTH, result);
    }

    @DisplayName("Test for fail on invalid length")
    @Test
    void invalidLength2()throws Exception{
        when(mockDatabase.queryDatabase(anyString())).thenReturn(mockResultEmpty);
        when(mockDatabase.queryDatabase(contains(name))).thenReturn(mockResultFull);
        when(mockResultFull.next()).thenReturn(false);
        length = 31;
        result = addService.addNewService(name, length);
        assertEquals(FAILED_LENGTH, result);
    }

    @DisplayName("Test for fail on the role already exists")
    @Test
    void roleExists(){
        name = "Blow";
        result = addService.addNewService(name, length);
        assertEquals(FAIL_NAME_EXISTS, result);
    }

    @DisplayName("Test for successful add to database")
    @Test
    void addService() throws Exception{
        setupForPositiveMatch(name, length);
        assertEquals(SUCCESS_WRITE, addService.addNewService(name, length));
    }

    @DisplayName("Test for fail addition to database")
    @Test
    void addServiceFailedAddToDatabase() throws Exception{
        when(mockDatabase.queryDatabase(anyString())).thenReturn(mockResultEmpty);
        when(mockDatabase.queryDatabase(contains(name))).thenReturn(mockResultFull);
        when(mockResultFull.next()).thenReturn(false);
        setupForNegativeMatch(name, length);
        assertEquals(FAILED_WRITE, addService.addNewService(name, length));
    }

    /** Set up a test so that we can test for a positive/success */
    private void setupForPositiveMatch(String name, int length) throws Exception{
        String regex = ".*" +name+ ".*" +length+ ".*";

        /*setting the default response as a fail */
        when(mockDatabase.updateDatabase(anyString())).thenReturn(false);
        when(mockDatabase.queryDatabase(anyString())).thenReturn(mockResultEmpty);

        /* change to success if expected inputs are found */
        when(mockDatabase.updateDatabase(matches(regex))).thenReturn(true);
        when(mockDatabase.queryDatabase(matches(regex))).thenReturn(mockResultFull);
    }

    private void setupForNegativeMatch(String name, int length) throws Exception {
        String regex = ".*" +name+ ".*" +length+ ".*";

        /*setting the default response as a success as we are testing if we correctly get an error back  */
        when(mockDatabase.updateDatabase(anyString())).thenReturn(true);
        when(mockDatabase.queryDatabase(anyString())).thenReturn(mockResultFull);

        /*change to error if we correctly find the bad data that was entered */
        when(mockDatabase.updateDatabase(matches(regex))).thenReturn(false);
        when(mockDatabase.queryDatabase(matches(regex))).thenReturn(mockResultEmpty);
    }
}
