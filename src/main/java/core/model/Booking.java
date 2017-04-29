package core.model;

import java.time.DayOfWeek;

/**
 * Created by louie on 12/03/2017.
 */
public class Booking {

    private Database database;
    private Session session = Session.getInstance();

    public Booking(Database database){this.database = database;}

    public int addBooking(String bookingTime, String bookingDate, String bookingType, int empID){

        int businessID = session.getBusinessSelected();
        int custID = session.getLoggedInUserId();

        String bookingDetailsSQL = "INSERT INTO bookingDetails(bookingID, custID, businessID, empID, bookingTime, bookingDate, bookingType) values(?,"
                + custID + "," +
                + businessID + "," +
                + empID + "," +
                "'" + bookingTime + "'," +
                "'" + bookingDate + "'," +
                "'" + bookingType  +"')";


        if(database.updateDatabase(bookingDetailsSQL)){
            return 1;
            }

        return 0;
    }

    //Check if employee if available for that day
    public boolean checkAvailability(String day){

        if(day.charAt(0) == '1' || day.charAt(1) == '1' || day.charAt(2) == '1'){
            return true;
        }
        return false;
    }

    //Check if emp is available for the specific ay
    public String getDayAvailability(DayOfWeek dotw, String availability){
        String[] days;
        String day = "000";
        days = availability.split(",");

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
}
