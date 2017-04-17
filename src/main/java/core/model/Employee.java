package core.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by harry on 18/03/2017.
 */

/**
 * This class handles the addition and removal of employees
 */
public class Employee {

    private static final Logger log = LogManager.getLogger(Employee.class.getName());

    private static Database database = Database.getInstance();

    /*
    public Employee(Database database){
        this.database = database;
    }
    */


    /**
     * Takes in employees details as parameters,
     * validates them and creates an sqlstring to
     * add a new row to empDetails table in the database
     * @param name
     * @param employeeRole
     * @param email
     * @param phone
     * @return
     */
    public static int addEmployee(String name, String employeeRole, String email, String phone){
        char first;

        /* Chaptalize first char */
        first = Character.toUpperCase(name.charAt(0));
        name = first + name.substring(1);

        log.debug("Inside addEmployee Method.");
        log.info("Adding employee details: " + name + " " + employeeRole + " " + email + " " + phone);

        int businessID = 1;

        if(!phoneValidation(phone)){
            log.debug("Failed to add employee by phone validation, returning to controller");
            return -1;
        }

        String employeeDetailsSQL = "INSERT INTO employeeDetails(businessID, name, " +
                "employeeRole, email, phone) values("
                + businessID + "," +
                "'" + name + "'," +
                "'" + employeeRole + "'," +
                "'" + email + "'," +
                "'" + phone  +"')";

        if(database.updateDatabase(employeeDetailsSQL)){
            log.debug("Successfully added employee");
            if(createEmployeeAvailability(name)){
                return 1;
            }
            return 0;
        }
        log.debug("Failed to added employee");
        return 0;

    }

    /** Add employee to the availability table */
    private static boolean createEmployeeAvailability(String name) {
        int empID;

        try {
            empID = findEmployee(name);

            String employeeAvailablitySQL = "INSERT INTO empAvailability(empID, availability) values(" +
                    "'" + empID + "'" + "," +
                    "'000,000,000,000,000,000,000')";

            if (database.updateDatabase(employeeAvailablitySQL)) {
                log.debug("Successfully added employee availability, returning to controller");
                return true;
            }
            log.debug("Failed to added availability employee, returning to controller");
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Creates an sqlstring with the parameters and removes employee from the empDetails table
     * @param empID
     * @param name
     * @return
     */
    public static int removeEmployee(int empID){
        log.debug("Inside removeEmployee Method.");
        String deleteSQL;

        log.debug("User entered only employeeID, removing employee by ID");
        deleteSQL = "DELETE FROM employeeDetails where empID = " + empID;

        if(database.updateDatabase(deleteSQL)){
            removeEmployeeAvailability(empID);
            log.debug("Successfully removed employee, returning to controller.");
            return 1;
        }
        log.debug("Failed to removed employee, returning to controller.");
        return 0;
    }

    public static void removeEmployeeAvailability(int empID){
        String deleteSQL;

        log.debug("Inside removeEmployeeAvailability Method.");
        deleteSQL = "DELETE FROM empAvailability where empID = " + empID;
        System.out.println("test");
        database.updateDatabase(deleteSQL);
    }

    /**
     * Returns the employeeID for removal from the name passed in.
     * Also serves as validation to ensure that the employee exists.
     * @param name
     * @return
     * @throws SQLException
     */
    public static int findEmployee(String name) throws SQLException {
        log.debug("Inside findEmployee Method.");
        ResultSet rs;
        int empID;
        String findEmpSQL = "SELECT empID FROM employeeDetails WHERE name = " + "'" + name + "'";

        log.debug("Querying database for emplyeeID with name" + name);
        rs = database.queryDatabase(findEmpSQL);

        if(rs.next()){
            empID = rs.getInt("empID");
            log.info("Found employeeID: " + empID);
            log.debug("Successfully found empID, returning to controller.");
            return empID;
        }
        log.debug("Failed to find empID, returning to controller.");
        return -1;
    }

    /**
     * Validates phone number
     * @param phone
     * @return
     */
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
