package core.controller;

import core.model.Database;
import core.model.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;

/**
 * Created by harry on 8/04/2017.
 */
public class AddEmpController {

    private static final Logger log = LogManager.getLogger(AddEmpController.class.getName());
    private ResultSet resultSet;
    private Employee employee = new Employee(Database.getInstance(), resultSet);
    //Add Employee Fields
    @FXML
    private TextField txtAddName;
    @FXML
    private TextField txtAddRole;
    @FXML
    private TextField txtAddEmail;
    @FXML
    private TextField txtAddPhone;
    @FXML
    private Label lblEmpAdded;

    @FXML
    public void btnAddEmp() throws IOException {
        log.debug("Add employee button clicked");
        String empName = txtAddName.getText();
        String empRole = txtAddRole.getText();
        String email = txtAddEmail.getText();
        String phone = txtAddPhone.getText();
        int result;

        log.debug("Calling addEmployee Method, leaving controller...");
        result = employee.addEmployee(empName, empRole, email, phone);
        log.debug("Returned to controller");

        if(empName.equals("") || empRole.equals("") || email.equals("") || phone.equals("")){
            lblEmpAdded.setTextFill(Color.web("#ff0000"));
            lblEmpAdded.setText("Please enter all of the fields");
        }
        else if(result == -1){
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
            txtAddRole.setText("");
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
