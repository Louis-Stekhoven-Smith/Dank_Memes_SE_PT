package core.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.*;


/**
 * Created by louie on 10/03/2017.
 */
public class Main extends Application{

    private static final Logger log = LogManager.getLogger(Main.class.getName());

    @Override
    public void start (Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/LoginPage.fxml"));
        primaryStage.setTitle("Appointment Booking System");
        primaryStage.setScene(new Scene(root, 384, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {

        log.debug("Beginning Program");

        /* to do driver */
        Database.setupDatabase();
        launch(args);


    }
}