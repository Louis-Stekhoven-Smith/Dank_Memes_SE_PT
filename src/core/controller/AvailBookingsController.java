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
import java.time.LocalDate;
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

    String test;

    @FXML
    public void initialize(){
        System.out.println("AvailBookingsController PAGE SHOW SHOWING!");
    }

    public void showDate(ActionEvent event) throws SQLException{
        LocalDate ld = dpBookingDate.getValue();
        System.out.println(ld.toString());
        String chosenType = serviceTypeController.type;
        displayTimes(chosenType);
    }

    public void displayTimes(String chosenType) throws SQLException{
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
        setNameLabels(myArray);
    }

    public void setNameLabels(String[] myArray){
        System.out.println(Arrays.toString(myArray));
        lblName1.setText(myArray[0]);
        lblName2.setText(myArray[1]);
    }
}
