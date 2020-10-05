package View_Controller;

import DAO.CustomerDAO;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerScreenController implements Initializable {

    Customer customer;
    ObservableList<Customer> customerList;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TextField nameText, phoneText, customerIDText, addressText, postalCodeText;

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol, addressCol, postalCodeCol, phoneCol, divisionIDCol;

    @FXML
    private ComboBox<Country> countryCombo;

    @FXML
    private ComboBox<FirstLevelDivision> stateCombo;

    @FXML
    private Label customerIDLabel, nameLabel, addressLabel, postalCodeLabel, phoneLabel, countryLabel, divisionLabel, pleaseSelectCustomerLabel;

    @FXML
    private Button saveButton;


    public void saveCustomer(ActionEvent event) throws SQLException {

        String name = nameText.getText();
        String address = addressText.getText();
        String postalCode = postalCodeText.getText();
        String phone = phoneText.getText();
        int divisionID = 1; //placeholder

        if (customerIDText.getText().isEmpty()) {
            Customer newCustomer = new Customer(name, address, postalCode, phone, divisionID);
            CustomerDAO.addCustomer(newCustomer);
        } else {
            int customerID = Integer.parseInt(customerIDText.getText());
            Customer newCustomer = new Customer(customerID, name, address, postalCode, phone, divisionID);
            CustomerDAO.updateCustomer(newCustomer);
        }

        customerIDText.setText("");
        nameText.setText("");
        phoneText.setText("");
        addressText.setText("");
        postalCodeText.setText("");

        customerList = CustomerDAO.getAllCustomers();
        customerTable.refresh();
    }

    public void addCustomer(ActionEvent event) {
        customerIDLabel.setDisable(false);
        nameLabel.setDisable(false);
        addressLabel.setDisable(false);
        postalCodeLabel.setDisable(false);
        phoneLabel.setDisable(false);
        countryLabel.setDisable(false);
        divisionLabel.setDisable(false);
        saveButton.setDisable(false);
        nameText.setDisable(false);
        phoneText.setDisable(false);
        addressText.setDisable(false);
        postalCodeText.setDisable(false);
        stateCombo.setDisable(false);
        countryCombo.setDisable(false);
    }

    public void setCustomer(Customer customer){
        this.customer = customer;

        customerIDText.setText(String.valueOf(customer.getCustomerID()));
        nameText.setText(customer.getCustomerName());
        addressText.setText(customer.getAddress());
        postalCodeText.setText(customer.getPostalCode());
        phoneText.setText(customer.getPhone());

    }

    public void updateCustomer(ActionEvent event) {

        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            pleaseSelectCustomerLabel.setText("Please select a customer to update.");
        }
        else {
            pleaseSelectCustomerLabel.setText("");
            customerIDLabel.setDisable(false);
            nameLabel.setDisable(false);
            addressLabel.setDisable(false);
            postalCodeLabel.setDisable(false);
            phoneLabel.setDisable(false);
            countryLabel.setDisable(false);
            divisionLabel.setDisable(false);
            saveButton.setDisable(false);
            nameText.setDisable(false);
            phoneText.setDisable(false);
            addressText.setDisable(false);
            postalCodeText.setDisable(false);
            stateCombo.setDisable(false);
            countryCombo.setDisable(false);

            setCustomer(customerTable.getSelectionModel().getSelectedItem());
        }

    }

    public void deleteCustomer(ActionEvent event) throws SQLException {

        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            pleaseSelectCustomerLabel.setText("Please select a customer to delete.");
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            CustomerDAO.deleteCustomer(customerTable.getSelectionModel().getSelectedItem());
        }

        customerList = CustomerDAO.getAllCustomers();
        customerTable.refresh();
    }

    public void exit(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)  {
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            customerTable.setItems(CustomerDAO.getAllCustomers());

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

    }


}
