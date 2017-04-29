package core.controller;

import core.model.Booking;
import core.model.Database;
import core.model.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by harry on 29/04/2017.
 */
public class BusinessBookingController {

    private static final Logger log = LogManager.getLogger(BusinessBookingController.class.getName());
    private Database database = Database.getInstance();
    private Session session = Session.getInstance();
    Booking booking = new Booking(Database.getInstance());
    private ResultSet rs;

    private int businessID;
    private int empID;
    private String bookingType;
    private String bookingDate;
    private String bookingTime;
    private String dayAvailability;

    @FXML ComboBox<String> comboRoles;
    @FXML DatePicker dpDate;
    @FXML ComboBox<String> comboEmps;
    @FXML ComboBox<String> comboTimes;


    public void initialize(){
        log.debug("Initializing BusinessAddBooking page");
        businessID = session.getLoggedInUserId();
        String getServices = "SELECT serviceName FROM availableServices WHERE businessID =" + businessID;
        rs = database.queryDatabase(getServices);

        try{
            while(rs.next()){
               comboRoles.getItems().add(rs.getString("serviceName"));
            }
        } catch (SQLException e){
            log.error("SQL ERROR: " + e.getMessage());
        }
    }

    public void clearAll(){
        comboEmps.getItems().clear();
        comboTimes.getItems().clear();
        dpDate.setValue(null);
    }

    public void loadEmployees(){
        if(dpDate.getValue() == null){
            return;
        }
        comboEmps.getItems().clear();
        comboTimes.getItems().clear();
        int[] empIDs;
        String [] empAvailability;
        int noOfEmps = 0, counter = 0;
        bookingType = comboRoles.getValue();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LL/yyyy");
        LocalDate bookingDateFormat = dpDate.getValue();
        bookingDate = bookingDateFormat.format(formatter);
        DayOfWeek day = bookingDateFormat.getDayOfWeek();

        String getEmpsSQL = "SELECT empID FROM employeeDetails WHERE employeeRole =" + "'" + bookingType + "' AND businessID =" + businessID;
        rs = database.queryDatabase(getEmpsSQL);

        try{
            //find amount of employees which work this service
            while(rs.next()){
                noOfEmps++;
            }
            if(noOfEmps == 0){
                log.debug("No employees found!");
                return;
            }
            empIDs = new int[noOfEmps];
            empAvailability = new String[noOfEmps];
            rs = database.queryDatabase(getEmpsSQL);
            //add employees to array
            while(rs.next()){
                empIDs[counter] = rs.getInt("empID");
                counter++;
            }

            //for each employee, get there availability, check if available for the date and if they are
            //add to combo box
            for(int i = 0; i < empIDs.length; i++){
                String getAvailSQL = "SELECT availability FROM empAvailability WHERE empID =" + empIDs[i];
                String getEmpNameSQL = "SELECT name FROM employeeDetails WHERE empID =" + empIDs[i];
                rs = database.queryDatabase(getAvailSQL);
                empAvailability[i] = rs.getString("availability");
                dayAvailability = booking.getDayAvailability(day, empAvailability[i]);
                rs = database.queryDatabase(getEmpNameSQL);
                if(booking.checkAvailability(dayAvailability)){
                    comboEmps.getItems().add("ID: "+ empIDs[i] + ": " + rs.getString("name"));
                }
            }
        } catch (SQLException e){
            log.error("SQL ERROR: " + e.getMessage());
        }
    }

    public void loadTimes(){
        empID = new Scanner(comboEmps.getValue()).useDelimiter("\\D+").nextInt();
        comboTimes.getItems().clear();
        String getServiceLength = "SELECT serviceLength FROM availableServices WHERE serviceName =" + "'" + comboRoles.getValue() + "'";
        rs = database.queryDatabase(getServiceLength);
        int length = 0;

        try{
            length = rs.getInt("serviceLength");
        } catch (SQLException e){
            log.error("SQL ERROR: " + e.getMessage());
        }

        //Set up time variables
        String startTime = "08:00";
        String startMorn = "07:59";
        String endMorn = "11:59";
        String endNoon = "16:59";
        String endTime = "21:01";
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date start = null; Date beginMorn = null; Date morn = null;Date noon = null;Date end = null;

        try {
            start = df.parse(startTime);
            beginMorn = df.parse(startMorn);
            morn = df.parse(endMorn);
            noon = df.parse(endNoon);
            end = df.parse(endTime);
        } catch (ParseException e) {
            log.error("Error passing time: " + e.getMessage());
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        //While time is before closing time
        while(cal.getTime().before(end)){
            String time = df.format(cal.getTime());
            //if emp is unavailable for morning shift, skip morning times and start at afternoon times
            if(dayAvailability.charAt(0) == '0' && cal.getTime().after(beginMorn) && cal.getTime().before(morn)){
                cal.add(Calendar.HOUR, 4);
                continue;
            }
            if(dayAvailability.charAt(1) == '0' && cal.getTime().after(morn) && cal.getTime().before(noon)){
                cal.add(Calendar.HOUR, 5);
                continue;
            }
            System.out.println(time);
            if(dayAvailability.charAt(2) == '0' && cal.getTime().after(noon)){
                return;
            }
            //if slot is not already booked
            if(booking.availableSlot(time, empID)){
                comboTimes.getItems().add(time);
            }
            cal.add(Calendar.MINUTE, length);
        }


    }

    @FXML
    public void btnAddBooking(ActionEvent event) throws IOException {

    }

    @FXML
    public void btnBack(ActionEvent event) throws IOException {
        Parent viewBooking_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/ViewBookings.fxml"));
        Scene viewBooking_scene = new Scene((viewBooking_parent));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(viewBooking_scene);
        primaryStage.show();
    }
}
