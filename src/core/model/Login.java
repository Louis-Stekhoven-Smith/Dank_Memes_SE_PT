package core.model;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Created by louie on 10/03/2017.
 */

/** This class handles authenticating login
 * attempts and associated tasks */
public class Login {

    /*TODO make username not case sensitive */
    /** Check username exists and password matches the associated username
     * Returns -1 if authentication failed
     * Returns 2 if users is a business owner
     * Returns 1 if user is a customer */
    public static int validateAttempt(String inputUsername, String inputPassword){
        String loginSQL, userName,password;
        ResultSet rs;

        inputUsername = inputUsername.toLowerCase();

        loginSQL = "SELECT userName, password, type FROM customerLogin WHERE userName =" + "'" + inputUsername + "'" + " AND password =" + "'" + inputPassword + "'";
        rs = Database.queryDatabase(loginSQL);


        try{
            if(rs.next()){
                userName =  rs.getString("userName").toLowerCase();
                password  = rs.getString("password");

        System.out.println(userName+" "+inputUsername);

                /* do we need to do this if statment, can we not just check if rs is empty? - Louis */
                if(userName.equals(inputUsername) && password.equals(inputPassword)){
                    if(rs.getString("type").equals("1")){
                        return 1;
                    }
                    if(rs.getString("type").equals("2")){
                        return 2;
                    }
                    return -1;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return -1;

    }
}