package core.controller;

import core.model.Availability;
import core.model.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by louie on 31/03/2017.
 */
public class AvailabilityController {

    @FXML
    private Button btnSaveTimes;

    @FXML
    private Button btnGotoEmployeesAvailability;

    @FXML
    private Label lblLoginError;

    @FXML
    private Button btnBackToBusinessScreen;
    @FXML
    private TextField txtEmployeeName;
    @FXML
    private Label empNameDisplay;

    @FXML
    private CheckBox monMorning;
    @FXML
    private CheckBox tueMorning;
    @FXML
    private CheckBox wedMorning;
    @FXML
    private CheckBox thurMorning;
    @FXML
    private CheckBox friMorning;

    @FXML
    private CheckBox monAfternoon;
    @FXML
    private CheckBox tueAfternoon;
    @FXML
    private CheckBox wedAfternoon;
    @FXML
    private CheckBox thurAfternoon;
    @FXML
    private CheckBox friAfternoon;

    @FXML
    private CheckBox monEvening;
    @FXML
    private CheckBox tueEvening;
    @FXML
    private CheckBox wedEvening;
    @FXML
    private CheckBox thurEvening;
    @FXML
    private CheckBox friEvening;

    @FXML
    private CheckBox satMorning;

    @FXML
    private CheckBox sunMorning;
    @FXML
    private CheckBox satAfternoon;
    @FXML
    private CheckBox sunAfternoon;
    @FXML
    private CheckBox satEvening;
    @FXML
    private CheckBox sunEvening;


    /*
    @FXML
    public void initialize() {
        Date today = new Date();
        Calendar cal = new GregorianCalendar();

        cal.setTime(today);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        today = cal.getTime();

        MondayDate.setText(new SimpleDateFormat("dd/MM").format(today));

        cal.add(Calendar.DAY_OF_WEEK, 6 );
        today = cal.getTime();

        SundayDate.setText(new SimpleDateFormat("dd/MM").format(today));
    }
*/

    private static final char AVAILABLE = '1';
    private static final char UNAVAILABLE = '0';
    private static final int EXISTS = 1;
    private String dayAvailability = "";
    private int empID = -1;

    /**
     * Saves availability to the currently selected employee
     */
    public Boolean btnSaveTimes(ActionEvent event) throws IOException {

        if (empID < 1) {
            lblLoginError.setText("No Employee selected");
            return false;
            /* no emp loaded */
        } else {

            setMonday();
            setTuesday();
            setWednesday();
            setThursday();
            setFriday();
            setSatuerday();
            setSunday();

            if (!Availability.addAvailability(dayAvailability, empID)) {
                lblLoginError.setText("Failure - Database maybe locked!!");
                return false;
            }else{
                lblLoginError.setText("Success!!");
                dayAvailability = "";
                return true;
            }
        }
    }


    /**
     * loads in an employee to alter there availability
     */
    public void loadEmp(ActionEvent event) throws IOException {
        String empName;
        char first;

        empName = txtEmployeeName.getCharacters().toString();

        if (empName.isEmpty()) {
            System.out.println("No input given");
            lblLoginError.setText("Employee does not exist");
        } else {

        /* Chaptalize first char */
            first = Character.toUpperCase(empName.charAt(0));
            empName = first + empName.substring(1);

            try {
                if ((empID = Employee.findEmployee(empName)) >= EXISTS) {
                    empNameDisplay.setText(empName);
                    System.out.println("loaded empId: " + empID);
                    lblLoginError.setText("");

                    removeDisplayedAvailablity();
                    loadCurrentAvailability();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            /*TODO if emp already has availability load in to checkboxes */

        }
    }

    /**
     * Loads the current availability set for the given
     * employee
     */
    private void loadCurrentAvailability() {
        String currentAvailability;
        String[] days;
        int type, dayOfTheWeek = 0;
        final char AVAILABLE = '1';
        final int MORNING = 1, AFTERNOON = 2, EVENING = 3;
        currentAvailability = Availability.getAvailability(empID);

        days = currentAvailability.split(",");

        for (String day : days) {

            dayOfTheWeek++;
            day.toCharArray();

            type = 0;
            for (char shift : day.toCharArray()) {
                type++;
                if (shift == AVAILABLE && type == MORNING) {
                    loadShiftMorning(dayOfTheWeek);
                }
                if (shift == AVAILABLE && type == AFTERNOON) {
                    loadShiftAfternoon(dayOfTheWeek);
                }
                if (shift == AVAILABLE && type == EVENING) {
                    loadShiftEvening(dayOfTheWeek);
                }
            }
        }
    }

    /**
     * sets a morning shift
     */
    private void loadShiftMorning(int dayOfTheWeek) {

        switch (dayOfTheWeek) {
            case 1:
                monMorning.setSelected(true);
                break;
            case 2:
                tueMorning.setSelected(true);
                break;
            case 3:
                wedMorning.setSelected(true);
                break;
            case 4:
                thurMorning.setSelected(true);
                break;
            case 5:
                friMorning.setSelected(true);
                break;
            case 6:
                satMorning.setSelected(true);
                break;
            case 7:
                sunMorning.setSelected(true);
                break;
            default:
                System.out.println("trying to load shift for unknown day");
        }
    }

    /**
     * sets a afternoon shift
     */
    private void loadShiftAfternoon(int dayOfTheWeek) {

        switch (dayOfTheWeek) {
            case 1:
                monAfternoon.setSelected(true);
                break;
            case 2:
                tueAfternoon.setSelected(true);
                break;
            case 3:
                wedAfternoon.setSelected(true);
                break;
            case 4:
                thurAfternoon.setSelected(true);
                break;
            case 5:
                friAfternoon.setSelected(true);
                break;
            case 6:
                satAfternoon.setSelected(true);
                break;
            case 7:
                sunAfternoon.setSelected(true);
                break;
            default:
                System.out.println("trying to load shift for unknown day");
        }
    }

    /**
     * sets a evening shift
     */
    private void loadShiftEvening(int dayOfTheWeek) {
        switch (dayOfTheWeek) {
            case 1:
                monEvening.setSelected(true);
                break;
            case 2:
                tueEvening.setSelected(true);
                break;
            case 3:
                wedEvening.setSelected(true);
                break;
            case 4:
                thurEvening.setSelected(true);
                break;
            case 5:
                friEvening.setSelected(true);
                break;
            case 6:
                satEvening.setSelected(true);
                break;
            case 7:
                sunEvening.setSelected(true);
                break;
            default:
                System.out.println("trying to load shift for unknown day");
        }
    }

    /**
     * removes currently loaded availablity
     */
    private void removeDisplayedAvailablity() {

        monMorning.setSelected(false);
        tueMorning.setSelected(false);
        wedMorning.setSelected(false);
        thurMorning.setSelected(false);
        friMorning.setSelected(false);
        satMorning.setSelected(false);
        sunMorning.setSelected(false);
        monAfternoon.setSelected(false);
        tueAfternoon.setSelected(false);
        wedAfternoon.setSelected(false);
        thurAfternoon.setSelected(false);
        friAfternoon.setSelected(false);
        satAfternoon.setSelected(false);
        sunAfternoon.setSelected(false);
        monEvening.setSelected(false);
        tueEvening.setSelected(false);
        wedEvening.setSelected(false);
        thurEvening.setSelected(false);
        friEvening.setSelected(false);
        satEvening.setSelected(false);
        sunEvening.setSelected(false);
    }

    /** Helpers */
    /** sets data for sunday */
    private void setSunday() {

        set(sunMorning.isSelected());
        set(sunAfternoon.isSelected());
        set(sunEvening.isSelected());
    }
    /** sets data for saturday */
    private void setSatuerday() {
        set(satMorning.isSelected());
        set(satAfternoon.isSelected());
        set(satEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for friday */
    private void setFriday() {
        set(friMorning.isSelected());
        set(friAfternoon.isSelected());
        set(friEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for thursday */
    private void setThursday() {
        set(thurMorning.isSelected());
        set(thurAfternoon.isSelected());
        set(thurEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for wednesday */
    private void setWednesday() {
        set(wedMorning.isSelected());
        set(wedAfternoon.isSelected());
        set(wedEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for tuesday */
    private void setTuesday() {
        set(tueMorning.isSelected());
        set(tueAfternoon.isSelected());
        set(tueEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for monday */
    private void setMonday() {
        set(monMorning.isSelected());
        set(monAfternoon.isSelected());
        set(monEvening.isSelected());
        dayAvailability += ',';
    }
    /** Sets a shifts availability e.g 8am - 12pm */
    private void set(Boolean available){
        if(available) {
            dayAvailability += AVAILABLE;
        }else{
            dayAvailability += UNAVAILABLE;
        }
    }

    @FXML
    public void btnBackToBusinessScreen(ActionEvent event) throws IOException {
        Parent business_parent = FXMLLoader.load(getClass().getResource("../view/BusinessHome.fxml"));
        Scene business_scene = new Scene (business_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(business_scene);
        primaryStage.show();
    }

    @FXML
    public void btnGotoEmployeesAvailability(ActionEvent event) throws IOException {
        Parent removeEmp_parent = FXMLLoader.load(getClass().getResource("../view/EmployeesAvailability.fxml"));
        Scene removeEmp_scene = new Scene((removeEmp_parent));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(removeEmp_scene);
        primaryStage.show();
    }

}
