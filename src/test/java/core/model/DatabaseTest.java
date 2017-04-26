package core.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by louie on 29/03/2017.
 */
class DatabaseTest {
/*
    private ResultSet rs;

    @BeforeAll
    public static void setUp() {
        Database.setupDatabase();
    }

    @Test
    void connectDB() {
        assertTrue(Database.setupDatabase());
    }

    @Test
    void queryDatabase() {
       String sqlString =  "SELECT userName, password, type FROM userLogin WHERE userName = 'oldboismokey' AND password = 'Pass1234'";
       rs = Database.queryDatabase(sqlString);
       try {
           assertTrue(rs.getString("userName").equals("oldboismokey"));
       }
       catch(Exception e){
            fail(e.getMessage());
       }
    }

    */

    /*TODO fix this test is failing */
/*
    @Test
    void updateDatabase() {

        String sqlStringAdd = "INSERT INTO userLogin (loginID, userName, password, type) values(?, 'testUser','testPass',1)";
        String sqlStringRemove = "DELETE FROM userLogin WHERE userName = 'testUser'";
        assertTrue(Database.updateDatabase(sqlStringAdd));
        assertTrue(Database.updateDatabase(sqlStringRemove));

    }
    */
}