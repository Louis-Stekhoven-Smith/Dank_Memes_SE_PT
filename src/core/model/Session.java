package core.model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Scanner;


/**
 * Created by louie on 11/03/2017.
 */
public class Session {
/*
    private String name, username, address, contactNumber;
    private Booking bookings[];
    private static final Logger log = LogManager.getLogger(Session.class.getName());

    public Session(String username){
        log.debug("Inside Session class, creating new session with user: " + username);
        this.username = username;
        if(!loadUserDetails()){
            System.out.println("user not found");
        }
    }


    public Boolean saveBookings(){

        return null;
    }
/*
    /** Helpers */
/*
    private void loadBookings(){
    }

    private Booking loadBooking(){
        return null;
    }
*/
    /** find customers details and load into session */
    /*
    private Boolean loadUserDetails() {
        log.debug("Inside loadUserDetails Method.");

        Scanner scan;
        int thisRecord = 1;
        String[] customerDetails;

        try {
            scan = new Scanner(new File("customerDetails.txt"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        while (scan.hasNext()) {
            customerDetails = scan.nextLine().split(",");
            if (username.equals(customerDetails[thisRecord])) {

                name = customerDetails[0];
                address = customerDetails[2];
                contactNumber = customerDetails[3];

                scan.close();
                return true;
            }
        }
        scan.close();
        return false;
    }
*/
    /** Mutators */
    /*
    public Boolean addBooking(){
        return null;
    }

    public Boolean removeBooking(){
        return null;
    }
*/
    /** getters **/
    /*
    public String getName(){
        return name;
    }
    public String getUsername(){
        return username;
    }
    public String getAddress(){
        return address;
    }
    public String getContactNumber (){
        return contactNumber;
    }

    public Booking[] getBooking(){
        return null;
    }
    */
}
