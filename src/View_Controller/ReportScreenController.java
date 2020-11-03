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
import java.time.ZonedDateTime;
import java.util.Formatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportScreenController implements Initializable {

    Stage stage;
    Parent scene;
    ObservableList<Appointment> appointmentsByContact = FXCollections.observableArrayList();
    ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

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
    private RadioButton appByTypeAndMonth, appByCurrentMonth, appByType;

    @FXML
    public void customerScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void appointmentScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void appointmentReport(ActionEvent event) {
        //the total number of customer appointments by type and month
    }

    @FXML
    void contactReport(ActionEvent event) {
        System.out.println("Contact Report clicked!");
        //a schedule for each contact in your organization that includes appointment ID, title, type and description, start date and time, end date and time, and customer ID
        int contactID = contactCombo.getSelectionModel().getSelectedItem().getContactID();

        //appointmentsByContact = AppointmentDAO.getContactAppointments(contactID);

    }

    @FXML
    void customerReport(ActionEvent event) {
        //number of new customers added within thirty days
    }

    @FXML
    void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)  {
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        contactCombo.setItems(ContactDAO.getAllContacts());
        contactCombo.setPromptText("Select contact");

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

}
