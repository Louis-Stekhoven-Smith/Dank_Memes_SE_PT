package core.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;

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




}
