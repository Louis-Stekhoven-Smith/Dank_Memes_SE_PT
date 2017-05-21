package core.controller;

import core.model.Availability;
import core.model.Database;
import core.model.Employee;
import core.model.Session;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by louie on 31/03/2017.
 */
public class AvailabilityController {

    private static final Logger log = LogManager.getLogger(AvailabilityController.class.getName());
    private Employee employee = new Employee(Database.getInstance(), Session.getInstance());

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
    @FXML
    private GridPane grid;
    private static final char AVAILABLE = '1';
    private static final char UNAVAILABLE = '0';
    private static final int EXISTS = 1;
    private String dayAvailability = "";
    private int empID = -1;
    private Database database = Database.getInstance();
    private Availability availability = new Availability(database);

    //currently does not take current session businessID but rather hardcoded businessID of int '4'
    public void initialize() throws IOException, SQLException {
        Session session = Session.getInstance();
        int businessID = session.getLoggedInUserId();

        ResultSet time;
        int[] times = new int[7];
        String findRoleTypeCount = "SELECT mon FROM businessDetails WHERE businessID =" + businessID;
        time = database.queryDatabase(findRoleTypeCount);
        times[0] = time.getInt("mon");

        findRoleTypeCount = "SELECT tue FROM businessDetails WHERE businessID =" + businessID;
        time = database.queryDatabase(findRoleTypeCount);
        times[1] = time.getInt("tue");

        findRoleTypeCount = "SELECT wed FROM businessDetails WHERE businessID =" + businessID;
        time = database.queryDatabase(findRoleTypeCount);
        times[2] = time.getInt("wed");

        findRoleTypeCount = "SELECT thu FROM businessDetails WHERE businessID =" + businessID;
        time = database.queryDatabase(findRoleTypeCount);
        times[3] = time.getInt("thu");

        findRoleTypeCount = "SELECT fri FROM businessDetails WHERE businessID =" + businessID;
        time = database.queryDatabase(findRoleTypeCount);
        times[4] = time.getInt("fri");

        findRoleTypeCount = "SELECT sat FROM businessDetails WHERE businessID =" + businessID;
        time = database.queryDatabase(findRoleTypeCount);
        times[5] = time.getInt("sat");

        findRoleTypeCount = "SELECT sun FROM businessDetails WHERE businessID =" + businessID;
        time = database.queryDatabase(findRoleTypeCount);
        times[6] = time.getInt("sun");

        System.out.println(Arrays.toString(times));

        /*
        Values disables the following
        1 = morning
        2 = midday
        3 = evening
        4 = morning, midday
        5 = midday, evening
        6 = morning, evening
        7 = all
        else none
        */

        for (int i = 0; i<times.length;i++) {
                 if (times[i] == 1) {
                    getNodeFromGridPane(grid, 1, i+1).setDisable(true);
                } else if (times[i] == 2) {
                    getNodeFromGridPane(grid, 2, i+1).setDisable(true);
                } else if (times[i] == 3) {
                    getNodeFromGridPane(grid, 3, i+1).setDisable(true);
                } else if (times[i] == 4) {
                    getNodeFromGridPane(grid, 1, i+1).setDisable(true);
                    getNodeFromGridPane(grid, 2, i+1).setDisable(true);
                } else if (times[i] == 5) {
                    getNodeFromGridPane(grid, 2, i+1).setDisable(true);
                    getNodeFromGridPane(grid, 3, i+1).setDisable(true);
                } else if (times[i] == 6) {
                    getNodeFromGridPane(grid, 1, i+1).setDisable(true);
                    getNodeFromGridPane(grid, 3, i+1).setDisable(true);
                } else if (times[i] == 7) {
                    getNodeFromGridPane(grid, 1, i+1).setDisable(true);
                    getNodeFromGridPane(grid, 2, i+1).setDisable(true);
                    getNodeFromGridPane(grid, 3, i+1).setDisable(true);
                }

            }
        }


    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    /**
     * Saves availability to the currently selected employee
     */
    public Boolean btnSaveTimes(ActionEvent event) throws IOException {

        log.debug("Save times button clicked");

        if (empID < 1) {
            lblLoginError.setText("No Employee selected");
            log.debug("Save times clicked with no employee loaded");
            return false;
            /* no emp loaded */
        } else {

            log.debug("Calling the method to set availability for each day");

            setMonday();
            setTuesday();
            setWednesday();
            setThursday();
            setFriday();
            setSaturday();
            setSunday();

            if (!availability.addAvailability(dayAvailability,empID)) {
                lblLoginError.setText("Failure");
                log.debug("Returned to controller, add availability failed.");
                return false;
            }else{
                log.debug("Returned to controller, availability added.");
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
        log.debug("Load employee button clicked");
        String empName;
        char first;

        empName = txtEmployeeName.getCharacters().toString();

        if (empName.isEmpty()) {
            log.debug("No employee entered");
            lblLoginError.setText("Employee does not exist");
        } else {

            /* Capitalize first char */
            first = Character.toUpperCase(empName.charAt(0));
            empName = first + empName.substring(1);


            log.debug("Calling findEmployee method, leaving controller...");
            if ((empID = employee.findEmployee(empName)) >= EXISTS) {
                log.debug("Returned to controller, employee found and loaded: " + empID);
                empNameDisplay.setText(empName);
                lblLoginError.setText("");

                removeDisplayedAvailablity();
                loadCurrentAvailability();
            } else {
                log.debug("Returned to controller, failed to find and load employee");
            }
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
        currentAvailability = availability.getAvailability(empID);

        if(!currentAvailability.isEmpty()){
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
     * Removes currently loaded availability
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
        log.debug("Inside setSunday method");

        setDataFor(sunMorning.isSelected());
        setDataFor(sunAfternoon.isSelected());
        setDataFor(sunEvening.isSelected());
    }
    /** sets data for saturday */
    private void setSaturday() {
        log.debug("Inside setSaturday method");

        setDataFor(satMorning.isSelected());
        setDataFor(satAfternoon.isSelected());
        setDataFor(satEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for friday */
    private void setFriday() {
        log.debug("Inside setFriday method");

        setDataFor(friMorning.isSelected());
        setDataFor(friAfternoon.isSelected());
        setDataFor(friEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for thursday */
    private void setThursday() {
        log.debug("Inside setThursday method");
        setDataFor(thurMorning.isSelected());
        setDataFor(thurAfternoon.isSelected());
        setDataFor(thurEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for wednesday */
    private void setWednesday() {
        log.debug("Inside setWednesday method");
        setDataFor(wedMorning.isSelected());
        setDataFor(wedAfternoon.isSelected());
        setDataFor(wedEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for tuesday */
    private void setTuesday() {
        log.debug("Inside setTuesday method");
        setDataFor(tueMorning.isSelected());
        setDataFor(tueAfternoon.isSelected());
        setDataFor(tueEvening.isSelected());
        dayAvailability += ',';
    }
    /** sets data for monday */
    private void setMonday() {
        log.debug("Inside setMonday method");
        setDataFor(monMorning.isSelected());
        setDataFor(monAfternoon.isSelected());
        setDataFor(monEvening.isSelected());
        dayAvailability += ',';
    }
    /** Sets a shifts availability e.g 8am - 12pm */
    private void setDataFor(Boolean available){
        if(available) {
            dayAvailability += AVAILABLE;
        }else{
            dayAvailability += UNAVAILABLE;
        }
    }

    /** Takes the user back to the main menu */
    @FXML
    public void btnBackToBusinessScreen(ActionEvent event) throws IOException {
        log.debug("Back (to business home screen) button clicked");
        log.debug("Loading business home screen, switching controller to Main controller...");
        Parent business_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/BusinessHome.fxml"));
        Scene business_scene = new Scene (business_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(business_scene);
        primaryStage.show();
    }

    /** Takes user to Employee Availability sceen */
    @FXML
    public void btnGotoEmployeesAvailability(ActionEvent event) throws IOException {
        Parent empAvailability_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/EmployeesAvailability.fxml"));
        Scene empAvailability_scene = new Scene((empAvailability_parent));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(empAvailability_scene);
        primaryStage.show();
    }

}
