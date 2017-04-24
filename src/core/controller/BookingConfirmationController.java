package core.controller;

import core.model.Booking;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by Konn on 25/04/2017.
 */
public class BookingConfirmationController {

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
        result = Booking.addBooking(bookingTime,bookingDate,bookingType);

        if (result == 1){
            System.out.println("Booking Success");
            Stage stage = (Stage) btnConfirm.getScene().getWindow();
            stage.close();


        } else {
            System.out.println("Booking Failed!");
        }

    }
}
