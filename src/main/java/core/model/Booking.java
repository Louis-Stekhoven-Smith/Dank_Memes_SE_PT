package core.model;

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

}
