package core.model;

/**
 * Created by louie on 12/03/2017.
 */
public class Booking {

    private Database database;
    public static int custID = 1;
    public static int businessID = 1;

    public Booking(Database database){this.database = database;}

    public int addBooking(String bookingTime, String bookingDate, String bookingType, int empID){



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

}
