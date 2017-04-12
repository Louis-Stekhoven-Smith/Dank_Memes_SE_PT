package core.model.dataClasses;

/**
 * Created by louie on 4/04/2017.
 */
public class EmpAvailability {

    private String name;
    private String monday, tuesday,wednesday,thursday,friday, saturday,sunday;

    /** Constructor takes employee availability string and converts into
     * individual days.
     *
      * @param name
     * @param availability
     */
    public EmpAvailability(String name, String availability){
        String[] days;

        this.name = name;

        days = availability.split(",");

        monday = covertToEnglish(days[0]);
        tuesday = covertToEnglish(days[1]);
        wednesday = covertToEnglish(days[2]);
        thursday = covertToEnglish(days[3]);
        friday = covertToEnglish(days[4]);
        saturday = covertToEnglish(days[5]);
        sunday = covertToEnglish(days[6]);
    }

    /** Coverts the csv 0's and 1's into an english representations
     *  e.g. 011  =  ' Afternoon  Evening '
     * @param day
     * @return
     */
    private String covertToEnglish(String day){
        char shifts[];
        String convertedString = "";

        shifts = day.toCharArray();
            if(shifts[0] == '1'){
                convertedString += " Morning ";
            }
            if(shifts[1] == '1'){
                convertedString +=" Afternoon ";
            }
            if(shifts[2] == '1'){
                convertedString += " Evening ";
            }
            if(convertedString.equals("")){
                return convertedString = "Unavailable";
            }

        return convertedString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }
}
