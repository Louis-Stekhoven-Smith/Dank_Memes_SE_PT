package core.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.mockito.Mock;


/**
 * Created by louie on 31/03/2017.
 */

/* mockito v2 doesn't support extendsWith out of the box*/
@ExtendWith(MockitoExtension.class)
@Tag("AvailabilityTests")
class AvailabilityTest {


    @Mock
    private Database mockDatabase;

    private Availability availability;

    @BeforeEach
    public void setup(){
        availability = new Availability(mockDatabase);
    }

    @DisplayName("Confirm can add an employees availability")
    @Test
    void addAvailabilitySuccess(){

        when(mockDatabase.updateDatabase(anyString())).thenReturn(true);
        assertTrue(availability.addAvailability("000,000,000,000,000,000,000",1),
                "Should have returned true");
    }

    @DisplayName("Confirm adding availability fails if string length is invalid")
    @Test
    void addAvailabilityFail_Length(){
        when(mockDatabase.updateDatabase(anyString())).thenReturn(false);
        assertFalse(availability.addAvailability("000,000,000,000,000,000,00",1),
                "Should have returned false");
    }


    @DisplayName("Confirm adding availability fails string contains letters")
    @Test
    void addAvailabilityFail_ContainsLetters(){
        when(mockDatabase.updateDatabase(anyString())).thenReturn(false);
        assertFalse(availability.addAvailability("000,000,0A0,000,000,000",1),
                "Should have returned false");
    }

    @DisplayName("Confirm adding availability fails string contains letters")
    @Test
    void addAvailabilitySuccessSuccessiveWrites(){
        when(mockDatabase.updateDatabase(anyString())).thenReturn(true);
        assertAll(
                "Running multiple add availability method calls",
                () -> assertTrue(availability.addAvailability("000,000,000,000,000,000,011",2),
                        "First add failed"),
                () -> assertTrue(availability.addAvailability("000,000,110,000,000,000,011",3),
                        "Second add failed"),
                () -> assertTrue(availability.addAvailability("000,000,000,111,000,000,111",4),
                        "Third add failed")
                );

    }

    @DisplayName("Confirm can overwrite previous availability")
    @Test
    void overwriteAvailability(){
        when(mockDatabase.updateDatabase(anyString())).thenReturn(true);
        availability.addAvailability("000,000,000,000,000,000,111",1);
        assertTrue(availability.addAvailability("111,000,000,000,000,000,000",1));


    }



}