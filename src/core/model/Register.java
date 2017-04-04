package core.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by harry on 11/03/2017.
 */

public class Register {

    private static final Logger log = LogManager.getLogger(Register.class.getName());

    public enum attemptOutcome {
        SUCCESS(6), WRITE_FAIL(5), USERNAME_TAKEN(4), PHONENO_FAIL(3), PASSWORD_UNSATISFIED(2), PASSWORDS_DIFFERENT(1), EMPTY_FIELDS(0);
        private int value;

        private attemptOutcome(int value){
            this.value = value;
        }
    }


    //This function is just to make it simpler so that you don't have to call every function from main.
    public attemptOutcome registerAttempt(HashMap customerDetailsHMap){
        log.debug("Inside registerAttempt Method.");

        if(!isNotEmpty(customerDetailsHMap)) {
            return attemptOutcome.EMPTY_FIELDS;
        }
        else if(!passwordMatches(customerDetailsHMap)) {
            return attemptOutcome.PASSWORDS_DIFFERENT;
        }
        else if(!passwordCriteria(customerDetailsHMap)) {
            return attemptOutcome.PASSWORD_UNSATISFIED;
        }
        else if(!phoneNoIsAus(customerDetailsHMap)) {
            return attemptOutcome.PHONENO_FAIL;
        }
        else if(!userNameFree(customerDetailsHMap)) {
            return attemptOutcome.USERNAME_TAKEN;
        }
        else if(!writeNewCustomer(customerDetailsHMap)) {
            return attemptOutcome.WRITE_FAIL;
        }
        else return attemptOutcome.SUCCESS;
    }

    private boolean isNotEmpty(HashMap custDetailsHMap){
        log.debug("Inside isNotEmpty Method.");

        //Check none of the fields are empty
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


    private boolean passwordMatches(HashMap custDetailsHMap){
        log.debug("Inside passwordMatches Method.");

        //Check if the passwords match each other
        if(custDetailsHMap.get("password1").equals(custDetailsHMap.get("password2"))){
            log.debug("Passwords match, returning true");
            return true;
        }
        log.debug("Passwords don't match, returning false");
        return false;

    }

    private boolean passwordCriteria(HashMap custDetailsHMap){
        log.debug("Inside passwordCriteria Method.");

        String password = (String) custDetailsHMap.get("password1");

        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());

        //Check if the password matches the criteria of 8 chars, at least 1 uppercase, 1 lowercase and 1 digit
        if((password.length() < 8) || !hasLowercase || !hasUppercase || !password.matches(".*\\d+.*")) {
            log.debug("Password unsatisfactory, returning false");
            return false;
        }

        log.debug("Satisfactory password, returning true");
        return true;
    }

    private boolean phoneNoIsAus(HashMap custDetailsHMap){
        log.debug("Inside phoneNoIsAud Method.");
        String phoneNo = (String) custDetailsHMap.get("phoneNo");
        if(phoneNo.matches("^\\({0,1}((0|\\+61)(2|4|3|7|8)){0,1}\\){0,1}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{1}(\\ |-){0,1}[0-9]{3}$")){
            log.debug("Valid phone number, returning true");
            return true;
        }
        log.debug("Invalid phone number, returning false");
        return false;
    }


    private boolean userNameFree(HashMap custDetailsHMap){
        log.debug("Inside userNameFree Method.");
        //Setup with datebase
        ResultSet rs;
        //Create SQL Query
        String sqlQuery = "SELECT userName FROM userLogin WHERE userName =" + "'" + custDetailsHMap.get("userName") + "'";
        //Pass through SQL Query to database class which returns the result set
        log.debug("Querying the database for existing userName: " + custDetailsHMap.get("userName"));
        rs = Database.queryDatabase(sqlQuery);
        try{
            //If there is something in the result set then there was a matching username, return false.
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



    private boolean writeNewCustomer(HashMap custDetailsHMap){
        log.debug("Inside writeNewCustomer Method.");
        //The SQLite statements for inserting a new customers details
        String custLoginSQL = "INSERT INTO userLogin(loginID, userName, password, type) values(?," +
                                "'" + custDetailsHMap.get("userName") + "'" + "," +
                                "'" + custDetailsHMap.get("password1") + "'" + "," +
                                "'" + 1 + "')";

        //Calling the function which will insert the data into the appropriate tables
        log.debug("Inserting new customer to userLogin table.");
        Database.updateDatabase(custLoginSQL);
        //Finding the auto allocated loginID to use as a foreign key in the customer details entry
        String loginQuery = "SELECT loginID FROM userLogin WHERE userName = " + "'" + custDetailsHMap.get("userName") + "'";

        log.debug("Querying Database for new users loginID to insert as foreign key to customerDetails.");

        ResultSet rs = Database.queryDatabase(loginQuery);
        int loginID = 0;
        try {
            if(rs.next()){
                loginID = rs.getInt("loginID");
                log.debug("Login ID found: " + loginID);
            }
        } catch (SQLException e) {
            log.error("SQL ERROR: " + e.getMessage());
        }

        String custDetailsSQL = "INSERT INTO customerDetails (custID, loginID, name, userName, address, phoneNo) values(?," +
                "'" + loginID + "'" + "," +
                "'" + custDetailsHMap.get("name") + "'" + "," +
                "'" + custDetailsHMap.get("userName")+ "'" + "," +
                "'" + custDetailsHMap.get("address") + "'" + "," +
                "'" + custDetailsHMap.get("phoneNo") + "'" + ")";

        log.debug("Inserting new customer to customerDetails table.");
        Database.updateDatabase(custDetailsSQL);
        log.debug("Customer inserted successfully, returning true");
        return true;
    }


}
