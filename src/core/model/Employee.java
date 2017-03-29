package core.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by harry on 18/03/2017.
 */
public class Employee {

    public static int addEmployee(String name, String employeeRole, String email, String phone){

        int businessID = 1;
        /* Maybe call a method here to retrieve what business is
        logged in to add the business ID to the employee table
        this will be necessary for future queries of the database where we
        will want to retrieve the tuples of employees for specific businesses
         */

        if(!phoneValidation(phone)){
            return -1;
        }

        String employeeDetailsSQL = "INSERT INTO employeeDetails(empID, businessID, name, employeeRole, email, phone) values(?,"
                                     + businessID + "," +
                                    "'" + name + "'," +
                                    "'" + employeeRole + "'," +
                                    "'" + email + "'," +
                                    "'" + phone  +"')";

        if(Database.updateDatabase(employeeDetailsSQL)){
            return 1;
        }
        else return 0;

    }

    public static void removeEmployee(int empID, String name){
        String deleteSQL;

        if(name.equals("")){
            deleteSQL = "DELETE FROM employeeDetails where empID = " + empID;
        }
        else {
            deleteSQL = "DELETE FROM employeeDetails where empID = " + empID + " AND name = " + "'" + name + "'";
        }

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

    private static boolean phoneValidation(String phone){

        if(phone.matches("^\\({0,1}((0|\\+61)(2|4|3|7|8)){0,1}\\){0,1}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{1}(\\ |-){0,1}[0-9]{3}$")){
            return true;
        }
        return false;
    }

}
