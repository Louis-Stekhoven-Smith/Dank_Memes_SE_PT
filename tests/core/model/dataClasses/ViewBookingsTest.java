package core.model.dataClasses;

import core.model.Database;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by harry on 7/04/2017.
 */
class ViewBookingsTest {

    private ViewBookings booking = new ViewBookings(1, 1, 1, "Female cut", "19:30", "20/05/17");

    @Test
    void getNameTest1() {Database.setupDatabase(); assertEquals("Louis", booking.getCustName(1));}

    @Test
    void getNameTest2() {Database.setupDatabase(); assertEquals("Katrina", booking.getEmpName(3));}

    @Test
    void getNameTest3() {Database.setupDatabase(); assertEquals("Rachel", booking.getEmpName(5));}

}