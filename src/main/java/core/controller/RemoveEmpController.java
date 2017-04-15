package core.controller;

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
import java.sql.SQLException;

/**
 * Created by harry on 8/04/2017.
 */
public class RemoveEmpController {

    private static final Logger log = LogManager.getLogger(RemoveEmpController.class.getName());
    //Remove Employee Fields
    @FXML
    private TextField txtEmpID;
    @FXML
    private TextField txtEmpName;
    @FXML
    private Label lblFindEmp;
    @FXML
    private Label lblRemoveError;

    @FXML
    public void btnFindEmp() throws IOException, SQLException {

        log.debug("Find employee button clicked");

        Employee employee = new Employee();
        String empName = txtEmpName.getText();
        int empID;

        log.debug("Calling find employee method, leaving controller...");
        empID = employee.findEmployee(empName);
        log.debug("Returned to controller");

        if(empID == -1){
            lblFindEmp.setTextFill(Color.web("#ff0000"));
            lblFindEmp.setText("Failed to find employee");
        }
        else{
            lblFindEmp.setText("We found this employee");
            txtEmpID.setText(Integer.toString(empID));
        }
    }


    @FXML
    public void btnRemoveEmp() throws IOException {
        log.debug("Remove employee button clicked");
        String empName = txtEmpName.getText();
        int result;
        if (empName.equals("") && txtEmpID.getText().equals("")) {
            lblRemoveError.setText("Please find employee!");
            return;
        }
        if (txtEmpID.getText().equals("")){
            lblRemoveError.setText("Please enter an employee ID!");
            return;
        }
        int empID = Integer.parseInt(txtEmpID.getText());
        log.debug("Remove employee button clicked, leaving controller...");
        result = Employee.removeEmployee(empID, empName);
        log.debug("Returned to controller");

        if(result == 1){
            lblRemoveError.setTextFill(Color.web("#ffffff"));
            lblRemoveError.setText("Employee Removed!");
            lblFindEmp.setText("");
            txtEmpName.setText("");
            txtEmpID.setText("");
        } else {
            lblRemoveError.setText("Failed to remove employee!");
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
