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
    private Button btnMorning1;

    @FXML
    private Button btnMorning2;

    @FXML
    private Button btnMidday1;

    @FXML
    private Button btnMidday2;

    @FXML
    private Button btnEvening1;

    @FXML
    private Button btnEvening2;

    @FXML
    private Label lblCurrentService;

    @FXML
    private Button btnBack;

    String test;

    @FXML
    public void initialize(){
        System.out.println("AvailBookingsController PAGE SHOW SHOWING!");
        lblCurrentService.setText(serviceTypeController.type);
    }

    //formats date chosen in easier to read format
    public void showDate(ActionEvent event) throws SQLException{
        LocalDate ld = dpBookingDate.getValue();
        System.out.println(ld.toString());

        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println(date);

        String finalDate = new SimpleDateFormat("EEE").format(date);
        System.out.println(finalDate);

        String chosenType = serviceTypeController.type;
        displayTimes(chosenType, finalDate);
    }

    //shoes available employee times based on the date and the service chosen by retrieving data from the database
    public void displayTimes(String chosenType, String finalDate) throws SQLException {
        System.out.println("Load " + chosenType + " employee & times...");

        ResultSet count;
        String counter;
        String findRoleTypeCount = "SELECT count(*) AS total FROM employeeDetails WHERE employeeRole=" + "'" + chosenType + "'";
        count = Database.queryDatabase(findRoleTypeCount);
        counter = count.getString("total");
        System.out.println("TOTAL COUNT OF THIS ROLE " + counter);
        if (counter.equals("1")) {
            singleCountDisplayTimes(chosenType,finalDate);

        } else {

            ResultSet rs;
            String name;
            String findEmpNameSQL = "SELECT name FROM employeeDetails WHERE employeeRole=" + "'" + chosenType + "'";
            rs = Database.queryDatabase(findEmpNameSQL);
            String[] myArray = new String[2];
            for (int i = 0; i < myArray.length; i++) {
                rs.next();
                name = rs.getString("name");
                myArray[i] = name;
            }

            ResultSet rs1;
            String empID;
            String findempIDSQL = "SELECT empID from employeeDetails WHERE employeeRole=" + "'" + chosenType + "'";
            rs1 = Database.queryDatabase(findempIDSQL);
            String[] myArray2 = new String[2];
            ;
            for (int i = 0; i < myArray2.length; i++) {
                rs1.next();
                empID = rs1.getString("empID");
                myArray2[i] = empID;
            }

            String firstID = myArray2[0];
            String secondID = myArray2[1];
            ResultSet rs2;
            String availability;
            String[] myArray3 = new String[2];
            ;
            for (int i = 0; i < myArray3.length; i++) {
                String findAvailabilitySQL = "SELECT availability from empAvailability WHERE empID=" + "'" + myArray2[i] + "'";
                rs2 = Database.queryDatabase(findAvailabilitySQL);
                rs2.next();
                availability = rs2.getString("availability");
                myArray3[i] = availability;
            }
            setNameLabels(myArray, myArray3, finalDate);
        }
    }

    //display times depending if there is one employee assigned to the job
    public void singleCountDisplayTimes(String chosenType, String finalDate) throws SQLException {
        ResultSet rs;
        String name;
        String findEmpNameSQL = "SELECT name FROM employeeDetails WHERE employeeRole=" + "'" + chosenType + "'";
        rs = Database.queryDatabase(findEmpNameSQL);
        String[] myArray = new String[1];
        for (int i = 0; i < myArray.length; i++) {
            rs.next();
            name = rs.getString("name");
            myArray[i] = name;
        }
        ResultSet rs1;
        String empID;
        String findempIDSQL = "SELECT empID from employeeDetails WHERE employeeRole=" + "'" + chosenType + "'";
        rs1 = Database.queryDatabase(findempIDSQL);
        String[] myArray2 = new String[1];
        ;
        for (int i = 0; i < myArray2.length; i++) {
            rs1.next();
            empID = rs1.getString("empID");
            myArray2[i] = empID;
        }

        String firstID = myArray2[0];
        ResultSet rs2;
        String availability;
        String[] myArray3 = new String[1];
        ;
        for (int i = 0; i < myArray3.length; i++) {
            String findAvailabilitySQL = "SELECT availability from empAvailability WHERE empID=" + "'" + myArray2[i] + "'";
            rs2 = Database.queryDatabase(findAvailabilitySQL);
            rs2.next();
            availability = rs2.getString("availability");
            myArray3[i] = availability;
        }
        setNameLabelsSingleCount(myArray,myArray3,finalDate);

    }

    //sets labels to the name of employee working that service
    public void setNameLabels(String[] myArray, String[] myArray3, String finalDate){
        System.out.println(Arrays.toString(myArray));
        System.out.println(Arrays.toString(myArray3));
        System.out.println(myArray3.length);
        lblName1.setText(myArray[0]);
        lblName2.setText(myArray[1]);

        String times1 = myArray3[0];
        String times2 = myArray3[1];

        String[] values = times1.split(",");
        System.out.println(Arrays.toString(values));

        String[] values2 = times2.split(",");
        System.out.println(Arrays.toString(values2));

        if (finalDate.equals("Mon")){
            String availabilityTimes = values[0];
            String availabilityTimes2 = values2[0];
            setTimeLabels1(availabilityTimes);
            setTimeLabels2(availabilityTimes2);
        } else if (finalDate.equals("Tue")) {
            String availabilityTimes = values[1];
            String availabilityTimes2 = values2[1];
            setTimeLabels1(availabilityTimes);
            setTimeLabels2(availabilityTimes2);
        } else if (finalDate.equals("Wed")) {
            String availabilityTimes = values[2];
            String availabilityTimes2 = values2[2];
            setTimeLabels1(availabilityTimes);
            setTimeLabels2(availabilityTimes2);
        } else if (finalDate.equals("Thu")) {
            String availabilityTimes = values[3];
            String availabilityTimes2 = values2[3];
            setTimeLabels1(availabilityTimes);
            setTimeLabels2(availabilityTimes2);
        } else if (finalDate.equals("Fri")) {
            String availabilityTimes = values[4];
            String availabilityTimes2 = values2[4];
            setTimeLabels1(availabilityTimes);
            setTimeLabels2(availabilityTimes2);
        } else if (finalDate.equals("Sat")) {
            String availabilityTimes = values[5];
            String availabilityTimes2 = values2[5];
            setTimeLabels1(availabilityTimes);
            setTimeLabels2(availabilityTimes2);
        } else if (finalDate.equals("Sun")) {
            String availabilityTimes = values[6];
            String availabilityTimes2 = values2[6];
            setTimeLabels1(availabilityTimes);
            setTimeLabels2(availabilityTimes2);
        } else {
            System.out.println("fail1");
        }

    }

    //sets label to single employee name
    public void setNameLabelsSingleCount(String[] myArray, String[] myArray3, String finalDate){
        System.out.println(Arrays.toString(myArray));
        System.out.println(Arrays.toString(myArray3));
        System.out.println(myArray3.length);
        lblName1.setText(myArray[0]);

        String times1 = myArray3[0];

        String[] values = times1.split(",");
        System.out.println(Arrays.toString(values));


        if (finalDate.equals("Mon")){
            String availabilityTimes = values[0];
            setTimeLabels1(availabilityTimes);
        } else if (finalDate.equals("Tue")) {
            String availabilityTimes = values[1];
            setTimeLabels1(availabilityTimes);
        } else if (finalDate.equals("Wed")) {
            String availabilityTimes = values[2];
            setTimeLabels1(availabilityTimes);
        } else if (finalDate.equals("Thu")) {
            String availabilityTimes = values[3];
            setTimeLabels1(availabilityTimes);
        } else if (finalDate.equals("Fri")) {
            String availabilityTimes = values[4];
            setTimeLabels1(availabilityTimes);
        } else if (finalDate.equals("Sat")) {
            String availabilityTimes = values[5];
            setTimeLabels1(availabilityTimes);
        } else if (finalDate.equals("Sun")) {
            String availabilityTimes = values[6];
            setTimeLabels1(availabilityTimes);
        } else {
            System.out.println("fail1");
        }

    }

    //000,001,011,111,110,100

    //sets the time buttons to available or N/A depending on availability
    public void setTimeLabels1(String availabilityTimes){
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
            System.out.println("fail2");
        }
    }

    //sets the time buttons to available or N/A depending on availability
    public void setTimeLabels2(String availabilityTimes){
        if (availabilityTimes.equals("000")){
            btnMorning2.setText("N/A");
            btnMidday2.setText("N/A");
            btnEvening2.setText("N/A");
        } else if (availabilityTimes.equals("001")){
            btnMorning2.setText("N/A");
            btnMidday2.setText("N/A");
            btnEvening2.setText("Available");
        } else if (availabilityTimes.equals("011")){
            btnMorning2.setText("N/A");
            btnMidday2.setText("Available");
            btnEvening2.setText("Available");
        } else if (availabilityTimes.equals("111")){
            btnMorning2.setText("Available");
            btnMidday2.setText("Available");
            btnEvening2.setText("Available");
        } else if (availabilityTimes.equals("110")){
            btnMorning2.setText("Available");
            btnMidday2.setText("Available");
            btnEvening2.setText("N/A");
        } else if (availabilityTimes.equals("100")){
            btnMorning2.setText("Available");
            btnMidday2.setText("N/A");
            btnEvening2.setText("N/A");
        } else if (availabilityTimes.equals("101")){
            btnMorning2.setText("Available");
            btnMidday2.setText("N/A");
            btnEvening2.setText("Available");
        } else if (availabilityTimes.equals("010")){
            btnMorning2.setText("N/A");
            btnMidday2.setText("Available");
            btnEvening2.setText("N/A");
        } else {
            System.out.println("fail3");
        }
    }

    //goes back to services page
    public void btnBackPressed(javafx.event.ActionEvent event) throws IOException{
        Parent viewBookings_parent = FXMLLoader.load(getClass().getResource("../view/ServiceType.fxml"));
        Scene viewBookings_scene = new Scene(viewBookings_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(viewBookings_scene);
        primaryStage.show();
    }
}
