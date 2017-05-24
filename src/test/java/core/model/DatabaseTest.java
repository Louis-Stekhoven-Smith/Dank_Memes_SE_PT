package core.model;

/**
 * Created by louie on 29/03/2017.
 */

class DatabaseTest {

    private Database database;
/*
    private ResultSet rs;

    @BeforeAll
    public static void setUp() {
        database = database.getInstance();
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
        String sqlStringAdd = "INSERT INTO userLogin(loginID, userName, password, type) values(?, 'testUser','testPass',1)";
        String sqlStringRemove = "DELETE FROM userLogin WHERE userName = 'testUser'";
        assertTrue(database.updateDatabase(sqlStringAdd));
        assertTrue(database.updateDatabase(sqlStringRemove));

    }
*/
}