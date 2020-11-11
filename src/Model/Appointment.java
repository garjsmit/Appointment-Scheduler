package Model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**Appointment class. Selects data from Customer, User, and Contact tables. Dates are saved as ZonedDateTime.*/
public class Appointment {

    private int appointmentID, customerID, userID, contactID;
    private String title;
    private String description;
    private String type;
    private String location;
    private String customerName;
    private String username;
    private String contactName;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    public Appointment(int appointmentID, String title, String description, String type, String location, ZonedDateTime startDate, ZonedDateTime endDate, int customerID, String customerName, int userID, String username, int contactID, String contactName) {
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
        this.customerName = customerName;
        this.username = username;
        this.contactName = contactName;
    }


    /**
     * @return Returns startDate.
     * */
    public ZonedDateTime getStartDate() {
        return startDate;
    }

    /**
     * @return Returns endDate.
     * */
    public ZonedDateTime getEndDate() {
        return endDate;
    }

    /**
     * @return Returns appointmentID.
     * */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * @return Returns title.
     * */
    public String getTitle() {
        return title;
    }

    /**
     * @return Returns description.
     * */
    public String getDescription() {
        return description;
    }

    /**
     * @return Returns type.
     * */
    public String getType() {
        return type;
    }

    /**
     * @return Returns location.
     * */
    public String getLocation() {
        return location;
    }

    /**
     * @return Returns customerName.
     * */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param startDate Set startDate.
     * */
    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * @param endDate Set endDate.
     * */
    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * @param customerName Set customerName.
     * */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return Returns username.
     * */
    public String getUsername() {
        return username;
    }

    /**
     * @param username Set username.
     * */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return Returns contactName.
     * */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName Set contactName.
     * */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return Returns customerID.
     * */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @return Returns userID.
     * */
    public int getUserID() {
        return userID;
    }

    /**
     * @return Returns contactID.
     * */
    public int getContactID() {
        return contactID;
    }

    /**
     * @param appointmentID Set appointmentID.
     * */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * @param title Set title.
     * */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param description Set description.
     * */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param type Set type.
     * */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param location Set location.
     * */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @param customerID Set customerID.
     * */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @param userID Set userID.
     * */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @param contactID Set contactID.
     * */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }



}
