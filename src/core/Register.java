package core;

/**
 * Created by harry on 11/03/2017.
 */
public class Register {

    public boolean isNotEmpty(String name, String uName, String pword1, String pword2, String address, String phoneNo){

        if(name.equals("")){
            System.out.println("You have not entered your name.");
            return false;
        }

        if(uName.equals("")){
            System.out.println("You have not entered a user name.");
            return false;
        }

        if(pword1.equals("")){
            System.out.println("You have not entered your password.");
            return false;
        }

        if(pword2.equals("")){
            System.out.println("You have not entered the retyped password.");
            return false;
        }

        if(address.equals("")){
            System.out.println("You have not entered an address");
            return false;
        }

        if(phoneNo.equals("")){
            System.out.println("You have not entered a phone number");
            return false;
        }
        return true;
    }

    public boolean pwordCriteria(String pword1){

        boolean hasUppercase = !pword1.equals(pword1.toLowerCase());
        boolean hasLowercase = !pword1.equals(pword1.toUpperCase());

        if(pword1.equals(pword1.toUpperCase())){
            System.out.println("hello");
        }

        if((pword1.length() < 8) || !hasLowercase || !hasUppercase || !pword1.matches(".*\\d+.*")) {
            System.out.println("je");
            return false;
        }

        return true;
    }

    public boolean pwordMatches(String pword1, String pword2){

        if(pword1.equals(pword2)){
            return true;
        }

        else return false;

    }

    public boolean uNameFree(String uName){

        return false;
    }

    public boolean register(String fName, String lName, String uName, String pword1){

        return false;
    }

}
