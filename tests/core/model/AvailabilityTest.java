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
        db.setupDatabase();
    }

    @Test
    void addAvailabilitySuccess(){
        assertTrue(availability.addAvailability("000,000,000,000,000,000,001",1));
    }

    @Test
    void addAvailabilityFail_Length(){
        assertFalse(availability.addAvailability("000,000,000,000,000,000,00",1));
    }

    @Test
    void addAvailabilityFail_ContainsLetters(){
        assertFalse(availability.addAvailability("000,000,0A0,000,000,000",1));
    }

    @Test
    void addAvailabilitySuccessSucessiveWrites(){
        assertTrue(availability.addAvailability("000,000,000,000,000,000,011",2));
        assertTrue(availability.addAvailability("000,000,000,000,000,000,111",3));
    }



}