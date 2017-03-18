package core;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Created by louie on 10/03/2017.
 */

/** This class handles authenticating login
 * attempts and associated tasks */
public class Login {

    /*TODO make username not case sensitive */
    /** check username exists and password matches the associated username*/
    public static Boolean validateAttempt(String inputUsername, String inputPassword){

        Database db = new Database();
        ResultSet rs;
        String loginSQL = "SELECT userName, password FROM customerLogin WHERE userName =" + "'" + inputUsername + "'" + " AND password =" + "'" + inputPassword + "'";
        rs = db.queryDatabase(loginSQL);
        try{
            if(rs.next()){
                if(rs.getString("userName").equals(inputUsername) && rs.getString("password").equals(inputPassword)){
                    return true;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return false;

        /** Scanner scan;
        String fileLine, username, password;
        String[] loginDetails;

        try {
            scan = new Scanner(new File("customersLogin.txt"));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        //loop through file checking each line for a match
        while(scan.hasNext()) {
            fileLine = scan.nextLine();
            loginDetails = fileLine.split(",");
            username = loginDetails[0];
            password = loginDetails[1];

            if (inputUsername.equals(username) && inputPassword.equals(password)) {
                scan.close();
                return true;
            }
        }
        scan.close();
        return false;
        */
    }
}