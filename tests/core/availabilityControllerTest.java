package core;

import core.controller.availabilityController;
import org.junit.Test;

import static org.testng.Assert.*;

/**
 * Created by Konn on 29/03/2017.
 */
public class availabilityControllerTest {
    @Test
    public void btnUpdateClicked(){
        availabilityController ac = new availabilityController();
        int result = ac.loopVal;
        assertEquals(0,result);
    }

    @Test
    public void reformatDate(){
        availabilityController ac = new availabilityController();
        String result = ac.finalDay;
        assertEquals(Integer.class.isInstance(result),false);
    }

    @Test
    public void btnDate1Clicked(){
        availabilityController ac = new availabilityController();
        String result = ac.currentDay;
        assertEquals(Integer.class.isInstance(result), false);
    }

}