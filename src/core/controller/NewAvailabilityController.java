package core.controller;

import core.model.Availability;
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

/**
 * Created by louie on 31/03/2017.
 */
public class NewAvailabilityController {

    @FXML
    private Button btnSaveTimes;

    @FXML
    private Button btnBackToBusinessScreen;
    @FXML
    private TextField txtEmployeeName;
    @FXML
    private Label MondayDate;

    @FXML
    private Label SundayDate;

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
    private String dayAvailability = "";

    public void btnSaveTimes(ActionEvent event) throws IOException {

        Availability availability = new Availability();

        setMonday();
        setTuesday();
        setWednesday();
        setThursday();
        setFriday();
        setSatuerday();
        setSunday();

        if(!availability.addAvailability(dayAvailability)){
            /*TODO out put error */
        }
        dayAvailability = "";
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

}
