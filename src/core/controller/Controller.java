package core.controller;
import core.model.Employee;
import core.model.Login;
import core.model.Register;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Konn on 13/03/2017.
 */
public class Controller {
    //Login and register customer fields
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Label lblLoginError;
    @FXML
    private Label lblLabelUser;
    @FXML
    private TextField txtUsernameReg;
    @FXML
    private TextField txtPasswordReg;
    @FXML
    private TextField txtPassVerifyReg;
    @FXML
    private TextField txtNameReg;
    @FXML
    private TextField txtAddressReg;
    @FXML
    private TextField txtContactReg;
    @FXML
    private Label lblErrors;
    @FXML
    private Button btnLoginClicked;
    @FXML
    private Button btnRegister;
    @FXML
    private Label lblRegisterTest;

    //Add Employee Fields
    @FXML
    private TextField txtAddName;
    @FXML
    private TextField txtAddRole;
    @FXML
    private TextField txtAddEmail;
    @FXML
    private TextField txtAddPhone;


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
    private Label lblEmpAdded;

    @FXML
    public void btnSignUpClicked(ActionEvent event) throws IOException {
        Parent Register_parent = FXMLLoader.load(getClass().getResource("../view/RegisterPage.fxml"));
        Scene Register_scene = new Scene (Register_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(Register_scene);
        primaryStage.show();
    }

    @FXML
    public void btnBackClicked(ActionEvent event) throws IOException {
        Parent Login_parent = FXMLLoader.load(getClass().getResource("../view/LoginPage.fxml"));
        Scene Login_scene = new Scene (Login_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(Login_scene);
        primaryStage.show();
    }

    @FXML
    public void RegisterPopup(ActionEvent event) throws IOException{
        Parent RegisterSuccess_parent = FXMLLoader.load(getClass().getResource("../view/RegisterSuccess.fxml"));
        Scene RegisterSuccess_scene = new Scene (RegisterSuccess_parent);
        Stage PopUpStage = new Stage();
        PopUpStage.setScene(RegisterSuccess_scene);
        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(e -> PopUpStage.close());
        PopUpStage.show();
        delay.play();

    }

    @FXML
    public void btnRegisterClicked (ActionEvent event) throws IOException {
        HashMap<String, String> customerDetails = new HashMap<>();
        Register reg = new Register();
        Register.attemptOutcome error;

        customerDetails.put("name", txtNameReg.getText());
        customerDetails.put("userName", txtUsernameReg.getText());
        customerDetails.put("password1", txtPasswordReg.getText());
        customerDetails.put("password2", txtPassVerifyReg.getText());
        customerDetails.put("address", txtAddressReg.getText());
        customerDetails.put("phoneNo", txtContactReg.getText());

        error = reg.registerAttempt(customerDetails);
        System.out.println(error);

        if (error == Register.attemptOutcome.EMPTY_FIELDS){
            lblErrors.setText("Empty fields exist!");
        } else if (error == Register.attemptOutcome.PASSWORD_UNSATISFIED){
            lblErrors.setText("Password: 8+ chars, 1 upper, 1 lower, 1 digit");
        } else if (error == Register.attemptOutcome.PASSWORDS_DIFFERENT){
            lblErrors.setText("Passwords do not match!");
        } else if (error == Register.attemptOutcome.USERNAME_TAKEN){
            lblErrors.setText("Username is taken!");
        } else if (error == Register.attemptOutcome.PHONENO_FAIL) {
            lblErrors.setText("Invalid Phone Number!");
        } else if (error == Register.attemptOutcome.WRITE_FAIL) {
            lblErrors.setText("Failed to write!");
        } else {
            lblErrors.setText("");
            btnBackClicked(event);
            RegisterPopup(event);
        }
    }

    @FXML
    public void btnLoginClicked (ActionEvent event) throws IOException{
        String inputUsername = txtUsername.getText();
        String inputPassword = txtPassword.getText();
        Login log = new Login();
        int result = log.validateAttempt(inputUsername,inputPassword);
        if (result == -1){
            lblLoginError.setText("Incorrect Login info!");
        }
        else if (result == 1){
            System.out.println("Logged in as: " + inputUsername);
            Parent LoginSuccess_parent = FXMLLoader.load(getClass().getResource("../view/LoginSuccess.fxml"));
            Scene LoginSuccess_scene = new Scene (LoginSuccess_parent);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.close();
            primaryStage.setScene(LoginSuccess_scene);
            primaryStage.show();
        }
        else if (result == 2){
            System.out.println("Logged in as: " + inputUsername);
            Parent LoginSuccess_parent = FXMLLoader.load(getClass().getResource("../view/BusinessHome.fxml"));
            Scene LoginSuccess_scene = new Scene (LoginSuccess_parent);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.close();
            primaryStage.setScene(LoginSuccess_scene);
            primaryStage.show();
        }
    }


    //Business home, add and remove employee controls

    @FXML
    public void btnGotoAddEmp(ActionEvent event) throws IOException {
        Parent addEmployee_parent = FXMLLoader.load(getClass().getResource("../view/AddEmployeePage.fxml"));
        Scene addEmployee_scene = new Scene(addEmployee_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(addEmployee_scene);
        primaryStage.show();
    }

    @FXML
    public void btnGotoRemoveEmp(ActionEvent event) throws IOException {
        Parent removeEmp_parent = FXMLLoader.load(getClass().getResource("../view/RemoveEmployeePage.fxml"));
        Scene removeEmp_scene = new Scene((removeEmp_parent));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(removeEmp_scene);
        primaryStage.show();
    }

    @FXML
    public void btnBackToBusinessHome(ActionEvent event) throws IOException {
        Parent business_parent = FXMLLoader.load(getClass().getResource("../view/BusinessHome.fxml"));
        Scene business_scene = new Scene (business_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(business_scene);
        primaryStage.show();
    }

    @FXML
    public void btnFindEmp() throws IOException, SQLException{

        Employee employee = new Employee();
        String empName = txtEmpName.getText();
        int empID;

        empID = employee.findEmployee(empName);

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
        result = Employee.removeEmployee(empID, empName);
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
    public void btnAddEmp() throws IOException {
        String empName = txtAddName.getText();
        String empRole = txtAddRole.getText();
        String email = txtAddEmail.getText();
        String phone = txtAddPhone.getText();
        int result;

        result = Employee.addEmployee(empName, empRole, email, phone);

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
    public void btnAddAvailability(ActionEvent event) throws IOException {
        Parent addAvailability_parent = FXMLLoader.load(getClass().getResource("../view/NewAvailability.fxml"));
        Scene addAvailability_scene = new Scene(addAvailability_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(addAvailability_scene);
        primaryStage.show();
    }



}
