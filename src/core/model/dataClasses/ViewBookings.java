package core.model.dataClasses;

import core.model.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by harry on 7/04/2017.
 */
public class ViewBookings {
    private String bookingID, custName, empName, type, time, date;

    public ViewBookings(int bookingID, int custID, int empID, String type, String time, String date){
        this.bookingID = Integer.toString(bookingID);
        custName = getCustName(custID);
        empName = getEmpName(empID);
        this.type = type;
        this.time = time;
        this.date = date;

    }

    public String getCustName(int id){
        String getCustNameSQL = "SELECT name FROM customerDetails WHERE custID = " + id;
        String custName;

        ResultSet rs = Database.queryDatabase(getCustNameSQL);
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

    public String getEmpName(int id){
        String getEmpNameSQL = "SELECT name FROM employeeDetails WHERE empID = " + id;
        String empName;
        ResultSet rs = Database.queryDatabase(getEmpNameSQL);
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
