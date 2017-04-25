package core.controller;

import core.model.Business;
import core.model.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by harry on 20/04/2017.
 */
public class AddServiceController {

    private static final Logger log = LogManager.getLogger(AddEmpController.class.getName());

    private Business business = new Business(Database.getInstance());

    @FXML
    private Slider sliderLengthTime;

    @FXML
    private Label lblAddError;

    @FXML
    private TextField txtAddName;

    /**Adds new service to the database*/
    @FXML
    public void btnAddNewService (ActionEvent e) {
        log.debug("Add service button clicked!");
        String name = txtAddName.getText();
        double lengthD = sliderLengthTime.getValue();
        int length = (int) lengthD;
        int result  = business.addNewService(name,length);
        if(result == 1){
            log.debug("Service added.");
            lblAddError.setTextFill(Color.web("#ffffff"));
            lblAddError.setText("Successfully added service!");
        }
        else if(result == -1){
            log.debug("Failed to add service: invalid name entered");
            lblAddError.setTextFill(Color.web("#ff0000"));
            lblAddError.setText("Name must only contain letters & spaces!");
        }
        else if(result == -2){
            log.debug("Failed to add service: role exists!");
            lblAddError.setTextFill(Color.web("#ff0000"));
            lblAddError.setText("Role already exists!");
        }
        else if(result == 0) {
            log.debug("Failed to add service to database");
            lblAddError.setTextFill(Color.web("#ff0000"));
            lblAddError.setText("Failed to add to database!");
        }
    }

    @FXML
    public void btnBackToBusinessHome(ActionEvent event) throws IOException {
        log.debug("Back (to business home) button clicked, loading business home page");
        Parent business_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/BusinessHome.fxml"));
        Scene business_scene = new Scene (business_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(business_scene);
        primaryStage.show();
    }
}
