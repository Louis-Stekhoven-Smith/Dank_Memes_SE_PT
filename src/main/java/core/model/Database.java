package core.model;

import core.interfaces.IDatabase;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;


/**
 * Created by harry on 18/03/2017.
 */

/** This class handles all Database functionality including the
 setting up of the database if it does not exist. Checking if the
 database does exist and setting up the tables for insertion.
 It also has a method for querying the database, inserting into
 the data base and updating entries in the database. */
public class Database implements IDatabase {

    private Connection con;
    private String DB_CONNECTION = "jdbc:sqlite:DankMemes.db";
    private String DB_DRIVER = "org.sqlite.JDBC";

    private final Logger log = LogManager.getLogger(Database.class.getName());

    private Database() {
        try{
            con = DriverManager.getConnection(DB_CONNECTION);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
        setupDatabase();
    }

    public static Database getInstance(){
        return SingletonHolder.instance;
    }

    private static final class SingletonHolder {
        static final Database instance = new Database();
    }
    /**
     * Connects to the database and creates tables if they do not exist
     * @return
     */
    private boolean setupDatabase(){
        log.debug("Inside setupDatabase Method.");

        try{
            Class.forName(DB_DRIVER);
        }
        catch (ClassNotFoundException e){
            log.error("Database Driver not found.");
            return false;
        }
        try{
            Statement state = con.createStatement();
            log.debug("Creating tables if they do not exist");
            createCustomerDetTable(state);
            createLoginTable(state);
            createBusinessDetailsTable(state);
            createAvailableServicesTable(state);
            createEmpAvailability(state);
            createBookingsTable(state);
            createEmployeeDetTable(state);
            updateBusiness();
            state.close();
        }
        catch (SQLException e){
            log.error("Failed to create tables: " + e.getMessage());
            return false;
        }
        return true;
    }

    private boolean updateBusiness(){
        String businessSQL, businessName, ownerName, email, ownerSQL,
                userName, userPassword,businessRecord[], type;
        ResultSet rs;
        int loginID, i ;

        Scanner scan;
        try {
            scan = new Scanner(new File("config.txt"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        while (scan.hasNext()) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                businessRecord = scan.nextLine().split(",");
            if(!businessRecord[0].contains("#")) {
                businessName = businessRecord[0];
                ownerName = businessRecord[1];
                email = businessRecord[2];
                userName = businessRecord[3];
                userPassword = businessRecord[4];
                type = businessRecord[5];

                ownerSQL = "INSERT INTO userLogin(loginID, userName, password, type) values(?," +
                        "'" + userName + "'" + "," +
                        "'" + userPassword + "'" + "," +
                        "'" + type + "'" + ")";

                updateDatabase(ownerSQL);

                rs = queryDatabase("SELECT loginID FROM userLogin WHERE userName = '" + userName + "'");

                try {
                    loginID = rs.getInt("loginID");

                    businessSQL = "INSERT INTO businessDetails(businessID, loginID, businessName, ownerName, email) values(?," +
                            "'" + loginID + "'," +
                            "'" + businessName + "'," +
                            "'" + ownerName + "'," +
                            "'" + email + "')";

                    updateDatabase(businessSQL);


                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    /** Creates employee details table in the database if none currently exist */
    private void createEmployeeDetTable(Statement state) throws SQLException {
        log.debug("Inside createEmployeeDetTable");
        ResultSet rs;
        rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='employeeDetails'");
        if(!rs.next()){
            log.debug("employeeDetails table does not exist. Creating now...");
            Statement empDetails = con.createStatement();
            String sqlEmpDetails = "CREATE TABLE employeeDetails " +
                    "(empID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " businessID INTEGER not NULL, " +
                    " name VARCHAR(40), " +
                    " employeeRole VARCHAR(40), " +
                    " address VARCHAR(100), " +
                    " email VARCHAR(40), " +
                    " phone VARCHAR(40), " +
                    " FOREIGN KEY (businessID) REFERENCES businessDetails (businessID))";
            empDetails.execute(sqlEmpDetails);

            /*TODO*/
             /*Just for testing remove for production !
             * need to add a check if database is already set move this out of this method
             */
            resetDatabase();
        }
    }

    /** Creates business details table in the database if none currently exist */
    private void createBusinessDetailsTable(Statement state) throws SQLException {
        log.debug("Inside createBusinessDetailsTable");
        ResultSet rs;
        rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='businessDetails'");
        if(!rs.next()){
            log.debug("businessDetails table does not exist. Creating now...");
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
            businessDetails.close();
        }
    }

    private void createAvailableServicesTable(Statement state) throws SQLException {
        log.debug("Inside createAvailableServicesTable");
        ResultSet rs;
        rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='availableServices'");
        if(!rs.next()){
            log.debug("availableServices table does not exist. Creating now...");
            Statement availableServices = con.createStatement();
            String sqlAvailableServices = "CREATE TABLE availableServices " +
                    "(serviceID INTEGER not NULL, " +
                    " businessID INTEGER not NULL, " +
                    " serviceName VARCHAR (40), " +
                    " serviceLength INTEGER not NULL, " +
                    " PRIMARY KEY(serviceID), " +
                    " FOREIGN KEY (businessID) REFERENCES businessDetails (businessID))";
            availableServices.execute(sqlAvailableServices);
            availableServices.close();
        }
    }

    /** Creates login table in the database if none currently exist */
    private void createLoginTable(Statement state) throws SQLException {
        log.debug("Inside createLoginTable");
        ResultSet rs;
        rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='userLogin'");
        if(!rs.next()){
            log.debug("userLogin table does not exist. Creating now...");
            Statement custLogin = con.createStatement();
            String sqlCustLogin = "CREATE TABLE userLogin " +
                    "(loginID INTEGER not NULL, " +
                    " userName VARCHAR(20), " +
                    " password VARCHAR(40), " +
                    " type VARCHAR(40)," +
                    " PRIMARY KEY(loginID))";
            custLogin.execute(sqlCustLogin);
            custLogin.close();
        }
    }

    /** this should return boolean */
    private void createCustomerDetTable(Statement state) throws SQLException {
        log.debug("Inside createCustomerDetTable");
        ResultSet rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='customerDetails'");
        if(!rs.next()) {
            log.debug("customerDetails table does not exist. Creating now...");
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
            custDetails.close();
        }
    }

    private void createBookingsTable(Statement state) throws SQLException {
        log.debug("Inside createBookingsTable");
        ResultSet rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='bookingDetails'");
        if(!rs.next()) {
            log.debug("bookings table does not exist. Creating now...");
            Statement bookingTable = con.createStatement();
            String bookingSQL = "CREATE TABLE bookingDetails " +
                    "(bookingID INTEGER not NULL, " +
                    " custID INTEGER not NULL, " +
                    " businessID INTEGER not NULL, " +
                    " empID INTEGER not NULL, " +
                    " bookingTime VARCHAR(5), " +
                    " bookingDate VARCHAR(8), " +
                    " bookingType VARCHAR(20), " +
                    " PRIMARY KEY (bookingID), " +
                    " FOREIGN KEY (custID) REFERENCES customerDetails (custID), " +
                    " FOREIGN KEY (businessID) REFERENCES businessDetails (BusinessID), " +
                    " FOREIGN KEY (empID) REFERENCES employeeDetails (empID))";
            bookingTable.execute(bookingSQL);
            bookingTable.close();
        }
    }

    private void createEmpAvailability(Statement state)throws SQLException{
        log.debug("Inside createEmpAvailability");
        ResultSet rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='empAvailability'");
        if(!rs.next()) {
            log.debug("empAvailability table does not exist. Creating now...");
            Statement empAvailability = con.createStatement();
            String sqlempAvailability = "CREATE TABLE empAvailability " +
                    "(empID INTEGER not NULL, " +
                    " availability VARCHAR(30), " +
                    " PRIMARY KEY (empID)" +
                    " FOREIGN KEY (empID) References employeeDetails(empID))";
            empAvailability.execute(sqlempAvailability);
            empAvailability.close();
        }

    }

    /** Takes in sqlString and returns the result as a ResultSet object */
    public ResultSet queryDatabase(String sqlString){
        log.debug("Inside queryDatabase");

        ResultSet res = null;
        try{
            log.debug("Querying the database with input string: " + sqlString);

            Statement state = con.createStatement();

            res = state.executeQuery(sqlString);

        } catch (SQLException e){
            log.error("Error querying the database: " + e.getMessage());
        }
        log.debug("Returning to calling Method");

        return res;
    }

    /** Takes in an sqlstring updates, removes or inserts into the database depending on string type */
    public Boolean updateDatabase(String sqlString){
        log.debug("Inside updateDatabase Method");

        try{
            Statement state = con.createStatement();

            //Execute insert statement
            log.debug("Updating the database with input string: " + sqlString);
            state.executeUpdate(sqlString);
            log.info("The database has been modified successfully");
            log.debug("Returning to calling Method with true");
            return true;

        } catch (SQLException e){
            log.error("Error updating the database: " + e.getMessage());
            log.debug("Returning to calling Method with false");
            return false;
        }
    }


    /** automate adding a few records to database if needed - just for testing */
    private void resetDatabase() {

        log.debug("Inside resetDatabase, inserting default values...");

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
        updateDatabase(cust1LoginSQL);
        updateDatabase(cust1DetailsSQL);


        String cust2LoginSQL = "INSERT INTO userLogin(loginID, userName, password, type) values(?," +
                "'" + "homy" + "'" + "," +
                "'" + "Homy1234" + "'" + "," +
                "'" + 2 + "'" + ")";

        String bussinessOwnerSQL = "INSERT INTO businessDetails(businessID, loginID, businessName, ownerName, email) values(?," +
                "'" + 2 + "'," +
                "'" + "Dank Memes" + "'," +
                "'" + "Homy Goodman" + "'," +
                "'" + "dankmemes@saloons.com" + "')";

        updateDatabase(cust2LoginSQL);
        updateDatabase(bussinessOwnerSQL);

        String service1SQL = "INSERT INTO availableServices(serviceID, businessID, serviceName, serviceLength) values(?, " +
                                "'" + 1 + "'," +
                                "'" + "Female cut" + "'," +
                                "'" + 60 + "')";
        String service2SQL = "INSERT INTO availableServices(serviceID, businessID, serviceName, serviceLength) values(?, " +
                                "'" + 1 + "'," +
                                "'" + "Male cut" + "'," +
                                "'" + 30 + "')";
        String service3SQL = "INSERT INTO availableServices(serviceID, businessID, serviceName, serviceLength) values(?, " +
                                "'" + 1 + "'," +
                                "'" + "Massage wash" + "'," +
                                "'" + 15 + "')";
        String service4SQL = "INSERT INTO availableServices(serviceID, businessID, serviceName, serviceLength) values(?, " +
                                "'" + 1 + "'," +
                                "'" + "Blow" + "'," +
                                "'" + 60 + "')";

        updateDatabase(service1SQL);
        updateDatabase(service2SQL);
        updateDatabase(service3SQL);
        updateDatabase(service4SQL);

        String emp1SQL = "INSERT INTO employeeDetails(empID, businessID, name, employeeRole, address, email, phone) values(?, " +
                            "'" + 1 + "'," +
                            "'" + "Sally" + "'," +
                            "'" + "Female cut" + "'," +
                            "'" + "123 Service Street, Melbourne" + "'," +
                            "'" + "sally@saloon.com" + "'," +
                            "'" + "0412345929" + "')";
        String emp2SQL = "INSERT INTO employeeDetails(empID, businessID, name, employeeRole, address, email, phone) values(?, " +
                            "'" + 1 + "'," +
                            "'" + "Bob" + "'," +
                            "'" + "Male cut" + "'," +
                            "'" + "123 sample Street, Melbourne" + "'," +
                            "'" + "bob@saloon.com" + "'," +
                            "'" + "0499283771" + "')";
        String emp3SQL = "INSERT INTO employeeDetails(empID, businessID, name, employeeRole, address, email, phone) values(?, " +
                            "'" + 1 + "'," +
                            "'" + "Katrina" + "'," +
                            "'" + "Blow" + "'," +
                            "'" + "123 handy Street, Melbourne" + "'," +
                            "'" + "katrina@saloon.com" + "'," +
                            "'" + "0438223141" + "')";
        String emp4SQL = "INSERT INTO employeeDetails(empID, businessID, name, employeeRole, address, email, phone) values(?, " +
                            "'" + 1 + "'," +
                            "'" + "Nick" + "'," +
                            "'" + "Massage wash" + "'," +
                            "'" + "123 lazy Street, Melbourne" + "'," +
                            "'" + "nick@saloon.com" + "'," +
                            "'" + "0462116661" + "')";
        String emp5SQL = "INSERT INTO employeeDetails(empID, businessID, name, employeeRole, address, email, phone) values(?, " +
                            "'" + 1 + "'," +
                            "'" + "Rachel" + "'," +
                            "'" + "Female cut" + "'," +
                            "'" + "123 bored Street, Melbourne" + "'," +
                            "'" + "rachel@saloon.com" + "'," +
                            "'" + "0429883772" + "')";


        String employeeAvailablitySQL = "INSERT INTO empAvailability(empID, availability) values(?," +
                "'110,010,100,100,100,110,111')";

        updateDatabase(emp1SQL);
        updateDatabase(employeeAvailablitySQL);
        updateDatabase(emp2SQL);
        updateDatabase(employeeAvailablitySQL);
        updateDatabase(emp3SQL);
        updateDatabase(employeeAvailablitySQL);
        updateDatabase(emp4SQL);
        updateDatabase(employeeAvailablitySQL);
        updateDatabase(emp5SQL);
        updateDatabase(employeeAvailablitySQL);

        String booking1SQL = "INSERT INTO bookingDetails(bookingID, custID, businessID, empID, bookingTime, bookingDate, bookingType) values (?," +
                            "'" + 1 + "'," +
                            "'" + 1 + "'," +
                            "'" + 1 + "'," +
                            "'" + "12:50" + "'," +
                            "'" + "29/04/17" + "'," +
                            "'" + "Female cut" + "')";

        String booking2SQL = "INSERT INTO bookingDetails(bookingID, custID, businessID, empID, bookingTime, bookingDate, bookingType) values (?," +
                            "'" + 1 + "'," +
                            "'" + 1 + "'," +
                            "'" + 2 + "'," +
                            "'" + "15:30" + "'," +
                            "'" + "01/05/17" + "'," +
                            "'" + "Male Cut" + "')";

        String booking3SQL = "INSERT INTO bookingDetails(bookingID, custID, businessID, empID, bookingTime, bookingDate, bookingType) values (?," +
                            "'" + 1 + "'," +
                            "'" + 1 + "'," +
                            "'" + 3 + "'," +
                            "'" + "09:00" + "'," +
                            "'" + "10/05/17" + "'," +
                            "'" + "Blow" + "')";

        String booking4SQL = "INSERT INTO bookingDetails(bookingID, custID, businessID, empID, bookingTime, bookingDate, bookingType) values (?," +
                            "'" + 1 + "'," +
                            "'" + 1 + "'," +
                            "'" + 4 + "'," +
                            "'" + "19:10" + "'," +
                            "'" + "12/05/17" + "'," +
                            "'" + "Massage Wash" + "')";

        String booking5SQL = "INSERT INTO bookingDetails(bookingID, custID, businessID, empID, bookingTime, bookingDate, bookingType) values (?," +
                            "'" + 1 + "'," +
                            "'" + 1 + "'," +
                            "'" + 5 + "'," +
                            "'" + "10:00" + "'," +
                            "'" + "20/05/17" + "'," +
                            "'" + "Female cut" + "')";

        updateDatabase(booking1SQL);
        updateDatabase(booking2SQL);
        updateDatabase(booking3SQL);
        updateDatabase(booking4SQL);
        updateDatabase(booking5SQL);



    }
}
