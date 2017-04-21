package core.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by louie on 2/04/2017.
 */
public class Business {

    private Database database;
    private ResultSet rs;
    private int businessID;

    public Business(Database database){this.database = database;}
    private static final Logger log = LogManager.getLogger(Business.class.getName());

    public int addNewService(String name, int length){
        log.debug("Adding new service to database");
        //get businessID function here

        if(!name.matches("[a-zA-z ]+") || name.equals("")){
            return -1;
        }
        if(!roleExists(name)){
            return -2;
        }
        if(length%15!=0){
            log.debug("Failed to add service, incorrectly spesified length");
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

    public void setBusinessID(int id){
        businessID = id;
    }

    public int getBusinessID(){
        return businessID;
    }


}
