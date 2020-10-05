package DAO;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private static Connection conn;
    private static PreparedStatement statement;

    public static ObservableList<Customer> populateCustomerList() throws SQLException {
        return customerList;
    }


    public static ObservableList<Customer> getAllCustomers() throws SQLException {

        customerList.clear();
        DBQuery.setPreparedStatement(Main.Main.conn, "SELECT * FROM customers");
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.execute();

        ResultSet rs = ps.getResultSet();

        while (rs.next()) {

            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionID = rs.getInt("Division_ID");

            Customer newCustomer = new Customer(customerID, customerName, address, postalCode, phone, divisionID);

            customerList.add(newCustomer);
        }

        return customerList;
    }

    public static boolean addCustomer(Customer customer) throws SQLException {

        String insertStatement = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";

        DBQuery.setPreparedStatement(Main.Main.conn, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setInt(5, customer.getDivisionID());

        return ps.execute();
    }

    public static boolean updateCustomer(Customer customer) throws SQLException {

        String updateStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?,  Phone = ?,  Division_ID = ? WHERE Customer_ID = ?";

        DBQuery.setPreparedStatement(Main.Main.conn, updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setInt(5, customer.getDivisionID());
        ps.setString(6, String.valueOf(customer.getCustomerID()));


        return ps.execute();
    }

    public static boolean deleteCustomer(Customer customer) throws SQLException {
        String deleteStatement = "DELETE FROM customers WHERE Customer_ID = ?";

        DBQuery.setPreparedStatement(Main.Main.conn, deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, String.valueOf(customer.getCustomerID()));

        return ps.execute();
    }


}