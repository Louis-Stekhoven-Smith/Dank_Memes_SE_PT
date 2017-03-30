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
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public NewAvailabilityController(){

}

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

    public void btnSaveTimes(ActionEvent event) throws IOException {

        Date today = new Date();
        Calendar cal = new GregorianCalendar();

        cal.setTime(today);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        today = cal.getTime();
        System.out.println(new SimpleDateFormat("dd/MM").format(today));

        Boolean[] dayAvailablity = new Boolean[2];
        Availability availability = new Availability();
        Date date = new Date();

        resetDayAvailablity(dayAvailablity);
        setMonday(dayAvailablity);
        availability.addDay(dayAvailablity,date);

        resetDayAvailablity(dayAvailablity);
        setTuesday(dayAvailablity);
        availability.addDay(dayAvailablity,date);

        resetDayAvailablity(dayAvailablity);
        setWednesday(dayAvailablity);
        availability.addDay(dayAvailablity,date);

        resetDayAvailablity(dayAvailablity);
        setThursday(dayAvailablity);
        availability.addDay(dayAvailablity,date);

        resetDayAvailablity(dayAvailablity);
        setFriday(dayAvailablity);
        availability.addDay(dayAvailablity,date);

        resetDayAvailablity(dayAvailablity);
        setSatuerday(dayAvailablity);
        availability.addDay(dayAvailablity,date);

        resetDayAvailablity(dayAvailablity);
        setSunday(dayAvailablity);
        availability.addDay(dayAvailablity,date);

    }

    private void setSunday(Boolean[] dayAvailablity) {
        if(sunMorning.isSelected()){
            dayAvailablity[0] = true;
        }
        if(sunAfternoon.isSelected()){
            dayAvailablity[1] = true;
        }
        if(sunEvening.isSelected()){
            dayAvailablity[2] = true;
        }
    }

    private void setSatuerday(Boolean[] dayAvailablity) {
        if(satMorning.isSelected()){
            dayAvailablity[0] = true;
        }
        if(satAfternoon.isSelected()){
            dayAvailablity[1] = true;
        }
        if(satEvening.isSelected()){
            dayAvailablity[2] = true;
        }
    }

    private void setFriday(Boolean[] dayAvailablity) {
        if(friMorning.isSelected()){
            dayAvailablity[0] = true;
        }
        if(friAfternoon.isSelected()){
            dayAvailablity[1] = true;
        }
        if(friEvening.isSelected()){
            dayAvailablity[2] = true;
        }
    }

    private void setThursday(Boolean[] dayAvailablity) {
        if(thurMorning.isSelected()){
            dayAvailablity[0] = true;
        }
        if(thurAfternoon.isSelected()){
            dayAvailablity[1] = true;
        }
        if(thurEvening.isSelected()){
            dayAvailablity[2] = true;
        }
    }

    private void setWednesday(Boolean[] dayAvailablity) {
        if(wedMorning.isSelected()){
            dayAvailablity[0] = true;
        }
        if(wedAfternoon.isSelected()){
            dayAvailablity[1] = true;
        }
        if(wedEvening.isSelected()){
            dayAvailablity[2] = true;
        }
    }

    private void setTuesday(Boolean[] dayAvailablity) {
        if(tueMorning.isSelected()){
            dayAvailablity[0] = true;
        }
        if(tueAfternoon.isSelected()){
            dayAvailablity[1] = true;
        }
        if(tueEvening.isSelected()){
            dayAvailablity[2] = true;
        }
    }

    private void setMonday(Boolean[] dayAvailablity) {
        if(monMorning.isSelected()){
            dayAvailablity[0] = true;
        }
        if(monAfternoon.isSelected()){
            dayAvailablity[1] = true;
        }
        if(monEvening.isSelected()){
            dayAvailablity[2] = true;
        }
    }

    private void resetDayAvailablity(Boolean[] dayAvailablity) {
        for(int i = 0; i < dayAvailablity.length; i++ ){
            dayAvailablity[i] = false;
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
