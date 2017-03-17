package core;
import java.io.File;
import java.util.Scanner;


/**
 * Created by louie on 11/03/2017.
 */
public class Session {

    private String name, username, address, contactNumber;
    private Booking bookings[];

    public Session(String username){
        this.username = username;
        if(!loadUserDetails()){
            System.out.println("user not found");
        }
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

    /** find customers details and load into session */
    private Boolean loadUserDetails() {
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
        /*TODO*/
        return null;
    }

}
