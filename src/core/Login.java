package core;

import java.io.File;
import java.util.Scanner;
/**
 * Created by louie on 10/03/2017.
 */
public class Login {

    public Boolean validateAttempt(String inputUsername, String inputPassword){

        Scanner scan;
        String fileLine, username, password;
        String[] loginDetails;

        try {
            scan = new Scanner(new File("customersLogin.txt"));
        }
        catch(Exception e) {
            System.out.println("customersLogin file not found");
            return false;
        }

        /**loop through file to check each line for a match
         *on username and then password */
        while(scan.hasNext()) {
            fileLine = scan.nextLine();
            loginDetails = fileLine.split(",");
            username = loginDetails[0];
            password = loginDetails[1];

            if (inputUsername.equals(username) && inputPassword.equals(password)) {
                return true;
            }
        }
        return false;
    }
}
