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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import utils.TimeUtils;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;


public class AppointmentScreenController implements Initializable {

    Stage stage;
    Parent scene;
    public static ObservableList<Contact> contacts = ContactDAO.getAllContacts();
    public static ObservableList<Customer> customers = CustomerDAO.getAllCustomers();
    public static ObservableList<User> users = UserDAO.getAllUsers();
    public static ObservableList<String> type = FXCollections.observableArrayList("Planning Session", "De-Briefing", "New Customer", "Event", "Sales Call");

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
    private TextField appointmentIDText, titleText, descriptionText, locationText;

    @FXML
    private ComboBox<String> typeCombo;

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
    private RadioButton startAmRad, startPmRad, endAmRad, endPmRad;

    @FXML
    private Label selectAppointmentLabel;

    @FXML
    public void customerScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void reportScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/ReportScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void addAppointment(ActionEvent event) {

        formGrid.setDisable(false);
        appointmentIDText.setText("");
        titleText.setText("");
        descriptionText.setText("");
        locationText.setText("");
        typeCombo.setValue(null);
        contactCombo.setValue(null);
        customerCombo.setValue(null);
        userCombo.setValue(null);
        selectedDate.setValue(null);
        startHourCombo.setValue(null);
        startMinuteCombo.setValue(null);
        endHourCombo.setValue(null);
        endMinuteCombo.setValue(null);

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
            typeCombo.setValue(selectedAppointment.getType());

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

            if(selectedAppointment.getStartDate().toLocalTime().getHour() < 12){
                startHourCombo.setValue(selectedAppointment.getStartDate().toLocalTime().getHour());
                startAmRad.setSelected(true);
            }
            else if(selectedAppointment.getStartDate().toLocalTime().getHour() == 12){
                startHourCombo.setValue(12);
                startPmRad.setSelected(true);
            }
            else{
                startHourCombo.setValue(selectedAppointment.getStartDate().toLocalTime().getHour() - 12);
                startPmRad.setSelected(true);
            }
            startMinuteCombo.setValue(selectedAppointment.getStartDate().toLocalTime().getMinute());

            if(selectedAppointment.getEndDate().toLocalTime().getHour() < 12){
                endHourCombo.setValue(selectedAppointment.getEndDate().toLocalTime().getHour());
                endAmRad.setSelected(true);
            }
            else if(selectedAppointment.getEndDate().toLocalTime().getHour() == 12){
                endHourCombo.setValue(12);
                endPmRad.setSelected(true);
            }
            else{
                endHourCombo.setValue(selectedAppointment.getEndDate().toLocalTime().getHour() - 12);
                endPmRad.setSelected(true);
            }
            endMinuteCombo.setValue(selectedAppointment.getEndDate().toLocalTime().getMinute());
        }
    }

    @FXML
    public void deleteAppointment(ActionEvent event) {


        if (appointmentTableview.getSelectionModel().getSelectedItem() == null) {
            selectAppointmentLabel.setText("Please select an appointment to delete.");
        }
        else{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this appointment?");
            alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {

                int appointmentID = appointmentTableview.getSelectionModel().getSelectedItem().getAppointmentID();
                String type = appointmentTableview.getSelectionModel().getSelectedItem().getType();
                AppointmentDAO.deleteAppointment(appointmentID);

                Alert deleteConfimred = new Alert(Alert.AlertType.INFORMATION, "Meeting: " + type + " with Appointment ID: " + appointmentID + " has been cancelled.");
                alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
                Optional<ButtonType> answer = deleteConfimred.showAndWait();
        }
            appointmentTableview.getItems().clear();
            AppointmentDAO.getAllAppointments();
            appointmentTableview.refresh();
        }

    }

    @FXML
    public void saveAppointment(ActionEvent event) {

        boolean overlap = false;
        ObservableList<Appointment> appointmentList = AppointmentDAO.getAllAppointments();
        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationText.getText();
        String type = typeCombo.getValue();
        int customerID = customerCombo.getSelectionModel().getSelectedItem().getCustomerID();
        String customerName = customerCombo.getSelectionModel().getSelectedItem().getCustomerName();
        int userID = userCombo.getSelectionModel().getSelectedItem().getUserID();
        int contactID = contactCombo.getSelectionModel().getSelectedItem().getContactID();


        ZonedDateTime startTime = TimeUtils.figureZonedDateTime(selectedDate.getValue(), startHourCombo.getValue(), startMinuteCombo.getValue(), startPmRad.isSelected());
        ZonedDateTime endTime = TimeUtils.figureZonedDateTime(selectedDate.getValue(), endHourCombo.getValue(), endMinuteCombo.getValue(), endPmRad.isSelected());
        ZonedDateTime sob = TimeUtils.getZonedStartOfBusiness(selectedDate.getValue());
        ZonedDateTime eob = TimeUtils.getZonedEndOfBusiness(selectedDate.getValue());

        if(startTime.isBefore(sob) || startTime.isAfter(eob) || endTime.isBefore(sob) || endTime.isAfter(eob)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Your appointment must start within business hours, which are daily from 8 am to 10pm, EST");
            alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            for (Appointment appointment : appointmentList) {
                ZonedDateTime appStartTime = appointment.getStartDate();
                ZonedDateTime appEndTime = appointment.getEndDate();

                boolean startTimeIsAfterAppStartTime = startTime.isAfter(appStartTime);
                boolean startTImeIsBeforeAppEndTime = startTime.isBefore(appEndTime);
                boolean endTimeIsAfterAppStartTime = endTime.isAfter(appStartTime);
                boolean endTimeIsBeforeAppEndTime = endTime.isBefore(appEndTime);
                boolean startTimeIsBeforeAppStartTime = startTime.isBefore(appStartTime);
                boolean endTimeIsAfterAppEndTime = endTime.isAfter(appEndTime);



                if (customerName.equals(appointment.getCustomerName()) &&
                        ((startTimeIsAfterAppStartTime && startTImeIsBeforeAppEndTime ||
                                (endTimeIsAfterAppStartTime && endTimeIsBeforeAppEndTime) //))) {
                      || (startTimeIsBeforeAppStartTime && endTimeIsAfterAppEndTime) ))){
                    overlap = true;
                    String errorMessage = appointment.getCustomerName() + " already has another appointment at that time. It starts at: \n Start time : " + TimeUtils.timeFormat.format(appointment.getStartDate()) + "\nEnd Time: " + TimeUtils.timeFormat.format(appointment.getEndDate()) + ".";
                    Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
                    alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
                    Optional<ButtonType> result = alert.showAndWait();
                    break;
                }
            }

            if(!overlap) {
                if (appointmentIDText.getText().isEmpty()) {
                    AppointmentDAO.addAppointment(title, description, location, type, startTime, endTime, customerID, userID, contactID);

                } else {
                    int appointmentID = Integer.parseInt(appointmentIDText.getText());
                    AppointmentDAO.updateAppointment(appointmentID, title, description, location, type, startTime, endTime, customerID, userID, contactID);
                }

                appointmentTableview.getItems().clear();
                AppointmentDAO.getAllAppointments();
                appointmentTableview.refresh();
                addAppointment(event);
            }
        }
    }


    @FXML
    public void exit(ActionEvent event) {

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
    alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
    Optional<ButtonType> result = alert.showAndWait();

    if(result.isPresent() && result.get() == ButtonType.OK)  {
            System.exit(0);
        }
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

        appointmentTableview.setItems(AppointmentDAO.getAllAppointments());

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
        typeCombo.setItems(type);
        startHourCombo.setItems(TimeUtils.getHours());
        startMinuteCombo.setItems(TimeUtils.getMinutes());
        endHourCombo.setItems(TimeUtils.getHours());
        endMinuteCombo.setItems(TimeUtils.getMinutes());


    }

}
