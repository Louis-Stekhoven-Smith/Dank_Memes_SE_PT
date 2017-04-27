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

    /** Check username exists and password matches the associated username
     * Returns -1    if authentication failed
     * Returns 2 if users is a business owner
     * Returns 1 if user is a customer */
    private final Logger log = LogManager.getLogger(Login.class.getName());
    private Database database;
    private Session session;

    public Login(Database database, Session session){
        this.database = database;
        this.session = session;
    }

    public int validateAttempt(String inputUsername, String inputPassword){
        ResultSet rs;
        int loginID;
        final int CUSTOMER = 1, OWNER = 2;
        log.debug("Inside validateAttempt Method");
        log.info("Validating login attempt for userName: " + inputUsername + " with password: " + inputPassword);

        inputUsername = inputUsername.toLowerCase();

        rs = getResultSet(inputUsername, inputPassword);

        try{
            /* incorrect login details */
            if(!(rs.next())) {
                return -1;
            }
            if(getSet(rs) == CUSTOMER){
                loginID =  rs.getInt("loginID");
                session.load(inputUsername,rs.getInt(loginID),CUSTOMER);
                log.debug("Successful customer login, logged in as: " + inputUsername);
                log.debug("Returning to MainController");
                return 1;
            }
            if(getSet(rs) == OWNER){
                loginID =  rs.getInt("loginID");
                session.load(inputUsername,rs.getInt(loginID),CUSTOMER);
                log.debug("Successful owner login, logged in as: " + inputUsername);
                log.debug("Returning to MainController");
                return 2;
                }
        }catch (SQLException e){
            log.error("SQL ERROR: " + e.getMessage());
        }

        log.debug("Failed login attempt, returning to controller");
        return -1;
    }

    private ResultSet getResultSet(String inputUsername, String inputPassword) {
        String loginSQL;
        ResultSet rs;
        loginSQL = "SELECT userName, password, type, loginID FROM userLogin WHERE userName =" + "'" + inputUsername + "'" + " AND password =" + "'" + inputPassword + "'";
        log.debug("Querying database for username and password tuple");
        rs = database.queryDatabase(loginSQL);
        return rs;
    }

    private int getSet(ResultSet rs) throws SQLException {
        return rs.getInt("type");
    }
}