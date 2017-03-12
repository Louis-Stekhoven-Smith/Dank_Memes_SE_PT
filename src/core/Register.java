package core;

import java.io.*;
import java.util.Scanner;

/**
 * Created by harry on 11/03/2017.
 */

public class Register {

    //This function is just to make it simpler so that you don't have to call every function from main.
    public boolean registerAttempt(String name, String userName, String password1, String password2, String address, String phoneNo){
        if(isNotEmpty(name, userName, password1, password2, address, phoneNo)){
            if(passwordMatches(password1, password2)){
                if(passwordCriteria(password1)){
                    if(userNameFree(userName)){
                        if(register(name, userName, password1, address, phoneNo)){
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean isNotEmpty(String name, String userName, String password1, String password2, String address, String phoneNo){

        //Check none of the fields are empty
        if(name.equals("") || userName.equals("") || password1.equals("") || password2.equals("") || address.equals("") || phoneNo.equals("")){
            System.out.println("You have not filled out all of the fields");
            return false;
        }

        return true;
    }

    public boolean passwordCriteria(String password1){

        boolean hasUppercase = !password1.equals(password1.toLowerCase());
        boolean hasLowercase = !password1.equals(password1.toUpperCase());

        //Check if the password matches the criteria of 8 chars, at least 1 uppercase, 1 lowercase and 1 digit
        if((password1.length() < 8) || !hasLowercase || !hasUppercase || !password1.matches(".*\\d+.*")) {
            System.out.println("Your password must have more than 8 characters, at least one uppercase, one lowercase and a number or symbol");
            return false;
        }

        return true;
    }

    public boolean passwordMatches(String password1, String password2){

        //Check if the passwords match each other
        if(password1.equals(password2)){
            return true;
        }

        else return false;

    }

    public boolean userNameFree(String userName){

        Scanner scan;
        String fileLine, takenUsername;
        String[] loginDetails;

        try {
            scan = new Scanner(new File("customersLogin.txt"));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        /**loop through file checking each line for a matching username */
        while(scan.hasNext()) {
            fileLine = scan.nextLine();
            loginDetails = fileLine.split(",");
            takenUsername = loginDetails[0];

            if (userName.equals(takenUsername)) {
                System.out.println("This user name is already taken, please choose another");
                scan.close();
                return false;
            }
        }
        scan.close();
        return true;
    }



    public boolean register(String name, String userName, String password1, String address, String phoneNo){

        PrintWriter out = null;

        //Write the userName, name, address and phone number to the customerDetails file
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter("customerDetails.txt", true)));
            out.println(userName + "," + name + "," + address + "," + phoneNo);
        }catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }finally{
            if(out != null){
                out.close();
            }
        }

        //Write the userName and password to the customersLogin file
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter("customersLogin.txt", true)));
            out.println(userName + "," + password1);
        }catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }finally{
            if(out != null){
                out.close();
            }
        }

        return true;
    }

}
