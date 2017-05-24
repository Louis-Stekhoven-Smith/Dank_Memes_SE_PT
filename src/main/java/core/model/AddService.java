package core.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by harry on 25/04/2017.
 */
public class AddService {

    private ResultSet rs;
    private Database database;
    private Session session;
    private static final Logger log = LogManager.getLogger(AddService.class.getName());

    public AddService(Database database, Session session){this.database = database; this.session = session;}

    /**Method for adding a new service to the database*/
    public int addNewService(String name, int length){
        log.debug("Adding new service to database");
        int bussinessID;
        bussinessID = session.getLoggedInUserId();
        //get businessID function here

        //Validating name is alphabetic
        if(!name.matches("[a-zA-z ]+") || name.equals("")){
            return -1;
        }
        //Validating that the role is not already in the system
        if(!roleExists(name)){
            return -2;
        }
        //Validating length is in multiples of 15
        if(length%15!=0){
            log.debug("Failed to add service, incorrectly specified length");
            return -3;
        }
        if(!tooManyServices()){
            return - 4;
        }

        String addServiceSQL = "INSERT into availableServices(serviceID, businessID, serviceName, serviceLength) values(?, " +
                "'" + bussinessID + "'," +
                "'" + name + "'," +
                "'" + length + "')";

        if(!database.updateDatabase(addServiceSQL)){
            return 0;
        }

        return 1;

    }

    /**Method to make sure that the role is not already in the system */
    public boolean roleExists(String name){
        String findRoleSQL = "SELECT serviceName FROM availableServices WHERE businessID =" +
                "'" + 1 + "' AND serviceName ='" + name + "' COLLATE NOCASE";

        rs = database.queryDatabase(findRoleSQL);
        try{
            if(rs.next()){
                return false;
            }
        } catch(SQLException e){
            log.debug("SQL ERROR: " + e.getMessage());
        }
        return true;
    }

    private boolean tooManyServices(){
        String sqlCheck = "SELECT serviceName FROM availableServices where businessID = " + session.getLoggedInUserId();
        rs = database.queryDatabase(sqlCheck);
        int counter = 0;
        try {
            while(rs.next()){
                System.out.println(counter);
                counter++;
            }
        } catch (SQLException e) {
            log.error("SQL ERROR: " + e.getMessage());
        }
        if(counter > 4){
            return false;
        }
        return true;
    }
}
