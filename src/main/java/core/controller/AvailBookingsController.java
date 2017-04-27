package core.controller;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;


/**
 * Created by Konn on 4/04/2017.
 */
public class AvailBookingsController {

    private Database database = Database.getInstance();
    private Session session = Session.getInstance();

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

    public static String futureBookingID;
    public static String employeesName;
    public static String businessesName;
    public static String yourName;
    public static String bookingType;
    public static String bookingDate;
    public static String bookingTime;
    public static int employeesIDfromName;


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
    public String employeeCount(String chosenType, String finalDate) throws SQLException {
        System.out.println("Load " + chosenType + " employee & times...");
        ResultSet count;
        int counter;
        String findRoleTypeCount = "SELECT count(*) AS total FROM employeeDetails WHERE employeeRole=" + "'" + chosenType + "'";
        count = database.queryDatabase(findRoleTypeCount);
        counter = count.getInt("total");
        System.out.println("TOTAL COUNT OF THIS ROLE " + counter);
        retrieveAvailabilityData(chosenType, finalDate, counter);
        return chosenType;
    }


    /**display times depending if there is one employee assigned to the job */
    public void retrieveAvailabilityData(String chosenType, String finalDate, int counter) throws SQLException {
        System.out.println("COUNTER INT VALUE IS = "+counter);
        ResultSet rs;
        String name;
        String findEmpNameSQL = "SELECT name FROM employeeDetails WHERE employeeRole=" + "'" + chosenType + "'" +
                     "AND businessID = "+ session.getBusinessSelected();
        rs = database.queryDatabase(findEmpNameSQL);
        String[] myArray = new String[counter];
        for (int i = 0; i < myArray.length; i++) {
            rs.next();
            name = rs.getString("name");
            myArray[i] = name;
        }
        ResultSet rs1;
        String empID;
        String findempIDSQL = "SELECT empID from employeeDetails WHERE employeeRole=" + "'" + chosenType + "'";
        rs1 = database.queryDatabase(findempIDSQL);
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
            rs2 = database.queryDatabase(findAvailabilitySQL);
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
        }
    }



    //000,001,011,111,110,100

    /**sets the time buttons to available or N/A depending on availability*/
    public void setTimeLabels1(String availabilityTimes, String[] myArray){
        String timeArray[] = availabilityTimes.split("(?!^)");
        System.out.println(Arrays.toString(timeArray));
        lblName1.setText(myArray[0]);
        lblName1.setVisible(true);
        btnMorning1.setVisible(true);
        btnMidday1.setVisible(true);
        btnEvening1.setVisible(true);
        if (Objects.equals(timeArray[0], "0")){
            btnMorning1.setDisable(true);
            btnMorning1.setText("Booked");
        } else if (Objects.equals(timeArray[0], "1")){
            btnMorning1.setDisable(false);
            btnMorning1.setText("Availabile");
        }
        if (Objects.equals(timeArray[1], "0")){
            btnMidday1.setDisable(true);
            btnMidday1.setText("Booked");
        } else if (Objects.equals(timeArray[1], "1")){
            btnMidday1.setDisable(false);
            btnMidday1.setText("Available");
        }
        if (Objects.equals(timeArray[2], "0")){
            btnEvening1.setDisable(true);
            btnEvening1.setText("Booked");
        } else if (Objects.equals(timeArray[2], "1")){
            btnEvening1.setDisable(false);
            btnEvening1.setText("Available");
        }
    }

    /**sets the time buttons to available or N/A depending on availability*/
    public void setTimeLabels2(String availabilityTimes2, String[]myArray) {
        String timeArray[] = availabilityTimes2.split("(?!^)");
        System.out.println(Arrays.toString(timeArray));
        lblName2.setText(myArray[1]);
        lblName2.setVisible(true);
        btnMorning2.setVisible(true);
        btnMidday2.setVisible(true);
        btnEvening2.setVisible(true);
        if (Objects.equals(timeArray[0], "0")) {
            btnMorning2.setDisable(true);
            btnMorning2.setText("Booked");
        } else if (Objects.equals(timeArray[0], "1")) {
            btnMorning2.setDisable(false);
            btnMorning2.setText("Availabile");
        }
        if (Objects.equals(timeArray[1], "0")) {
            btnMidday2.setDisable(true);
            btnMidday2.setText("Booked");
        } else if (Objects.equals(timeArray[1], "1")) {
            btnMidday2.setDisable(false);
            btnMidday2.setText("Available");
        }
        if (Objects.equals(timeArray[2], "0")) {
            btnEvening2.setDisable(true);
            btnEvening2.setText("Booked");
        } else if (Objects.equals(timeArray[2], "1")) {
            btnEvening2.setDisable(false);
            btnEvening2.setText("Available");
        }
    }

    public void setTimeLabels3(String availabilityTimes3, String[]myArray){
        String timeArray[] = availabilityTimes3.split("(?!^)");
        System.out.println(Arrays.toString(timeArray));
        lblName3.setText(myArray[2]);
        lblName3.setVisible(true);
        btnMorning3.setVisible(true);
        btnMidday3.setVisible(true);
        btnEvening3.setVisible(true);
        if (Objects.equals(timeArray[0], "0")) {
            btnMorning3.setDisable(true);
            btnMorning3.setText("Booked");
        } else if (Objects.equals(timeArray[0], "1")) {
            btnMorning3.setDisable(false);
            btnMorning3.setText("Availabile");
        }
        if (Objects.equals(timeArray[1], "0")) {
            btnMidday3.setDisable(true);
            btnMidday3.setText("Booked");
        } else if (Objects.equals(timeArray[1], "1")) {
            btnMidday3.setDisable(false);
            btnMidday3.setText("Available");
        }
        if (Objects.equals(timeArray[2], "0")) {
            btnEvening3.setDisable(true);
            btnEvening3.setText("Booked");
        } else if (Objects.equals(timeArray[2], "1")) {
            btnEvening3.setDisable(false);
            btnEvening3.setText("Available");
        }
    }

    public void setTimeLabels4(String availabilityTimes4, String[]myArray){
        String timeArray[] = availabilityTimes4.split("(?!^)");
        System.out.println(Arrays.toString(timeArray));
        lblName4.setText(myArray[3]);
        lblName4.setVisible(true);
        btnMorning4.setVisible(true);
        btnMidday4.setVisible(true);
        btnEvening4.setVisible(true);
        if (Objects.equals(timeArray[0], "0")) {
            btnMorning4.setDisable(true);
            btnMorning4.setText("Booked");
        } else if (Objects.equals(timeArray[0], "1")) {
            btnMorning4.setDisable(false);
            btnMorning4.setText("Availabile");
        }
        if (Objects.equals(timeArray[1], "0")) {
            btnMidday4.setDisable(true);
            btnMidday4.setText("Booked");
        } else if (Objects.equals(timeArray[1], "1")) {
            btnMidday4.setDisable(false);
            btnMidday4.setText("Available");
        }
        if (Objects.equals(timeArray[2], "0")) {
            btnEvening4.setDisable(true);
            btnEvening4.setText("Booked");
        } else if (Objects.equals(timeArray[2], "1")) {
            btnEvening4.setDisable(false);
            btnEvening4.setText("Available");
        }
    }

    public void setTimeLabels5(String availabilityTimes5, String[]myArray){
        String timeArray[] = availabilityTimes5.split("(?!^)");
        System.out.println(Arrays.toString(timeArray));
        lblName5.setText(myArray[4]);
        lblName5.setVisible(true);
        btnMorning5.setVisible(true);
        btnMidday5.setVisible(true);
        btnEvening5.setVisible(true);
        if (Objects.equals(timeArray[0], "0")) {
            btnMorning5.setDisable(true);
            btnMorning5.setText("Booked");
        } else if (Objects.equals(timeArray[0], "1")) {
            btnMorning5.setDisable(false);
            btnMorning5.setText("Availabile");
        }
        if (Objects.equals(timeArray[1], "0")) {
            btnMidday5.setDisable(true);
            btnMidday5.setText("Booked");
        } else if (Objects.equals(timeArray[1], "1")) {
            btnMidday5.setDisable(false);
            btnMidday5.setText("Available");
        }
        if (Objects.equals(timeArray[2], "0")) {
            btnEvening5.setDisable(true);
            btnEvening5.setText("Booked");
        } else if (Objects.equals(timeArray[2], "1")) {
            btnEvening5.setDisable(false);
            btnEvening5.setText("Available");
        }
    }

    public void setTimeLabels6(String availabilityTimes6, String[]myArray){
        String timeArray[] = availabilityTimes6.split("(?!^)");
        System.out.println(Arrays.toString(timeArray));
        lblName6.setText(myArray[5]);
        lblName6.setVisible(true);
        btnMorning6.setVisible(true);
        btnMidday6.setVisible(true);
        btnEvening6.setVisible(true);
        if (Objects.equals(timeArray[0], "0")) {
            btnMorning6.setDisable(true);
            btnMorning6.setText("Booked");
        } else if (Objects.equals(timeArray[0], "1")) {
            btnMorning6.setDisable(false);
            btnMorning6.setText("Availabile");
        }
        if (Objects.equals(timeArray[1], "0")) {
            btnMidday6.setDisable(true);
            btnMidday6.setText("Booked");
        } else if (Objects.equals(timeArray[1], "1")) {
            btnMidday6.setDisable(false);
            btnMidday6.setText("Available");
        }
        if (Objects.equals(timeArray[2], "0")) {
            btnEvening6.setDisable(true);
            btnEvening6.setText("Booked");
        } else if (Objects.equals(timeArray[2], "1")) {
            btnEvening6.setDisable(false);
            btnEvening6.setText("Available");
        }
    }

    public void setTimeLabels7(String availabilityTimes7, String[]myArray){
        String timeArray[] = availabilityTimes7.split("(?!^)");
        System.out.println(Arrays.toString(timeArray));
        lblName7.setText(myArray[6]);
        lblName7.setVisible(true);
        btnMorning7.setVisible(true);
        btnMidday7.setVisible(true);
        btnEvening7.setVisible(true);
        if (Objects.equals(timeArray[0], "0")) {
            btnMorning7.setDisable(true);
            btnMorning7.setText("Booked");
        } else if (Objects.equals(timeArray[0], "1")) {
            btnMorning7.setDisable(false);
            btnMorning7.setText("Availabile");
        }
        if (Objects.equals(timeArray[1], "0")) {
            btnMidday7.setDisable(true);
            btnMidday7.setText("Booked");
        } else if (Objects.equals(timeArray[1], "1")) {
            btnMidday7.setDisable(false);
            btnMidday7.setText("Available");
        }
        if (Objects.equals(timeArray[2], "0")) {
            btnEvening7.setDisable(true);
            btnEvening7.setText("Booked");
        } else if (Objects.equals(timeArray[2], "1")) {
            btnEvening7.setDisable(false);
            btnEvening7.setText("Available");
        }
    }

    public void setTimeLabels8(String availabilityTimes8, String[]myArray){
        String timeArray[] = availabilityTimes8.split("(?!^)");
        System.out.println(Arrays.toString(timeArray));
        lblName8.setText(myArray[7]);
        lblName8.setVisible(true);
        btnMorning8.setVisible(true);
        btnMidday8.setVisible(true);
        btnEvening8.setVisible(true);
        if (Objects.equals(timeArray[0], "0")) {
            btnMorning8.setDisable(true);
            btnMorning8.setText("Booked");
        } else if (Objects.equals(timeArray[0], "1")) {
            btnMorning8.setDisable(false);
            btnMorning8.setText("Availabile");
        }
        if (Objects.equals(timeArray[1], "0")) {
            btnMidday8.setDisable(true);
            btnMidday8.setText("Booked");
        } else if (Objects.equals(timeArray[1], "1")) {
            btnMidday8.setDisable(false);
            btnMidday8.setText("Available");
        }
        if (Objects.equals(timeArray[2], "0")) {
            btnEvening8.setDisable(true);
            btnEvening8.setText("Booked");
        } else if (Objects.equals(timeArray[2], "1")) {
            btnEvening8.setDisable(false);
            btnEvening8.setText("Available");
        }
    }

    public void setTimeLabels9(String availabilityTimes9, String[]myArray){
        String timeArray[] = availabilityTimes9.split("(?!^)");
        System.out.println(Arrays.toString(timeArray));
        lblName9.setText(myArray[8]);
        lblName9.setVisible(true);
        btnMorning9.setVisible(true);
        btnMidday9.setVisible(true);
        btnEvening9.setVisible(true);
        if (Objects.equals(timeArray[0], "0")) {
            btnMorning9.setDisable(true);
            btnMorning9.setText("Booked");
        } else if (Objects.equals(timeArray[0], "1")) {
            btnMorning9.setDisable(false);
            btnMorning9.setText("Availabile");
        }
        if (Objects.equals(timeArray[1], "0")) {
            btnMidday9.setDisable(true);
            btnMidday9.setText("Booked");
        } else if (Objects.equals(timeArray[1], "1")) {
            btnMidday9.setDisable(false);
            btnMidday9.setText("Available");
        }
        if (Objects.equals(timeArray[2], "0")) {
            btnEvening9.setDisable(true);
            btnEvening9.setText("Booked");
        } else if (Objects.equals(timeArray[2], "1")) {
            btnEvening9.setDisable(false);
            btnEvening9.setText("Available");
        }
    }

    public void setTimeLabels10(String availabilityTimes10, String[]myArray){
        String timeArray[] = availabilityTimes10.split("(?!^)");
        System.out.println(Arrays.toString(timeArray));
        lblName10.setText(myArray[9]);
        lblName10.setVisible(true);
        btnMorning10.setVisible(true);
        btnMidday10.setVisible(true);
        btnEvening10.setVisible(true);
        if (Objects.equals(timeArray[0], "0")) {
            btnMorning10.setDisable(true);
            btnMorning10.setText("Booked");
        } else if (Objects.equals(timeArray[0], "1")) {
            btnMorning10.setDisable(false);
            btnMorning10.setText("Availabile");
        }
        if (Objects.equals(timeArray[1], "0")) {
            btnMidday10.setDisable(true);
            btnMidday10.setText("Booked");
        } else if (Objects.equals(timeArray[1], "1")) {
            btnMidday10.setDisable(false);
            btnMidday10.setText("Available");
        }
        if (Objects.equals(timeArray[2], "0")) {
            btnEvening10.setDisable(true);
            btnEvening10.setText("Booked");
        } else if (Objects.equals(timeArray[2], "1")) {
            btnEvening10.setDisable(false);
            btnEvening10.setText("Available");
        }
    }

    public void getBookingData() throws IOException, SQLException{
        LocalDate ld = dpBookingDate.getValue();
        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

        ResultSet rs;
        String name;
        String findyourNameDSQL = "SELECT name from customerDetails WHERE custID=" + session.getLoggedInUserId();
        rs = database.queryDatabase(findyourNameDSQL);
        String[] myArray = new String[1];
        for (int i = 0; i < myArray.length; i++) {
            rs.next();
            name = rs.getString("name");
            myArray[i] = name;
        }

        ResultSet rs1;
        String businessName;
        String findbusinessNameDSQL = "SELECT businessName from businessDetails WHERE businessID=" + session.getBusinessSelected();
        rs1 = database.queryDatabase(findbusinessNameDSQL);
        String[] myArray2 = new String[1];
        for (int i = 0; i < myArray.length; i++) {
            rs1.next();
            businessName = rs1.getString("businessName");
            myArray2[i] = businessName;
        }

        ResultSet rs2;
        int bookingID;
        String findbookingIDSQL = "SELECT MAX(bookingID) AS bookingID from bookingDetails";
        rs2 = database.queryDatabase(findbookingIDSQL);
        int[] myArray3 = new int[1];
        for (int i = 0; i < myArray.length; i++) {
            rs2.next();
            bookingID = rs2.getInt("bookingID");
            myArray3[i] = bookingID;
        }

        ResultSet rs3;
        int employeesID;
        String findEmployeesIDSQL = "SELECT empID from employeeDetails WHERE name=" + "'" + employeesName + "'";
        rs3 = database.queryDatabase(findEmployeesIDSQL);
        int[] myArray4 = new int[1];
        for (int i = 0; i < myArray.length; i++) {
            rs3.next();
            employeesID = rs3.getInt("empID");
            myArray4[i] = employeesID;
        }

        employeesIDfromName = myArray4[0];
        futureBookingID = String.valueOf(myArray3[0]+1);
        businessesName = myArray2[0];
        yourName = myArray[0];
        bookingDate = new SimpleDateFormat("dd/MM/yy").format(date);
        bookingType = lblCurrentService.getText();

        Parent bookingConfirmation_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/BookingConfirmation.fxml"));
        Scene bookingConfirmation_scene = new Scene(bookingConfirmation_parent);
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(bookingConfirmation_scene);
        secondaryStage.show();
    }


    public void btnMor1Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName1.getText();
        bookingTime = ("Morning");
        getBookingData();
    }

    public void btnMid1Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName1.getText();
        bookingTime = ("Midday");
        getBookingData();
    }

    public void btnEve1Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName1.getText();
        bookingTime = ("Evening");
        getBookingData();
    }

    public void btnMor2Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName2.getText();
        bookingTime = ("Morning");
        getBookingData();
    }

    public void btnMid2Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName2.getText();
        bookingTime = ("Midday");
        getBookingData();
    }

    public void btnEve2Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName2.getText();
        bookingTime = ("Evening");
        getBookingData();
    }

    public void btnMor3Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName3.getText();
        bookingTime = ("Morning");
        getBookingData();
    }

    public void btnMid3Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName3.getText();
        bookingTime = ("Midday");
        getBookingData();
    }

    public void btnEve3Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName3.getText();
        bookingTime = ("Evening");
        getBookingData();
    }

    public void btnMor4Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName4.getText();
        bookingTime = ("Morning");
        getBookingData();
    }
    public void btnMid4Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName4.getText();
        bookingTime = ("Midday");
        getBookingData();
    }
    public void btnEve4Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName4.getText();
        bookingTime = ("Evening");
        getBookingData();
    }

    public void btnMor5Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName5.getText();
        bookingTime = ("Morning");
        getBookingData();
    }

    public void btnMid5Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName1.getText();
        bookingTime = ("Midday");
        getBookingData();
    }

    public void btnEve5Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName5.getText();
        bookingTime = ("Evening");
        getBookingData();
    }

    public void btnMor6Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName6.getText();
        bookingTime = ("Morning");
        getBookingData();
    }

    public void btnMid6Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName6.getText();
        bookingTime = ("Midday");
        getBookingData();
    }
    public void btnEve6Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName6.getText();
        bookingTime = ("Evening");
        getBookingData();
    }

    public void btnMor7Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName7.getText();
        bookingTime = ("Morning");
        getBookingData();
    }

    public void btnMid7Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName7.getText();
        bookingTime = ("Mid");
        getBookingData();
    }

    public void btnEve7Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName7.getText();
        bookingTime = ("Evening");
        getBookingData();
    }

    public void btnMor8Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName8.getText();
        bookingTime = ("Morning");
        getBookingData();
    }

    public void btnMid8Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName8.getText();
        bookingTime = ("Midday");
        getBookingData();
    }

    public void btnEve8Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName8.getText();
        bookingTime = ("Midday");
        getBookingData();
    }

    public void btnMor9Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName9.getText();
        bookingTime = ("Morning");
        getBookingData();
    }

    public void btnMid9Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName9.getText();
        bookingTime = ("Midday");
        getBookingData();
    }

    public void btnEve9Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName9.getText();
        bookingTime = ("Evening");
        getBookingData();
    }

    public void btnMor10Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName10.getText();
        bookingTime = ("Morning");
        getBookingData();
    }

    public void btnMid10Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName10.getText();
        bookingTime = ("Midday");
        getBookingData();
    }

    public void btnEve10Clicked (javafx.event.ActionEvent event) throws IOException, SQLException{
        employeesName = lblName10.getText();
        bookingTime = ("Evening");
        getBookingData();
    }




    /**goes back to services page */
    public void btnBackPressed(javafx.event.ActionEvent event) throws IOException{
        Parent viewBookings_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/ServiceType.fxml"));
        Scene viewBookings_scene = new Scene(viewBookings_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(viewBookings_scene);
        primaryStage.show();
    }
}
