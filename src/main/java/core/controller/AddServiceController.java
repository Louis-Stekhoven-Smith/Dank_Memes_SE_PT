package core.controller;

import core.model.AddService;
import core.model.Database;
import core.model.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by harry on 20/04/2017.
 */
public class AddServiceController {

    private static final Logger log = LogManager.getLogger(AddServiceController.class.getName());
    private AddService addService = new AddService(Database.getInstance(), Session.getInstance());
    private Database database = Database.getInstance();

    @FXML
    private Slider sliderLengthTime;

    @FXML
    private Label lblAddError;

    @FXML
    private TextField txtAddName;

    @FXML
    private ComboBox<String> comboRemService;

    @FXML
    public void initialize(){
        log.debug("Initializing the services combo box");
        ResultSet rs;
        ObservableList<String> services = FXCollections.observableArrayList();


        String servicesSQL = "SELECT serviceName FROM availableServices WHERE businessID = " + Session.getInstance().getLoggedInUserId();
        rs = database.queryDatabase(servicesSQL);

        try {
            while(rs.next()){
                services.add(rs.getString("serviceName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        comboRemService.setItems(services);
    }

    /**Adds new service to the database*/
    @FXML
    public void btnAddNewService (ActionEvent e) {
        log.debug("Add service button clicked!");
        String name = txtAddName.getText();
        double lengthD = sliderLengthTime.getValue();
        int length = (int) lengthD;
        int result  = addService.addNewService(name,length);
        if(result == 1){
            log.debug("Service added.");
            lblAddError.setTextFill(Color.web("#ffffff"));
            lblAddError.setText("Successfully added service!");
            txtAddName.setText("");
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
        else if(result == -4){
            log.debug("Failed to add service: too many services!");
            lblAddError.setTextFill(Color.web("#ff0000"));
            lblAddError.setText("Exceeded max of 5 services!");
        }
        else if(result == 0) {
            log.debug("Failed to add service to database");
            lblAddError.setTextFill(Color.web("#ff0000"));
            lblAddError.setText("Failed to add to database!");
        }
    }

    @FXML
    public void btnRemoveService(ActionEvent event) throws IOException{
        if(comboRemService.getValue() == null || comboRemService.getValue() == ""){
            log.debug("Failed to remove service from database");
            lblAddError.setTextFill(Color.web("#ff0000"));
            lblAddError.setText("Failed to remove from database!");
            return;
        }
        String removeSQL = "DELETE FROM availableServices WHERE serviceName = " + "'" + comboRemService.getValue() + "'";
        database.updateDatabase(removeSQL);
        lblAddError.setText("Removed service!");
        comboRemService.setValue("");
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
