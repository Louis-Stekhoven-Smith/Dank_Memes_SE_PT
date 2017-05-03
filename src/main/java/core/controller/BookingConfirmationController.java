package core.controller;

import core.model.Booking;
import core.model.Database;
import core.model.Session;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by Konn on 25/04/2017.
 */
public class BookingConfirmationController {
    Booking booking = new Booking(Database.getInstance());
    AvailBookingsController availBookingsController = new AvailBookingsController();
    Session session = Session.getInstance();

    private static final Logger log = LogManager.getLogger(BookingConfirmationController.class.getName());

    @FXML
    private Label lblBookingID;

    @FXML
    private Label lblBusinessName;

    @FXML
    private Label lblEmployeeName;

    @FXML
    private Label lblYourName;

    @FXML
    private Label lblBookingType;

    @FXML
    private Label lblBookingTime;

    @FXML
    private Label lblBookingDate;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnCancel;

    public void initialize(){

        lblBookingID.setText(Integer.toString(availBookingsController.getFutureBookingID()));
        lblBusinessName.setText(availBookingsController.getBusinessesName());
        lblEmployeeName.setText(availBookingsController.getEmpName());
        lblYourName.setText(availBookingsController.getCustName());
        lblBookingTime.setText(availBookingsController.getTime());
        lblBookingDate.setText(availBookingsController.getDate());
        lblBookingType.setText(availBookingsController.getType());


    }

    public void btnConfirmPressed(javafx.event.ActionEvent event) throws IOException {
        String bookingTime = lblBookingTime.getText();
        String bookingDate = lblBookingDate.getText();
        String bookingType = lblBookingType.getText();
        int empID = availBookingsController.getEmpID();
        int businessID = session.getBusinessSelected();
        int custID = session.getLoggedInUserId();

        int result;

        result = booking.addBooking(bookingTime,bookingDate,bookingType,empID, businessID, custID);

        if (result == 1){
            log.debug("Booking Success");
            Stage stage = (Stage) btnConfirm.getScene().getWindow();
            stage.close();

            Parent BookingSuccess_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/BookingSuccess.fxml"));
            Scene BookingSuccess_scene = new Scene (BookingSuccess_parent);
            Stage PopUpStage = new Stage();
            PopUpStage.setScene(BookingSuccess_scene);
            PauseTransition delay = new PauseTransition(Duration.seconds(1.2));
            delay.setOnFinished(e -> PopUpStage.close());
            PopUpStage.initStyle(StageStyle.UNDECORATED);
            PopUpStage.show();
            delay.play();

        } else {
            log.debug("Booking Failed!");
        }

        Parent reset_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/AvailBookings.fxml"));
        Scene reset_scene = new Scene((reset_parent));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(reset_scene);
        primaryStage.show();

    }

    public void btnCancelPressed(javafx.event.ActionEvent event) throws IOException{
        Stage stage = (Stage) btnConfirm.getScene().getWindow();
        stage.close();
    }
}