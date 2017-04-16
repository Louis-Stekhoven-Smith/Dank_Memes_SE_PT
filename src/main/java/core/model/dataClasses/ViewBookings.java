package core.model.dataClasses;

import core.model.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by harry on 7/04/2017.
 */

/**
 * This class is used for creating an instance of a booking used in conjunction
 * with the viewBookingController to display bookings made to the owner of the business.
 */
public class ViewBookings {
    private static final Logger log = LogManager.getLogger(ViewBookings.class.getName());
    private String bookingID, custName, empName, type, time, date;

    /**
     * Constructor for a booking
     * @param bookingID
     * @param custID
     * @param empID
     * @param type
     * @param time
     * @param date
     */
    public ViewBookings(int bookingID, int custID, int empID, String type, String time, String date){
        log.debug("Creating a new booking instance to fxml");
        this.bookingID = Integer.toString(bookingID);
        custName = getCustName(custID);
        empName = getEmpName(empID);
        this.type = type;
        this.time = time;
        this.date = date;

    }


    /**
     * Method used to get a customers name from a given customerID(int)
     * @param id
     * @return
     */
    public String getCustName(int id){
        log.debug("Inside getCustName method");
        Database database = new Database();
        String getCustNameSQL = "SELECT name FROM customerDetails WHERE custID = " + id;
        String custName;

        ResultSet rs = database.queryDatabase(getCustNameSQL);
        try{
            rs.next();
            custName = rs.getString("name");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        return custName;
    }

    /**
     * Method used to find the employees name from a given empID (int)
     * @param id
     * @return
     */
    public String getEmpName(int id){
        Database database = new Database();
        log.debug("Inside getEmpName method");
        String getEmpNameSQL = "SELECT name FROM employeeDetails WHERE empID = " + id;
        String empName;
        ResultSet rs = database.queryDatabase(getEmpNameSQL);
        try{
            rs.next();
            empName = rs.getString("name");

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        return empName;
    }

    public String getBookingID(){return bookingID;}
    public void setBookingID(String bookingID){this.bookingID = bookingID;}
    public String getCustName(){return custName;}
    public void setCustName(String custName){this.custName = custName;}
    public String getEmpName(){return empName;}
    public void setEmpName(String empName){this.empName = empName;}
    public String getType(){return type;}
    public void setType(String type){this.type = type;}
    public String getTime(){return time;}
    public void setTime(String time){this.time = time;}
    public String getDate(){return date;}
    public void setDate(String date){this.date = date;}

}
