package core.controller;

import core.model.Business;
import core.model.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Created by harry on 20/04/2017.
 */
public class RemoveBookingController {

    private static final Logger log = LogManager.getLogger(AddEmpController.class.getName());

    private Database database = Database.getInstance();
    private Business business = new Business(database);
    private ResultSet rs;
    private String date;
    private int custID;

    @FXML
    private ComboBox<String> comboTimes;

    @FXML
    private ComboBox<String> comboNames;

    @FXML
    private DatePicker dateSelector;

    @FXML
    private Label lblRemoveError;

    @FXML
    public void btnFillTimes(ActionEvent e) throws IOException {
        log.debug("Date selected, adding times to combo box..");
        LocalDate lDate = dateSelector.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LL/yy");
        date = lDate.format(formatter);
        ObservableList<String> times = FXCollections.observableArrayList();

        String getTimesSQL = "SELECT DISTINCT bookingTime FROM bookingDetails WHERE businessID = 1 AND bookingDate = " + "'" + date + "'";
        rs = database.queryDatabase(getTimesSQL);

        try{
            while(rs.next()){
                times.add(rs.getString("bookingTime"));
            }
        } catch (SQLException error){
            log.error("SQL error: " + error.getMessage());
            error.printStackTrace();
        }

        comboTimes.setItems(times);
    }

    @FXML
    public void btnFillNames(ActionEvent e){
        log.debug("Time selected adding bookingID and names available to combobox");
        String time = comboTimes.getValue();
        ObservableList<String> names = FXCollections.observableArrayList();

        String getNamesSQL = "SELECT bookingDetails.bookingID, customerDetails.name FROM customerDetails " +
                            "JOIN bookingDetails ON customerDetails.custID = bookingDetails.custID " +
                            "WHERE bookingDetails.bookingDate = " + "'" + date + "' AND bookingDetails.bookingTime = " +
                            "'" + time + "'";

        rs = database.queryDatabase(getNamesSQL);

        try{
            while(rs.next()){
                names.add("ID: " + rs.getInt("bookingID") + ", " +rs.getString("name"));
            }
        } catch (SQLException error){
            log.debug("SQL ERROR: " + error.getMessage());
        }

        comboNames.setItems(names);

    }

    @FXML
    public void btnRemoveBooking(ActionEvent e){
        log.debug("Remove button clicked, removing booking..");
        String name = comboNames.getValue();
        if(name == null){
            log.debug("Empty fields exist!");
            lblRemoveError.setTextFill(Color.web("#ff0000"));
            lblRemoveError.setText("Please select/enter all fields!");
            return;
        }
        int id;
        Scanner findID = new Scanner(name).useDelimiter("\\D+");
        if(findID.hasNextInt()){
            id = findID.nextInt();
            String removeBookingSQL = "DELETE FROM bookingDetails WHERE bookingID = " + id;
            if(database.updateDatabase(removeBookingSQL)){
                log.debug("Successfully removed booking");
                lblRemoveError.setTextFill(Color.web("ffffff"));
                lblRemoveError.setText("Successfully removed booking!");
                comboNames.setValue("");
                comboTimes.setValue("");
            }
            else{
                log.error("Failed to remove booking!");
                lblRemoveError.setTextFill(Color.web("#ff0000"));
                lblRemoveError.setText("Failed to remove booking..");
            }
        }
    }

    @FXML
    public void btnBackToBusinessHome(ActionEvent event) throws IOException {
        log.debug("Back (to business home) button clicked, loading business home page");
        Parent business_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/BusinessHome.fxml"));
        Scene business_scene = new Scene (business_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(business_scene);
        primaryStage.show();
    }

}
