package core.model;

import java.sql.*;

/**
 * Created by harry on 18/03/2017.
 */

/** This class handles all Database functionality including the
 setting up of the database if it does not exist. Checking if the
 database does exist and setting up the tables for insertion.
 It also has a method for querying the database, inserting into
 the data base and updating entries in the database. */
public class Database {

    private static Connection con;
    private static boolean hasData = false;
    private static String DB_CONNECTION = "jdbc:sqlite:DankMemes.db";
    private static String DB_DRIVER = "org.sqlite.JDBC";

    /* this isnt working as expected??? always runs setupConnection - louis*/
    public static void setupDataBase(){
        if(con == null){
            setupConnection();

        }

    }

    private static void setupConnection(){
        try{
            Class.forName(DB_DRIVER);
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            con = DriverManager.getConnection(DB_CONNECTION);

            if(!hasData){
                hasData = true;
                Statement state = con.createStatement();

                createCustomerDetTable(state);
                createLoginTable(state);
                createBusinessDetailsTable(state);
                createEmpAvailability(state);
                createEmployeeDetTable(state);


            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    /** this should return boolean */
    private static void createEmployeeDetTable(Statement state) throws SQLException {
        ResultSet rs;
        rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='employeeDetails'");
        if(!rs.next()){
            System.out.println("employeeDetails table does not exist. Creating now...");
            Statement empDetails = con.createStatement();
            String sqlEmpDetails = "CREATE TABLE employeeDetails " +
                    "(empID INTEGER not NULL, " +
                    " businessID INTEGER not NULL, " +
                    " name VARCHAR(40), " +
                    " employeeRole VARCHAR(40), " +
                    " email VARCHAR(40), " +
                    " phone VARCHAR(40), " +
                    " PRIMARY KEY (empID), " +
                    " FOREIGN KEY (businessID) REFERENCES businessDetails (businessID))";
            empDetails.execute(sqlEmpDetails);

            /*TODO*/
             /* Just for testing remove for production !!!!!!!!!!!!!!
             need to move this somewhere better
             Add drop tables to the reset and move back into setup connection
              */
            resetDatabase();
        }
    }

    /** this should return boolean */
    private static void createBusinessDetailsTable(Statement state) throws SQLException {
        ResultSet rs;
        rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='businessDetails'");
        if(!rs.next()){
            System.out.println("businessDetails table does not exist. Creating now...");
            Statement businessDetails = con.createStatement();
            String sqlbusinessDetails = "CREATE TABLE businessDetails " +
                    "(businessID INTEGER not NULL, " +
                    " loginID INTEGER not NULL, " +
                    " businessName VARCHAR(50), " +
                    " ownerName VARCHAR(40), " +
                    " email VARCHAR(40), " +
                    " PRIMARY KEY(businessID), " +
                    " FOREIGN KEY (loginID) REFERENCES userLogin (loginID))";
            businessDetails.execute(sqlbusinessDetails);
        }
    }

    /** this should return boolean */
    private static void createLoginTable(Statement state) throws SQLException {
        ResultSet rs;
        rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='userLogin'");
        if(!rs.next()){
            System.out.println("userLogin table does not exist. Creating now...");
            Statement custLogin = con.createStatement();
            String sqlCustLogin = "CREATE TABLE userLogin " +
                    "(loginID INTEGER not NULL, " +
                    " userName VARCHAR(20), " +
                    " password VARCHAR(40), " +
                    " type VARCHAR(40)," +
                    " PRIMARY KEY(loginID))";
            custLogin.execute(sqlCustLogin);
        }
    }

    /** this should return boolean */
    private static void createCustomerDetTable(Statement state) throws SQLException {
        ResultSet rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='customerDetails'");
        if(!rs.next()) {
            System.out.println("customerDetails table does not exist. Creating now...");
            Statement custDetails = con.createStatement();
            String sqlCustDetails = "CREATE TABLE customerDetails " +
                    "(custID INTEGER not NULL, " +
                    " loginID INTEGER not NULL, " +
                    " name VARCHAR(40), " +
                    " userName VARCHAR(20), " +
                    " address VARCHAR(50), " +
                    " phoneNo VARCHAR(20), " +
                    " PRIMARY KEY (custID), " +
                    " FOREIGN KEY (loginID) REFERENCES userLogin (loginID))";
            custDetails.execute(sqlCustDetails);
        }
    }


    private static void createEmpAvailability(Statement state)throws SQLException{
        ResultSet rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='empAvailability'");
        if(!rs.next()) {
            System.out.println("EmpAvailability table does not exist. Creating now...");
            Statement empAvailability = con.createStatement();
            String sqlempAvailability = "CREATE TABLE empAvailability " +
                    "(empID INTEGER not NULL, " +
                    " availability VARCHAR(30), " +
                    " PRIMARY KEY (empID)" +
                    " FOREIGN KEY (empID) References employeeDetails(empID))";
            empAvailability.execute(sqlempAvailability);
        }

    }


    /** Takes in sqlString and returns the result as a ResultSet object */
    public static ResultSet queryDatabase(String sqlString){
        ResultSet res = null;
        try{
            Statement state = con.createStatement();
            res = state.executeQuery(sqlString);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return res;
    }

    /** this should return boolean */
    public static Boolean updateDatabase(String sqlString){
        try{
            Statement state = con.createStatement();
            //Execute insert statement
            state.executeUpdate(sqlString);
            System.out.println("The database has been modified successfully");
            return true;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }


    /** automate adding a few records to database if needed - just for testing */
    private static void resetDatabase() {

        String cust1DetailsSQL = "INSERT INTO customerDetails (custID, loginID, name, userName, address, phoneNo) values(?," +
                "'" + 1 + "'" + "," +
                "'" + "Louis" + "'" + "," +
                "'" + "oldboismokey" + "'" + "," +
                "'" + "123 Fake Street" + "'" + "," +
                "'" + "0423456789" + "'" + ")";

        String cust1LoginSQL = "INSERT INTO userLogin (loginID, userName, password, type) values(?," +
                "'" + "oldboismokey" + "'" + "," +
                "'" + "Pass1234" + "'" + "," +
                "'" + 1 + "'" + ")";

        //Calling the function which will insert the data into the appropriate tables
        Database.updateDatabase(cust1LoginSQL);
        Database.updateDatabase(cust1DetailsSQL);


        String cust2LoginSQL = "INSERT INTO userLogin(loginID, userName, password, type) values(?," +
                "'" + "homy" + "'" + "," +
                "'" + "Homy1234" + "'" + "," +
                "'" + 2 + "'" + ")";

        String bussinessOwnerSQL = "INSERT INTO businessDetails(businessID, loginID, businessName, ownerName, email) values(?," +
                "'" + 2 + "'," +
                "'" + "Dank Memes" + "'," +
                "'" + "Jeff Goodman" + "'," +
                "'" + "dankmemes@saloons.com" + "')";

        Database.updateDatabase(cust2LoginSQL);
        Database.updateDatabase(bussinessOwnerSQL);

        String emp1SQL = "INSERT INTO employeeDetails(empID, businessID, name, employeeRole, email, phone) values(?, " +
                            "'" + 1 + "'," +
                            "'" + "Sally" + "'," +
                            "'" + "Female cut" + "'," +
                            "'" + "sally@saloon.com" + "'," +
                            "'" + "0412345929" + "')";
        String emp2SQL = "INSERT INTO employeeDetails(empID, businessID, name, employeeRole, email, phone) values(?, " +
                            "'" + 1 + "'," +
                            "'" + "Bob" + "'," +
                            "'" + "Male cut" + "'," +
                            "'" + "bob@saloon.com" + "'," +
                            "'" + "0499283771" + "')";
        String emp3SQL = "INSERT INTO employeeDetails(empID, businessID, name, employeeRole, email, phone) values(?, " +
                            "'" + 1 + "'," +
                            "'" + "Katrina" + "'," +
                            "'" + "Blow" + "'," +
                            "'" + "katrina@saloon.com" + "'," +
                            "'" + "0438223141" + "')";
        String emp4SQL = "INSERT INTO employeeDetails(empID, businessID, name, employeeRole, email, phone) values(?, " +
                            "'" + 1 + "'," +
                            "'" + "Nick" + "'," +
                            "'" + "Massage wash" + "'," +
                            "'" + "nick@saloon.com" + "'," +
                            "'" + "0462116661" + "')";
        String emp5SQL = "INSERT INTO employeeDetails(empID, businessID, name, employeeRole, email, phone) values(?, " +
                            "'" + 1 + "'," +
                            "'" + "Rachel" + "'," +
                            "'" + "Female cut" + "'," +
                            "'" + "rachel@saloon.com" + "'," +
                            "'" + "0429883772" + "')";

        Database.updateDatabase(emp1SQL);
        Database.updateDatabase(emp2SQL);
        Database.updateDatabase(emp3SQL);
        Database.updateDatabase(emp4SQL);
        Database.updateDatabase(emp5SQL);

    }
}
