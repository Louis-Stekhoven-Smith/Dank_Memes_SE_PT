package core.controller;
import core.model.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

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

    //Loads sign up page
    @FXML
    public void btnSignUpClicked(ActionEvent event) throws IOException {
        log.debug("Sign up button clicked");
        log.debug("Loading sign up page...");
        Parent Register_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/RegisterPage.fxml"));
        Scene Register_scene = new Scene (Register_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(Register_scene);
        primaryStage.show();
    }

    //Logs user in if the login information is correct
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
            Parent LoginSuccess_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/ChooseBusiness.fxml"));
            Scene LoginSuccess_scene = new Scene (LoginSuccess_parent);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.close();
            primaryStage.setScene(LoginSuccess_scene);
            primaryStage.show();
        }
        else if (result == 2){
            log.debug("Owner Logged in as: " + inputUsername);
            log.debug("Loading owner page...");
            Parent LoginSuccess_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/BusinessHome.fxml"));
            Scene LoginSuccess_scene = new Scene (LoginSuccess_parent);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.close();
            primaryStage.setScene(LoginSuccess_scene);
            primaryStage.show();
        }
    }

}
