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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Konn on 13/03/2017.
 */
public class Controller {

    private static final Logger log = LogManager.getLogger(Controller.class.getName());

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
        log.debug("Sign up button clicked");
        log.debug("Loading sign up page...");
        Parent Register_parent = FXMLLoader.load(getClass().getResource("../view/RegisterPage.fxml"));
        Scene Register_scene = new Scene (Register_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(Register_scene);
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

    @FXML
    public void RegisterPopup(ActionEvent event) throws IOException{
        log.debug("Loading registration success popup...");
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
        log.debug("Register button clicked, attempting to register new customer");
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

        if (error == Register.attemptOutcome.EMPTY_FIELDS){
            log.info("Failed register attempt: Empty fields exist.");
            lblErrors.setText("Empty fields exist!");
        } else if (error == Register.attemptOutcome.PASSWORD_UNSATISFIED){
            log.info("Failed register attempt: Failed password criteria.");
            lblErrors.setText("Password: 8+ chars, 1 upper, 1 lower, 1 digit");
        } else if (error == Register.attemptOutcome.PASSWORDS_DIFFERENT){
            log.info("Failed register attempt: Password different.");
            lblErrors.setText("Passwords do not match!");
        } else if (error == Register.attemptOutcome.USERNAME_TAKEN){
            log.info("Failed register attempt: Username taken.");
            lblErrors.setText("Username is taken!");
        } else if (error == Register.attemptOutcome.PHONENO_FAIL) {
            log.info("Failed register attempt: Incorrect phone number.");
            lblErrors.setText("Invalid Phone Number!");
        } else if (error == Register.attemptOutcome.WRITE_FAIL) {
            log.info("Failed register attempt: Failed to write to database.");
            lblErrors.setText("Failed to write!");
        } else {
            log.info("Successful register attempt.");
            lblErrors.setText("");
            btnBackClicked(event);
            RegisterPopup(event);
        }
    }

    @FXML
    public void btnLoginClicked (ActionEvent event) throws IOException{
        log.debug("Login button clicked, attempting to login");
        String inputUsername = txtUsername.getText();
        String inputPassword = txtPassword.getText();
        Login login = new Login();
        log.debug("Calling method validateAttempt, leaving controller...");
        int result = login.validateAttempt(inputUsername,inputPassword);
        log.debug("Returned to controller");
        if (result == -1){
            log.debug("Failed login attempt");
            lblLoginError.setText("Incorrect Login info!");
        }
        else if (result == 1){
            log.debug("Customer Logged in as: " + inputUsername);
            log.debug("Loading customer page...");
            Parent LoginSuccess_parent = FXMLLoader.load(getClass().getResource("../view/ChooseBusiness.fxml"));
            Scene LoginSuccess_scene = new Scene (LoginSuccess_parent);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.close();
            primaryStage.setScene(LoginSuccess_scene);
            primaryStage.show();
        }
        else if (result == 2){
            log.debug("Owner Logged in as: " + inputUsername);
            log.debug("Loading owner page...");
            Parent LoginSuccess_parent = FXMLLoader.load(getClass().getResource("../view/BusinessHome.fxml"));
            Scene LoginSuccess_scene = new Scene (LoginSuccess_parent);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.close();
            primaryStage.setScene(LoginSuccess_scene);
            primaryStage.show();
        }
    }


    //Employee home, add and remove employee controls

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
    public void btnBackToBusinessHome(ActionEvent event) throws IOException {
        log.debug("Back (to business home) button clicked, loading business home page");
        Parent business_parent = FXMLLoader.load(getClass().getResource("../view/BusinessHome.fxml"));
        Scene business_scene = new Scene (business_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(business_scene);
        primaryStage.show();
    }

    @FXML
    public void btnFindEmp() throws IOException, SQLException{

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
    public void btnAddEmp() throws IOException {
        log.debug("Add employee button clicked");
        String empName = txtAddName.getText();
        String empRole = txtAddRole.getText();
        String email = txtAddEmail.getText();
        String phone = txtAddPhone.getText();
        int result;

        log.debug("Calling addEmployee Method, leaving controller...");
        result = Employee.addEmployee(empName, empRole, email, phone);
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



}
