package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by louie on 10/03/2017.
 */
public class Main extends Application{

    @Override
    public void start (Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        primaryStage.setTitle("Appointment Booking System");
        primaryStage.setScene(new Scene(root, 384, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        /* to do driver */
        launch(args);

        Login login = new Login();

        if (login.validateAttempt("OldBoiSmokey", "abc123")) {
            System.out.println("You are logged in");
            Session session = new Session("OldBoiSmokey");

            System.out.println(session.getName());

        }
        else{
            System.out.println("Login attempt failed");
        }

    }
}