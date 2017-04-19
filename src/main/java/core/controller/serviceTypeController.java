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

    public static String type;

    @FXML
    public void initialize() throws SQLException, IOException {
        System.out.println("serviceTypeController INITIALLISEDDD!");
        loadRoles();
    }

    public void loadRoles() throws SQLException, IOException {
        ResultSet rs;
        String role;
        Database database = Database.getInstance();
        String findEmpSQL = "SELECT DISTINCT employeeRole FROM employeeDetails";
        rs = database.queryDatabase(findEmpSQL);
        String[] myArray = new String[4];
        for (int i=0; i<myArray.length;i++) {
            rs.next();
            role = rs.getString("employeeRole");
            myArray[i]=role;
            System.out.println(role);
        } fillButtons(myArray);
    }

    public void fillButtons(String[] myArray) throws IOException{
        btnService1.setText(myArray[0]);
        btnService2.setText(myArray[1]);
        btnService3.setText(myArray[2]);
        btnService4.setText(myArray[3]);
    }

    @FXML
    public String btnService1Clicked(javafx.event.ActionEvent event) throws IOException{
        type = btnService1.getText();
        System.out.println(type);
        System.out.println("Service " + type + " chosen");
        Parent viewBookings_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/AvailBookings.fxml"));
        Scene viewBookings_scene = new Scene(viewBookings_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(viewBookings_scene);
        primaryStage.show();
        return type;
    }
        //latest: choosing service type and showing corresponding employee+times
    @FXML
    public String btnService2Clicked(javafx.event.ActionEvent event) throws IOException{
        type = btnService2.getText();
        System.out.println(type);
        System.out.println("Service " + type + " chosen");
        Parent viewBookings_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/AvailBookings.fxml"));
        Scene viewBookings_scene = new Scene(viewBookings_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(viewBookings_scene);
        primaryStage.show();
        return type;
    }

    @FXML
    public String btnService3Clicked(javafx.event.ActionEvent event) throws IOException{
        type = btnService3.getText();
        System.out.println(type);
        System.out.println("Service " + type + " chosen");
        Parent viewBookings_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/AvailBookings.fxml"));
        Scene viewBookings_scene = new Scene(viewBookings_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(viewBookings_scene);
        primaryStage.show();
        return type;
    }

    @FXML
    public String btnService4Clicked(javafx.event.ActionEvent event) throws IOException{
        type = btnService4.getText();
        System.out.println(type);
        System.out.println("Service " + type + " chosen");
        Parent viewBookings_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/AvailBookings.fxml"));
        Scene viewBookings_scene = new Scene(viewBookings_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(viewBookings_scene);
        primaryStage.show();
        return type;
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