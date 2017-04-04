package core.controller;

import core.model.dataClasses.EmpAvailability;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.text.TableView;

/**
 * Created by louie on 4/04/2017.
 */
public class EmployeesAvailabilityController {

    @FXML
    private javafx.scene.control.TableView table;

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



    public initilise(){


        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        MonCol
                TueCol
        WedCol
                ThurCol
        FriCol

                SatCol

        SunCol
        table.


    }



    public ObservableList<EmpAvailability> getEmpAvailability() {
        ObservableList<EmpAvailability>  empAvailabilities = FXCollections.observableArrayList();

        /*TODO* add loop getting data from the database */



        empAvailabilities.add(new EmpAvailability("Louis","000,111,011,000,100,111,111"));
        empAvailabilities.add(new EmpAvailability("BOB","000,111,011,000,100,111,111"));
        empAvailabilities.add(new EmpAvailability("Ya mum","000,111,011,000,100,111,111"));
        return empAvailabilities;
    }
}
