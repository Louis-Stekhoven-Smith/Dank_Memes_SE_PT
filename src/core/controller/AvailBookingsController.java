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
        String availabilityTimes,availabilityTimes2, availabilityTimes3, availabilityTimes4, availabilityTimes5, availabilityTimes6, availabilityTimes7, availabilityTimes8, availabilityTimes9, availabilityTimes10;

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
        } else if (counter == 3){
            String[] values = availabilityArray[0].split(",");
            String[] values2 = availabilityArray[1].split(",");
            String[] values3 = availabilityArray[2].split(",");
            for (int i=0; i<days.length;i++)
                if (finalDate.equals(days[i])){
                    availabilityTimes = values[i];
                    availabilityTimes2 = values2[i];
                    availabilityTimes3 = values3[i];
                    setTimeLabels1(availabilityTimes, myArray);
                    setTimeLabels2(availabilityTimes2, myArray);
                    setTimeLabels3(availabilityTimes3, myArray);
                }
                lblName1.setVisible(true);
                btnMorning1.setVisible(true);
                btnMidday1.setVisible(true);
                btnEvening1.setVisible(true);
                lblName2.setVisible(true);
                btnMorning2.setVisible(true);
                btnMidday2.setVisible(true);
                btnEvening2.setVisible(true);
                lblName3.setVisible(true);
                btnMorning3.setVisible(true);
                btnMidday3.setVisible(true);
                btnEvening3.setVisible(true);
        } else if (counter == 4){
            String[] values = availabilityArray[0].split(",");
            String[] values2 = availabilityArray[1].split(",");
            String[] values3 = availabilityArray[2].split(",");
            String[] values4 = availabilityArray[3].split(",");
            for (int i=0; i<days.length;i++)
                if (finalDate.equals(days[i])){
                    availabilityTimes = values[i];
                    availabilityTimes2 = values2[i];
                    availabilityTimes3 = values3[i];
                    availabilityTimes4 = values4[i];
                    setTimeLabels1(availabilityTimes, myArray);
                    setTimeLabels2(availabilityTimes2, myArray);
                    setTimeLabels3(availabilityTimes3, myArray);
                    setTimeLabels4(availabilityTimes4, myArray);
                }
            lblName1.setVisible(true);
            btnMorning1.setVisible(true);
            btnMidday1.setVisible(true);
            btnEvening1.setVisible(true);
            lblName2.setVisible(true);
            btnMorning2.setVisible(true);
            btnMidday2.setVisible(true);
            btnEvening2.setVisible(true);
            lblName3.setVisible(true);
            btnMorning3.setVisible(true);
            btnMidday3.setVisible(true);
            btnEvening3.setVisible(true);
            lblName4.setVisible(true);
            btnMorning4.setVisible(true);
            btnMidday4.setVisible(true);
            btnEvening4.setVisible(true);
        } else if (counter == 5){
            String[] values = availabilityArray[0].split(",");
            String[] values2 = availabilityArray[1].split(",");
            String[] values3 = availabilityArray[2].split(",");
            String[] values4 = availabilityArray[3].split(",");
            String[] values5 = availabilityArray[4].split(",");
            for (int i=0; i<days.length;i++)
                if (finalDate.equals(days[i])){
                    availabilityTimes = values[i];
                    availabilityTimes2 = values2[i];
                    availabilityTimes3 = values3[i];
                    availabilityTimes4 = values4[i];
                    availabilityTimes5 = values5[i];
                    setTimeLabels1(availabilityTimes, myArray);
                    setTimeLabels2(availabilityTimes2, myArray);
                    setTimeLabels3(availabilityTimes3, myArray);
                    setTimeLabels4(availabilityTimes4, myArray);
                    setTimeLabels5(availabilityTimes5, myArray);
                }
            lblName1.setVisible(true);
            btnMorning1.setVisible(true);
            btnMidday1.setVisible(true);
            btnEvening1.setVisible(true);
            lblName2.setVisible(true);
            btnMorning2.setVisible(true);
            btnMidday2.setVisible(true);
            btnEvening2.setVisible(true);
            lblName3.setVisible(true);
            btnMorning3.setVisible(true);
            btnMidday3.setVisible(true);
            btnEvening3.setVisible(true);
            lblName4.setVisible(true);
            btnMorning4.setVisible(true);
            btnMidday4.setVisible(true);
            btnEvening4.setVisible(true);
            lblName5.setVisible(true);
            btnMorning5.setVisible(true);
            btnMidday5.setVisible(true);
            btnEvening5.setVisible(true);
        } else if (counter == 6){
            String[] values = availabilityArray[0].split(",");
            String[] values2 = availabilityArray[1].split(",");
            String[] values3 = availabilityArray[2].split(",");
            String[] values4 = availabilityArray[3].split(",");
            String[] values5 = availabilityArray[4].split(",");
            String[] values6 = availabilityArray[5].split(",");
            for (int i=0; i<days.length;i++)
                if (finalDate.equals(days[i])){
                    availabilityTimes = values[i];
                    availabilityTimes2 = values2[i];
                    availabilityTimes3 = values3[i];
                    availabilityTimes4 = values4[i];
                    availabilityTimes5 = values5[i];
                    availabilityTimes6 = values6[i];
                    setTimeLabels1(availabilityTimes, myArray);
                    setTimeLabels2(availabilityTimes2, myArray);
                    setTimeLabels3(availabilityTimes3, myArray);
                    setTimeLabels4(availabilityTimes4, myArray);
                    setTimeLabels5(availabilityTimes5, myArray);
                    setTimeLabels6(availabilityTimes6, myArray);
                }
            lblName1.setVisible(true);
            btnMorning1.setVisible(true);
            btnMidday1.setVisible(true);
            btnEvening1.setVisible(true);
            lblName2.setVisible(true);
            btnMorning2.setVisible(true);
            btnMidday2.setVisible(true);
            btnEvening2.setVisible(true);
            lblName3.setVisible(true);
            btnMorning3.setVisible(true);
            btnMidday3.setVisible(true);
            btnEvening3.setVisible(true);
            lblName4.setVisible(true);
            btnMorning4.setVisible(true);
            btnMidday4.setVisible(true);
            btnEvening4.setVisible(true);
            lblName5.setVisible(true);
            btnMorning5.setVisible(true);
            btnMidday5.setVisible(true);
            btnEvening5.setVisible(true);
            lblName6.setVisible(true);
            btnMorning6.setVisible(true);
            btnMidday6.setVisible(true);
            btnEvening6.setVisible(true);
        } else if (counter == 7){
            String[] values = availabilityArray[0].split(",");
            String[] values2 = availabilityArray[1].split(",");
            String[] values3 = availabilityArray[2].split(",");
            String[] values4 = availabilityArray[3].split(",");
            String[] values5 = availabilityArray[4].split(",");
            String[] values6 = availabilityArray[5].split(",");
            String[] values7 = availabilityArray[6].split(",");
            for (int i=0; i<days.length;i++)
                if (finalDate.equals(days[i])){
                    availabilityTimes = values[i];
                    availabilityTimes2 = values2[i];
                    availabilityTimes3 = values3[i];
                    availabilityTimes4 = values4[i];
                    availabilityTimes5 = values5[i];
                    availabilityTimes6 = values6[i];
                    availabilityTimes7 = values7[i];
                    setTimeLabels1(availabilityTimes, myArray);
                    setTimeLabels2(availabilityTimes2, myArray);
                    setTimeLabels3(availabilityTimes3, myArray);
                    setTimeLabels4(availabilityTimes4, myArray);
                    setTimeLabels5(availabilityTimes5, myArray);
                    setTimeLabels6(availabilityTimes6, myArray);
                    setTimeLabels7(availabilityTimes7, myArray);
                }
            lblName1.setVisible(true);
            btnMorning1.setVisible(true);
            btnMidday1.setVisible(true);
            btnEvening1.setVisible(true);
            lblName2.setVisible(true);
            btnMorning2.setVisible(true);
            btnMidday2.setVisible(true);
            btnEvening2.setVisible(true);
            lblName3.setVisible(true);
            btnMorning3.setVisible(true);
            btnMidday3.setVisible(true);
            btnEvening3.setVisible(true);
            lblName4.setVisible(true);
            btnMorning4.setVisible(true);
            btnMidday4.setVisible(true);
            btnEvening4.setVisible(true);
            lblName5.setVisible(true);
            btnMorning5.setVisible(true);
            btnMidday5.setVisible(true);
            btnEvening5.setVisible(true);
            lblName6.setVisible(true);
            btnMorning6.setVisible(true);
            btnMidday6.setVisible(true);
            btnEvening6.setVisible(true);
            lblName7.setVisible(true);
            btnMorning7.setVisible(true);
            btnMidday7.setVisible(true);
            btnEvening7.setVisible(true);
        } else if (counter == 8){
            String[] values = availabilityArray[0].split(",");
            String[] values2 = availabilityArray[1].split(",");
            String[] values3 = availabilityArray[2].split(",");
            String[] values4 = availabilityArray[3].split(",");
            String[] values5 = availabilityArray[4].split(",");
            String[] values6 = availabilityArray[5].split(",");
            String[] values7 = availabilityArray[6].split(",");
            String[] values8 = availabilityArray[7].split(",");
            for (int i=0; i<days.length;i++)
                if (finalDate.equals(days[i])){
                    availabilityTimes = values[i];
                    availabilityTimes2 = values2[i];
                    availabilityTimes3 = values3[i];
                    availabilityTimes4 = values4[i];
                    availabilityTimes5 = values5[i];
                    availabilityTimes6 = values6[i];
                    availabilityTimes7 = values7[i];
                    availabilityTimes8 = values8[i];
                    setTimeLabels1(availabilityTimes, myArray);
                    setTimeLabels2(availabilityTimes2, myArray);
                    setTimeLabels3(availabilityTimes3, myArray);
                    setTimeLabels4(availabilityTimes4, myArray);
                    setTimeLabels5(availabilityTimes5, myArray);
                    setTimeLabels6(availabilityTimes6, myArray);
                    setTimeLabels7(availabilityTimes7, myArray);
                    setTimeLabels8(availabilityTimes8, myArray);
                }
            lblName1.setVisible(true);
            btnMorning1.setVisible(true);
            btnMidday1.setVisible(true);
            btnEvening1.setVisible(true);
            lblName2.setVisible(true);
            btnMorning2.setVisible(true);
            btnMidday2.setVisible(true);
            btnEvening2.setVisible(true);
            lblName3.setVisible(true);
            btnMorning3.setVisible(true);
            btnMidday3.setVisible(true);
            btnEvening3.setVisible(true);
            lblName4.setVisible(true);
            btnMorning4.setVisible(true);
            btnMidday4.setVisible(true);
            btnEvening4.setVisible(true);
            lblName5.setVisible(true);
            btnMorning5.setVisible(true);
            btnMidday5.setVisible(true);
            btnEvening5.setVisible(true);
            lblName6.setVisible(true);
            btnMorning6.setVisible(true);
            btnMidday6.setVisible(true);
            btnEvening6.setVisible(true);
            lblName7.setVisible(true);
            btnMorning7.setVisible(true);
            btnMidday7.setVisible(true);
            btnEvening7.setVisible(true);
            lblName8.setVisible(true);
            btnMorning8.setVisible(true);
            btnMidday8.setVisible(true);
            btnEvening8.setVisible(true);
        } else if (counter == 9){
            String[] values = availabilityArray[0].split(",");
            String[] values2 = availabilityArray[1].split(",");
            String[] values3 = availabilityArray[2].split(",");
            String[] values4 = availabilityArray[3].split(",");
            String[] values5 = availabilityArray[4].split(",");
            String[] values6 = availabilityArray[5].split(",");
            String[] values7 = availabilityArray[6].split(",");
            String[] values8 = availabilityArray[7].split(",");
            String[] values9 = availabilityArray[8].split(",");
            for (int i=0; i<days.length;i++)
                if (finalDate.equals(days[i])){
                    availabilityTimes = values[i];
                    availabilityTimes2 = values2[i];
                    availabilityTimes3 = values3[i];
                    availabilityTimes4 = values4[i];
                    availabilityTimes5 = values5[i];
                    availabilityTimes6 = values6[i];
                    availabilityTimes7 = values7[i];
                    availabilityTimes8 = values8[i];
                    availabilityTimes9 = values9[i];
                    setTimeLabels1(availabilityTimes, myArray);
                    setTimeLabels2(availabilityTimes2, myArray);
                    setTimeLabels3(availabilityTimes3, myArray);
                    setTimeLabels4(availabilityTimes4, myArray);
                    setTimeLabels5(availabilityTimes5, myArray);
                    setTimeLabels6(availabilityTimes6, myArray);
                    setTimeLabels7(availabilityTimes7, myArray);
                    setTimeLabels8(availabilityTimes8, myArray);
                    setTimeLabels9(availabilityTimes9, myArray);
                }
            lblName1.setVisible(true);
            btnMorning1.setVisible(true);
            btnMidday1.setVisible(true);
            btnEvening1.setVisible(true);
            lblName2.setVisible(true);
            btnMorning2.setVisible(true);
            btnMidday2.setVisible(true);
            btnEvening2.setVisible(true);
            lblName3.setVisible(true);
            btnMorning3.setVisible(true);
            btnMidday3.setVisible(true);
            btnEvening3.setVisible(true);
            lblName4.setVisible(true);
            btnMorning4.setVisible(true);
            btnMidday4.setVisible(true);
            btnEvening4.setVisible(true);
            lblName5.setVisible(true);
            btnMorning5.setVisible(true);
            btnMidday5.setVisible(true);
            btnEvening5.setVisible(true);
            lblName6.setVisible(true);
            btnMorning6.setVisible(true);
            btnMidday6.setVisible(true);
            btnEvening6.setVisible(true);
            lblName7.setVisible(true);
            btnMorning7.setVisible(true);
            btnMidday7.setVisible(true);
            btnEvening7.setVisible(true);
            lblName8.setVisible(true);
            btnMorning8.setVisible(true);
            btnMidday8.setVisible(true);
            btnEvening8.setVisible(true);
            lblName9.setVisible(true);
            btnMorning9.setVisible(true);
            btnMidday9.setVisible(true);
            btnEvening9.setVisible(true);
        } else if (counter == 10){
            String[] values = availabilityArray[0].split(",");
            String[] values2 = availabilityArray[1].split(",");
            String[] values3 = availabilityArray[2].split(",");
            String[] values4 = availabilityArray[3].split(",");
            String[] values5 = availabilityArray[4].split(",");
            String[] values6 = availabilityArray[5].split(",");
            String[] values7 = availabilityArray[6].split(",");
            String[] values8 = availabilityArray[7].split(",");
            String[] values9 = availabilityArray[8].split(",");
            String[] values10 = availabilityArray[9].split(",");
            for (int i=0; i<days.length;i++)
                if (finalDate.equals(days[i])){
                    availabilityTimes = values[i];
                    availabilityTimes2 = values2[i];
                    availabilityTimes3 = values3[i];
                    availabilityTimes4 = values4[i];
                    availabilityTimes5 = values5[i];
                    availabilityTimes6 = values6[i];
                    availabilityTimes7 = values7[i];
                    availabilityTimes8 = values8[i];
                    availabilityTimes9 = values9[i];
                    availabilityTimes10 = values10[i];
                    setTimeLabels1(availabilityTimes, myArray);
                    setTimeLabels2(availabilityTimes2, myArray);
                    setTimeLabels3(availabilityTimes3, myArray);
                    setTimeLabels4(availabilityTimes4, myArray);
                    setTimeLabels5(availabilityTimes5, myArray);
                    setTimeLabels6(availabilityTimes6, myArray);
                    setTimeLabels7(availabilityTimes7, myArray);
                    setTimeLabels8(availabilityTimes8, myArray);
                    setTimeLabels9(availabilityTimes9, myArray);
                    setTimeLabels10(availabilityTimes10, myArray);
                }
            lblName1.setVisible(true);
            btnMorning1.setVisible(true);
            btnMidday1.setVisible(true);
            btnEvening1.setVisible(true);
            lblName2.setVisible(true);
            btnMorning2.setVisible(true);
            btnMidday2.setVisible(true);
            btnEvening2.setVisible(true);
            lblName3.setVisible(true);
            btnMorning3.setVisible(true);
            btnMidday3.setVisible(true);
            btnEvening3.setVisible(true);
            lblName4.setVisible(true);
            btnMorning4.setVisible(true);
            btnMidday4.setVisible(true);
            btnEvening4.setVisible(true);
            lblName5.setVisible(true);
            btnMorning5.setVisible(true);
            btnMidday5.setVisible(true);
            btnEvening5.setVisible(true);
            lblName6.setVisible(true);
            btnMorning6.setVisible(true);
            btnMidday6.setVisible(true);
            btnEvening6.setVisible(true);
            lblName7.setVisible(true);
            btnMorning7.setVisible(true);
            btnMidday7.setVisible(true);
            btnEvening7.setVisible(true);
            lblName8.setVisible(true);
            btnMorning8.setVisible(true);
            btnMidday8.setVisible(true);
            btnEvening8.setVisible(true);
            lblName9.setVisible(true);
            btnMorning9.setVisible(true);
            btnMidday9.setVisible(true);
            btnEvening9.setVisible(true);
            lblName10.setVisible(true);
            btnMorning10.setVisible(true);
            btnMidday10.setVisible(true);
            btnEvening10.setVisible(true);
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

    public void setTimeLabels3(String availabilityTimes3, String[]myArray){
        lblName3.setText(myArray[2]);
        if (availabilityTimes3.equals("000")){
            btnMorning3.setText("N/A");
            btnMidday3.setText("N/A");
            btnEvening3.setText("N/A");
        } else if (availabilityTimes3.equals("001")){
            btnMorning3.setText("N/A");
            btnMidday3.setText("N/A");
            btnEvening3.setText("Available");
        } else if (availabilityTimes3.equals("011")){
            btnMorning3.setText("N/A");
            btnMidday3.setText("Available");
            btnEvening3.setText("Available");
        } else if (availabilityTimes3.equals("111")){
            btnMorning3.setText("Available");
            btnMidday3.setText("Available");
            btnEvening3.setText("Available");
        } else if (availabilityTimes3.equals("110")){
            btnMorning3.setText("Available");
            btnMidday3.setText("Available");
            btnEvening3.setText("N/A");
        } else if (availabilityTimes3.equals("100")){
            btnMorning3.setText("Available");
            btnMidday3.setText("N/A");
            btnEvening3.setText("N/A");
        } else if (availabilityTimes3.equals("101")){
            btnMorning3.setText("Available");
            btnMidday3.setText("N/A");
            btnEvening3.setText("Available");
        } else if (availabilityTimes3.equals("010")){
            btnMorning3.setText("N/A");
            btnMidday3.setText("Available");
            btnEvening3.setText("N/A");
        } else {
            System.out.println("fail3");
        }
    }

    public void setTimeLabels4(String availabilityTimes4, String[]myArray){
        lblName4.setText(myArray[3]);
        if (availabilityTimes4.equals("000")){
            btnMorning4.setText("N/A");
            btnMidday4.setText("N/A");
            btnEvening4.setText("N/A");
        } else if (availabilityTimes4.equals("001")){
            btnMorning4.setText("N/A");
            btnMidday4.setText("N/A");
            btnEvening4.setText("Available");
        } else if (availabilityTimes4.equals("011")){
            btnMorning4.setText("N/A");
            btnMidday4.setText("Available");
            btnEvening4.setText("Available");
        } else if (availabilityTimes4.equals("111")){
            btnMorning4.setText("Available");
            btnMidday4.setText("Available");
            btnEvening4.setText("Available");
        } else if (availabilityTimes4.equals("110")){
            btnMorning4.setText("Available");
            btnMidday4.setText("Available");
            btnEvening4.setText("N/A");
        } else if (availabilityTimes4.equals("100")){
            btnMorning4.setText("Available");
            btnMidday4.setText("N/A");
            btnEvening4.setText("N/A");
        } else if (availabilityTimes4.equals("101")){
            btnMorning4.setText("Available");
            btnMidday4.setText("N/A");
            btnEvening4.setText("Available");
        } else if (availabilityTimes4.equals("010")){
            btnMorning4.setText("N/A");
            btnMidday4.setText("Available");
            btnEvening4.setText("N/A");
        } else {
            System.out.println("fail4");
        }
    }

    public void setTimeLabels5(String availabilityTimes5, String[]myArray){
        lblName5.setText(myArray[4]);
        if (availabilityTimes5.equals("000")){
            btnMorning5.setText("N/A");
            btnMidday5.setText("N/A");
            btnEvening5.setText("N/A");
        } else if (availabilityTimes5.equals("001")){
            btnMorning5.setText("N/A");
            btnMidday5.setText("N/A");
            btnEvening5.setText("Available");
        } else if (availabilityTimes5.equals("011")){
            btnMorning5.setText("N/A");
            btnMidday5.setText("Available");
            btnEvening5.setText("Available");
        } else if (availabilityTimes5.equals("111")){
            btnMorning5.setText("Available");
            btnMidday5.setText("Available");
            btnEvening5.setText("Available");
        } else if (availabilityTimes5.equals("110")){
            btnMorning5.setText("Available");
            btnMidday5.setText("Available");
            btnEvening5.setText("N/A");
        } else if (availabilityTimes5.equals("100")){
            btnMorning5.setText("Available");
            btnMidday5.setText("N/A");
            btnEvening5.setText("N/A");
        } else if (availabilityTimes5.equals("101")){
            btnMorning5.setText("Available");
            btnMidday5.setText("N/A");
            btnEvening5.setText("Available");
        } else if (availabilityTimes5.equals("010")){
            btnMorning5.setText("N/A");
            btnMidday5.setText("Available");
            btnEvening5.setText("N/A");
        } else {
            System.out.println("fail5");
        }
    }

    public void setTimeLabels6(String availabilityTimes6, String[]myArray){
        lblName6.setText(myArray[5]);
        if (availabilityTimes6.equals("000")){
            btnMorning6.setText("N/A");
            btnMidday6.setText("N/A");
            btnEvening6.setText("N/A");
        } else if (availabilityTimes6.equals("001")){
            btnMorning6.setText("N/A");
            btnMidday6.setText("N/A");
            btnEvening6.setText("Available");
        } else if (availabilityTimes6.equals("011")){
            btnMorning6.setText("N/A");
            btnMidday6.setText("Available");
            btnEvening6.setText("Available");
        } else if (availabilityTimes6.equals("111")){
            btnMorning6.setText("Available");
            btnMidday6.setText("Available");
            btnEvening6.setText("Available");
        } else if (availabilityTimes6.equals("110")){
            btnMorning6.setText("Available");
            btnMidday6.setText("Available");
            btnEvening6.setText("N/A");
        } else if (availabilityTimes6.equals("100")){
            btnMorning6.setText("Available");
            btnMidday6.setText("N/A");
            btnEvening6.setText("N/A");
        } else if (availabilityTimes6.equals("101")){
            btnMorning6.setText("Available");
            btnMidday6.setText("N/A");
            btnEvening6.setText("Available");
        } else if (availabilityTimes6.equals("010")){
            btnMorning6.setText("N/A");
            btnMidday6.setText("Available");
            btnEvening6.setText("N/A");
        } else {
            System.out.println("fail6");
        }
    }

    public void setTimeLabels7(String availabilityTimes7, String[]myArray){
        lblName7.setText(myArray[6]);
        if (availabilityTimes7.equals("000")){
            btnMorning7.setText("N/A");
            btnMidday7.setText("N/A");
            btnEvening7.setText("N/A");
        } else if (availabilityTimes7.equals("001")){
            btnMorning7.setText("N/A");
            btnMidday7.setText("N/A");
            btnEvening7.setText("Available");
        } else if (availabilityTimes7.equals("011")){
            btnMorning7.setText("N/A");
            btnMidday7.setText("Available");
            btnEvening7.setText("Available");
        } else if (availabilityTimes7.equals("111")){
            btnMorning7.setText("Available");
            btnMidday7.setText("Available");
            btnEvening7.setText("Available");
        } else if (availabilityTimes7.equals("110")){
            btnMorning7.setText("Available");
            btnMidday7.setText("Available");
            btnEvening7.setText("N/A");
        } else if (availabilityTimes7.equals("100")){
            btnMorning7.setText("Available");
            btnMidday7.setText("N/A");
            btnEvening7.setText("N/A");
        } else if (availabilityTimes7.equals("101")){
            btnMorning7.setText("Available");
            btnMidday7.setText("N/A");
            btnEvening7.setText("Available");
        } else if (availabilityTimes7.equals("010")){
            btnMorning7.setText("N/A");
            btnMidday7.setText("Available");
            btnEvening7.setText("N/A");
        } else {
            System.out.println("fail7");
        }
    }

    public void setTimeLabels8(String availabilityTimes8, String[]myArray){
        lblName8.setText(myArray[7]);
        if (availabilityTimes8.equals("000")){
            btnMorning8.setText("N/A");
            btnMidday8.setText("N/A");
            btnEvening8.setText("N/A");
        } else if (availabilityTimes8.equals("001")){
            btnMorning8.setText("N/A");
            btnMidday8.setText("N/A");
            btnEvening8.setText("Available");
        } else if (availabilityTimes8.equals("011")){
            btnMorning8.setText("N/A");
            btnMidday8.setText("Available");
            btnEvening8.setText("Available");
        } else if (availabilityTimes8.equals("111")){
            btnMorning8.setText("Available");
            btnMidday8.setText("Available");
            btnEvening8.setText("Available");
        } else if (availabilityTimes8.equals("110")){
            btnMorning8.setText("Available");
            btnMidday8.setText("Available");
            btnEvening8.setText("N/A");
        } else if (availabilityTimes8.equals("100")){
            btnMorning8.setText("Available");
            btnMidday8.setText("N/A");
            btnEvening8.setText("N/A");
        } else if (availabilityTimes8.equals("101")){
            btnMorning8.setText("Available");
            btnMidday8.setText("N/A");
            btnEvening8.setText("Available");
        } else if (availabilityTimes8.equals("010")){
            btnMorning8.setText("N/A");
            btnMidday8.setText("Available");
            btnEvening8.setText("N/A");
        } else {
            System.out.println("fail8");
        }
    }

    public void setTimeLabels9(String availabilityTimes9, String[]myArray){
        lblName9.setText(myArray[8]);
        if (availabilityTimes9.equals("000")){
            btnMorning9.setText("N/A");
            btnMidday9.setText("N/A");
            btnEvening9.setText("N/A");
        } else if (availabilityTimes9.equals("001")){
            btnMorning9.setText("N/A");
            btnMidday9.setText("N/A");
            btnEvening9.setText("Available");
        } else if (availabilityTimes9.equals("011")){
            btnMorning9.setText("N/A");
            btnMidday9.setText("Available");
            btnEvening9.setText("Available");
        } else if (availabilityTimes9.equals("111")){
            btnMorning9.setText("Available");
            btnMidday9.setText("Available");
            btnEvening9.setText("Available");
        } else if (availabilityTimes9.equals("110")){
            btnMorning9.setText("Available");
            btnMidday9.setText("Available");
            btnEvening9.setText("N/A");
        } else if (availabilityTimes9.equals("100")){
            btnMorning9.setText("Available");
            btnMidday9.setText("N/A");
            btnEvening9.setText("N/A");
        } else if (availabilityTimes9.equals("101")){
            btnMorning9.setText("Available");
            btnMidday9.setText("N/A");
            btnEvening9.setText("Available");
        } else if (availabilityTimes9.equals("010")){
            btnMorning9.setText("N/A");
            btnMidday9.setText("Available");
            btnEvening9.setText("N/A");
        } else {
            System.out.println("fail9");
        }
    }

    public void setTimeLabels10(String availabilityTimes10, String[]myArray){
        lblName10.setText(myArray[9]);
        if (availabilityTimes10.equals("000")){
            btnMorning10.setText("N/A");
            btnMidday10.setText("N/A");
            btnEvening10.setText("N/A");
        } else if (availabilityTimes10.equals("001")){
            btnMorning10.setText("N/A");
            btnMidday10.setText("N/A");
            btnEvening10.setText("Available");
        } else if (availabilityTimes10.equals("011")){
            btnMorning10.setText("N/A");
            btnMidday10.setText("Available");
            btnEvening10.setText("Available");
        } else if (availabilityTimes10.equals("111")){
            btnMorning10.setText("Available");
            btnMidday10.setText("Available");
            btnEvening10.setText("Available");
        } else if (availabilityTimes10.equals("110")){
            btnMorning10.setText("Available");
            btnMidday10.setText("Available");
            btnEvening10.setText("N/A");
        } else if (availabilityTimes10.equals("100")){
            btnMorning10.setText("Available");
            btnMidday10.setText("N/A");
            btnEvening10.setText("N/A");
        } else if (availabilityTimes10.equals("101")){
            btnMorning10.setText("Available");
            btnMidday10.setText("N/A");
            btnEvening10.setText("Available");
        } else if (availabilityTimes10.equals("010")){
            btnMorning10.setText("N/A");
            btnMidday10.setText("Available");
            btnEvening10.setText("N/A");
        } else {
            System.out.println("fail10");
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
