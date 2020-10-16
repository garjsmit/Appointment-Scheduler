package DAO;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAO {

    private static ObservableList<Contact> contactList = FXCollections.observableArrayList();

    public static ObservableList<Contact> getAllContacts(){

        String selectStatement = "SELECT * FROM contacts";
        contactList.clear();
        try{
            PreparedStatement ps = Main.Main.conn.prepareStatement(selectStatement);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()){

                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contact newContact = new Contact(contactID, contactName, contactEmail);

                contactList.add(newContact);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }


        return contactList;
    }


}
