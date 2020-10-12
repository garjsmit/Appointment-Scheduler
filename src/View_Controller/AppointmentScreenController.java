package View_Controller;

import Model.Appointment;
import Model.Customer;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;

public class AppointmentScreenController {

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;

    @FXML
    private TableColumn<Appointment, String> titleCol, descriptionCol, userCol, customerCol, typeCol, locationCol, contactCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startDateCol, endDateCol;

    @FXML
    private GridPane formGrid;

    @FXML
    private TextField appointmentIDText, titleText, descriptionText, locationText;

    @FXML
    private ComboBox<LocalDateTime> startTime, endTime;

    @FXML
    private ComboBox<String> typeCombo;

    @FXML
    private ComboBox<User> userCombo;

    @FXML
    private ComboBox<?> contactCombo;

    @FXML
    private DatePicker startDate;

    @FXML
    private ComboBox<Customer> customerCombo;

    @FXML
    private Label selectAppointmentLabel;


    public void addAppointment(ActionEvent event) {
        formGrid.setDisable(false);
    }

    public void updateAppointment(ActionEvent event) {
    }

    public void deleteAppointment(ActionEvent event) {
    }

    public void saveAppointment(ActionEvent event) {
    }

    public void exit(ActionEvent event) {

    }


}
