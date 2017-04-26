package core.controller;

import core.model.Database;
import core.model.Employee;
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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by harry on 8/04/2017.
 */
public class AddEmpController {

    private static final Logger log = LogManager.getLogger(AddEmpController.class.getName());

    private Database database = Database.getInstance();
    private Employee employee = new Employee(Database.getInstance());
    //Add Employee Fields
    @FXML
    private TextField txtAddName;
    @FXML
    private ComboBox<String> comboRoles;
    @FXML
    private TextField txtAddEmail;
    @FXML
    private TextField txtAddPhone;
    @FXML
    private TextField txtAddAddress;
    @FXML
    private Label lblEmpAdded;

    @FXML
    public void initialize(){
        log.debug("Initializing the roles combo box");
        ResultSet rs;
        ObservableList<String> services = FXCollections.observableArrayList();

        //Get businessID function here
        String servicesSQL = "SELECT serviceName FROM availableServices WHERE businessID = 1";
        rs = database.queryDatabase(servicesSQL);

        try {
            while(rs.next()){
                services.add(rs.getString("serviceName"));
            }
            comboRoles.setItems(services);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnAddEmp() throws IOException {
        log.debug("Add employee button clicked");
        String empName = txtAddName.getText();
        String empRole = comboRoles.getValue();
        String address = txtAddAddress.getText();
        String email = txtAddEmail.getText();
        String phone = txtAddPhone.getText();
        int result;

        if(empName.equals("") || comboRoles == null || email.equals("") || phone.equals("")){
            lblEmpAdded.setTextFill(Color.web("#ff0000"));
            lblEmpAdded.setText("Please enter all of the fields");
            return;
        }

        log.debug("Calling addEmployee Method, leaving controller...");
        result = employee.addEmployee(empName, empRole, address, email, phone);
        log.debug("Returned to controller");

        if(result == -1){
            lblEmpAdded.setTextFill(Color.web("#ff0000"));
            lblEmpAdded.setText("Name must only contain letters & spaces!");
            txtAddName.setText("");
        }
        else if(result == -2){
            lblEmpAdded.setTextFill(Color.web("#ff0000"));
            lblEmpAdded.setText("Invalid Email!");
            txtAddEmail.setText("");
        }
        else if(result == -3){
            lblEmpAdded.setTextFill(Color.web("#ff0000"));
            lblEmpAdded.setText("Invalid phone number!");
            txtAddPhone.setText("");
        }
        else if(result == 0){
            lblEmpAdded.setTextFill(Color.web("#ff0000"));
            lblEmpAdded.setText("Failed to add employee!");
        }
        else if(result == 1){
            lblEmpAdded.setText("Employee has been added!");
            txtAddName.setText("");
            txtAddEmail.setText("");
            txtAddPhone.setText("");
        }

        /* TODO Get business ID function and validation */
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
