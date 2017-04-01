package core.model;

/**
 * Created by louie on 31/03/2017.
 */
public class Availability {

    private String empAvailabilitySQL;


    public Boolean addAvailability(String weeklyAvailability) {

        if(!createSQLString(weeklyAvailability)){
            return false;
        }
        /* turn into a log statment */
        System.out.println("error with update "+ empAvailabilitySQL);
        if(!Database.updateDatabase(empAvailabilitySQL)){
            return false;
        }
        return true;
    }

    private Boolean createSQLString(String weeklyAvailability){

        /*Checking intput vaild */
        if(!(weeklyAvailability.length() == 27)){
            System.out.println("Invalid input " + weeklyAvailability);
            return false;
        }
        if(!(weeklyAvailability.matches("[0-9, /,]+"))){
            System.out.println("Invalid input test " + weeklyAvailability);
            return false;
        }
        empAvailabilitySQL = "INSERT INTO empAvailability (empID, availability) values(?," +
                "'" + weeklyAvailability + "'" + ")";

        /*TODO turn this into a log statment */
        /*System.out.println(empAvailSQL);*/

        return true;


    }
}
