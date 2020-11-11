package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** DB Connection class*/
public class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String serverName = "//wgudb.ucertify.com/WJ07E5Z";

    private static final String jdbcURL = protocol + vendorName + serverName;

    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;

    private static final String username = "U07E5Z";
    private static final String password = "53689000316";

    /** Start Connection */
    public static Connection startConnection(){
        try{
            Class.forName(MYSQLJDBCDriver);
            conn= DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful!");
        }
        catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return conn;
    }

    /** End Connection */
    public static void closeConnection(){
        try {
            conn.close();
            System.out.println("Connection closed.");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
