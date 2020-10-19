package DAO;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentDAO {

    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private static ObservableList<Appointment> currentMonthAppointmentList = FXCollections.observableArrayList();
    private static ObservableList<Appointment> currentWeekAppointmentList = FXCollections.observableArrayList();
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm");
    private static ZoneId zid = ZoneId.systemDefault();

    public static ObservableList<Appointment> getAllAppointments(){

        appointmentList.clear();

        try {
            String selectStatement = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, customers.Customer_ID, customers.Customer_Name, users.User_ID, users.User_Name, contacts.Contact_ID, contacts.Contact_Name\n" +
                    "FROM appointments, customers, users, contacts\n" +
                    "WHERE appointments.Customer_ID = customers.Customer_ID\n" +
                    "AND appointments.User_ID = users.User_ID\n" +
                    "AND appointments.Contact_ID = contacts.Contact_ID\n" +
                    "ORDER BY Appointment_ID;";

            PreparedStatement ps = Main.Main.conn.prepareStatement(selectStatement);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                ZonedDateTime startDate = ZonedDateTime.of(rs.getTimestamp("Start").toLocalDateTime(), ZoneId.systemDefault());
                ZonedDateTime endDate = ZonedDateTime.of(rs.getTimestamp("End").toLocalDateTime(), ZoneId.systemDefault());
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                int contactID = rs.getInt("User_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(appointmentID, title, description, type, location, startDate, endDate, customerID, customerName, userID, username,  contactID, contactName);

                currentMonthAppointmentList.add(newAppointment);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return currentMonthAppointmentList;
    }

    public static ObservableList<Appointment> getCurrentMonthAppointments () {

        currentMonthAppointmentList.clear();

        String selectStatement = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, customers.Customer_ID, customers.Customer_Name, users.User_ID, users.User_Name, contacts.Contact_ID, contacts.Contact_Name\n" +
                "FROM appointments, customers, users, contacts\n" +
                "WHERE (SELECT MONTH(Start)) = (SELECT MONTH(CURDATE()))\n" +
                "AND appointments.Customer_ID = customers.Customer_ID\n" +
                "AND appointments.User_ID = users.User_ID\n" +
                "AND appointments.Contact_ID = contacts.Contact_ID\n" +
                "ORDER BY Appointment_ID;\n";
        try {
            PreparedStatement ps = Main.Main.conn.prepareStatement(selectStatement);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                ZonedDateTime startDate = ZonedDateTime.of(rs.getTimestamp("Start").toLocalDateTime(), ZoneId.systemDefault());
                ZonedDateTime endDate = ZonedDateTime.of(rs.getTimestamp("End").toLocalDateTime(), ZoneId.systemDefault());
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                int contactID = rs.getInt("User_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(appointmentID, title, description, type, location, startDate, endDate, customerID, customerName, userID, username,  contactID, contactName);
                currentMonthAppointmentList.add(newAppointment);

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return currentMonthAppointmentList;

    }

    public static ObservableList<Appointment> getCurrentWeekAppointments () {

        currentWeekAppointmentList.clear();

        String selectStatement = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, customers.Customer_ID, customers.Customer_Name, users.User_ID, users.User_Name, contacts.Contact_ID, contacts.Contact_Name\n" +
                "FROM appointments, customers, users, contacts\n" +
                "WHERE YEARWEEK(Start) = YEARWEEK(NOW())\n" +
                "AND appointments.Customer_ID = customers.Customer_ID\n" +
                "AND appointments.User_ID = users.User_ID\n" +
                "AND appointments.Contact_ID = contacts.Contact_ID\n" +
                "ORDER BY Appointment_ID;";
        try {
            PreparedStatement ps = Main.Main.conn.prepareStatement(selectStatement);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                ZonedDateTime startDate = ZonedDateTime.of(rs.getTimestamp("Start").toLocalDateTime(), ZoneId.systemDefault());
                ZonedDateTime endDate = ZonedDateTime.of(rs.getTimestamp("End").toLocalDateTime(), ZoneId.systemDefault());
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                int contactID = rs.getInt("User_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(appointmentID, title, description, type, location, startDate, endDate, customerID, customerName, userID, username,  contactID, contactName);
                currentWeekAppointmentList.add(newAppointment);

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return currentWeekAppointmentList;

    }

    public static void addAppointment(String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, int customerID, int userID, int contactID){

        try {
            String insertStatement = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement ps = Main.Main.conn.prepareStatement(insertStatement);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start.toLocalDateTime()));
            ps.setTimestamp(6, Timestamp.valueOf(end.toLocalDateTime()));
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);

            ps.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateAppointment(int appointmentID, String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, int customerID, int userID, int contactID){

        String updateStatement = "UPDATE appointments SET " +
                "Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID =? WHERE Appointment_ID = ?";

        try {
            PreparedStatement ps = Main.Main.conn.prepareStatement(updateStatement);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start.toLocalDateTime()));
            ps.setTimestamp(6, Timestamp.valueOf(end.toLocalDateTime()));
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            ps.setInt(10, appointmentID);

            ps.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteAppointment(int appointmentID){
        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";

        try {
            PreparedStatement ps = Main.Main.conn.prepareStatement(deleteStatement);

            ps.setString(1, String.valueOf(appointmentID));
            ps.execute();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}