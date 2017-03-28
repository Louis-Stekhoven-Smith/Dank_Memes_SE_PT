package core;

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
    void add_then_removeEmployee() throws SQLException {
        Database db = new Database();
        db.setupDataBase();

        String name = "Harry Potter";
        String role = "Apprentice Barber";
        ResultSet rs;

        testEmployee.addEmployee(name, role);

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

        empID = testEmployee.findEmployee(name);
        testEmployee.removeEmployee(empID, name);
        rs = db.queryDatabase(sqlRemoveTest);

        assertTrue(empID >= 0);
        assertFalse(rs.next());
    }
}