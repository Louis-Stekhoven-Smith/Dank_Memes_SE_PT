package core.model.dataClasses;

/**
 * Created by louie on 2/04/2017.
 */
public class Business {

    int businessID;
    private String businessName;
    private String logo;

    public Business(int businessID, String businessName, String logo) {
        this. businessID = businessID;
    }
        /*TODO*/
        /* possibly add employees */
        /* possibly add bookings  */

    public int getBusinessID() {
        return businessID;
    }

    public void setBusinessID(int businessID) {
        this.businessID = businessID;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
