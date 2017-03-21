package core;

/**
 * Created by harry on 18/03/2017.
 */
public class Employee {

    public static void addEmployee(String name, String business, String employeeRole){
        Database db = new Database();

        String employeeDetailsSQL = "INSERT INTO employeeDetails(employeeID, name, business, employeeRole) values(?," +
                                    "'" + name + "'" +
                                    "'" + business + "'" +
                                    "'" + employeeRole + "'";

        db.insertIntoDatabase(employeeDetailsSQL);

    }

}
