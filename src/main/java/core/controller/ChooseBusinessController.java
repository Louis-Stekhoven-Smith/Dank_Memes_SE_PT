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
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Konn on 31/03/2017.
 */




public class ChooseBusinessController {

    public int businessSelector = 0;

    @FXML private Button btnBusiness1;
    @FXML private ComboBox<String> businessChooser;

    private Database database = Database.getInstance();

    public void initialize() throws IOException, SQLException {
        ResultSet count;
        int counter;
        String findBusinessCount = "SELECT count(*) AS total FROM businessDetails";
        count = database.queryDatabase(findBusinessCount);
        counter = count.getInt("total");

        ResultSet rs;
        String role;
        Database database = Database.getInstance();
        String findEmpSQL = "SELECT businessName FROM businessDetails";
        rs = database.queryDatabase(findEmpSQL);
        String[] myArray = new String[counter];
        for (int i=0; i<myArray.length;i++) {
            rs.next();
            role = rs.getString("businessName");
            myArray[i] = role;
            businessChooser.getItems().addAll(String.valueOf(myArray[i]));
        }

    }

    public void comboChanged() throws IOException, SQLException {
        btnBusiness1.setVisible(true);

        ResultSet business;
        int businessNo;
        String findBusinessNo = "SELECT businessID FROM businessDetails WHERE businessName = '" + businessChooser.getValue() + "'";
        business = database.queryDatabase(findBusinessNo);
        businessNo = business.getInt("businessID");
        businessSelector = businessNo;
        System.out.println(businessSelector);

        ResultSet businessBG;
        String businessBackground;
        String findBusinessBG = "SELECT imageURL FROM businessDetails WHERE businessName = '" + businessChooser.getValue() + "'";
        businessBG = database.queryDatabase(findBusinessBG);
        businessBackground = businessBG.getString("imageURL");


        btnBusiness1.setStyle("-fx-background-image: url('"+businessBackground+"')");
        btnBusiness1.setVisible(true);
    }

    Session session = Session.getInstance();
    /*TODO  hard code a bunch business buttons, hide them if a business matching that id doesn't yet exist*/
    @FXML
    public void btnBusiness1Clicked(javafx.event.ActionEvent event) throws IOException {
        session.setBusinessSelected(businessSelector);
        selectedBusiness(event);
    }

    @FXML
    public void btnBusiness2Clicked(javafx.event.ActionEvent event) throws IOException {
        session.setBusinessSelected(2);
        selectedBusiness(event);
    }

    @FXML
    public void btnBusiness3Clicked(javafx.event.ActionEvent event) throws IOException {
        session.setBusinessSelected(3);
        selectedBusiness(event);
    }

    private void selectedBusiness(ActionEvent event) throws IOException {
        Parent viewBookings_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/ServiceType.fxml"));
        Scene viewBookings_scene = new Scene(viewBookings_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(viewBookings_scene);
        primaryStage.show();
    }


    //goes back to main menu
    public void btnBackPressed(javafx.event.ActionEvent event) throws IOException{
        Parent viewBookings_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/CustomerNavigation.fxml"));
        Scene viewBookings_scene = new Scene(viewBookings_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(viewBookings_scene);
        primaryStage.show();
    }
}
