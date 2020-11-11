package View_Controller;

import DAO.AppointmentDAO;
import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDivisionDAO;
import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.*;

/** Customer Screen Controller */
public class CustomerScreenController implements Initializable {

    Stage stage;
    Parent scene;
    Customer customer;
    ArrayList<Appointment> customerAppointments;
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

    /**
     * Appointment Screen button event handler
     * */
    @FXML
    public void appointmentScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Report Screen button event handler */
    @FXML
    public void reportScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/ReportScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Add Customer button event handler. Enables customer information's text fields */
    @FXML
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

    /** Save Customer button event handler. Saves customer information's text fields to Customer database. Creates new customer if customerID is null, overwrites existing information if customerID is not null.*/
    @FXML
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

    /** Populates form text fields with selected customer information */
    @FXML
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

    /** Country ComboBox event handler. Populates first level division with associated countries after a country has been selected */
    @FXML
    private void populateDivision() throws SQLException {
        try {
            divisionCombo.setItems(FirstLevelDivisionDAO.getSelectedFirstLevelDivisions(countryCombo.getValue().getCountryID()));
        }
        catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    /** Delete Customer button handler. Warns user that existing customer appointments from database will be deleted, deletes appointments then deletes customer from database */
    @FXML
    public void deleteCustomer(ActionEvent event) throws SQLException {

        customerIDText.setText("");
        nameText.setText("");
        phoneText.setText("");
        addressText.setText("");
        postalCodeText.setText("");

        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            pleaseSelectCustomerLabel.setText("Please select a customer to delete.");
        }

        int custID = customerTable.getSelectionModel().getSelectedItem().getCustomerID();
        customerAppointments = AppointmentDAO.getCustomerAppointments(custID);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer? " + customerAppointments.size() + " appointments will be deleted.");
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {

            for (Appointment appointment : customerAppointments) {
                AppointmentDAO.deleteAppointment(appointment.getAppointmentID());
            }
            CustomerDAO.deleteCustomer(custID);
        }

        //TODO - refresh the appointment tableview
        //appointmentTableview.refresh();
        customerList = CustomerDAO.getAllCustomers();
        customerTable.refresh();
    }

    /**
     * Exit button event handler. Asks for confirmation before exiting.
     * */
    @FXML
    public void exit(ActionEvent event) {
        System.exit(0);

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)  {
            System.exit(0);
        }
    }

    /** Initializes tableview and comboboxes */
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
