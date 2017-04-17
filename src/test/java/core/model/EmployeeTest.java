package core.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.sql.ResultSet;


/**
 * Created by harry on 25/03/2017.
 */
/* mockito v2 doesn't support extendsWith out of the box*/
@ExtendWith(MockitoExtension.class)
@Tag("EmployeeTests")
class EmployeeTest {

    @Mock
    private Database mockDatabase;

    @Mock
    private ResultSet mockResultSetFull;

    @Mock
    private ResultSet mockResultSetEmpty;

    private Employee employee;
    private String name;
    private String role;
    private String email;
    private String phone;
    private int result;
    private int FAILED_TO_ADD_EMP = -1, SUCCEEDED_ADDING_EDP = 1, DATABASE_FAILED_TO_ADD = 0;

    @BeforeEach
    public void setup() throws Exception{
        employee = new Employee(mockDatabase);
        name = "Harry Potter";
        role = "Apprentice Barber";
        email = "potter@wizard.com";
        phone = "0423457368";

        when(mockDatabase.updateDatabase(anyString())).thenReturn(true);
        when(mockDatabase.queryDatabase(anyString())).thenReturn(mockResultSetFull);
        when(mockResultSetFull.next()).thenReturn(true);
    }

    @DisplayName("Confirm phone validation picks up length to short")
    @Test
    void invalidPhoneTest1(){
        phone = "043457368";
        result = employee.addEmployee(name, role, email, phone);
        assertEquals(FAILED_TO_ADD_EMP ,result);
    }

    @DisplayName("Confirm phone validation picks up length to long")
    @Test
    void invalidPhoneTest2(){
        phone = "04345736891";
        result = employee.addEmployee(name, role, email, phone);
        assertEquals(FAILED_TO_ADD_EMP ,result);
    }

    @DisplayName("Confirm phone validation picks up char in string")
    @Test
    void invalidPhoneTest3(){
        phone = "043457368A";
        result = employee.addEmployee(name, role, email, phone);
        assertEquals(FAILED_TO_ADD_EMP ,result);
    }

    @DisplayName("Confirm phone validation correctly validates acceptable number")
    @Test
    void invalidPhoneTest4(){
        phone = "0423457368";
        result = employee.addEmployee(name, role, email, phone);
        assertEquals(SUCCEEDED_ADDING_EDP,result);
    }

    @DisplayName("Confirm successfully add employee")
    @Test
    void addEmployee() {
        result = employee.addEmployee(name, role, email, phone);
        assertEquals(SUCCEEDED_ADDING_EDP, result);
    }

    @DisplayName("Confirm error returned if the database was unable to update")
    @Test
    void addEmployeeFailedToUpdateDatabase(){
        when(mockDatabase.updateDatabase(anyString())).thenReturn(false);
        assertEquals(DATABASE_FAILED_TO_ADD,employee.addEmployee(name, role, email, phone));
    }

    @DisplayName("Confirm successfully finds employee")
    @Test
    void findValidEmployeeTest() throws Exception {
        int empID = 10;
        when(mockDatabase.queryDatabase(anyString())).thenReturn(mockResultSetEmpty);
        when(mockDatabase.queryDatabase(contains(name))).thenReturn(mockResultSetFull);
        when(mockResultSetFull.getInt(anyString())).thenReturn(empID);

        assertEquals(empID,employee.findEmployee(name));
    }

    @DisplayName("Confirm returns error when non-existent employee is searched for")
    @Test
    void findInvalidEmployee() throws Exception{
        String name = "Wrong employee";
        when(mockDatabase.queryDatabase(contains("Wrong employee"))).thenReturn(mockResultSetEmpty);
        when(mockResultSetEmpty.next()).thenReturn(false);

        assertEquals(FAILED_TO_ADD_EMP, employee.findEmployee(name));
    }

    @DisplayName("Confirm successfully removes employee")
    @Test
    void removeEmployeeTest(){
        when(mockDatabase.updateDatabase(anyString())).thenReturn(false);
        when(mockDatabase.updateDatabase(contains("1"))).thenReturn(true);

        assertTrue(employee.removeEmployee(1));
    }

    /*TODO*/
    /*if possible add  SQL statements are valid unit test
     * else use integration tests to test SQL statements are valid */
}