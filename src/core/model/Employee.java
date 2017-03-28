package core.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by harry on 18/03/2017.
 */
public class Employee {

    public static void addEmployee(String name, String employeeRole){
        int businessID = 1;
        /* Maybe call a method here to retrieve what business is
        logged in to add the business ID to the employee table
        this will be necessary for future queries of the database where we
        will want to retrieve the tuples of employees for specific businesses
         */

        String employeeDetailsSQL = "INSERT INTO employeeDetails(empID, businessID, name, employeeRole) values(?,"
                                     + businessID + "," +
                                    "'" + name + "'," +
                                    "'" + employeeRole + "')";

        Database.updateDatabase(employeeDetailsSQL);

    }

    public static void removeEmployee(int empID, String name){
        String deleteSQL = "DELETE FROM employeeDetails where empID = " + empID + " AND name = " + "'" + name + "'";

        Database.updateDatabase(deleteSQL);

    }

    public static int findEmployee(String name) throws SQLException {
        ResultSet rs;
        int empID;
        String findEmpSQL = "SELECT empID FROM employeeDetails WHERE name = " + "'" + name + "'";

        rs = Database.queryDatabase(findEmpSQL);

        if(rs.next()){
            empID = rs.getInt("empID");
            return empID;
        }
        else return -1;
    }

}
