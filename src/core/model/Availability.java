package core.model;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by louie on 31/03/2017.
 */
public class Availability {


    HashMap<Date,Boolean[]> empsAvailability = new HashMap<>();
    Boolean[] dayAvailabilty = new Boolean[2];


    public Boolean addDay(Boolean[] dayAvil,Date day){
        empsAvailability.put(day,dayAvil);
        return true;
    }




}
