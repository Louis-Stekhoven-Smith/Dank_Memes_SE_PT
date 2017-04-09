package core.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Created by louie on 10/03/2017.
 */

/** This class handles authenticating login
 * attempts and associated tasks */
public class Login {

    /*TODO make username not case sensitive */
    /** Check username exists and password matches the associated username
     * Returns -1    if authentication failed
     * Returns 2 if users is a business owner
     * Returns 1 if user is a customer */

    private static final Logger log = LogManager.getLogger(Login.class.getName());

    public static int validateAttempt(String inputUsername, String inputPassword){
        String loginSQL, userName,password;
        ResultSet rs;

        log.debug("Inside validateAttempt Method");
        log.info("Validating login attempt for userName: " + inputUsername + " with password: " + inputPassword);

        inputUsername = inputUsername.toLowerCase();

        loginSQL = "SELECT userName, password, type FROM userLogin WHERE userName =" + "'" + inputUsername + "'" + " AND password =" + "'" + inputPassword + "'";
        log.debug("Querying database for username and password tuple");
        rs = Database.queryDatabase(loginSQL);

        try{
            if(rs.next()){
                userName =  rs.getString("userName").toLowerCase();
                password  = rs.getString("password");

                /* do we need to do this if statment, can we not just check if rs is empty? - Louis */
                if(userName.equals(inputUsername) && password.equals(inputPassword)){
                    if(rs.getString("type").equals("1")){
                        log.debug("Successful customer login, logged in as: " + inputUsername);
                        log.debug("Returning to Controller");
                        return 1;
                    }
                    if(rs.getString("type").equals("2")){
                        log.debug("Successful owner login, logged in as: " + inputUsername);
                        log.debug("Returning to Controller");
                        return 2;
                    }
                    return -1;
                }
            }
        }catch (SQLException e){
            log.error("SQL ERROR: " + e.getMessage());
        }

        log.debug("Failed login attempt, returning to controller");

        return -1;

    }
}