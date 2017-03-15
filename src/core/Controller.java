package core;

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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by Konn on 13/03/2017.
 */
public class Controller {

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
    private Button btnRegister;
    @FXML
    private Label lblRegisterTest;

    @FXML
    public void btnSignUpClicked(ActionEvent event) throws IOException {
        Parent Register_parent = FXMLLoader.load(getClass().getResource("RegisterPage.fxml"));
        Scene Register_scene = new Scene (Register_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(Register_scene);
        primaryStage.show();
    }

    @FXML
    public void btnBackClicked(ActionEvent event) throws IOException {
        Parent Login_parent = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene Login_scene = new Scene (Login_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(Login_scene);
        primaryStage.show();
    }

    public void RegisterPopup(ActionEvent event) throws IOException{
        Parent RegisterSuccess_parent = FXMLLoader.load(getClass().getResource("RegisterSuccess.fxml"));
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
        String userName = txtUsernameReg.getText();
        String password1 = txtPasswordReg.getText();
        String password2 = txtPassVerifyReg.getText();
        String name = txtNameReg.getText();
        String address = txtAddressReg.getText();
        String phoneNo = txtContactReg.getText();
        Register reg = new Register();
        if (reg.isNotEmpty(name,userName,password1,password2,address,phoneNo) == false){
            lblErrors.setText("Empty fields exist!");
        } else if (reg.passwordCriteria(password1) == false){
            lblErrors.setText("Password: 8+ chars, 1 upper, 1 lower, 1 digit");
        } else if (reg.passwordMatches(password1, password2) == false){
            lblErrors.setText("Passwords do not match!");
        } else if (reg.userNameFree(userName) == false){
            lblErrors.setText("Username is taken!");
        } else {
            reg.register(name,userName,password1,address,phoneNo);
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
        if (log.validateAttempt(inputUsername,inputPassword) == false){
            lblLoginError.setText("Incorrect Login info!");
        } else {
            System.out.println("Logged in as: " + inputUsername);
            Parent LoginSuccess_parent = FXMLLoader.load(getClass().getResource("LoginSuccess.fxml"));
            Scene LoginSuccess_scene = new Scene (LoginSuccess_parent);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.close();
            primaryStage.setScene(LoginSuccess_scene);
            primaryStage.show();
        }
    }


}
