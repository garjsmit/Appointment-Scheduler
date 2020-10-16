package Model;

public class Contact {

    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    private int contactID;
    private String contactName, email;

    public int getContactID() {
        return contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return (contactID + " | " + contactName);
    }
}
