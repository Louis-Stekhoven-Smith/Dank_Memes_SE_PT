package core;

/**
 * Created by louie on 10/03/2017.
 */
public class Main {

    public static void main(String[] args) {
        /* to do driver */

        Login login = new Login();

        if (login.validateAttempt("OldBoiSmokey", "abc123")) {
            System.out.println("You are logged in");
            Session session = new Session("OldBoiSmokey");

        }
        else{
            System.out.println("Login attempt failed");
        }

    }
}