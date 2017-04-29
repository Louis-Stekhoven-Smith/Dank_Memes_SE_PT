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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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


/**
 * Created by Konn on 4/04/2017.
 */

/**
 * This class controls all of the functionality and logic
 * of reserving a booking
 */
public class AvailBookingsController {

    private static final Logger log = LogManager.getLogger(AvailBookingsController.class.getName());
    private Database database = Database.getInstance();
    private Session session = Session.getInstance();
    Booking booking = new Booking(Database.getInstance());
    private ResultSet rs;

    @FXML
    private DatePicker dpBookingDate;
    //labels
    @FXML private Label lblName1;
    @FXML private Label lblName2;
    @FXML private Label lblName3;
    @FXML private Label lblName4;
    @FXML private Label lblName5;
    @FXML private Label lblName6;
    @FXML private Label lblName7;
    @FXML private Label lblName8;
    @FXML private Label lblName9;
    @FXML private Label lblName10;
    @FXML private Label lblCurrentService;
    //buttons
    @FXML private Button book1;
    @FXML private Button book2;
    @FXML private Button book3;
    @FXML private Button book4;
    @FXML private Button book5;
    @FXML private Button book6;
    @FXML private Button book7;
    @FXML private Button book8;
    @FXML private Button book9;
    @FXML private Button book10;
    //listviews
    @FXML private ListView<String> listView1;
    @FXML private ListView<String> listView2;
    @FXML private ListView<String> listView3;
    @FXML private ListView<String> listView4;
    @FXML private ListView<String> listView5;
    @FXML private ListView<String> listView6;
    @FXML private ListView<String> listView7;
    @FXML private ListView<String> listView8;
    @FXML private ListView<String> listView9;
    @FXML private ListView<String> listView10;

    //Stored booking data
    private static int futureBookingID;
    private static int empID;
    private static String employeesName;
    private static String businessName;
    private static String yourName;
    private static String bookingType;
    private static String bookingDate;
    private static String bookingTime;
    private static int businessID;


    //Load customer, business details and future booking ID
    public void initialize() {
        log.debug("AvailBookingsController PAGE SHOW SHOWING!");

        businessID = session.getBusinessSelected();
        yourName = session.getUsername();

        lblCurrentService.setText(serviceTypeController.type);
        bookingType = serviceTypeController.type;

        //Change this to session instead of booking
        String futureBookingIDSQL = "SELECT MAX(bookingID) AS highID FROM bookingDetails";
        rs = database.queryDatabase(futureBookingIDSQL);

        try{
            futureBookingID = rs.getInt("highID") + 1;
        }catch (SQLException e){
            log.error("SQL ERROR: " + e.getMessage());
        }
    }

    //Get employees which are are available for the date selected
    @FXML
    public void getEmployees(ActionEvent event){
        clearListView();
        int[] empIDs;
        String [] empAvailability;
        String dayAvailability;
        int noOfAvailEmps = 1, noOfEmps = 0, counter = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LL/yyyy");
        LocalDate bookingDateFormat = dpBookingDate.getValue();
        bookingDate = bookingDateFormat.format(formatter);
        DayOfWeek day = bookingDateFormat.getDayOfWeek();

        String getEmpsSQL = "SELECT empID FROM employeeDetails WHERE employeeRole =" + "'" + serviceTypeController.type + "' AND businessID =" + businessID;
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
            //Set labels and times available
            for(int i = 0; i < empIDs.length; i++){
                String getAvailSQL = "SELECT availability FROM empAvailability WHERE empID =" + empIDs[i];
                rs = database.queryDatabase(getAvailSQL);
                empAvailability[i] = rs.getString("availability");
                dayAvailability = booking.getDayAvailability(day, empAvailability[i]);
                if(booking.checkAvailability(dayAvailability)){
                    setLabels(empIDs[i], noOfAvailEmps);
                    setTimes(empIDs[i], dayAvailability, noOfAvailEmps);
                    noOfAvailEmps++;
                }
            }
        } catch (SQLException e){
            log.error("SQL ERROR: " + e.getMessage());
        }
    }

    //Set the labels visable for each emp
    public void setLabels(int empID, int labelNo){
        String empNameSQL = "SELECT name FROM employeeDetails WHERE empID =" + empID;
        String empName;
        rs = database.queryDatabase(empNameSQL);
        try{
            empName = rs.getString("name");
            switch(labelNo){
                case 1: lblName1.setText(empName); lblName1.setVisible(true); book1.setVisible(true); break;
                case 2: lblName2.setText(empName); lblName2.setVisible(true); book2.setVisible(true);break;
                case 3: lblName3.setText(empName); lblName3.setVisible(true); book3.setVisible(true);break;
                case 4: lblName4.setText(empName); lblName4.setVisible(true); book4.setVisible(true);break;
                case 5: lblName5.setText(empName); lblName5.setVisible(true); book5.setVisible(true);break;
                case 6: lblName6.setText(empName); lblName6.setVisible(true); book6.setVisible(true);break;
                case 7: lblName7.setText(empName); lblName7.setVisible(true); book7.setVisible(true);break;
                case 8: lblName8.setText(empName); lblName8.setVisible(true); book8.setVisible(true);break;
                case 9: lblName9.setText(empName); lblName9.setVisible(true); book9.setVisible(true);break;
                case 10: lblName10.setText(empName); lblName10.setVisible(true); book10.setVisible(true);break;
            }
        } catch (SQLException e){
            log.error("SQL ERROR: " + e.getMessage());
        }
    }

    //Set the times available for each emp
    public void setTimes(int empID, String dayAvailability, int listNo){
        //get length of service
        String getServiceLength = "SELECT serviceLength FROM availableServices WHERE serviceName =" + "'" + serviceTypeController.type + "'";
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
                switch(listNo){
                    case 1: listView1.getItems().add(time); listView1.setVisible(true); break;
                    case 2: listView2.getItems().add(time); listView2.setVisible(true); break;
                    case 3: listView3.getItems().add(time); listView3.setVisible(true); break;
                    case 4: listView4.getItems().add(time); listView4.setVisible(true); break;
                    case 5: listView5.getItems().add(time); listView5.setVisible(true); break;
                    case 6: listView6.getItems().add(time); listView6.setVisible(true); break;
                    case 7: listView7.getItems().add(time); listView7.setVisible(true); break;
                    case 8: listView8.getItems().add(time); listView8.setVisible(true); break;
                    case 9: listView9.getItems().add(time); listView9.setVisible(true); break;
                    case 10: listView10.getItems().add(time); listView10.setVisible(true); break;
                }
            }
            cal.add(Calendar.MINUTE, length);
        }

    }

    //If another date is selected, clear the list for fresh update
    public void clearListView(){
        listView1.getItems().clear();
        listView2.getItems().clear();
        listView3.getItems().clear();
        listView4.getItems().clear();
        listView5.getItems().clear();
        listView6.getItems().clear();
        listView7.getItems().clear();
        listView8.getItems().clear();
        listView9.getItems().clear();
        listView10.getItems().clear();
    }

    //set empname and time and go to confirmation page
    @FXML
    public void btnBook(ActionEvent e) throws IOException {
        String text = ((Button)e.getSource()).getId();
        switch(text){
            case "book1": bookingTime = listView1.getSelectionModel().getSelectedItem();
                          employeesName = lblName1.getText();
                          break;
            case "book2": bookingTime = listView2.getSelectionModel().getSelectedItem();
                            employeesName = lblName2.getText();
                            break;
            case "book3": bookingTime = listView3.getSelectionModel().getSelectedItem();
                            employeesName = lblName3.getText();
                            break;
            case "book4": bookingTime = listView4.getSelectionModel().getSelectedItem();
                            employeesName = lblName4.getText();
                            break;
            case "book5": bookingTime = listView5.getSelectionModel().getSelectedItem();
                            employeesName = lblName5.getText();
                            break;
            case "book6": bookingTime = listView6.getSelectionModel().getSelectedItem();
                            employeesName = lblName6.getText();
                            break;
            case "book7": bookingTime = listView7.getSelectionModel().getSelectedItem();
                            employeesName = lblName7.getText();
                            break;
            case "book8": bookingTime = listView8.getSelectionModel().getSelectedItem();
                            employeesName = lblName8.getText();
                            break;
            case "book9": bookingTime = listView9.getSelectionModel().getSelectedItem();
                            employeesName = lblName9.getText();
                            break;
            case "book10": bookingTime = listView10.getSelectionModel().getSelectedItem();
                            employeesName = lblName10.getText();
                            break;

        }

        String getEmpID = "SELECT empID FROM employeeDetails WHERE name =" + "'" + employeesName + "'";
        rs = database.queryDatabase(getEmpID);
        try{
            empID = rs.getInt("empID");
        } catch (SQLException error){
            log.error("SQL ERROR: " + error.getMessage());
        }

        Parent bookingConfirmation_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/BookingConfirmation.fxml"));
        Scene bookingConfirmation_scene = new Scene(bookingConfirmation_parent);
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(bookingConfirmation_scene);
        secondaryStage.show();
    }

    public int getFutureBookingID(){return futureBookingID;}
    public int getEmpID(){return empID;}
    public String getEmpName(){return employeesName;}
    public String getTime(){return bookingTime;}
    public String getDate(){return bookingDate;}
    public String getType(){return bookingType;}
    public String getCustName(){return yourName;}
    public String getBusinessesName(){
        String getBusNameSQL = "SELECT businessName FROM businessDetails WHERE businessID =" + businessID;
        rs = database.queryDatabase(getBusNameSQL);
        try{
            businessName = rs.getString("businessName");
        } catch (SQLException e){
            log.error("SQL ERROR: " + e.getMessage());
        }
        return businessName;
    }
    
    /** Takes user back to BusinessHome screen */
    @FXML
    public void btnBackPressed(ActionEvent event) throws IOException {
        Parent removeEmp_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/serviceType.fxml"));
        Scene removeEmp_scene = new Scene((removeEmp_parent));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(removeEmp_scene);
        primaryStage.show();
    }

}



