package View_Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import utils.TimeUtils;

import java.net.URL;
import java.sql.Time;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.time.ZoneOffset.UTC;

public class AppointmentScreenController implements Initializable {

    public static ObservableList<Appointment> appointmentList = AppointmentDAO.getAllAppointments();
    public static ObservableList<Contact> contacts = ContactDAO.getAllContacts();
    public static ObservableList<Customer> customers = CustomerDAO.getAllCustomers();
    public static ObservableList<User> users = UserDAO.getAllUsers();



    @FXML
    private TableView<Appointment> appointmentTableview;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;

    @FXML
    private TableColumn<Appointment, String> titleCol, descriptionCol, userCol, customerCol, typeCol, locationCol, contactCol;

    @FXML
    private TableColumn<Appointment, ZonedDateTime> dateCol, startTimeCol, endTimeCol;

    @FXML
    private GridPane formGrid;

    @FXML
    private TextField appointmentIDText, titleText, descriptionText, locationText, typeText;

    @FXML
    private ComboBox<LocalTime> startTime, endTime;

    @FXML
    private ComboBox<Integer> startHourCombo, startMinuteCombo, endHourCombo, endMinuteCombo;

    @FXML
    private ComboBox<User> userCombo;

    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private ComboBox<Customer> customerCombo;

    @FXML
    private DatePicker selectedDate;

    @FXML
    private RadioButton showAllRadio, currentWeekRadio, currentMonthRadio, startAmRad, startPmRad, endAmRad, endPmRad;

    @FXML
    private Label selectAppointmentLabel;

    @FXML
    public void addAppointment(ActionEvent event) {
        formGrid.setDisable(false);
        appointmentIDText.setText("");
        titleText.setText("");
        descriptionText.setText("");
        locationText.setText("");
        typeText.setText("");
        contactCombo.setValue(null);
        customerCombo.setValue(null);
        userCombo.setValue(null);
        selectedDate.setValue(null);
    }

    @FXML
    public void updateAppointment(ActionEvent event) {

        formGrid.setDisable(false);
        selectAppointmentLabel.setText("");

        Appointment selectedAppointment = appointmentTableview.getSelectionModel().getSelectedItem();

        if(selectedAppointment == null){
            selectAppointmentLabel.setText("Please select appointment to update.");
        }
        else {
            appointmentIDText.setText(String.valueOf(selectedAppointment.getAppointmentID()));
            titleText.setText(selectedAppointment.getTitle());
            descriptionText.setText(selectedAppointment.getDescription());
            locationText.setText(selectedAppointment.getLocation());
            typeText.setText(selectedAppointment.getType());

            for(Contact contact : ContactDAO.getAllContacts()){
                if(contact.getContactID() == selectedAppointment.getContactID()){
                    contactCombo.getSelectionModel().select(contact);
                    break;
                }
            }

            for(Customer customer : CustomerDAO.getAllCustomers()){
                if(customer.getCustomerID() == selectedAppointment.getCustomerID()){
                    customerCombo.getSelectionModel().select(customer);
                    break;
                }
            }

            for(User user : UserDAO.getAllUsers()){
                if(user.getUserID() == selectedAppointment.getUserID()){
                    userCombo.getSelectionModel().select(user);
                    break;
                }
            }

            selectedDate.setValue(selectedAppointment.getStartDate().toLocalDate());
            startTime.setValue(selectedAppointment.getStartDate().toLocalTime());
            endTime.setValue(selectedAppointment.getEndDate().toLocalTime());
        }
    }

    @FXML
    public void deleteAppointment(ActionEvent event) {


        if (appointmentTableview.getSelectionModel().getSelectedItem() == null) {
            selectAppointmentLabel.setText("Please select an appointment to delete.");
        }
        else{
            AppointmentDAO.deleteAppointment(appointmentTableview.getSelectionModel().getSelectedItem().getAppointmentID()); //delete this and comment below for full functionality

/*
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {

                int appointmentID = appointmentTableview.getSelectionModel().getSelectedItem().getAppointmentID();
                String type = appointmentTableview.getSelectionModel().getSelectedItem().getType();
                AppointmentDAO.deleteAppointment(appointmentID);

                Alert deleteConfimred = new Alert(Alert.AlertType.INFORMATION, "Meeting: " + type + " with Appointment ID: " + appointmentID + " has been cancelled.");
                Optional<ButtonType> answer = deleteConfimred.showAndWait();
        }

 */

            appointmentTableview.getItems().clear();
            AppointmentDAO.getAllAppointments();
            appointmentTableview.refresh();
        }

    }

    @FXML
    public void saveAppointment(ActionEvent event) {

        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationText.getText();
        String type = typeText.getText();
        int customerID = customerCombo.getSelectionModel().getSelectedItem().getCustomerID();
        int userID = userCombo.getSelectionModel().getSelectedItem().getUserID();
        int contactID = contactCombo.getSelectionModel().getSelectedItem().getContactID();

        ZonedDateTime startTime = TimeUtils.figureZonedDateTime(selectedDate.getValue(), startHourCombo.getValue(), startMinuteCombo.getValue(), startPmRad.isSelected());
        ZonedDateTime endTime = TimeUtils.figureZonedDateTime(selectedDate.getValue(), endHourCombo.getValue(), endMinuteCombo.getValue(), endPmRad.isSelected());

        if((startTime.isBefore(TimeUtils.getZonedStartOfBusiness(selectedDate.getValue())) || startTime.isAfter(TimeUtils.getZonedEndOfBusiness(selectedDate.getValue())))){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Your appointment must start within business hours, which are daily from 8 am to 10pm, EST");
            Optional<ButtonType> result = alert.showAndWait();

        }else if((endTime.isBefore(TimeUtils.getZonedStartOfBusiness(selectedDate.getValue())) || endTime.isAfter(TimeUtils.getZonedEndOfBusiness(selectedDate.getValue())))){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Your appointment must end within business hours, which are daily from 8 am to 10pm, EST");
            Optional<ButtonType> result = alert.showAndWait();

        }else{
            if(appointmentIDText.getText().isEmpty()){
                AppointmentDAO.addAppointment(title, description, location, type, startTime, endTime, customerID, userID, contactID);
            }
            else{
                int appointmentID = Integer.parseInt(appointmentIDText.getText());
                AppointmentDAO.updateAppointment(appointmentID, title, description, location, type, startTime, endTime, customerID, userID, contactID);
            }
            appointmentTableview.getItems().clear();
            AppointmentDAO.getAllAppointments();
            appointmentTableview.refresh();
            addAppointment(event);
        }
    }

    @FXML
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

    @FXML
    void filterByMonth(ActionEvent event) {
        appointmentTableview.getItems().clear();
        ObservableList<Appointment> currentMonthAppointmentList = AppointmentDAO.getCurrentMonthAppointments();
        appointmentTableview.setItems(currentMonthAppointmentList);
    }

    @FXML
    void filterByWeek(ActionEvent event) {
        appointmentTableview.getItems().clear();
        ObservableList<Appointment> currentWeekAppointmentList = AppointmentDAO.getCurrentWeekAppointments();
        appointmentTableview.setItems(currentWeekAppointmentList);

    }

    @FXML
    void showAll(ActionEvent event) {

        appointmentTableview.getItems().clear();
        appointmentTableview.setItems(AppointmentDAO.getAllAppointments());
        appointmentTableview.refresh();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

        appointmentTableview.setItems(appointmentList);
        
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

        contactCombo.setItems(contacts);
        customerCombo.setItems(customers);
        userCombo.setItems(users);
        startHourCombo.setItems(TimeUtils.getHours());
        startMinuteCombo.setItems(TimeUtils.getMinutes());
        endHourCombo.setItems(TimeUtils.getHours());
        endMinuteCombo.setItems(TimeUtils.getMinutes());


    }

}
