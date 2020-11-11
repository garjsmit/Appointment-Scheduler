package View_Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import utils.TimeUtils;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Formatter;
import java.util.Optional;
import java.util.ResourceBundle;

/** Report Screen Controller */
public class ReportScreenController implements Initializable {

    Stage stage;
    Parent scene;
    private static ObservableList<Appointment> appointmentsByContact = FXCollections.observableArrayList();
    private static ObservableList<String> months = FXCollections.observableArrayList("January" , "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private static ObservableList<Customer> customersFromLast30Days = FXCollections.observableArrayList();

    @FXML
    private Label currentYearReportLabel, planSessCountLabel, debriefingCountLabel, newCustCountLabel,  eventCountLabel, salesCallCountLabel;

    @FXML
    private TableView<Customer> reportCustomerTable;

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol, addressCol, postalCodeCol, phoneCol, divisionIDCol;

    @FXML
    private TableView<Appointment> reportAppointmentTableview;

    @FXML
    private TableColumn<Appointment, String> titleCol, descriptionCol, locationCol, typeCol, contactCol, userCol, customerCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;

    @FXML
    private TableColumn<Appointment, ZonedDateTime> dateCol, startTimeCol, endTimeCol;

    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private ComboBox<String> monthCombo;


    /**
     * Customer Button event handler
     * */
    @FXML
    public void customerScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

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

    /**
     * Select Month event handler. Returns the meeting type counts for the selected months
     * */
    @FXML
    void populateTypeCountReport(ActionEvent event) {

        int planningCount = 0, debriefingCount = 0, newCustCount = 0, eventCount = 0, salesCallCount = 0;
        String selectedMonth = monthCombo.getValue();

        ObservableList<Appointment> appointments = AppointmentDAO.getAppointmentsByMonth(selectedMonth);

        for(Appointment appointment : appointments){
            if(appointment.getType().equals("Planning Session")) {
                planningCount++;
            }
            else if(appointment.getType().equals("De-Briefing")) {
                debriefingCount++;
                break;
            }
            else if(appointment.getType().equals("New Customer")) {
                newCustCount++;
                break;
            }
            else if(appointment.getType().equals("Event")) {
                eventCount++;
                break;
            }
            else if(appointment.getType().equals("Sales Call")) {
                salesCallCount++;
                break;
            }
            else {
                System.out.println("No type found");
                break;
            }
        }

        planSessCountLabel.setText(String.valueOf(planningCount));
        debriefingCountLabel.setText(String.valueOf(debriefingCount));
        newCustCountLabel.setText(String.valueOf(newCustCount));
        eventCountLabel.setText(String.valueOf(eventCount));
        salesCallCountLabel.setText(String.valueOf(salesCallCount));

    }

    /**
     * Contact schedule event handler. Populates appointment tableview for the selected contact.
     * */
    @FXML
    void contactSchedule(ActionEvent event) {

        int contactID = contactCombo.getSelectionModel().getSelectedItem().getContactID();
        System.out.println(contactID);
        appointmentsByContact = AppointmentDAO.getContactAppointments(contactID);
        appointmentTableSetup(appointmentsByContact);
    }

    /**
     * Exit button event handler. Asks for confirmation before exiting.
     * */
    @FXML
    void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)  {
            System.exit(0);
        }
    }

    /** Initializes the appointment table columns */
    public void appointmentTableSetup(ObservableList<Appointment> appointmentList){
        reportAppointmentTableview.setItems(appointmentList);
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        dateCol.setCellFactory(column -> {
            TableCell<Appointment, ZonedDateTime> cell = new TableCell<Appointment, ZonedDateTime>() {

                @Override
                protected void updateItem(ZonedDateTime date, boolean empty) {
                    super.updateItem(date, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        if (date != null)
                            this.setText(TimeUtils.dateFormat.format(date));
                    }
                }
            };
            return cell;
        });

        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startTimeCol.setCellFactory(column -> {
            TableCell<Appointment, ZonedDateTime> cell = new TableCell<Appointment, ZonedDateTime>() {

                @Override
                protected void updateItem(ZonedDateTime date, boolean empty) {
                    super.updateItem(date, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        if (date != null)
                            this.setText(TimeUtils.timeFormat.format(date));
                    }
                }
            };
            return cell;
        });

        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        endTimeCol.setCellFactory(column -> {
            TableCell<Appointment, ZonedDateTime> cell = new TableCell<Appointment, ZonedDateTime>() {

                @Override
                protected void updateItem(ZonedDateTime date, boolean empty) {
                    super.updateItem(date, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        if (date != null)
                            this.setText(TimeUtils.timeFormat.format(date));
                    }
                }
            };
            return cell;
        });

    }

    /** Intialize */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentYearReportLabel.setText("Last twelve months");

        monthCombo.setPromptText("Select month");
        monthCombo.setItems(months);

        planSessCountLabel.setText("");
        debriefingCountLabel.setText("");
        newCustCountLabel.setText("");
        eventCountLabel.setText("");
        salesCallCountLabel.setText("");

        contactCombo.setItems(ContactDAO.getAllContacts());
        contactCombo.setPromptText("Select contact");

        customersFromLast30Days = CustomerDAO.customersFromLast30Days();
        reportCustomerTable.setItems(customersFromLast30Days);
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionIDCol.setCellValueFactory(new PropertyValueFactory<>("division"));

    }
}
