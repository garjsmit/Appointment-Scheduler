package View_Controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDivisionDAO;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class CustomerScreenController implements Initializable {

    Customer customer;
    ObservableList<Customer> customerList;
    ObservableList<Country> countries = CountryDAO.getAllCountries();
    ObservableList<FirstLevelDivision> allFirstLevelDivisions = FirstLevelDivisionDAO.getAllFirstLevelDivisions();


    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TextField nameText, phoneText, customerIDText, addressText, postalCodeText;

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol, addressCol, postalCodeCol, phoneCol, divisionIDCol;

    @FXML
    private GridPane formGrid;

    @FXML
    private ComboBox<Country> countryCombo;

    @FXML
    private ComboBox<FirstLevelDivision> divisionCombo;

    @FXML
    private Label pleaseSelectCustomerLabel;

    public CustomerScreenController() throws SQLException {
    }

    public void addCustomer(ActionEvent event) {

        formGrid.setDisable(false);
        customerIDText.setText("");
        nameText.setText("");
        phoneText.setText("");
        addressText.setText("");
        postalCodeText.setText("");
        countryCombo.setValue(null);
        divisionCombo.setValue(null);
    }

    public void saveCustomer(ActionEvent event) throws SQLException {

            String name = nameText.getText();
            String address = addressText.getText();
            String postalCode = postalCodeText.getText();
            String phone = phoneText.getText();
            int divisionID = divisionCombo.getSelectionModel().getSelectedItem().getDivisionID();
            String division = divisionCombo.getSelectionModel().getSelectedItem().getDivision();
            int countryID = countryCombo.getSelectionModel().getSelectedItem().getCountryID();
            String country = countryCombo.getSelectionModel().getSelectedItem().getCountry();


            if (customerIDText.getText().isEmpty()) {

                CustomerDAO.addCustomer(name, address, postalCode, phone, divisionID);

            } else {
                int customerID = Integer.parseInt(customerIDText.getText());
                CustomerDAO.updateCustomer(customerID, name, address, postalCode, phone, divisionID);
            }

            customerList = CustomerDAO.getAllCustomers();
            customerTable.refresh();

            addCustomer(event);
    }

    public void updateCustomer(ActionEvent event) throws SQLException {

        formGrid.setDisable(false);
        pleaseSelectCustomerLabel.setText("");

        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            pleaseSelectCustomerLabel.setText("Please select a customer to update.");
        }
        else {

            for(Country country : countries){
                if(selectedCustomer.getCountryID() == country.getCountryID() ){
                    countryCombo.getSelectionModel().select(country);
                    break;
                }

            }

            for(FirstLevelDivision fld : allFirstLevelDivisions){
                if(selectedCustomer.getDivisionID() == fld.getDivisionID()){
                    divisionCombo.getSelectionModel().select(fld);
                    break;
                }
            }

            customerIDText.setText(String.valueOf(selectedCustomer.getCustomerID()));
            nameText.setText(selectedCustomer.getCustomerName());
            addressText.setText(selectedCustomer.getAddress());
            postalCodeText.setText(selectedCustomer.getPostalCode());
            phoneText.setText(selectedCustomer.getPhone());
        }
    }

    @FXML
    private void populateDivision() throws SQLException {
        try {
            divisionCombo.setItems(FirstLevelDivisionDAO.getSelectedFirstLevelDivisions(countryCombo.getValue().getCountryID()));
        }
        catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }


    public void deleteCustomer(ActionEvent event) throws SQLException {

        customerIDText.setText("");
        nameText.setText("");
        phoneText.setText("");
        addressText.setText("");
        postalCodeText.setText("");

        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            pleaseSelectCustomerLabel.setText("Please select a customer to delete.");
        }
/*
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            CustomerDAO.deleteCustomer(customerTable.getSelectionModel().getSelectedItem());
        }
*/
        //TODO move this line of code to the if statement. this is for streamlining testing
        CustomerDAO.deleteCustomer(customerTable.getSelectionModel().getSelectedItem().getCustomerID());

        customerList = CustomerDAO.getAllCustomers();
        customerTable.refresh();
    }

    public void exit(ActionEvent event) {
        System.exit(0);
/*
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)  {
            System.exit(0);
        }
 */
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        customerTable.setItems(CustomerDAO.getAllCustomers());

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionIDCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryCombo.setItems(countries);

    }

}
