package core.controller;

import core.model.Database;
import core.model.Session;
import core.model.dataClasses.EmpAvailability;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;

/**
 * Created by louie on 4/04/2017.
 */
public class AllAvailabilityController {

    @FXML
    private javafx.scene.control.TableView<EmpAvailability> table;
    @FXML
    private TableColumn NameCol;
    @FXML
    private TableColumn MonCol;
    @FXML
    private TableColumn TueCol;
    @FXML
    private TableColumn WedCol;
    @FXML
    private TableColumn ThurCol;
    @FXML
    private TableColumn FriCol;
    @FXML
    private TableColumn SatCol;
    @FXML
    private TableColumn SunCol;

    /** Sets up TableView and populates with all employee's names and
     * availability */
    public void initialize(){

        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        MonCol.setCellValueFactory(new PropertyValueFactory<>("monday"));
        TueCol.setCellValueFactory(new PropertyValueFactory<>("tuesday"));
        WedCol.setCellValueFactory(new PropertyValueFactory<>("wednesday"));
        ThurCol.setCellValueFactory(new PropertyValueFactory<>("thursday"));
        FriCol.setCellValueFactory(new PropertyValueFactory<>("friday"));
        SatCol.setCellValueFactory(new PropertyValueFactory<>("saturday"));
        SunCol.setCellValueFactory(new PropertyValueFactory<>("sunday"));

        NameCol.setCellFactory(param -> getTableCell());
        MonCol.setCellFactory(param -> getTableCell());
        TueCol.setCellFactory(param -> getTableCell());
        WedCol.setCellFactory(param -> getTableCell());
        ThurCol.setCellFactory(param -> getTableCell());
        FriCol.setCellFactory(param -> getTableCell());
        SatCol.setCellFactory(param -> getTableCell());
        SunCol.setCellFactory(param -> getTableCell());

        table.setItems(getEmpAvailability());
    }

    /** Format's table cells */
    private Object getTableCell() {
        return new TableCell<EmpAvailability,String>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                    Text text = new Text(item);
                    text.setStyle("-fx-text-alignment: center;");
                    text.setWrappingWidth(62);

                    setPrefHeight(60);
                    setGraphic(text);
                }
            };
    }

    /** Creates and returns an observable list of all current employees
     *  names and availability
     * @return ObservableList<EmpAvailability>
     */
    public ObservableList<EmpAvailability> getEmpAvailability() {
        ResultSet rs;
        String empsAvailability, name, getEmpAvailability;
        Database database = Database.getInstance();
        Session session = Session.getInstance();
        ObservableList<EmpAvailability>  empAvailabilities = FXCollections.observableArrayList();

        session.getLoggedInUserId();

        getEmpAvailability = "SELECT employeeDetails.name, empAvailability.availability FROM employeeDetails, empAvailability" +
                " WHERE employeeDetails.empID = empAvailability.empID " +
                " AND employeeDetails.businessID = " + session.getLoggedInUserId();

        rs = database.queryDatabase(getEmpAvailability);

        try {
            while (rs.next()) {
                name = rs.getString("name");
                empsAvailability = rs.getString("availability");
                empAvailabilities.add(new EmpAvailability(name, empsAvailability));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return empAvailabilities;
    }

    /** Takes user back to AddAvailability screen */
    @FXML
    public void btnBackToAddAvailability(ActionEvent event) throws IOException {
        Parent removeEmp_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/AddAvailability.fxml"));
        Scene removeEmp_scene = new Scene((removeEmp_parent));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(removeEmp_scene);
        primaryStage.show();
    }

    /** Takes user back to main menu screen */
    @FXML
    public void btnBackToHomePage(ActionEvent event) throws IOException {
        Parent removeEmp_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/BusinessHome.fxml"));
        Scene removeEmp_scene = new Scene((removeEmp_parent));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(removeEmp_scene);
        primaryStage.show();
    }


}
