package core;


/**
 * Created by louie on 11/03/2017.
 */
public class Session {

    private String name, username, address, contactNumber;
    private Booking bookings[];

    public Session(String username){
        this.username = username;
    }


    public Boolean saveBookings(){
        /*TODO*/
        return null;
    }

    /** Helpers */
    private void loadBookings(){
        /*TODO*/
    }

    private Booking loadBooking(){
        /*TODO*/
        return null;
    }

    private void loadUserDetails(){
        /*TODO*/
    }

    /** Mutators */
    public Boolean addBooking(){
        /*TODO*/
        return null;
    }

    public Boolean removeBooking(){
        /*TODO*/
        return null;
    }

    /** getters **/
    public String getName(){
        /*TODO*/
        return null;
    }
    public String getUsername(){
        /*TODO*/
        return null;
    }
    public String getAddress(){
        /*TODO*/
        return null;
    }

    public String getContactNumber (){
        /*TODO*/
        return null;
    }

    public Booking[] getBooking(){
        /*TODO*/
        return null;
    }

}
