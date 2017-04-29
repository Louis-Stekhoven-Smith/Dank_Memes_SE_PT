package core.controller;

import core.model.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by Konn on 2/04/2017.
 */
public class serviceTypeController {

    @FXML
    private Button btnService1;
    @FXML
    private Button btnService2;
    @FXML
    private Button btnService3;
    @FXML
    private Button btnService4;
    @FXML
    private Button btnService5;

    public static String type;

    private Database database = Database.getInstance();

    @FXML
    public void initialize() throws SQLException, IOException {
        System.out.println("serviceTypeController INITIALLISEDDD!");
        loadRoles();
    }

    public void loadRoles() throws SQLException, IOException {
        ResultSet count;
        int counter;
        String findRoleTypeCount = "SELECT count(*) AS total FROM availableServices";
        count = database.queryDatabase(findRoleTypeCount);
        counter = count.getInt("total");

        ResultSet rs;
        String role;
        Database database = Database.getInstance();
        String findEmpSQL = "SELECT DISTINCT serviceName FROM availableServices";
        rs = database.queryDatabase(findEmpSQL);
        String[] myArray = new String[4];
        for (int i=0; i<myArray.length;i++) {
            rs.next();
            role = rs.getString("serviceName");
            myArray[i]=role;
        }
        fillButtons(myArray, counter);
        System.out.println("Counter = " +counter);
        System.out.println(Arrays.toString(myArray));
    }


    public void fillButtons(String[] myArray, int counter) throws IOException{
        if (counter == 0){

        } else if (counter == 1){
            btnService1.setVisible(true);
            btnService1.setText(myArray[0]);
        } else if (counter == 2){
            btnService1.setVisible(true);
            btnService1.setText(myArray[0]);
            btnService2.setVisible(true);
            btnService2.setText(myArray[1]);
        } else if (counter == 3){
            btnService1.setVisible(true);
            btnService1.setText(myArray[0]);
            btnService2.setVisible(true);
            btnService2.setText(myArray[1]);
            btnService3.setVisible(true);
            btnService3.setText(myArray[2]);
        } else if (counter == 4){
            btnService1.setVisible(true);
            btnService1.setText(myArray[0]);
            btnService2.setVisible(true);
            btnService2.setText(myArray[1]);
            btnService3.setVisible(true);
            btnService3.setText(myArray[2]);
            btnService4.setVisible(true);
            btnService4.setText(myArray[3]);
        } else if (counter == 5){
            btnService1.setVisible(true);
            btnService1.setText(myArray[0]);
            btnService2.setVisible(true);
            btnService2.setText(myArray[1]);
            btnService3.setVisible(true);
            btnService3.setText(myArray[2]);
            btnService4.setVisible(true);
            btnService4.setText(myArray[3]);
            btnService5.setVisible(true);
            btnService5.setText(myArray[4]);
        } else {
            System.out.println("FAIL");
        }
    }

    @FXML
    public String btnService1Clicked(javafx.event.ActionEvent event) throws IOException{
        type = btnService1.getText();
        System.out.println(type);
        System.out.println("Service " + type + " chosen");
        loadAvailBookings(event);
        return type;
    }
        //latest: choosing service type and showing corresponding employee+times
    @FXML
    public String btnService2Clicked(javafx.event.ActionEvent event) throws IOException{
        type = btnService2.getText();
        System.out.println(type);
        System.out.println("Service " + type + " chosen");
        loadAvailBookings(event);
        return type;
    }

    @FXML
    public String btnService3Clicked(javafx.event.ActionEvent event) throws IOException{
        type = btnService3.getText();
        System.out.println(type);
        System.out.println("Service " + type + " chosen");
        loadAvailBookings(event);
        return type;
    }

    @FXML
    public String btnService4Clicked(javafx.event.ActionEvent event) throws IOException{
        type = btnService4.getText();
        System.out.println(type);
        System.out.println("Service " + type + " chosen");
        loadAvailBookings(event);
        return type;
    }

    @FXML
    public String btnService5Clicked(javafx.event.ActionEvent event) throws IOException{
        type = btnService5.getText();
        System.out.println(type);
        System.out.println("Service " + type + " chosen");
        loadAvailBookings(event);
        return type;
    }

    public void loadAvailBookings(javafx.event.ActionEvent event) throws IOException{
        Parent viewBookings_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/AvailBookings.fxml"));
        Scene viewBookings_scene = new Scene(viewBookings_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(viewBookings_scene);
        primaryStage.show();
    }

    public void btnBackPressed(javafx.event.ActionEvent event) throws IOException{
        Parent LoginSuccess_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/ChooseBusiness.fxml"));
        Scene LoginSuccess_scene = new Scene (LoginSuccess_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(LoginSuccess_scene);
        primaryStage.show();
    }


}