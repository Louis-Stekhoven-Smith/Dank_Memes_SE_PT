package core.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by louie on 31/03/2017.
 */
public class Availability {

    private String empAvailabilitySQL;
    private final Logger log = LogManager.getLogger(Availability.class.getName());
    Database database;


    public Availability(Database database){
        this.database = database;
    }


    /** Adds availability to database as a string
     *
     * @param weeklyAvailability
     * @return returns true is successful
     */
    public Boolean addAvailability(String weeklyAvailability, int empID) {
        log.debug("Inside addAvailability Method.");

        if(!createSQLString(weeklyAvailability, empID)){
            log.debug("Failed to add availability, returning to controller.");
            return false;
        }

        log.debug("Attempting to update availability in the database");
        if(!database.updateDatabase(empAvailabilitySQL)){
            log.debug("Failed to add availability, returning to controller");
            empAvailabilitySQL = "";
            return false;
        }
        empAvailabilitySQL = "";
        log.debug("Successfully added availability, returning to controller");
        return true;
    }

    /** Gets an employees current availability. If emp doesn't exist return null
     *
     * @param empID
     * @return String containing weekly availability
     */
    public String getAvailability(int empID){
        ResultSet rs;
        String findEmpSQL = "SELECT availability FROM EmpAvailability WHERE empID = " + "'" + empID + "'";

        rs = database.queryDatabase(findEmpSQL);

        try {
            return rs.getString("availability");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Injects string input into an SQL statement
     *
     * @param weeklyAvailability
     * @return false if invalid string
     */
    private Boolean createSQLString(String weeklyAvailability, int empID){

        log.debug("Inside createSQLString Method");

        if(!(weeklyAvailability.length() == 27)){
            log.info("Invalid input length" + weeklyAvailability);
            log.debug("Returning false");
            return false;
        }
        if(!(weeklyAvailability.matches("[0-9, /,]+"))){
            log.info("Invalid input non numeric" + weeklyAvailability);
            log.debug("Returning false");
            return false;
        }
        empAvailabilitySQL = "UPDATE empAvailability " +
                "SET availability =" + "'" + weeklyAvailability + "'" +
                "WHERE empID =" + "'" + empID + "'" + ";";

        log.debug("Returning true, SQLString set to: " + empAvailabilitySQL);
        return true;

    }
}
