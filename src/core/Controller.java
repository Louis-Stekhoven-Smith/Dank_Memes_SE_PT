package core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Created by Konn on 12/03/2017.
 */
public class Controller {

    @FXML
    private Label txtLabel;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private void LoginButton(ActionEvent event) throws IOException{
        if (txtUsername.getText().equals("test") && txtPassword.getText().equals("1")) {
            txtLabel.setText("Login");
        } else {
            txtLabel.setText("FAIL");
        }

    }

}
