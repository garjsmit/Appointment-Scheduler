package Model;

/**Contact class */
public class Contact {

    private int contactID;
    private String contactName, email;


    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * @return Returns contactID.
     * */
    public int getContactID() {
        return contactID;
    }

    /**
     * @return Returns contactName.
     * */
    public String getContactName() {
        return contactName;
    }

    /**
     * @return Returns email.
     * */
    public String getEmail() {
        return email;
    }

    /**
     * @return Returns formatted "contactID | contactName" for display in ComboBox
     * */
    @Override
    public String toString() {
        return (contactID + " | " + contactName);
    }

}
