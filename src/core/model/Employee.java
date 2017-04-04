package core.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by harry on 18/03/2017.
 */
public class Employee {

    private static final Logger log = LogManager.getLogger(Employee.class.getName());

    public static int addEmployee(String name, String employeeRole, String email, String phone){

        log.debug("Inside addEmployee Method.");
        log.info("Adding employee details: " + name + " " + employeeRole + " " + email + " " + phone);


        int businessID = 1;
        /* Maybe call a method here to retrieve what business is
        logged in to add the business ID to the employee table
        this will be necessary for future queries of the database where we
        will want to retrieve the tuples of employees for specific businesses
         */

        if(!phoneValidation(phone)){
            log.debug("Failed to add employee by phone validation, returning to controller");
            return -1;
        }

        String employeeDetailsSQL = "INSERT INTO employeeDetails(empID, businessID, name, employeeRole, email, phone) values(?,"
                                     + businessID + "," +
                                    "'" + name + "'," +
                                    "'" + employeeRole + "'," +
                                    "'" + email + "'," +
                                    "'" + phone  +"')";

        if(Database.updateDatabase(employeeDetailsSQL)){
            log.debug("Successfully added employee, returning to controller");
            return 1;
        }
        log.debug("Failed to added employee, returning to controller");
        return 0;


    }

    public static int removeEmployee(int empID, String name){
        log.debug("Inside removeEmployee Method.");
        String deleteSQL;

        if(name.equals("")){
            log.debug("User entered only employeeID, removing employee by ID");
            deleteSQL = "DELETE FROM employeeDetails where empID = " + empID;
        }
        else {
            log.debug("User entered both employeeID and name, removing with both");
            deleteSQL = "DELETE FROM employeeDetails where empID = " + empID + " AND name = " + "'" + name + "'";
        }

        if(Database.updateDatabase(deleteSQL)){
            log.debug("Successfully removed employee, returning to controller.");
            return 1;
        }
        log.debug("Failed to removed employee, returning to controller.");
        return 0;


    }

    public static int findEmployee(String name) throws SQLException {
        log.debug("Inside findEmployee Method.");
        ResultSet rs;
        int empID;
        String findEmpSQL = "SELECT empID FROM employeeDetails WHERE name = " + "'" + name + "'";

        log.debug("Querying database for emplyeeID with name" + name);
        rs = Database.queryDatabase(findEmpSQL);

        if(rs.next()){
            empID = rs.getInt("empID");
            log.info("Found employeeID: " + empID);
            log.debug("Successfully found empID, returning to controller.");
            return empID;
        }
        log.debug("Failed to find empID, returning to controller.");
        return -1;
    }

    private static boolean phoneValidation(String phone){
        log.debug("Inside phoneValidation Method. Validating phone number: " + phone);

        if(phone.matches("^\\({0,1}((0|\\+61)(2|4|3|7|8)){0,1}\\){0,1}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{1}(\\ |-){0,1}[0-9]{3}$")){
            log.debug("Valid phone number, returning true");
            return true;
        }
        log.debug("Invalid phone number, returning false");
        return false;
    }

}
