package core;

/**
 * Created by harry on 18/03/2017.
 */
public class Employee {

    public static void addEmployee(int businessID, String name, String employeeRole){
        Database db = new Database();

        /* Maybe call a method here to retrieve what business is
        logged in to add the business ID to the employee table
        this will be necessary for future queries of the database where we
        will want to retrieve the tuples of employees for specific businesses
         */

        String employeeDetailsSQL = "INSERT INTO employeeDetails(employeeID, businessID, name, employeeRole) values(?," +
                                    "'" + businessID + "'" +
                                    "'" + name + "'" +
                                    "'" + employeeRole + "'";

        db.updateDatabase(employeeDetailsSQL);

    }

    public static void removeEmployee(int empID, String name){
        Database db = new Database();

        String deleteSQL = "DELETE FROM employeeDetails where employeeID =" + "'" + empID + "'";

        db.updateDatabase(deleteSQL);

    }

}
