package core.controller;

import javafx.event.ActionEvent;
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

/**
 * Created by Konn on 28/04/2017.
 */

public class CustomerNavigationController {
    private static final Logger log = LogManager.getLogger(CustomerNavigationController.class.getName());

    @FXML
    private Button btnCustNav1;

    @FXML
    private Button btnCustNav2;

    @FXML
    private Button btnBack;

    public void btnCustNav1Pressed (ActionEvent event) throws IOException {
        log.debug("Customer selected make booking..");
        Parent CustNav1_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/ChooseBusiness.fxml"));
        Scene CustNav1_scene = new Scene (CustNav1_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(CustNav1_scene);
        primaryStage.show();
    }

    public void btnCustNav2Pressed (ActionEvent event) throws IOException {
        log.debug("customer selected view bookings..");
        Parent CustNav2_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/CurrentBookings.fxml"));
        Scene CustNav2_scene = new Scene (CustNav2_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(CustNav2_scene);
        primaryStage.show();
    }

    public void btnBackPressed (ActionEvent event) throws IOException {
        Parent Back_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/LoginPage.fxml"));
        Scene Back_scene = new Scene (Back_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(Back_scene);
        primaryStage.show();
    }

}
