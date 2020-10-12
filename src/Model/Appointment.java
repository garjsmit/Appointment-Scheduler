package Model;

import java.time.LocalDateTime;

public class Appointment {

    private int appointmentID, customerID, userID, contactID;

    public Appointment(int appointmentID, int customerID, int userID, int contactID, String title, String description, String type, String location, LocalDateTime startDate, LocalDateTime endDate) {
        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.title = title;
        this.description = description;
        this.type = type;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private String title, description, type, location;
    private LocalDateTime startDate, endDate;

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getUserID() {
        return userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

}
