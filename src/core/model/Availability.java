package core.model;

/**
 * Created by louie on 31/03/2017.
 */
public class Availability {

    private static String empAvailabilitySQL;

    /** Adds availability to database as a string
     *
     * @param weeklyAvailability
     * @return returns true is successful
     */
    public static Boolean addAvailability(String weeklyAvailability, int empID) {

        if(!createSQLString(weeklyAvailability, empID)){
            return false;
        }
        /* turn into a log statement */
        System.out.println("updating with "+ empAvailabilitySQL);
        if(!Database.updateDatabase(empAvailabilitySQL)){
            empAvailabilitySQL = "";
            return false;
        }
        empAvailabilitySQL = "";
        return true;
    }

    /** Injects string input into an SQL statement
     *
     * @param weeklyAvailability
     * @return false if invalid string
     */
    private static Boolean createSQLString(String weeklyAvailability, int empID){

        if(!(weeklyAvailability.length() == 27)){
            System.out.println("Invalid input length" + weeklyAvailability);
            return false;
        }
        if(!(weeklyAvailability.matches("[0-9, /,]+"))){
            System.out.println("Invalid input non numeric" + weeklyAvailability);
            return false;
        }
        empAvailabilitySQL = "UPDATE empAvailability " +
                "SET availability =" + "'" + weeklyAvailability + "'" +
                "WHERE empID =" + "'" + empID + "'" + ";";


        /*TODO turn this into a log statement */
        /*System.out.println(empAvailSQL);*/
        return true;

    }
}
