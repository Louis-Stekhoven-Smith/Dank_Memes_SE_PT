package core.controller;

import core.model.Database;
import core.model.Session;
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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by harry on 8/04/2017.
 */
public class BusinessHomeController {
    private static final Logger log = LogManager.getLogger(BusinessHomeController.class.getName());

    @FXML
    public void btnGotoRemoveEmp(ActionEvent event) throws IOException {
        log.debug("Go to remove employee button clicked");
        log.debug("Loading remove employee page...");
        Parent removeEmp_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/RemoveEmployeePage.fxml"));
        Scene removeEmp_scene = new Scene((removeEmp_parent));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(removeEmp_scene);
        primaryStage.show();
    }

    @FXML
    public void btnGotoAddEmp(ActionEvent event) throws IOException {
        ResultSet res;
        Session session = Session.getInstance();
        Database database = Database.getInstance();
        log.debug("Go to add employee button clicked");
        log.debug("Loading add employee page...");
        res = database.queryDatabase("SELECT businessID FROM availableServices WHERE businessID = "+ session.getLoggedInUserId());
        try {
            if(res.next()){
                Parent addEmployee_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/AddEmployeePage.fxml"));
                Scene addEmployee_scene = new Scene(addEmployee_parent);
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.close();
                primaryStage.setScene(addEmployee_scene);
                primaryStage.show();
            }
        } catch (SQLException e) {
            /*TODO
                    Display an error message advising the user needs to create at least one service type before
                    they can add employees
                 */

            e.printStackTrace();
        }
    }

    @FXML
    public void btnGotoViewBookings(ActionEvent event) throws IOException {
        log.debug("Go to view bookings button clicked");
        log.debug("Loading view bookings page...");
        Parent bookings_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/ViewBookings.fxml"));
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
        Parent addAvailability_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/AddAvailability.fxml"));
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
        Parent Login_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/LoginPage.fxml"));
        Scene Login_scene = new Scene (Login_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(Login_scene);
        primaryStage.show();
    }

    @FXML
    public void btnGotoAddService(ActionEvent event) throws IOException {
        log.debug("Goto add service page button clicked");
        log.debug("Loading add new service page...");
        Parent addService_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/AddService.fxml"));
        Scene addService_scene = new Scene (addService_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(addService_scene);
        primaryStage.show();
    }

}
