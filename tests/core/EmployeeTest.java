package core;

import core.model.Database;
import core.model.Employee;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by harry on 25/03/2017.
 */
class EmployeeTest {

    Employee testEmployee = new Employee();


    @Test
    void invalidPhone(){
        String name = "Harry Potter";
        String role = "Apprentice Barber";
        String email = "potter@wizard.com";
        String phone = "1234567891";
        int result;

        result = testEmployee.addEmployee(name, role, email, phone);
        assertEquals(result, -1);
    }
    @Test
    void add_then_removeEmployee() throws SQLException {
        Database db = new Database();
        db.setupDataBase();

        String name = "Harry Potter";
        String role = "Apprentice Barber";
        String email = "potter@wizard.com";
        String phone = "0466666666";
        int result;
        ResultSet rs;

        result = testEmployee.addEmployee(name, role, email, phone);

        assertEquals(result, 1);

        //add employee tests
        String sqlAddTest = "SELECT name FROM employeeDetails WHERE name = " + "'" + name + "'";
        rs = db.queryDatabase(sqlAddTest);

        if(rs.next()){
            assertEquals(rs.getString("name"), name);
        }
        else{
            assertEquals(rs.getString("name"), name);
        }

        //Remove employee and find employee tests
        String sqlRemoveTest = "SELECT name FROM employeeDetails WHERE name = " + "'" + name + "'";
        int empID;

        //Find employee
        empID = testEmployee.findEmployee(name);

        assertTrue(empID >= 0);

        //Remove employee
        testEmployee.removeEmployee(empID, name);

        //Check if employee is in db
        rs = db.queryDatabase(sqlRemoveTest);

        assertFalse(rs.next());
    }
}