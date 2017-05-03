package core.model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Created by louie on 11/03/2017.
 */
public class Session {

    private String username;
    private int loggedInUserDd,type;

    private int businessSelected;

    private static final Logger log = LogManager.getLogger(Session.class.getName());

    private Session(){}

    public static Session getInstance(){
        return SingletonHolder.instance;
    }

    private static final class SingletonHolder {
        static final Session instance = new Session();
    }

    public void load(String username, int id, int type) {
        log.debug("Inside loadMethod.");
        this.username = username;
        this.loggedInUserDd = id;
        this.type = type;
    }
    /** setters and getters **/

    public int getLoggedInUserId() {
        return loggedInUserDd;
    }
    public int getBusinessSelected() {
        return businessSelected;
    }
    public void setBusinessSelected(int businessSelected) {
        this.businessSelected = businessSelected;
    }
    public String getUsername() {
        return username;
    }


}
