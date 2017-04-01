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
                createOwnerTable(state);
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
    private static void createOwnerTable(Statement state) throws SQLException {
        ResultSet rs;
        rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='businessLogin'");
        if(!rs.next()){
            System.out.println("businessLogin table does not exist. Creating now...");
            Statement businessLogin = con.createStatement();
            String sqlbusinessLogin = "CREATE TABLE businessLogin " +
                    "(businessID INTEGER not NULL, " +
                    " userName VARCHAR(40), " +
                    " password VARCHAR(40), " +
                    " PRIMARY KEY(businessID))";
            businessLogin.execute(sqlbusinessLogin);
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
                    " businessName VARCHAR(50), " +
                    " ownerName VARCHAR(40), " +
                    " userName VARCHAR(40), " +
                    " email VARCHAR(40), " +
                    " PRIMARY KEY(businessID))";
            businessDetails.execute(sqlbusinessDetails);
        }
    }

    /** this should return boolean */
    private static void createLoginTable(Statement state) throws SQLException {
        ResultSet rs;
        rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='customerLogin'");
        if(!rs.next()){
            System.out.println("customerLogin table does not exist. Creating now...");
            Statement custLogin = con.createStatement();
            String sqlCustLogin = "CREATE TABLE customerLogin " +
                    "(custID INTEGER not NULL, " +
                    " userName VARCHAR(20), " +
                    " password VARCHAR(40), " +
                    " type VARCHAR(40)," +
                    " PRIMARY KEY(custID))";
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
                    " name VARCHAR(40), " +
                    " userName VARCHAR(20), " +
                    " address VARCHAR(50), " +
                    " phoneNo VARCHAR(20), " +
                    " PRIMARY KEY (custID))";
            custDetails.execute(sqlCustDetails);
        }
    }


    private static void createEmpAvailability(Statement state)throws SQLException{
        ResultSet rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='empAvailability'");
        if(!rs.next()) {
            System.out.println("empAvailability table does not exist. Creating now...");
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

        String cust1DetailsSQL = "INSERT INTO customerDetails (custID, name, userName, address, phoneNo) values(?," +
                "'" + "Louis" + "'" + "," +
                "'" + "oldboismokey" + "'" + "," +
                "'" + "123 Fake Street" + "'" + "," +
                "'" + "0423456789" + "'" + ")";

        String cust1LoginSQL = "INSERT INTO customerLogin (custID, userName, password, type) values(?," +
                "'" + "oldboismokey" + "'" + "," +
                "'" + "Pass1234" + "'" + "," +
                "'" + 1 + "'" + ")";

        //Calling the function which will insert the data into the appropriate tables
        Database.updateDatabase(cust1DetailsSQL);
        Database.updateDatabase(cust1LoginSQL);

        String cust2DetailsSQL = "INSERT INTO customerDetails (custID, name, userName, address, phoneNo) values(?," +
                "'" + "homy" + "'" + "," +
                "'" + "homy" + "'" + "," +
                "'" + "any" + "'" + "," +
                "'" + "0478812798" + "'" + ")";

        String cust2LoginSQL = "INSERT INTO customerLogin (custID, userName, password, type) values(?," +
                "'" + "homy" + "'" + "," +
                "'" + "Homy1234" + "'" + "," +
                "'" + 2 + "'" + ")";


        Database.updateDatabase(cust2DetailsSQL);
        Database.updateDatabase(cust2LoginSQL);
    }
}
