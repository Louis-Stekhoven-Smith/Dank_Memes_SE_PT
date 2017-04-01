package core.model;

import core.model.Database;
import core.model.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by harry on 25/03/2017.
 */
class EmployeeTest {

    Employee testEmployee = new Employee();

    @BeforeAll
    public static void setUpDB(){
        Database db = new Database();
        db.setupDataBase();
    }

    @Test
    void invalidPhoneTest(){
        String name = "Harry Potter";
        String role = "Apprentice Barber";
        String email = "potter@wizard.com";
        String phone = "1234567891";
        int result;

        result = testEmployee.addEmployee(name, role, email, phone);
        assertEquals(result, -1);
    }


    @Test
    void addEmployee() throws SQLException {
        ResultSet rs;
        String name = "Harry Potter";
        String role = "Apprentice Barber";
        String email = "potter@wizard.com";
        String phone = "0466666666";
        testEmployee.addEmployee(name, role, email, phone);
        String sqlAddTest = "SELECT name FROM employeeDetails WHERE name = " + "'" + name + "'";
        rs = Database.queryDatabase(sqlAddTest);
        rs.next();

        assertEquals(rs.getString("name"), name);

        int empID = testEmployee.findEmployee(name);
        testEmployee.removeEmployee(empID, name);
    }

    @Test
    void findValidEmployeeTest() throws SQLException {
        String name = "Harry Potter";
        String role = "Apprentice Barber";
        String email = "potter@wizard.com";
        String phone = "0466666666";
        testEmployee.addEmployee(name, role, email, phone);
        int empID = testEmployee.findEmployee(name);

        assertTrue(empID >= 0);
        testEmployee.removeEmployee(empID, name);

    }

    @Test
    void findInvalidEmployee() throws SQLException {
        String name = "Wrong employee";
        int empID = testEmployee.findEmployee(name);

        assertTrue(empID == -1);
    }

    @Test
    void removeEmployeeTest() throws SQLException {
        String name = "Harry Potter";
        String role = "Apprentice Barber";
        String email = "potter@wizard.com";
        String phone = "0466666666";
        testEmployee.addEmployee(name, role, email, phone);
        ResultSet rs;
        String sqlRemoveTest = "SELECT name FROM employeeDetails WHERE name = " + "'" + name + "'";
        int empID = testEmployee.findEmployee(name);

        testEmployee.removeEmployee(empID, name);

        //Check if employee is in db
        rs = Database.queryDatabase(sqlRemoveTest);

        assertFalse(rs.next());
    }
}