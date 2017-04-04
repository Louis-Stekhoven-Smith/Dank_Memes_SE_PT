package core.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Konn on 31/03/2017.
 */
public class ChooseBusinessController {
    @FXML
    private Button btnBusiness1;

    int businessChoice = 0;

    @FXML
    public int btnBusinessClicked(javafx.event.ActionEvent event) throws IOException {
        businessChoice = 1;
        System.out.println("Business "+businessChoice+" chosen");
        Parent viewBookings_parent = FXMLLoader.load(getClass().getResource("../view/ServiceType.fxml"));
        Scene viewBookings_scene = new Scene(viewBookings_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(viewBookings_scene);
        primaryStage.show();
        return businessChoice;

    }
}
