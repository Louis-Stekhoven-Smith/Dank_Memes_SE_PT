package core.model;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by louie on 29/03/2017.
 */
class DatabaseTest {

    private ResultSet rs;
    private static Connection con;
    private static String DB_CONNECTION = "jdbc:sqlite:DankMemes.db";
    private static String DB_DRIVER = "org.sqlite.JDBC";

    /*problems with testing this code needs redesign */
    @Before
    public void setUp() {
        try{
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_CONNECTION);
            Statement state = con.createStatement();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
/*
    @Test
    void queryDatabase() {
       String sqlString =  "SELECT userName, password, type FROM customerLogin WHERE userName = ' OldBoiSmokey' AND password = 'Pass1234'";
       rs = Database.queryDatabase(sqlString);
       try {
           rs.getString("userName").equals("OldBoiSmokey");
       }
       catch(Exception e){
            fail(e.getMessage());
       }
    }
    */
/*
    @Test
    void updateDatabase() {

        String sqlString = "INSERT INTO customerLogin (custID, userName, password, type) values(?, 'testUser','testPass',1)";

        assertTrue(Database.updateDatabase(sqlString));

    }
    */
}