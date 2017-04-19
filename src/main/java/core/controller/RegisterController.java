package core.controller;

import core.model.Database;
import core.model.Register;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by harry on 8/04/2017.
 */
public class RegisterController {

    private static final Logger log = LogManager.getLogger(AddEmpController.class.getName());

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
    public void btnRegisterClicked (ActionEvent event) throws IOException {
        log.debug("Register button clicked, attempting to register new customer");
        HashMap<String, String> customerDetails = new HashMap<>();
        Register reg = new Register(Database.getInstance());

        customerDetails.put("name", txtNameReg.getText());
        customerDetails.put("userName", txtUsernameReg.getText());
        customerDetails.put("password1", txtPasswordReg.getText());
        customerDetails.put("password2", txtPassVerifyReg.getText());
        customerDetails.put("address", txtAddressReg.getText());
        customerDetails.put("phoneNo", txtContactReg.getText());

        if (!reg.isNotEmpty(customerDetails)){
            log.info("Failed register attempt: Empty fields exist.");
            lblErrors.setText("Empty fields exist!");
        }

        else if(!reg.nameValidation(customerDetails)){
            log.info("Failed register attempt: Failed name validation");
            lblErrors.setText("Name must only contain Letters and spaces");
        }
        else if (!reg.passwordCriteria(customerDetails)) {

            log.info("Failed register attempt: Failed password criteria.");
            lblErrors.setText("Password: 8+ chars, 1 upper, 1 lower, 1 digit");
        }
        else if (!reg.passwordMatches(customerDetails)) {
            log.info("Failed register attempt: Password different.");
            lblErrors.setText("Passwords do not match!");
        }
        else if (!reg.userNameFree(customerDetails)){
            log.info("Failed register attempt: Username taken.");
            lblErrors.setText("Username is taken!");
        }
        else if (!reg.phoneNoIsAus(customerDetails)) {
            log.info("Failed register attempt: Incorrect phone number.");
            lblErrors.setText("Invalid Phone Number!");
        }

        else if (!reg.writeNewCustomer(customerDetails)) {
            log.info("Failed register attempt: Failed to write to database.");
            lblErrors.setText("Failed to write!");
        }
        else {
            log.info("Successful register attempt.");
            lblErrors.setText("");
            btnBackClicked(event);
            RegisterPopup(event);
        }
    }

    @FXML
    public void RegisterPopup(ActionEvent event) throws IOException{
        log.debug("Loading registration success popup...");
        Parent RegisterSuccess_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/RegisterSuccess.fxml"));
        Scene RegisterSuccess_scene = new Scene (RegisterSuccess_parent);
        Stage PopUpStage = new Stage();
        PopUpStage.setScene(RegisterSuccess_scene);
        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(e -> PopUpStage.close());
        PopUpStage.show();
        delay.play();

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
}
