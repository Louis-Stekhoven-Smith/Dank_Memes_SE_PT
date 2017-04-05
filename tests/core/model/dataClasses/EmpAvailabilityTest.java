package core.model.dataClasses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by louie on 5/04/2017.
 */
class EmpAvailabilityTest {

    private EmpAvailability empAvailability = new EmpAvailability("louis","001,111,101,111,101,100,000");

    @Test
    void convertToEnglish1(){
        assertEquals(" Evening ",empAvailability.getMonday());
    }
    @Test
    void convertToEnglish2(){
        assertEquals(" Morning  Afternoon  Evening ",empAvailability.getTuesday());
    }
    @Test
    void convertToEnglish3(){
        assertEquals(" Morning  Evening ",empAvailability.getFriday());
    }
    @Test
    void convertToEnglish4(){
        assertEquals(" Morning ",empAvailability.getSaturday());
    }


}