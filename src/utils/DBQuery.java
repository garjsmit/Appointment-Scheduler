package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBQuery {


    //Statement reference
    private static PreparedStatement statement;

    //create Statement object
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
    }

    //returns statement object
    public static PreparedStatement getPreparedStatement(){
        return statement;
    }
}
