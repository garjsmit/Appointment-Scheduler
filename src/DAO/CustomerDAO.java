package DAO;

import Main.Main;
import Model.Customer;
import javafx.beans.binding.ObjectExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**Customer DAO*/
public class CustomerDAO {

    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private static ObservableList<Customer> customersLast30Days = FXCollections.observableArrayList();

    /**
     * @return customerList
     * This is a list of all customers.
     * */
    public static ObservableList<Customer> getAllCustomers() {

        customerList.clear();

        try {

            PreparedStatement ps = Main.conn.prepareStatement(
                    "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, Division, first_level_divisions.COUNTRY_ID, Country, customers.Create_Date\n" +
                            "FROM customers, first_level_divisions, countries\n" +
                            "WHERE customers.Division_ID = first_level_divisions.Division_ID \n" +
                            "AND first_level_divisions.COUNTRY_ID = countries.Country_ID ORDER BY Customer_ID;");

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()) {

                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID");
                String country = rs.getString("Country");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();


                Customer newCustomer = new Customer(customerID, customerName, address, postalCode, phone, divisionID, division, countryID, country, createDate);

                customerList.add(newCustomer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }

    /**
     * @return customersLast30Days
     * This is a list of customers added in the last 30 days.
     * */
    public static ObservableList<Customer> customersFromLast30Days() {

        customersLast30Days.clear();

        String selectStatement = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, Division, first_level_divisions.COUNTRY_ID, Country, customers.Create_Date\n" +
                "                            FROM customers, first_level_divisions, countries\n" +
                "                            WHERE customers.Create_Date BETWEEN NOW() - INTERVAL 30 DAY AND NOW()\n" +
                "                            AND customers.Division_ID = first_level_divisions.Division_ID\n" +
                "                            AND first_level_divisions.COUNTRY_ID = countries.Country_ID ORDER BY Customer_ID;";

        try {

            PreparedStatement ps = Main.conn.prepareStatement(selectStatement);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()) {

                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID");
                String country = rs.getString("Country");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();


                Customer newCustomer = new Customer(customerID, customerName, address, postalCode, phone, divisionID, division, countryID, country, createDate);

                customersLast30Days.add(newCustomer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customersLast30Days;

    }

    /**
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID
     * Adds new customer to database. customerID is assigned by database. */
    public static void addCustomer(String customerName, String address, String postalCode, String phone, int divisionID) {

        String insertStatement = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = Main.conn.prepareStatement(insertStatement);

            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionID);

            ps.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID
     * Updates existing customer to database. Finds existing record by matching customerID. */
    public static void updateCustomer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID) {
        String updateStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?,  Phone = ?,  Division_ID = ? WHERE Customer_ID = ?";

        try{
        PreparedStatement ps = Main.conn.prepareStatement(updateStatement);

        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionID);
        ps.setString(6, String.valueOf(customerID));

        ps.execute();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    /**
     *  @param customerID
    *   Deletes existing customer from database. Finds existing record by matching customerID. */
    public static void deleteCustomer(int customerID)  {
        String deleteStatement = "DELETE FROM customers WHERE Customer_ID = ?";

        try {
            PreparedStatement ps = Main.conn.prepareStatement(deleteStatement);

            ps.setString(1, String.valueOf(customerID));

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}