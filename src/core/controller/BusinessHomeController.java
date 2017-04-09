package core.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by harry on 8/04/2017.
 */
public class BusinessHomeController {
    private static final Logger log = LogManager.getLogger(BusinessHomeController.class.getName());

    @FXML
    public void btnGotoRemoveEmp(ActionEvent event) throws IOException {
        log.debug("Go to remove employee button clicked");
        log.debug("Loading remove employee page...");
        Parent removeEmp_parent = FXMLLoader.load(getClass().getResource("../view/RemoveEmployeePage.fxml"));
        Scene removeEmp_scene = new Scene((removeEmp_parent));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(removeEmp_scene);
        primaryStage.show();
    }

    @FXML
    public void btnGotoAddEmp(ActionEvent event) throws IOException {
        log.debug("Go to add employee button clicked");
        log.debug("Loading add employee page...");
        Parent addEmployee_parent = FXMLLoader.load(getClass().getResource("../view/AddEmployeePage.fxml"));
        Scene addEmployee_scene = new Scene(addEmployee_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(addEmployee_scene);
        primaryStage.show();
    }

    @FXML
    public void btnGotoViewBookings(ActionEvent event) throws IOException {
        log.debug("Go to view bookings button clicked");
        log.debug("Loading view bookings page...");
        Parent bookings_parent = FXMLLoader.load(getClass().getResource("../view/ViewBookings.fxml"));
        Scene bookings_scene = new Scene(bookings_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(bookings_scene);
        primaryStage.show();
    }

    @FXML
    public void btnAddAvailability(ActionEvent event) throws IOException {
        log.debug("Go to add availability button clicked");
        log.debug("Loading add availability page, changing controller to AvailabilityController...");
        Parent addAvailability_parent = FXMLLoader.load(getClass().getResource("../view/AddAvailability.fxml"));
        Scene addAvailability_scene = new Scene(addAvailability_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(addAvailability_scene);
        primaryStage.show();
    }

    @FXML
    public void btnBackClicked(ActionEvent event) throws IOException {
        log.debug("Back to login page button clicked");
        log.debug("Loading login page...");
        Parent Login_parent = FXMLLoader.load(getClass().getResource("../view/LoginPage.fxml"));
        Scene Login_scene = new Scene (Login_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(Login_scene);
        primaryStage.show();
    }


}
