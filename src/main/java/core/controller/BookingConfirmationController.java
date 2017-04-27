package core.controller;

import core.model.Booking;
import core.model.Database;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by Konn on 25/04/2017.
 */
public class BookingConfirmationController {
    Booking booking = new Booking(Database.getInstance());

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
        lblBookingID.setText(AvailBookingsController.futureBookingID);
        lblBusinessName.setText(AvailBookingsController.businessesName);
        lblEmployeeName.setText(AvailBookingsController.employeesName);
        lblYourName.setText(AvailBookingsController.yourName);
        lblBookingTime.setText(AvailBookingsController.bookingTime);
        lblBookingDate.setText(AvailBookingsController.bookingDate);
        lblBookingType.setText(AvailBookingsController.bookingType);

    }

    public void btnConfirmPressed(javafx.event.ActionEvent event) throws IOException {
        String bookingTime = lblBookingTime.getText();
        String bookingDate = lblBookingDate.getText();
        String bookingType = lblBookingType.getText();

        int result;
        result = booking.addBooking(bookingTime,bookingDate,bookingType);

        if (result == 1){
            System.out.println("Booking Success");
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
            System.out.println("Booking Failed!");
        }

    }

    public void btnCancelPressed(javafx.event.ActionEvent event) throws IOException{
        Stage stage = (Stage) btnConfirm.getScene().getWindow();
        stage.close();
    }
}