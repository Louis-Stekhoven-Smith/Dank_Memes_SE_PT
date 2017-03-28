package core;

import core.model.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by louie on 11/03/2017.
 */
class SessionTest {

    Session session = new Session("OldBoiSmokey");
    @Test
    void saveBookings() {

    }

    @Test
    void addBooking() {

    }

    @Test
    void removeBooking() {

    }

    /*
    @Test
    void getName() {
        assertEquals("Louis",session.getName());
        assertNotEquals("",session.getName());
    }


*/
    @Test
    void getUsername() {
        assertEquals("OldBoiSmokey",session.getUsername());
        assertNotEquals("asd",session.getUsername());

    }
/*
    @Test
    void getAddress() {
        assertEquals("123 Fake st",session.getAddress());
        assertNotEquals("1asdf",session.getAddress());
    }

    @Test
    void getContactNumber() {
        assertEquals("0423457368",session.getContactNumber());
        assertNotEquals("asdflkjh342",session.getContactNumber());

    }
*/
    @Test
    void getBooking() {

    }

}