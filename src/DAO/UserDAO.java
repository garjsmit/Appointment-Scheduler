package DAO;

import Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {

    private static ArrayList<User> userList;
    private static User foundUser;

    private static ArrayList<User> getAllUsers() {

        userList.clear();

        try {
            PreparedStatement ps = Main.Main.conn.prepareStatement("SELECT * FROM users");

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");

                User newUser = new User(userID, username, password);

                userList.add(newUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public static User getUserByName(String username)  {
        try {
            PreparedStatement ps = Main.Main.conn.prepareStatement("SELECT User_ID, Password FROM users WHERE User_Name = ?");

            ps.setString(1, String.valueOf(username));
            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String password = rs.getString("Password");

                foundUser = new User(userID, username, password);

            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return foundUser;
    }

}