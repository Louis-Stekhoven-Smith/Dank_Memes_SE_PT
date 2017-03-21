package core;
import java.sql.*;

/**
 * Created by harry on 18/03/2017.
 */

/* This class handles all Database functionality including the
setting up of the database if it does not exist. Checking if the
database does exist and setting up the tables for insertion.
It also has a method for querying the database, inserting into
the data base and updating entries in the database. */
public class Database {

    private static Connection con;
    private static boolean hasData = false;
    private static String DB_CONNECTION = "jdbc:sqlite:DankMemes.db";
    private static String DB_DRIVER = "org.sqlite.JDBC";

    public void setupDataBase(){
        if(con == null){
            setupConnection();
        }

    }

    private void setupConnection(){
        try{
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{

            con = DriverManager.getConnection(DB_CONNECTION);

            if(!hasData){
                hasData = true;
                Statement state = con.createStatement();
                ResultSet rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='customerDetails'");
                if(!rs.next()) {
                    System.out.println("customerDetails table does not exist. Creating now...");
                    Statement custDetails = con.createStatement();
                    String sqlCustDetails = "CREATE TABLE customerDetails " +
                            "(custID INTEGER not NULL, " +
                            " name VARCHAR(40), " +
                            " userName VARCHAR(20), " +
                            " address VARCHAR(50), " +
                            " phoneNo VARCHAR(20), " +
                            " PRIMARY KEY (custID))";
                    custDetails.execute(sqlCustDetails);
                }

                rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='customerLogin'");
                if(!rs.next()){
                    System.out.println("customerLogin table does not exist. Creating now...");
                    Statement custLogin = con.createStatement();
                    String sqlCustLogin = "CREATE TABLE customerLogin " +
                            "(custID INTEGER not NULL, " +
                            " userName VARCHAR(20), " +
                            " password VARCHAR(40), " +
                            " PRIMARY KEY(custID))";
                    custLogin.execute(sqlCustLogin);
                }

                rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='employeeDetails'");
                if(!rs.next()){
                    System.out.println("employeeDetails table does not exist. Creating now...");
                    Statement empDetails = con.createStatement();
                    String sqlEmpDetails = "CREATE TABLE employeeDetails " +
                                            "(empID INTEGER not NULL, " +
                                            " businessID INTEGER, " +
                                            " name VARCHAR(40), " +
                                            " employeeRole VARCHR(40), ";
                    empDetails.execute(sqlEmpDetails);
                }

                }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public ResultSet queryDatabase(String sqlString){
        ResultSet res = null;
        try{
            Statement state = con.createStatement();
            res = state.executeQuery(sqlString);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }


        return res;
    }

    public void updateDatabase(String sqlString){
        try{
            Statement state = con.createStatement();
            //Execute insert statment
            state.executeUpdate(sqlString);
            System.out.println("The database has been modified successfully");


        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
