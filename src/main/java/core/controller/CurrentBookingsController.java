package core.controller;

import core.model.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Konn on 28/04/2017.
 */


public class CurrentBookingsController {

    @FXML private Button btnBack;
    @FXML private Button btnBooking1;
    @FXML private Button btnBooking2;
    @FXML private Button btnBooking3;
    @FXML private Button btnBooking4;
    @FXML private Button btnBooking5;
    @FXML private Button btnCancel;
    @FXML private Label lblBookingID;
    @FXML private Label lblBusinessName;
    @FXML private Label lblEmployeeName;
    @FXML private Label lblYourName;
    @FXML private Label lblBookingType;
    @FXML private Label lblBookingTime;
    @FXML private Label lblBookingDate;
    @FXML private ComboBox<String> bookingsCombo;

    public static int userLoggedID = 1;
    public static String currentBooking = "1";
    public static String businessName ="";
    public static String employeeName ="";
    public static String yourName ="";
    public static String bookingType ="";
    public static String bookingTime ="";
    public static String bookingDate ="";
    public static String[][] myArray;

    private Database database = Database.getInstance();

    public void initialize() throws IOException, SQLException {
        ResultSet count;
        int counter;
        String findRoleTypeCount = "SELECT count(*) AS total FROM bookingDetails WHERE custID=1";
        count = database.queryDatabase(findRoleTypeCount);
        counter = count.getInt("total");
        ResultSet rs;
        retrieveBookings(counter);
    }

    public void retrieveBookings(int counter) throws IOException, SQLException {
        String findbookingsSQL = "SELECT * FROM bookingDetails WHERE custID=" + "'" + userLoggedID + "'";
        ResultSet result = database.queryDatabase(findbookingsSQL);
        ResultSetMetaData rsmd = result.getMetaData();

        ArrayList <String[]> rs = new ArrayList<String[]>();
        int columnCount = result.getMetaData().getColumnCount();
        while (result.next()) {
            String[] row = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = result.getString(i + 1);
            }
            rs.add(row);
        } System.out.println(counter);

        String[][] newArray = new String[counter][counter];

        for (int i=0; i<newArray.length; i++) {
            newArray[i] = rs.get(i);
            bookingsCombo.getItems().addAll(String.valueOf(i+1));
            System.out.println(Arrays.toString(newArray[i]));
        } myArray = newArray;
    }

    public void queries(ActionEvent event, String custName, String businessID, String empNo) throws IOException, SQLException{

        ResultSet rs2;
        String custN;
        String findcustNSQL = "SELECT name FROM customerDetails WHERE custID=" + "'" + custName + "'";
        rs2 = database.queryDatabase(findcustNSQL);
        custN = rs2.getString("name");

        ResultSet rs;
        String businessN;
        String findBusinessNSQL = "SELECT businessName FROM businessDetails WHERE businessID=" + "'" + businessID + "'";
        rs = database.queryDatabase(findBusinessNSQL);
        businessN = rs.getString("businessName");


        ResultSet rs1;
        String empN;
        String findempNSQL = "SELECT name FROM employeeDetails WHERE empID=" + "'" + empNo + "'";
        rs1 = database.queryDatabase(findempNSQL);
        empN = rs1.getString("name");

        businessName = businessN;
        employeeName = empN;
        yourName = custN;
    }

    public void comboChanged (ActionEvent event) throws IOException, SQLException {
        lblBookingID.setText(myArray[(Integer.valueOf(bookingsCombo.getValue())-1)][0]);

        String custName = myArray[(Integer.valueOf(bookingsCombo.getValue())-1)][1];
        String businessID = myArray[(Integer.valueOf(bookingsCombo.getValue())-1)][2];
        String empNo = myArray[(Integer.valueOf(bookingsCombo.getValue())-1)][3];

        queries(event, custName, businessID, empNo);

        lblBusinessName.setText(businessName);
        lblEmployeeName.setText(employeeName);
        lblYourName.setText(yourName);
        lblBookingType.setText(myArray[(Integer.valueOf(bookingsCombo.getValue())-1)][6]);
        lblBookingDate.setText(myArray[(Integer.valueOf(bookingsCombo.getValue())-1)][5]);
        lblBookingTime.setText(myArray[(Integer.valueOf(bookingsCombo.getValue())-1)][4]);
    }

    public void btnBackPressed (ActionEvent event) throws IOException {
        Parent Back_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/CustomerNavigation.fxml"));
        Scene Back_scene = new Scene (Back_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(Back_scene);
        primaryStage.show();
    }
}
