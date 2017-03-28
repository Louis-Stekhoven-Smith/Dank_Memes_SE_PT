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
     * Returns 1 if users is a business owner
     * Returns 2 if user is a customer */
    public static int validateAttempt(String inputUsername, String inputPassword){

        ResultSet rs;
        String loginSQL = "SELECT userName, password, type FROM customerLogin WHERE userName =" + "'" + inputUsername + "'" + " AND password =" + "'" + inputPassword + "'";
        rs = Database.queryDatabase(loginSQL);
        try{
            if(rs.next()){
                if(rs.getString("userName").equals(inputUsername) && rs.getString("password").equals(inputPassword)){
                    if(rs.getString("type").equals("1")){
                        System.out.println("vaildateAttempt returning 1");
                        return 1;
                    }
                    if(rs.getString("type").equals("2")){
                        System.out.println("vaildateAttempt returning 2");
                        return 2;
                    }
                    return -1;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return -1;


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