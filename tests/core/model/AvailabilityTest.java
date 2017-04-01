package core.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by louie on 31/03/2017.
 */
class AvailabilityTest {

    private Availability availability = new Availability();

    @BeforeAll
    public static void setupDataBase(){
        Database db = new Database();
        db.setupDataBase();
    }

    @Test
    void addAvailabilitySuccess(){
        assertTrue(availability.addAvailability("000,000,000,000,000,000,000"));
    }

    @Test
    void addAvailabilityFail_Length(){
        assertFalse(availability.addAvailability("000,000,000,000,000,000,00"));
    }

    @Test
    void addAvailabilityFail_ContainsLetters(){
        assertFalse(availability.addAvailability("000,000,0A0,000,000,000"));
    }

    @Test
    void addAvailabilitySuccessSucessiveWrites(){
        assertTrue(availability.addAvailability("000,000,000,000,000,000,000"));
        assertTrue(availability.addAvailability("000,000,000,000,000,000,000"));
    }



}