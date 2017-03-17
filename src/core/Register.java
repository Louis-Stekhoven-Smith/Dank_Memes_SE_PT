package core;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by harry on 11/03/2017.
 */

public class Register {

    public enum attemptOutcome {
        SUCCESS(6), WRITE_FAIL(5), USERNAME_TAKEN(4), PHONENO_FAIL(3), PASSWORD_UNSATISFIED(2), PASSWORDS_DIFFERENT(1), EMPTY_FIELDS(0);
        private int value;

        private attemptOutcome(int value){
            this.value = value;
        }
    }


    //This function is just to make it simpler so that you don't have to call every function from main.
    public attemptOutcome registerAttempt(HashMap customerDetailsHMap){

        if(!isNotEmpty(customerDetailsHMap)) {
            return attemptOutcome.EMPTY_FIELDS;
        }
        else if(!passwordMatches(customerDetailsHMap)) {
            return attemptOutcome.PASSWORDS_DIFFERENT;
        }
        else if(!passwordCriteria(customerDetailsHMap)) {
            return attemptOutcome.PASSWORD_UNSATISFIED;
        }
        else if(!phoneNoIsAus(customerDetailsHMap)) {
            return attemptOutcome.PHONENO_FAIL;
        }
        else if(!userNameFree(customerDetailsHMap)) {
            return attemptOutcome.USERNAME_TAKEN;
        }
        else if(!writeNewCustomer(customerDetailsHMap)) {
            return attemptOutcome.WRITE_FAIL;
        }
        else return attemptOutcome.SUCCESS;
    }

    private boolean isNotEmpty(HashMap custDetailsHMap){

        //Check none of the fields are empty
        if(custDetailsHMap.get("name").equals("") ||
            custDetailsHMap.get("userName").equals("") ||
            custDetailsHMap.get("password1").equals("") ||
            custDetailsHMap.get("password2").equals("") ||
            custDetailsHMap.get("address").equals("") ||
            custDetailsHMap.get("phoneNo").equals(""))  {

                System.out.println("You have not filled out all of the fields");
                return false;
        }

        return true;
    }


    private boolean passwordMatches(HashMap custDetailsHMap){

        //Check if the passwords match each other
        if(custDetailsHMap.get("password1").equals(custDetailsHMap.get("password2"))){
            return true;
        } else return false;

    }


    private boolean passwordCriteria(HashMap custDetailsHMap){

        String password = (String) custDetailsHMap.get("password1");

        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());

        //Check if the password matches the criteria of 8 chars, at least 1 uppercase, 1 lowercase and 1 digit
        if((password.length() < 8) || !hasLowercase || !hasUppercase || !password.matches(".*\\d+.*")) {
            System.out.println("Your password must have more than 8 characters, at least one uppercase, one lowercase and a number or symbol");
            return false;
        }

        return true;
    }

    private boolean phoneNoIsAus(HashMap custDetailsHMap){
        String phoneNo = (String) custDetailsHMap.get("phoneNo");
        if(phoneNo.matches("^\\({0,1}((0|\\+61)(2|4|3|7|8)){0,1}\\){0,1}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{1}(\\ |-){0,1}[0-9]{3}$")){
            return true;
        }
        return false;
    }


    private boolean userNameFree(HashMap custDetailsHMap){

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

        //loop through file checking each line for a matching username
        while(scan.hasNext()) {
            fileLine = scan.nextLine();
            loginDetails = fileLine.split(",");
            takenUsername = loginDetails[0];

            if (custDetailsHMap.get("userName").equals(takenUsername)) {
                System.out.println("This user name is already taken, please choose another");
                scan.close();
                return false;
            }
        }
        scan.close();
        return true;
    }



    private boolean writeNewCustomer(HashMap custDetailsHMap){

        PrintWriter out = null;

        //Write the userName, name, address and phone number to the customerDetails file
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter("customerDetails.txt", true)));
            out.println(custDetailsHMap.get("userName") + "," + custDetailsHMap.get("name") + "," + custDetailsHMap.get("address") + "," + custDetailsHMap.get("phoneNo"));
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
            out.println(custDetailsHMap.get("userName") + "," + custDetailsHMap.get("password1"));
        }catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }finally{
                out.close();
        }

        
        return true;
    }

}
