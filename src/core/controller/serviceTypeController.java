package core.controller;

import core.model.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Konn on 2/04/2017.
 */
public class serviceTypeController {

    @FXML
    private Button btnService1;
    @FXML
    private Button btnService2;
    @FXML
    private Button btnService3;
    @FXML
    private Button btnService4;


    @FXML
    public void initialize() throws SQLException, IOException {
        System.out.println("serviceTypeController INITIALLISEDDD!");
        loadRoles();
    }

    public void loadRoles() throws SQLException, IOException {
        ResultSet rs;
        String role;
        String findEmpSQL = "SELECT DISTINCT employeeRole FROM employeeDetails";
        rs = Database.queryDatabase(findEmpSQL);
        String[] myArray = new String[4];
        for (int i=0; i<myArray.length;i++) {
            rs.next();
            role = rs.getString("employeeRole");
            myArray[i]=role;
            System.out.println(role);
        } fillButtons(myArray);
    }

    public void fillButtons(String[] myArray) throws IOException{
        btnService1.setText(myArray[0]);
        btnService2.setText(myArray[1]);
        btnService3.setText(myArray[2]);
        btnService4.setText(myArray[3]);
    }

    @FXML
    public String btnService1Clicked(javafx.event.ActionEvent event) throws IOException{
        String type = btnService1.getText();
        System.out.println(type);
        return type;
    }

    @FXML
    public String btnService2Clicked(javafx.event.ActionEvent event) throws IOException{
        String type = btnService2.getText();
        System.out.println(type);
        return type;
    }

    @FXML
    public String btnService3Clicked(javafx.event.ActionEvent event) throws IOException{
        String type = btnService3.getText();
        System.out.println(type);
        return type;
    }

    @FXML
    public String btnService4Clicked(javafx.event.ActionEvent event) throws IOException{
        String type = btnService4.getText();
        System.out.println(type);
        return type;
    }
}