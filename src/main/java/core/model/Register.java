package core.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by harry on 11/03/2017.
 */

/**
 * This class registers a new customer with the system and validates the data input
 */
public class Register {

    private static final Logger log = LogManager.getLogger(Register.class.getName());

    Database database;

    public Register(Database database){
        this.database = database;
    }

    /**
     * Validates that all of the fields have input
     * @param custDetailsHMap
     * @return
     */
    public boolean isNotEmpty(HashMap custDetailsHMap){
        log.debug("Inside isNotEmpty Method.");

        if(custDetailsHMap.get("name").equals("") ||
                custDetailsHMap.get("userName").equals("") ||
                custDetailsHMap.get("password1").equals("") ||
                custDetailsHMap.get("password2").equals("") ||
                custDetailsHMap.get("address").equals("") ||
                custDetailsHMap.get("phoneNo").equals(""))  {
            log.debug("Empty fields, returning false");
            return false;
        }

        log.debug("No empty fields, returning true");
        return true;
    }

    /**
     * Validates that the two passwords enter match
     * @param custDetailsHMap
     * @return
     */

    public boolean nameValidation (HashMap custDetailsHMap){
        String name = (String) custDetailsHMap.get("name");
        if(name.matches("[a-zA-z ]+")){
            return true;
        }
        return false;
    }
    public boolean passwordMatches(HashMap custDetailsHMap){
        log.debug("Inside passwordMatches Method.");

        if(custDetailsHMap.get("password1").equals(custDetailsHMap.get("password2"))){
            log.debug("Passwords match, returning true");
            return true;
        }
        log.debug("Passwords don't match, returning false");
        return false;

    }

    /**
     * Validates that the password satisfies the password criteria of
     * at lease one uppercase, lowercase and number and be at least 8 characters long
     * @param custDetailsHMap
     * @return
     */
    public boolean passwordCriteria(HashMap custDetailsHMap){
        String password;
        log.debug("Inside passwordCriteria Method.");

        password = (String) custDetailsHMap.get("password1");

        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());

        if((password.length() < 8) || !hasLowercase || !hasUppercase || !password.matches(".*\\d+.*")) {
            log.debug("Password unsatisfactory, returning false");
            return false;
        }
        log.debug("Satisfactory password, returning true");
        return true;
    }

    /**
     * Validates that the phone number entered is Australian
     * @param custDetailsHMap
     * @return
     */
    public boolean phoneNoIsAus(HashMap custDetailsHMap){
        String phoneNo;

        log.debug("Inside phoneNoIsAud Method.");
        phoneNo = (String) custDetailsHMap.get("phoneNo");
        if(phoneNo.matches("^\\({0,1}((0|\\+61)(2|4|3|7|8)){0,1}\\){0,1}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{1}(\\ |-){0,1}[0-9]{3}$")){
            log.debug("Valid phone number, returning true");
            return true;
        }
        log.debug("Invalid phone number, returning false");
        return false;
    }

    /**
     * Validates that the username entered it not already taken
     * @param custDetailsHMap
     * @return
     */
    public boolean userNameFree(HashMap custDetailsHMap){
        ResultSet rs;
        String sqlQuery;
        log.debug("Inside userNameFree Method.");

        sqlQuery = "SELECT userName FROM userLogin WHERE userName =" + "'" + custDetailsHMap.get("userName") + "'";
        log.debug("Querying the database for existing userName: " + custDetailsHMap.get("userName"));
        rs = database.queryDatabase(sqlQuery);
        try{
            if(rs.next()){
                log.debug("Username taken, returning false");
                return false;
            }
        }catch (SQLException e){
            log.error("SQL ERROR: " + e.getMessage());
        }
        log.debug("Username free, returning true");
        return true;
    }

    /**
     * Creates an sqlString with the new customers input and inserts into the database
     * @param custDetailsHMap
     * @return
     */
    public boolean writeNewCustomer(HashMap custDetailsHMap){
        return((writeCustomerLogin(custDetailsHMap) && writeCustomerDetails(custDetailsHMap)));
    }

    private boolean writeCustomerLogin(HashMap custDetailsHMap) {
        String custLoginSQL;
        log.debug("Inside writeNewCustomer Method.");
        custLoginSQL = "INSERT INTO userLogin(loginID, userName, password, type) values(?," +
                "'" + custDetailsHMap.get("userName") + "'" + "," +
                "'" + custDetailsHMap.get("password1") + "'" + "," +
                "'" + 1 + "')";
        log.debug("Inserting new customer to userLogin table.");
        if(!database.updateDatabase(custLoginSQL)){
            return false;
        }
        return true;
    }

    //Finding the auto allocated loginID to use as a foreign key in the customer details entry
    private boolean writeCustomerDetails(HashMap custDetailsHMap) {
        int loginID ;
        String loginQuery;
        ResultSet rs;

        loginQuery = "SELECT loginID FROM userLogin WHERE userName = " + "'" + custDetailsHMap.get("userName") + "'";
        log.debug("Querying Database for new users loginID to insert as foreign key to customerDetails.");

        rs = database.queryDatabase(loginQuery);
        loginID = extractIDFromResultSet(rs);

        if(loginID < 0){
            log.error("Was unable to find the newly created customerID");
            return false;
        }

        String custDetailsSQL = "INSERT INTO customerDetails (custID, loginID, name, userName, address, phoneNo) values(?," +
                "'" + loginID + "'" + "," +
                "'" + custDetailsHMap.get("name") + "'" + "," +
                "'" + custDetailsHMap.get("userName")+ "'" + "," +
                "'" + custDetailsHMap.get("address") + "'" + "," +
                "'" + custDetailsHMap.get("phoneNo") + "'" + ")";

        log.debug("Inserting new customer to customerDetails table.");
        if(!database.updateDatabase(custDetailsSQL)){
            log.error("Was unable to add employee to database");
            return false;
        }
        log.debug("Customer inserted successfully, returning true");
        return true;
    }

    private int extractIDFromResultSet(ResultSet rs) {
        int loginID = -1;
        try {
            if(rs.next()){
                loginID = rs.getInt("loginID");
                log.debug("Login ID found: " + loginID);
            }
        } catch (SQLException e) {
            log.error("SQL ERROR: " + e.getMessage());
        }
        return loginID;
    }
}
