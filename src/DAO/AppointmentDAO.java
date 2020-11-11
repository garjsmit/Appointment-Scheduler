package DAO;

import Main.Main;
import Model.Appointment;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/***Appointment DAO */
public class AppointmentDAO {

    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentListByUser = FXCollections.observableArrayList();
    private static ArrayList<Appointment> appointmentListByCustomer = new ArrayList<>();
    private static ObservableList<Appointment> appointmentListByContact = FXCollections.observableArrayList();
    private static ObservableList<Appointment> currentMonthAppointmentList = FXCollections.observableArrayList();
    private static ObservableList<Appointment> currentWeekAppointmentList = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentsByMonth = FXCollections.observableArrayList();
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm");
    private static ZoneId zid = ZoneId.systemDefault();

    /**
     * @return Returns ObservableList<Appointment> appointmentList. This is a list of all appointments.
     * */
    public static ObservableList<Appointment> getAllAppointments(){

        appointmentList.clear();

        try {
            String selectStatement = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, customers.Customer_ID, customers.Customer_Name, users.User_ID, users.User_Name, contacts.Contact_ID, contacts.Contact_Name\n" +
                    "FROM appointments, customers, users, contacts\n" +
                    "WHERE appointments.Customer_ID = customers.Customer_ID\n" +
                    "AND appointments.User_ID = users.User_ID\n" +
                    "AND appointments.Contact_ID = contacts.Contact_ID\n" +
                    "ORDER BY Appointment_ID;";

            PreparedStatement ps = Main.conn.prepareStatement(selectStatement);

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
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(appointmentID, title, description, type, location, startDate, endDate, customerID, customerName, userID, username,  contactID, contactName);

                appointmentList.add(newAppointment);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return appointmentList;
    }

    /**
     * @return Returns ObservableList<Appointment> currentMonthAppointmentList. This is a list of all appointments in the current month.
     * */
    public static ObservableList<Appointment> getCurrentMonthAppointments() {

        currentMonthAppointmentList.clear();

        String selectStatement = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, customers.Customer_ID, customers.Customer_Name, users.User_ID, users.User_Name, contacts.Contact_ID, contacts.Contact_Name\n" +
                "FROM appointments, customers, users, contacts\n" +
                "WHERE (SELECT MONTH(Start)) = (SELECT MONTH(CURDATE()))\n" +
                "AND appointments.Customer_ID = customers.Customer_ID\n" +
                "AND appointments.User_ID = users.User_ID\n" +
                "AND appointments.Contact_ID = contacts.Contact_ID\n" +
                "ORDER BY Appointment_ID;\n";
        try {
            PreparedStatement ps = Main.conn.prepareStatement(selectStatement);
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
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(appointmentID, title, description, type, location, startDate, endDate, customerID, customerName, userID, username,  contactID, contactName);
                currentMonthAppointmentList.add(newAppointment);

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return currentMonthAppointmentList;

    }

    /**
     * @return Returns ObservableList<Appointment> currentWeekAppointmentList. This is a list of all appointments in the current week.
     * */
    public static ObservableList<Appointment> getCurrentWeekAppointments() {

        currentWeekAppointmentList.clear();

        String selectStatement = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, customers.Customer_ID, customers.Customer_Name, users.User_ID, users.User_Name, contacts.Contact_ID, contacts.Contact_Name\n" +
                "FROM appointments, customers, users, contacts\n" +
                "WHERE YEARWEEK(Start) = YEARWEEK(NOW())\n" +
                "AND appointments.Customer_ID = customers.Customer_ID\n" +
                "AND appointments.User_ID = users.User_ID\n" +
                "AND appointments.Contact_ID = contacts.Contact_ID\n" +
                "ORDER BY Appointment_ID;";
        try {
            PreparedStatement ps = Main.conn.prepareStatement(selectStatement);
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
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(appointmentID, title, description, type, location, startDate, endDate, customerID, customerName, userID, username,  contactID, contactName);
                currentWeekAppointmentList.add(newAppointment);

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return currentWeekAppointmentList;

    }

    /**
     * @param userID
     * @return Returns ObservableList<Appointment> appointmentListByUser. Returns all appointments that related with matching userID.
     * */
    public static ObservableList<Appointment> getUserAppointments(int userID){

        appointmentListByUser.clear();

        String selectStatement = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, customers.Customer_ID, customers.Customer_Name, users.User_ID, users.User_Name, contacts.Contact_ID, contacts.Contact_Name\n" +
                "                FROM appointments, customers, users, contacts\n" +
                "                WHERE appointments.User_ID = ?\n" +
                "                AND appointments.User_ID = users.User_ID\n" +
                "                AND appointments.Customer_ID = customers.Customer_ID\n" +
                "                AND appointments.Contact_ID = contacts.Contact_ID\n" +
                "\t\t\t\tORDER BY Appointment_ID;";
        try{
            PreparedStatement ps = Main.conn.prepareStatement(selectStatement);

            ps.setString(1, String.valueOf(userID));

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
                int userIDNum = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(appointmentID, title, description, type, location, startDate, endDate, customerID, customerName, userIDNum, username,  contactID, contactName);

                appointmentListByUser.add(newAppointment);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return appointmentListByUser;
    }

    /**
     * @param custID
     * @return Returns ArrayList<Appointment> appointmentListByCustomer. Returns all appointments that related with matching custID.
     * */
    public static ArrayList<Appointment> getCustomerAppointments(int custID){

        appointmentListByCustomer.clear();

        String selectStatement = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, customers.Customer_ID, customers.Customer_Name, users.User_ID, users.User_Name, contacts.Contact_ID, contacts.Contact_Name\n" +
                "                FROM appointments, customers, users, contacts\n" +
                "                WHERE appointments.Customer_ID = ?\n" +
                "                AND appointments.User_ID = users.User_ID\n" +
                "                AND appointments.Customer_ID = customers.Customer_ID\n" +
                "                AND appointments.Contact_ID = contacts.Contact_ID\n" +
                "                ORDER BY Appointment_ID;";

        try{
            PreparedStatement ps = Main.conn.prepareStatement(selectStatement);

            ps.setString(1, String.valueOf(custID));

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
                int userIDNum = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(appointmentID, title, description, type, location, startDate, endDate, customerID, customerName, userIDNum, username,  contactID, contactName);

                appointmentListByCustomer.add(newAppointment);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return appointmentListByCustomer;
    }

    /**
     * @param contactID
     * @return Returns ObservableList<Appointment> appointmentListByContact. Returns all appointments that related with matching contactID.
     * */
    public static ObservableList<Appointment> getContactAppointments(int contactID) {

        appointmentListByContact.clear();

        String selectStatement = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, customers.Customer_ID, customers.Customer_Name, users.User_ID, users.User_Name, contacts.Contact_ID, contacts.Contact_Name\n" +
                "                                FROM appointments, customers, users, contacts\n" +
                "                                WHERE appointments.Contact_ID = ?\n" +
                "                                AND appointments.User_ID = users.User_ID\n" +
                "                                AND appointments.Customer_ID = customers.Customer_ID\n" +
                "                                AND appointments.Contact_ID = contacts.Contact_ID\n" +
                "                                ORDER BY Appointment_ID;";
        try {
            PreparedStatement ps = Main.conn.prepareStatement(selectStatement);

            ps.setInt(1, contactID);

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
                int userIDNum = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(appointmentID, title, description, type, location, startDate, endDate, customerID, customerName, userIDNum, username,  contactID, contactName);

                appointmentListByContact.add(newAppointment);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }


        return appointmentListByContact;
    }

    /**
     * @param month
     * @return Returns ObservableList<Appointment> appointmentsByMonth. Extracts the string value in the Create_Date column of the appointments database, and returns all appointment that match the user's selection.
     * */
    public static ObservableList<Appointment> getAppointmentsByMonth (String month){

        appointmentsByMonth.clear();

        String selectStatement = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, customers.Customer_ID, customers.Customer_Name, users.User_ID, users.User_Name, contacts.Contact_ID, contacts.Contact_Name\n" +
                "                FROM appointments, customers, users, contacts\n" +
                "                WHERE (SELECT MONTHNAME(Start)) = ?\n" +
                "                AND appointments.Customer_ID = customers.Customer_ID\n" +
                "                AND appointments.User_ID = users.User_ID\n" +
                "                AND appointments.Contact_ID = contacts.Contact_ID\n" +
                "                ORDER BY Appointment_ID;";


        try {
            PreparedStatement ps = Main.conn.prepareStatement(selectStatement);
            ps.setString(1, month);
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
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppointment = new Appointment(appointmentID, title, description, type, location, startDate, endDate, customerID, customerName, userID, username,  contactID, contactName);
                appointmentsByMonth.add(newAppointment);

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return appointmentsByMonth;

    }

    /**
     * @param title meeting title
     * @param description meeting description
     * @param location meeting location
     * @param type one of five choices "Planning Session", "De-Briefing", "New Customer", "Event", "Sales Call"
     * @param start start time of meeting as a ZonedTimeDate @ system default
     * @param end end time of meeting as a ZonedTimeDate @ system default
     * @param customerID customerID of meeting
     * @param userID staff member ID for whom meeting is
     * @param contactID contact ID for meeting
     * Creates new record in the appointment table. AppointmentID assigned by database*/
    public static void addAppointment(String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, int customerID, int userID, int contactID){

        try {
            String insertStatement = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement ps = Main.conn.prepareStatement(insertStatement);

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

    /**
     * @param appointmentID auto-increment assigned by database. Used here to find appointment
     * @param title meeting title
     * @param description meeting description
     * @param location meeting location
     * @param type one of five choices "Planning Session", "De-Briefing", "New Customer", "Event", "Sales Call"
     * @param start start time of meeting as a ZonedTimeDate @ system default
     * @param end end time of meeting as a ZonedTimeDate @ system default
     * @param customerID customerID of meeting
     * @param userID staff member ID for whom meeting is
     * @param contactID contact ID for meeting
     * Updates existing record in database. Locates appointment by appointmentID*/
    public static void updateAppointment(int appointmentID, String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, int customerID, int userID, int contactID){

        String updateStatement = "UPDATE appointments SET " +
                "Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID =? WHERE Appointment_ID = ?";

        try {
            PreparedStatement ps = Main.conn.prepareStatement(updateStatement);

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

    /**
     * @param appointmentID auto-increment assigned by database. Used here to find appointment
     * Deletes existing appointment in database. Locates appointment by appointmentID*/
    public static void deleteAppointment(int appointmentID){
        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";

        try {
            PreparedStatement ps = Main.conn.prepareStatement(deleteStatement);

            ps.setString(1, String.valueOf(appointmentID));
            ps.execute();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}