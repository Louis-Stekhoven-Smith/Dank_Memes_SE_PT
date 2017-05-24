package core.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;

/**
 * Created by louie on 12/03/2017.
 */
public class Booking {

    private Database database;
    private Session session = Session.getInstance();
    private ResultSet rs;
    private static final Logger log = LogManager.getLogger(Booking.class.getName());

    public Booking(Database database){this.database = database;}

    public int addBooking(String bookingTime, String bookingDate, String bookingType, int empID, int businessID, int custID){
        log.debug("Adding new booking");
        String bookingDetailsSQL = "INSERT INTO bookingDetails(bookingID, custID, businessID, empID, bookingTime, bookingDate, bookingType) values(?,"
                + custID + "," +
                + businessID + "," +
                + empID + "," +
                "'" + bookingTime + "'," +
                "'" + bookingDate + "'," +
                "'" + bookingType  +"')";


        if(database.updateDatabase(bookingDetailsSQL)){
            log.debug("Successfully added booking");
            return 1;
            }
        log.debug("Failed to add booking");
        return 0;
    }

    //Check if employee if available for that day
    public boolean checkAvailability(String day){

        if(day.length() != 3){
            return false;
        }

        if(day.charAt(0) == '1' || day.charAt(1) == '1' || day.charAt(2) == '1'){
            return true;
        }
        return false;
    }

    //Check if emp is available for the specific day
    public String getDayAvailability(DayOfWeek dotw, String availability){
        String[] days;
        String day = "000";
        days = availability.split(",");
        System.out.println(days.length);
        if(days.length != 7){
            return null;
        }

        switch(dotw){
            case MONDAY: day = days[0]; break;
            case TUESDAY: day = days[1]; break;
            case WEDNESDAY: day = days[2]; break;
            case THURSDAY: day = days[3]; break;
            case FRIDAY: day = days[4]; break;
            case SATURDAY: day = days[5]; break;
            case SUNDAY: day = days[6]; break;
        }
        return day;
    }

    //check employee does not already have a booking for that time
    public boolean availableSlot(String time, int empID){
        System.out.println(time.length());
        if(time.length() != 5){
            log.debug("Available slot method: Invalid format of time!");
            return false;
        }
        String checkSlot = "SELECT bookingTime FROM bookingDetails WHERE bookingTime =" + "'" + time + "'" + "AND empID =" + empID;
        rs = database.queryDatabase(checkSlot);
        try{
            if(rs.next()){
                return false;
            }
        }catch(SQLException e){
            log.error("SQL ERROR: " + e.getMessage());
        }
        return true;
    }
}
