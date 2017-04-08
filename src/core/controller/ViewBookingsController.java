package core.controller;

import core.model.Database;
import core.model.dataClasses.ViewBookings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;

/**
 * Created by harry on 7/04/2017.
 */
public class ViewBookingsController {

    private static final Logger log = LogManager.getLogger(ViewBookingsController.class.getName());

    @FXML
    private javafx.scene.control.TableView<ViewBookings> bookingsTable;

    @FXML
    private Button btnBackToBusinessHome;

    @FXML
    private VBox vbox;

    @FXML
    private TableColumn BookingCol;

    @FXML
    private TableColumn CustCol;

    @FXML
    private TableColumn EmpCol;

    @FXML
    private TableColumn TypeCol;

    @FXML
    private TableColumn DateCol;

    @FXML
    private TableColumn TimeCol;

    /** Sets up TableView and populates with all employee's names and
     * availability */
    public void initialize(){
        log.debug("Initializing the table to show bookings");

        BookingCol.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        CustCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        EmpCol.setCellValueFactory(new PropertyValueFactory<>("empName"));
        TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        DateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
        TimeCol.setCellValueFactory(new PropertyValueFactory<>("Time"));


        BookingCol.setCellFactory(param -> {return getTableCell();});
        CustCol.setCellFactory(param -> {return getTableCell();});
        EmpCol.setCellFactory(param -> {return getTableCell();});
        DateCol.setCellFactory(param -> {return getTableCell();});
        TimeCol.setCellFactory(param -> {return getTableCell();});
        TypeCol.setCellFactory(param -> {return getTableCell();});

        bookingsTable.setItems(getBookings());
    }

    /** Format's table cells */
    private Object getTableCell() {
        return new TableCell<ViewBookings,String>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                System.out.println(item);
                super.updateItem(item, empty);
                Text text = new Text(item);
                text.setStyle("-fx-text-alignment: center;");
                text.setWrappingWidth(62);

                setPrefHeight(60);
                setGraphic(text);
            }
        };
    }

    /** Creates and returns an observable list of all current bookings
     * @return ObservableList<ViewBookings>
     */
    public ObservableList<ViewBookings> getBookings() {
        log.debug("Finding all of the bookings");

        ObservableList<ViewBookings> bookings = FXCollections.observableArrayList();

        ResultSet rs;
        int businessID = 1, custID, empID, bookingID;
        String getBookingSQL, type, date, time;

        getBookingSQL = "SELECT bookingID, custID, empID, bookingType, bookingDate, bookingTime FROM bookingDetails WHERE businessID = " + businessID;

        rs = Database.queryDatabase(getBookingSQL);


        try{
            while(rs.next()){

                bookingID = rs.getInt("bookingID");
                custID = rs.getInt("custID");
                empID = rs.getInt("empID");
                type = rs.getString("bookingType");
                time = rs.getString("bookingTime");
                date = rs.getString("bookingDate");

                bookings.add(new ViewBookings(bookingID, custID, empID, type, time, date));
            }
        }
        catch (Exception e){
            log.error(e.getMessage());
            return null;
        }

        return bookings;
    }

    /** Takes user back to BusinessHome screen */
    @FXML
    public void btnBackToBusinessScreen(ActionEvent event) throws IOException {
        Parent removeEmp_parent = FXMLLoader.load(getClass().getResource("../view/BusinessHome.fxml"));
        Scene removeEmp_scene = new Scene((removeEmp_parent));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(removeEmp_scene);
        primaryStage.show();
    }

}
