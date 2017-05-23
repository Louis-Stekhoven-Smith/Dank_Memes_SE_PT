package core.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.sql.ResultSet;
import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by louie on 15/04/2017.
 */
/* mockito v2 doesn't support extendsWith out of the box*/
@ExtendWith(MockitoExtension.class)
public class BookingTest {

    @Mock
    Database mockDatabase;
    @Mock
    ResultSet mockResultFull;
    @Mock
    ResultSet mockResultEmpty;

    private Booking booking;

    @BeforeEach
    public void setup() throws Exception{
        booking = new Booking(mockDatabase);
        when(mockDatabase.updateDatabase(anyString())).thenReturn(true);
        when(mockDatabase.queryDatabase(anyString())).thenReturn(mockResultFull);
        when(mockResultFull.next()).thenReturn(true);
        when(mockResultEmpty.next()).thenReturn(false);
    }

    @DisplayName("Test for success")
    @Test
    void availableSlotSuccess(){
        when(mockDatabase.queryDatabase(anyString())).thenReturn(mockResultEmpty);
        assertEquals(booking.availableSlot("15:30", 1), true);
    }

    @DisplayName("Invalid format")
    @Test
    void availableSlotFail(){assertEquals(booking.availableSlot("15:2012", 1), false);}

    @DisplayName("Test for successful find of booked out time")
    @Test
    void availableSlotFail2(){
        assertEquals(booking.availableSlot("15:30", 1), false);
    }

    @DisplayName("Test for success")
    @Test
    void addBookingSuccess(){
        assertEquals(booking.addBooking("15:30", "12/05/17", "haircut", 2,2,2), 1);
    }

    @DisplayName("Test for failure to add booking returns correctly")
    @Test
    void addBookingFailure(){
        when(mockDatabase.updateDatabase(anyString())).thenReturn(false);
        assertEquals(booking.addBooking("15:30", "12/05/17", "haircut", 2,2,2), 0);
    }

    @DisplayName("Test for success")
    @Test
    void checkAvailabilitySuccess1(){
        assertEquals(booking.checkAvailability("100"), true);
    }

    @DisplayName("Test for success")
    @Test
    void checkAvailabilitySuccess3(){
        assertEquals(booking.checkAvailability("101"), true);
    }

    @DisplayName("Invalid format")
    @Test
    void checkAvailabilityFail1(){
        assertEquals(booking.checkAvailability("0100"), false);
    }

    @DisplayName("Test for successful fail")
    @Test
    void checkAvailabilityFail2(){
        assertEquals(booking.checkAvailability("000"), false);
    }

    @DisplayName("Invalid format")
    @Test
    void checkAvailabilityfail3(){
        assertEquals(booking.checkAvailability("10"), false);
    }

    @DisplayName("Test for success")
    @Test
    void getDayAvailTest1(){
        assertEquals(booking.getDayAvailability(DayOfWeek.MONDAY, "101,010,101,010,101,010,101"), "101");
    }
    @DisplayName("Test for success")
    @Test
    void getDayAvailTest2(){
        assertEquals(booking.getDayAvailability(DayOfWeek.TUESDAY, "101,010,101,010,101,010,101"), "010");
    }

    @DisplayName("Invalid format")
    @Test
    void getDayAvailFail(){
        assertEquals(booking.getDayAvailability(DayOfWeek.MONDAY, "101,010,101,010,101,010,101,101"), null);
    }



}