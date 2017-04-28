package core.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Created by Konn on 28/04/2017.
 */

public class CustomerNavigationController {

    @FXML
    private Button btnCustNav1;

    @FXML
    private Button btnCustNav2;

    @FXML
    private Button btnBack;

    public void btnCustNav1Pressed (ActionEvent event) throws IOException {
        Parent LoginSuccess_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/ChooseBusiness.fxml"));
        Scene LoginSuccess_scene = new Scene (LoginSuccess_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(LoginSuccess_scene);
        primaryStage.show();
    }

    public void btnBackPressed (ActionEvent event) throws IOException {
        Parent LoginSuccess_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/LoginPage.fxml"));
        Scene LoginSuccess_scene = new Scene (LoginSuccess_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(LoginSuccess_scene);
        primaryStage.show();
    }

}
