package core.controller;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import core.model.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Konn on 28/04/2017.
 */


public class CurrentBookingsController {

    @FXML
    private Button btnBack;

    public static int userLoggedID = 1;

    private Database database = Database.getInstance();

    public void initialize() throws IOException, SQLException {
        ResultSet count;
        int counter;
        String findRoleTypeCount = "SELECT count(*) AS total FROM bookingDetails WHERE custID=1";
        count = database.queryDatabase(findRoleTypeCount);
        counter = count.getInt("total");
        ResultSet rs;
        retrieveBookings(counter);
    }

    public void retrieveBookings(int counter) throws IOException, SQLException {
        String findbookingsSQL = "SELECT * FROM bookingDetails WHERE custID=" + "'" + userLoggedID + "'";
        ResultSet result = database.queryDatabase(findbookingsSQL);
        ResultSetMetaData rsmd = result.getMetaData();

        ArrayList <String[]> rs = new ArrayList<String[]>();
        int columnCount = result.getMetaData().getColumnCount();
        while (result.next()) {
            String[] row = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = result.getString(i + 1);
            }
            rs.add(row);
        }

        String[][] newArray = new String[counter][counter];

        for (int i=0; i<counter; i++) {
            newArray[i] = rs.get(i);
            System.out.println(Arrays.toString(newArray[i]));
        }

    }

    public void btnBackPressed (ActionEvent event) throws IOException {
        Parent Back_parent = FXMLLoader.load(getClass().getClassLoader().getResource("resources/CustomerNavigation.fxml"));
        Scene Back_scene = new Scene (Back_parent);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        primaryStage.setScene(Back_scene);
        primaryStage.show();
    }
}
