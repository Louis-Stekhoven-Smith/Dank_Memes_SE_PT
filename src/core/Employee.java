package core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by harry on 18/03/2017.
 */
public class Employee {

    public static void addEmployee(String name, String employeeRole){
        Database db = new Database();

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

        db.updateDatabase(employeeDetailsSQL);

    }

    public static void removeEmployee(int empID, String name){
        Database db = new Database();

        String deleteSQL = "DELETE FROM employeeDetails where empID = " + empID + " AND name = " + "'" + name + "'";

        db.updateDatabase(deleteSQL);

    }

    public static int findEmployee(String name) throws SQLException {
        Database db = new Database();
        ResultSet rs;
        int empID;
        String findEmpSQL = "SELECT empID FROM employeeDetails WHERE name = " + "'" + name + "'";

        rs = db.queryDatabase(findEmpSQL);

        if(rs.next()){
            empID = rs.getInt("empID");
            return empID;
        }
        else return -1;
    }

}
