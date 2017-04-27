package core.controller;

import core.model.Database;
import core.model.Employee;
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
public class RemoveEmpController {

    private static final Logger log = LogManager.getLogger(RemoveEmpController.class.getName());
    private Database database = Database.getInstance();
    private Employee employee = new Employee(database, Session.getInstance());

    //Remove Employee Fields
    @FXML
    private TextField txtEmpID;
    @FXML
    private ComboBox<String> comboName;
    @FXML
    private Label lblFindEmp;
    @FXML
    private Label lblRemoveError;

    @FXML
    public void initialize(){
        log.debug("Initializing the roles combo box");
        ResultSet rs;
        ObservableList<String> employees = FXCollections.observableArrayList();

        //Get businessID function here
        String servicesSQL = "SELECT name FROM employeeDetails WHERE businessID = 1";
        rs = database.queryDatabase(servicesSQL);

        try {
            while(rs.next()){
                employees.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        comboName.setItems(employees);
    }

    @FXML
    public void btnFindEmp() throws IOException, SQLException {

        log.debug("Find employee button clicked");

        String empName = comboName.getValue();
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
        int empID;
        log.debug("Remove employee button clicked");
        String empName = comboName.getValue();
        Boolean result;
        if (empName == null && txtEmpID.getText().equals("")) {
            lblRemoveError.setText("Please find employee!");
            return;
        }
        if (txtEmpID.getText().equals("")){
            lblRemoveError.setText("Please enter an employee ID!");
            return;
        }
        if(!txtEmpID.getText().matches("[-+]?\\d*\\.?\\d+")){
            lblRemoveError.setText("Non numeric employee ID!");
            return;
        }
        empID = Integer.parseInt(txtEmpID.getText());


        log.debug("Remove employee button clicked, leaving controller...");
        result = employee.removeEmployee(empID);
        log.debug("Returned to controller");

        if(result){
            lblRemoveError.setTextFill(Color.web("#ffffff"));
            lblRemoveError.setText("Employee Removed!");
            lblFindEmp.setText("");
            comboName.valueProperty().set(null);
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
