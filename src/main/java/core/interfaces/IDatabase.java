package core.interfaces;

import java.sql.ResultSet;

/**
 * Created by louie on 17/04/2017.
 */
public interface IDatabase  {

    ResultSet queryDatabase(String sqlString);
    Boolean updateDatabase(String sqlString);
}
