package core;

import java.io.File;
import java.util.Scanner;
/**
 * Created by louie on 10/03/2017.
 */
public class Login {

    private String username;
    private String password;

    public Login(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Boolean validateUsername(){

        Scanner scan;
        String existingUsername;

        try {
            scan = new Scanner(new File("usernames.txt"));
        }
        catch(Exception e) {
            System.out.println("usernames file not found");
            return false;
        }
        while(scan.hasNext()) {
            existingUsername = scan.nextLine();

            if (username.equals(existingUsername)) {
                validatePassword();
                return true;
            }
        }
        return false;
    }


    public Boolean validatePassword(){

        Scanner scan;
        String existingPassword;

        try {
            scan = new Scanner(new File("passwords.txt"));
        }
        catch(Exception e) {
            System.out.println("passwords file not found");
            return false;
        }
        while(scan.hasNext()) {
            existingPassword = scan.nextLine();
            if (password.equals(existingPassword)) {
                System.out.println("true");
                return true;
            }
        }
        return false;
    }


}
