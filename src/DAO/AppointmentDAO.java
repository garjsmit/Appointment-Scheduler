package DAO;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentDAO {

    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAllAppointments(){

        try {
            String selectStatement = "SELECT * FROM appointments";

            PreparedStatement ps = Main.Main.conn.prepareStatement(selectStatement);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startDate = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDate = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("User_ID");

                Appointment newAppointment = new Appointment(appointmentID, customerID, userID, contactID, title, description, type, location, startDate, endDate);

                appointmentList.add(newAppointment);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return appointmentList;
    }

    public static ObservableList<Appointment> getFilteredAppointments () {

        return appointmentList;
    }

    public static void addAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID){

        try {
            String insertStatement = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement ps = Main.Main.conn.prepareStatement(insertStatement);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(1, Timestamp.valueOf(end));
            ps.setInt(1, customerID);
            ps.setInt(1, userID);
            ps.setInt(1, contactID);

            ps.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateAppointment(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID){

        String updateStatement = "UPDATE appointments SET " +
                "Title = ?, Description = ?, Location =?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID =?, WHERE Appointment_ID = ?";

        try {
            PreparedStatement ps = Main.Main.conn.prepareStatement(updateStatement);

            ps.setInt(1, appointmentID);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            ps.setInt(9, appointmentID);

            ps.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


    public static void deleteAppointment(int appointmentI){
        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";

        try {
            PreparedStatement ps = Main.Main.conn.prepareStatement(deleteStatement);

            ps.execute();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}

