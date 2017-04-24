package core.model;

/**
 * Created by louie on 12/03/2017.
 */
public class Booking {

    public static int addBooking(String bookingTime, String bookingDate, String bookingType){

        int custID = 1;
        int businessID = 1;
        int bookingID = 1;

        String bookingDetailsSQL = "INSERT INTO bookingDetails(bookingID, custID, businessID, empID," +
                "bookingTime, bookingDate, bookingType) values(?,"
                + bookingID +"," +
                + custID + "," +
                + businessID + "," +
                "'" + bookingTime + "'," +
                "'" + bookingDate + "'," +
                "'" + bookingType  +"')";


        if(Database.updateDatabase(bookingDetailsSQL)){
            return 1;
            }
            return 0;

    }

}
