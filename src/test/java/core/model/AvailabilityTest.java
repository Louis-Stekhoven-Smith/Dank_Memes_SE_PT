package core.model;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


import java.sql.Connection;


/**
 * Created by louie on 31/03/2017.
 */

class AvailabilityTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock Database mockDatabase = mock(Database.class);

    private Availability availability = new Availability(mockDatabase);



    @BeforeAll
    public static void setupDataBase(){

    }


    @Test
    void addAvailabilitySuccess(){

        when(mockDatabase.updateDatabase(anyString())).thenReturn(true);
        assertTrue(availability.addAvailability("000,000,000,000,000,000,001",1));
    }

    @Test
    void addAvailabilityFail_Length(){
        when(mockDatabase.updateDatabase(anyString())).thenReturn(false);
        assertFalse(availability.addAvailability("000,000,000,000,000,000,00",1));
    }



    @Test
    void addAvailabilityFail_ContainsLetters(){
        when(mockDatabase.updateDatabase(anyString())).thenReturn(false);
        assertFalse(availability.addAvailability("000,000,0A0,000,000,000",1));
    }

    @Test
    void addAvailabilitySuccessSucessiveWrites(){
        when(mockDatabase.updateDatabase(anyString())).thenReturn(true);
        assertTrue(availability.addAvailability("000,000,000,000,000,000,011",2));
        assertTrue(availability.addAvailability("000,000,000,000,000,000,111",3));
    }



}