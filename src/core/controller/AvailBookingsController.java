package core.controller;

import core.model.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Konn on 4/04/2017.
 */
public class AvailBookingsController {

    @FXML
    private DatePicker dpBookingDate;

    @FXML
    private Label lblName1;

    @FXML
    private Label lblName2;

    @FXML
    private Label lblName3;

    @FXML
    private Label lblName4;

    @FXML
    private Label lblName5;

    @FXML
    private Label lblName6;

    @FXML
    private Label lblName7;

    @FXML
    private Label lblName8;

    @FXML
    private Label lblName9;

    @FXML
    private Label lblName10;

    @FXML
    private Button btnMorning1;

    @FXML
    private Button btnMorning2;

    @FXML
    private Button btnMorning3;

    @FXML
    private Button btnMorning4;

    @FXML
    private Button btnMorning5;

    @FXML
    private Button btnMorning6;

    @FXML
    private Button btnMorning7;

    @FXML
    private Button btnMorning8;

    @FXML
    private Button btnMorning9;

    @FXML
    private Button btnMorning10;

    @FXML
    private Button btnMidday1;

    @FXML
    private Button btnMidday2;

    @FXML
    private Button btnMidday3;

    @FXML
    private Button btnMidday4;

    @FXML
    private Button btnMidday5;

    @FXML
    private Button btnMidday6;

    @FXML
    private Button btnMidday7;

    @FXML
    private Button btnMidday8;

    @FXML
    private Button btnMidday9;

    @FXML
    private Button btnMidday10;

    @FXML
    private Button btnEvening1;

    @FXML
    private Button btnEvening2;

    @FXML
    private Button btnEvening3;

    @FXML
    private Button btnEvening4;

    @FXML
    private Button btnEvening5;

    @FXML
    private Button btnEvening6;

    @FXML
    private Button btnEvening7;

    @FXML
    private Button btnEvening8;

    @FXML
    private Button btnEvening9;

    @FXML
    private Button btnEvening10;

    @FXML
    private Label lblCurrentService;

    @FXML
    public void initialize(){
        System.out.println("AvailBookingsController PAGE SHOW SHOWING!");
        lblCurrentService.setText(serviceTypeController.type);
    }

    /**formats date chosen in easier to read format */
    public void showDate(ActionEvent event) throws SQLException{
        LocalDate ld = dpBookingDate.getValue();
        System.out.println(ld.toString());

        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println(date);

        String finalDate = new SimpleDateFormat("EEE").format(date);
        System.out.println(finalDate);

        String chosenType = serviceTypeController.type;
        employeeCount(chosenType, finalDate);
    }

    /** shows available employee times based on the date and the service chosen by retrieving data from the database */
    public void employeeCount(String chosenType, String finalDate) throws SQLException {
        System.out.println("Load " + chosenType + " employee & times...");
        ResultSet count;
        int counter;
        String findRoleTypeCount = "SELECT count(*) AS total FROM employeeDetails WHERE employeeRole=" + "'" + chosenType + "'";
        count = Database.queryDatabase(findRoleTypeCount);
        counter = count.getInt("total");
        System.out.println("TOTAL COUNT OF THIS ROLE " + counter);
        retrieveAvailabilityData(chosenType, finalDate, counter);
    }


    /**display times depending if there is one employee assigned to the job */
    public void retrieveAvailabilityData(String chosenType, String finalDate, int counter) throws SQLException {
        System.out.println("COUNTER INT VALUE IS = "+counter);
        ResultSet rs;
        String name;
        String findEmpNameSQL = "SELECT name FROM employeeDetails WHERE employeeRole=" + "'" + chosenType + "'";
        rs = Database.queryDatabase(findEmpNameSQL);
        String[] myArray = new String[counter];
        for (int i = 0; i < myArray.length; i++) {
            rs.next();
            name = rs.getString("name");
            myArray[i] = name;
        }
        ResultSet rs1;
        String empID;
        String findempIDSQL = "SELECT empID from employeeDetails WHERE employeeRole=" + "'" + chosenType + "'";
        rs1 = Database.queryDatabase(findempIDSQL);
        String[] myArray2 = new String[counter];
        ;
        for (int i = 0; i < myArray2.length; i++) {
            rs1.next();
            empID = rs1.getString("empID");
            myArray2[i] = empID;
        }

        String firstID = myArray2[0];
        ResultSet rs2;
        String availability;
        String[] availabilityArray = new String[counter];
        ;
        for (int i = 0; i < availabilityArray.length; i++) {
            String findAvailabilitySQL = "SELECT availability from empAvailability WHERE empID=" + "'" + myArray2[i] + "'";
            rs2 = Database.queryDatabase(findAvailabilitySQL);
            rs2.next();
            availability = rs2.getString("availability");
            availabilityArray[i] = availability;
        } setNameLabels(myArray, availabilityArray, finalDate, counter);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /** Sets labels to the name of employee working that service*/
    public void setNameLabels(String[] myArray, String[] availabilityArray, String finalDate, int counter){
        String availabilityTimes,availabilityTimes2;

        System.out.println(Arrays.toString(myArray));
        System.out.println(Arrays.toString(availabilityArray));
        System.out.println(availabilityArray.length);

        String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};


        if (counter == 1){
            String[] values = availabilityArray[0].split(",");
            for (int i=0; i<days.length;i++)
                if (finalDate.equals(days[i])){
                    availabilityTimes = values[i];
                    setTimeLabels1(availabilityTimes, myArray);
                }
                lblName1.setVisible(true);
                btnMorning1.setVisible(true);
                btnMidday1.setVisible(true);
                btnEvening1.setVisible(true);
        } else if (counter == 2){
            String[] values = availabilityArray[0].split(",");
            String[] values2 = availabilityArray[1].split(",");
            for (int i=0; i<days.length;i++)
                if (finalDate.equals(days[i])){
                    availabilityTimes = values[i];
                    availabilityTimes2 = values2[i];
                    setTimeLabels1(availabilityTimes, myArray);
                    setTimeLabels2(availabilityTimes2, myArray);
                }
                lblName1.setVisible(true);
                btnMorning1.setVisible(true);
                btnMidday1.setVisible(true);
                btnEvening1.setVisible(true);
                lblName2.setVisible(true);
                btnMorning2.setVisible(true);
                btnMidday2.setVisible(true);
                btnEvening2.setVisible(true);
        }
    }



    //000,001,011,111,110,100

    /**sets the time buttons to available or N/A depending on availability*/
    public void setTimeLabels1(String availabilityTimes, String[] myArray){
        lblName1.setText(myArray[0]);
        if (availabilityTimes.equals("000")){
            btnMorning1.setText("N/A");
            btnMidday1.setText("N/A");
            btnEvening1.setText("N/A");
        } else if (availabilityTimes.equals("001")){
            btnMorning1.setText("N/A");
            btnMidday1.setText("N/A");
            btnEvening1.setText("Available");
        } else if (availabilityTimes.equals("011")){
            btnMorning1.setText("N/A");
            btnMidday1.setText("Available");
            btnEvening1.setText("Available");
        } else if (availabilityTimes.equals("111")){
            btnMorning1.setText("Available");
            btnMidday1.setText("Available");
            btnEvening1.setText("Available");
        } else if (availabilityTimes.equals("110")){
            btnMorning1.setText("Available");
            btnMidday1.setText("Available");
            btnEvening1.setText("N/A");
        } else if (availabilityTimes.equals("100")){
            btnMorning1.setText("Available");
            btnMidday1.setText("N/A");
            btnEvening1.setText("N/A");
        } else if (availabilityTimes.equals("101")){
            btnMorning1.setText("Available");
            btnMidday1.setText("N/A");
            btnEvening1.setText("Available");
        } else if (availabilityTimes.equals("010")){
            btnMorning1.setText("N/A");
            btnMidday1.setText("Available");
            btnEvening1.setText("N/A");
        } else {
            System.out.println("fail1");
        }
    }

    /**sets the time buttons to available or N/A depending on availability*/
    public void setTimeLabels2(String availabilityTimes2, String[]myArray){
        lblName2.setText(myArray[1]);
        if (availabilityTimes2.equals("000")){
            btnMorning2.setText("N/A");
            btnMidday2.setText("N/A");
            btnEvening2.setText("N/A");
        } else if (availabilityTimes2.equals("001")){
            btnMorning2.setText("N/A");
            btnMidday2.setText("N/A");
            btnEvening2.setText("Available");
        } else if (availabilityTimes2.equals("011")){
            btnMorning2.setText("N/A");
            btnMidday2.setText("Available");
            btnEvening2.setText("Available");
        } else if (availabilityTimes2.equals("111")){
            btnMorning2.setText("Available");
            btnMidday2.setText("Available");
            btnEvening2.setText("Available");
        } else if (availabilityTimes2.equals("110")){
            btnMorning2.setText("Available");
            btnMidday2.setText("Available");
            btnEvening2.setText("N/A");
        } else if (availabilityTimes2.equals("100")){
            btnMorning2.setText("Available");
            btnMidday2.setText("N/A");
            btnEvening2.setText("N/A");
        } else if (availabilityTimes2.equals("101")){
            btnMorning2.setText("Available");
            btnMidday2.setText("N/A");
            btnEvening2.setText("Available");
        } else if (availabilityTimes2.equals("010")){
            btnMorning2.setText("N/A");
            btnMidday2.setText("Available");
            btnEvening2.setText("N/A");
        } else {
            System.out.println("fail2");
        }
    }

    /**goes back to services page */
    public void btnBackPressed(javafx.event.ActionEvent event) throws IOException{
        Parent viewBookings_parent = FXMLLoader.load(getClass().getResource("../view/ServiceType.fxml"));
        Scene viewBookings_scene = new Scene(viewBookings_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(viewBookings_scene);
        primaryStage.show();
    }
}
