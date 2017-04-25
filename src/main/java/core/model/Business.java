package core.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by louie on 2/04/2017.
 */
public class Business {

    int businessID;
    private String businessName;
    private String owner;
    private String logo;
    private Database database;
    private ResultSet rs;
    private static final Logger log = LogManager.getLogger(Business.class.getName());

    public Business(int businessID, Database database) {
        this. businessID = businessID;
        this.database = database;
    }
        /*TODO*/
        /* possibly add employees */
        /* possibly add bookings  */

    public int getBusinessID() {
        return businessID;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getOwner() {
        return owner;
    }

    public String getLogo() {
        return logo;
    }

    /**Method for adding a new service to the database*/
    public int addNewService(String name, int length){
        log.debug("Adding new service to database");
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

        String addServiceSQL = "INSERT into availableServices(serviceID, businessID, serviceName, serviceLength) values(?, " +
                "'" + 1 + "'," +
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


}
