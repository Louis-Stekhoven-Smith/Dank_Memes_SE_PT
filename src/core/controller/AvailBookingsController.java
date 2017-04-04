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
            private Label lblEvening1;

    String test;

    @FXML
    public void initialize(){
        System.out.println("AvailBookingsController PAGE SHOW SHOWING!");
    }

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

    public void displayTimes(String chosenType, String finalDate) throws SQLException{
        System.out.println("Load "+chosenType+" employee & times...");
        ResultSet rs;
        String name;
        String findEmpNameSQL = "SELECT name FROM employeeDetails WHERE employeeRole="+"'"+chosenType+"'";
        rs = Database.queryDatabase(findEmpNameSQL);
        String[] myArray = new String[2];
        for (int i=0; i<myArray.length;i++) {
            rs.next();
            name = rs.getString("name");
            myArray[i]=name;
        }
        ResultSet rs1;
        String empID;
        String findempIDSQL = "SELECT empID from employeeDetails WHERE employeeRole="+"'"+chosenType+"'";
        rs1 = Database.queryDatabase(findempIDSQL);
        String[] myArray2 = new String[2];;
        for (int i=0; i<myArray2.length;i++) {
            rs1.next();
            empID = rs1.getString("empID");
            myArray2[i] = empID;
        }
        String firstID = myArray2[0];
        String secondID = myArray2[1];
        ResultSet rs2;
        String availability;
        String[] myArray3 = new String[2];;
        for (int i=0; i<myArray3.length;i++) {
            String findAvailabilitySQL = "SELECT availability from empAvailability WHERE empID="+"'"+myArray2[i]+"'";
            rs2 = Database.queryDatabase(findAvailabilitySQL);
            rs2.next();
            availability = rs2.getString("availability");
            myArray3[i] = availability;
        }
        setNameLabels(myArray, myArray3, finalDate);
    }

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

        if (finalDate.equals("Mon")){
            System.out.println("Monday");
        } else if (finalDate.equals("Tue")){
            System.out.println("Tuesday");
        } else if (finalDate.equals("Fri")){
            String availabilityTimes = values[6];
            System.out.println(availabilityTimes);
            setTimeLabels(availabilityTimes);
        } else {
            System.out.println("test-"+finalDate+"-test");
            System.out.println("fail1");
        }

    }

    public void setTimeLabels(String availabilityTimes){
        if (availabilityTimes.equals("000")){
            System.out.println("000");
        } else if (availabilityTimes.equals("100")){
            System.out.println("100");
        } else if (availabilityTimes.equals("001")){
            System.out.println("001 Success");
            lblEvening1.setText("Available");
        } else {
            System.out.println("fail2");
        }
    }
}
