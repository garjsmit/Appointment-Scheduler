package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** User DAO */
public class UserDAO {

    private static ObservableList<User> userList = FXCollections.observableArrayList();
    private static User foundUser;

    /**
     * @return userList
     * This is a list of all users*/
    public static ObservableList<User> getAllUsers() {

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

    /**
     * @return userList
     * This is a list of all users*/
    public static User getUserByName(String username)  {

        String selectStatement = "SELECT User_ID, Password FROM users WHERE User_Name = ?";

        try {
            PreparedStatement ps = Main.Main.conn.prepareStatement(selectStatement);

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