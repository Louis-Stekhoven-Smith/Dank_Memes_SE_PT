package core.controller;

import core.model.Database;
import core.model.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Konn on 2/04/2017.
 */
public class serviceTypeController {

    private Session session = Session.getInstance();

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

    public static String length;

    public static String[] roleArray;

    private Database database = Database.getInstance();
    private static final Logger log = LogManager.getLogger(serviceTypeController.class.getName());

    @FXML
    public void initialize() throws SQLException, IOException {
        log.debug("serviceTypeController INITIALLISEDDD!");
        loadRoles();
    }

    public void loadRoles() throws SQLException, IOException {
        log.debug("Loading Roles..");
        ResultSet count;
        int counter;
        String findRoleTypeCount = "SELECT count(*) AS total FROM availableServices WHERE businessID =" + session.getBusinessSelected();
        count = database.queryDatabase(findRoleTypeCount);
        counter = count.getInt("total");

        ResultSet rs;
        String role;
        Database database = Database.getInstance();
        String findEmpSQL = "SELECT DISTINCT serviceName FROM availableServices WHERE businessID =" + session.getBusinessSelected();
        rs = database.queryDatabase(findEmpSQL);
        String[] myArray = new String[counter];
        for (int i=0; i<myArray.length;i++) {
            rs.next();
            role = rs.getString("serviceName");
            myArray[i]=role;
        }
        roleArray = myArray;
        fillButtons(myArray, counter);
    }

    public void queryServiceLength(String role) throws IOException, SQLException{
        ResultSet rs;
        String findRoleTypeCount = "SELECT serviceLength FROM availableServices WHERE serviceName=" + "'" + role + "'";
        rs = database.queryDatabase(findRoleTypeCount);
        length = rs.getString("serviceLength");
    }

    public void fillButtons(String[] myArray, int counter) throws IOException, SQLException{
        if (counter == 0){

        } else if (counter == 1){
            queryServiceLength(myArray[0]);
            btnService1.setVisible(true);
            btnService1.setText(myArray[0]+" | "+length+" min");
        } else if (counter == 2){
            queryServiceLength(myArray[0]);
            btnService1.setVisible(true);
            btnService1.setText(myArray[0]+" | "+length+" min");
            queryServiceLength(myArray[1]);
            btnService2.setVisible(true);
            btnService2.setText(myArray[1]+" | "+length+" min");
        } else if (counter == 3){
            queryServiceLength(myArray[0]);
            btnService1.setVisible(true);
            btnService1.setText(myArray[0]+" | "+length+" min");
            queryServiceLength(myArray[1]);
            btnService2.setVisible(true);
            btnService2.setText(myArray[1]+" | "+length+" min");
            queryServiceLength(myArray[2]);
            btnService3.setVisible(true);
            btnService3.setText(myArray[2]+" | "+length+" min");
        } else if (counter == 4){
            queryServiceLength(myArray[0]);
            btnService1.setVisible(true);
            btnService1.setText(myArray[0]+" | "+length+" min");
            queryServiceLength(myArray[1]);
            btnService2.setVisible(true);
            btnService2.setText(myArray[1]+" | "+length+" min");
            queryServiceLength(myArray[2]);
            btnService3.setVisible(true);
            btnService3.setText(myArray[2]+" | "+length+" min");
            queryServiceLength(myArray[3]);
            btnService4.setVisible(true);
            btnService4.setText(myArray[3]+" | "+length+" min");
        } else if (counter == 5){
            queryServiceLength(myArray[0]);
            btnService1.setVisible(true);
            btnService1.setText(myArray[0]+" | "+length+" min");
            queryServiceLength(myArray[1]);
            btnService2.setVisible(true);
            btnService2.setText(myArray[1]+" | "+length+" min");
            queryServiceLength(myArray[2]);
            btnService3.setVisible(true);
            btnService3.setText(myArray[2]+" | "+length+" min");
            queryServiceLength(myArray[3]);
            btnService4.setVisible(true);
            btnService4.setText(myArray[3]+" | "+length+" min");
            queryServiceLength(myArray[4]);
            btnService5.setVisible(true);
            btnService5.setText(myArray[4]+" | "+length+" min");
        } else {
            System.out.println("FAIL");
        }
    }

    @FXML
    public String btnService1Clicked(javafx.event.ActionEvent event) throws IOException{
        type = roleArray[0];
        log.debug("Service " + type + " chosen");
        loadAvailBookings(event);
        return type;
    }
        //latest: choosing service type and showing corresponding employee+times
    @FXML
    public String btnService2Clicked(javafx.event.ActionEvent event) throws IOException{
        type = roleArray[1];
        log.debug("Service " + type + " chosen");
        loadAvailBookings(event);
        return type;
    }

    @FXML
    public String btnService3Clicked(javafx.event.ActionEvent event) throws IOException{
        type = roleArray[2];
        log.debug("Service " + type + " chosen");
        loadAvailBookings(event);
        return type;
    }

    @FXML
    public String btnService4Clicked(javafx.event.ActionEvent event) throws IOException{
        type = roleArray[3];
        log.debug("Service " + type + " chosen");
        loadAvailBookings(event);
        return type;
    }

    @FXML
    public String btnService5Clicked(javafx.event.ActionEvent event) throws IOException{
        type = roleArray[4];
        log.debug("Service " + type + " chosen");
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