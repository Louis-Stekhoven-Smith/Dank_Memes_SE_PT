package core.controller;

import core.model.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Konn on 31/03/2017.
 */
public class ChooseBusinessController {

    Session session = Session.getInstance();
    /*TODO  hard code a bunch business buttons, hide them if a business matching that id doesn't yet exist*/
    @FXML
    public void btnBusiness1Clicked(javafx.event.ActionEvent event) throws IOException {
        session.setBusinessSelected(1);
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
