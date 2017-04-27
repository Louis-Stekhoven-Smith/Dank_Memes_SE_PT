package core.model;

import core.controller.AvailBookingsController;

/**
 * Created by louie on 12/03/2017.
 */
public class Booking {

    private Database database;

    public Booking(Database database){this.database = database;}

    public int addBooking(int custID,String bookingTime, String bookingDate, String bookingType,int businessID){

        String bookingDetailsSQL = "INSERT INTO bookingDetails(bookingID, custID, businessID, empID, bookingTime, bookingDate, bookingType) values("
                + AvailBookingsController.futureBookingID +"," +
                + custID + "," +
                + businessID + "," +
                + AvailBookingsController.employeesIDfromName + "," +
                "'" + bookingTime + "'," +
                "'" + bookingDate + "'," +
                "'" + bookingType  +"')";


        if(database.updateDatabase(bookingDetailsSQL)){
            return 1;
            }
            return 0;

    }

}
