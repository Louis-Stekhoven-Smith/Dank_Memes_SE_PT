package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        Database db = new Database();
        db.setupDataBase();
        launch(args);

        ResultSet rs;

        String sql = "SELECT name FROM customerDetails";
        rs = db.queryDatabase(sql);

        try {
            while(rs.next()){
                System.out.println(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Login login = new Login();

        if (login.validateAttempt("OldBoiSmokey", "Pass1234")) {
            System.out.println("You are logged in");
            Session session = new Session("OldBoiSmokey");

        }
        else{
            System.out.println("Login attempt failed");
        }

    }
}