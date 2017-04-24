package core.controller;

import core.model.Database;
import core.model.dataClasses.Business;
import core.model.dataClasses.EmpAvailability;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Created by Konn on 31/03/2017.
 */
public class ChooseBusinessController {

    int businessChoice = 0;
    Database database = Database.getInstance();
    public void initialize(){

        /*TODO*/
        /* get all the bossiness logos and any other relevant dets*/

    }

    //loads services page once business is selected
    @FXML
    public int btnBusinessClicked(javafx.event.ActionEvent event) throws IOException {
        businessChoice = 1;
        System.out.println("Business "+businessChoice+" chosen");
        Parent viewBookings_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/ServiceType.fxml"));
        Scene viewBookings_scene = new Scene(viewBookings_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(viewBookings_scene);
        primaryStage.show();
        return businessChoice;
    }

    //goes back to main menu
    public void btnBackPressed(javafx.event.ActionEvent event) throws IOException{
        Parent viewBookings_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/LoginPage.fxml"));
        Scene viewBookings_scene = new Scene(viewBookings_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(viewBookings_scene);
        primaryStage.show();
    }
}
