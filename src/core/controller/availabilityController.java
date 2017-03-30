package core.controller;

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
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Konn on 29/03/2017.
 */
public class availabilityController {
    @FXML
    private Button btnUpdateDay;
    @FXML
    private Button btnDate1;
    @FXML
    private Button btnBackToBusinessHome;
    @FXML
    private Button btnDate2;
    @FXML
    private Button btnDate3;
    @FXML
    private Button btnDate4;
    @FXML
    private Button btnDate5;
    @FXML
    private Button btnDate6;
    @FXML
    private Button btnDate7;
    @FXML
    private Button btnDate8;
    @FXML
    private Button btnDate9;
    @FXML
    private Button btnDate10;
    @FXML
    private Button btnDate11;
    @FXML
    private Button btnDate12;
    @FXML
    private Button btnDate13;
    @FXML
    private Button btnDate14;
    @FXML
    private Button btnDate15;
    @FXML
    private Button btnDate16;
    @FXML
    private Button btnDate17;
    @FXML
    private Button btnDate18;
    @FXML
    private Button btnDate19;
    @FXML
    private Button btnDate20;
    @FXML
    private Button btnDate21;
    @FXML
    private Button btnDate22;
    @FXML
    private Button btnDate23;
    @FXML
    private Button btnDate24;
    @FXML
    private Button btnDate25;
    @FXML
    private Button btnDate26;
    @FXML
    private Button btnDate27;
    @FXML
    private Button btnDate28;
    @FXML
    private Button btnDate29;
    @FXML
    private Button btnDate30;
    @FXML
    private Button btnSaveTimes;
    @FXML
    private CheckBox cb9am;
    @FXML
    private CheckBox cb10am;
    @FXML
    private CheckBox cb11am;
    @FXML
    private CheckBox cb12pm;
    @FXML
    private CheckBox cb1pm;
    @FXML
    private CheckBox cb2pm;
    @FXML
    private CheckBox cb3pm;
    @FXML
    private CheckBox cb4pm;

    /**
     * Created by louie on 31/03/2017.
     */

    @FXML
    private Button btnBackToBusinessScreen;
    @FXML
    private TextField txtEmployeeName;
    @FXML
    private Label dates;

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
    public void initialize() {
        dates.setText("test");
    }






    public int loopVal;
    public String currentDay;
        String newDay;
        public String finalDay;

    Date today = new Date();
    Calendar cal = new GregorianCalendar();

    public void btnUpdateDayClicked(ActionEvent event) throws IOException {
        for (loopVal = 1; loopVal <= 30; loopVal++) {
            cal.setTime(today);
            cal.add(Calendar.DAY_OF_MONTH, loopVal);
            Date today30 = cal.getTime();
            if (loopVal == 1) {
                btnDate1.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 2){
                btnDate2.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 3){
                btnDate3.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 4){
                btnDate4.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 5){
                btnDate5.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 6){
                btnDate6.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 7){
                btnDate7.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 8){
                btnDate8.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 9){
                btnDate9.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 10){
                btnDate10.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 11){
                btnDate11.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 12){
                btnDate12.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 13){
                btnDate13.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 14){
                btnDate14.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 15){
                btnDate15.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 16){
                btnDate16.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 17){
                btnDate17.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 18){
                btnDate18.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 19){
                btnDate19.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 20){
                btnDate20.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 21){
                btnDate21.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 22){
                btnDate22.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 23){
                btnDate23.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 24){
                btnDate24.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 25){
                btnDate25.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 26){
                btnDate26.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 27){
                btnDate27.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 28){
                btnDate28.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 29){
                btnDate29.setText(new SimpleDateFormat("dd/MM").format(today30));
            } else if (loopVal == 30){
                btnDate30.setText(new SimpleDateFormat("dd/MM").format(today30));
            }
        }
    }


    public String reformatDate(String currentDay) throws IOException{
        newDay = currentDay.replaceAll("/","");
        finalDay = newDay.replaceFirst("(.*)(.*)(.)(.)", "$3$4$2$1");
        System.out.println(finalDay);
        return finalDay;
    }

    public void btnDate1Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate1.getText();
        reformatDate(currentDay);
    }

    public void btnDate2Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate2.getText();
        reformatDate(currentDay);
    }

    public void btnDate3Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate3.getText();
        reformatDate(currentDay);
    }

    public void btnDate4Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate4.getText();
        reformatDate(currentDay);
    }

    public void btnDate5Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate5.getText();
        reformatDate(currentDay);
    }

    public void btnDate6Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate6.getText();
        reformatDate(currentDay);
    }

    public void btnDate7Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate7.getText();
        reformatDate(currentDay);
    }

    public void btnDate8Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate8.getText();
        reformatDate(currentDay);
    }

    public void btnDate9Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate9.getText();
        reformatDate(currentDay);
    }

    public void btnDate10Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate10.getText();
        reformatDate(currentDay);
    }

    public void btnDate11Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate11.getText();
        reformatDate(currentDay);
    }

    public void btnDate12Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate12.getText();
        reformatDate(currentDay);
    }

    public void btnDate13Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate13.getText();
        reformatDate(currentDay);
    }

    public void btnDate14Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate14.getText();
        reformatDate(currentDay);
    }

    public void btnDate15Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate15.getText();
        reformatDate(currentDay);
    }

    public void btnDate16Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate16.getText();
        reformatDate(currentDay);
    }

    public void btnDate17Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate17.getText();
        reformatDate(currentDay);
    }

    public void btnDate18Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate18.getText();
        reformatDate(currentDay);
    }

    public void btnDate19Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate19.getText();
        reformatDate(currentDay);
    }

    public void btnDate20Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate20.getText();
        reformatDate(currentDay);
    }

    public void btnDate21Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate21.getText();
        reformatDate(currentDay);
    }

    public void btnDate22Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate22.getText();
        reformatDate(currentDay);
    }

    public void btnDate23Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate23.getText();
        reformatDate(currentDay);
    }

    public void btnDate24Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate24.getText();
        reformatDate(currentDay);
    }

    public void btnDate25Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate25.getText();
        reformatDate(currentDay);
    }

    public void btnDate26Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate26.getText();
        reformatDate(currentDay);
    }

    public void btnDate27Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate27.getText();
        reformatDate(currentDay);
    }

    public void btnDate28Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate28.getText();
        reformatDate(currentDay);
    }

    public void btnDate29Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate29.getText();
        reformatDate(currentDay);
    }

    public void btnDate30Clicked(ActionEvent event) throws IOException{
        currentDay = btnDate30.getText();
        reformatDate(currentDay);
    }

    public void btnSaveTimes(ActionEvent event) throws IOException{
        String empName = txtEmployeeName.getText();
        int[] dayArray;
        dayArray = new int[8];
        if (cb9am.isSelected()) {
            dayArray[0] = 1;
        }
        if (cb10am.isSelected()) {
            dayArray[1] = 1;
        }
        if (cb11am.isSelected()) {
            dayArray[2] = 1;
        }
        if (cb12pm.isSelected()) {
            dayArray[3] = 1;
        }
        if (cb1pm.isSelected()) {
            dayArray[4] = 1;
        }

        if (cb3pm.isSelected()) {
            dayArray[6] = 1;
        }
        if (cb4pm.isSelected()) {
            dayArray[7] = 1;
        }
        try
        {
            PrintWriter pr = new PrintWriter(finalDay+empName+"TIMES.txt");

            for (int i=0; i<dayArray.length ; i++)
            {
                pr.println(dayArray[i]);
            }
            pr.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("No such file exists.");
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
