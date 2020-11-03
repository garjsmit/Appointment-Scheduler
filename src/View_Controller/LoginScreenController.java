package View_Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.User;
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
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.*;

import static utils.TimeUtils.dateFormat;
import static utils.TimeUtils.timeFormat;

public class LoginScreenController implements Initializable {

    Stage stage;
    Parent scene;
    String loginError, enterValidUserPass, exitConfirm;
    
    @FXML
    private Button exitButton, loginButton;

    @FXML
    private Label titleLabel, localTimeLabel, locationLabel, usernameLabel, passwordLabel;

    @FXML
    private TextField userIDText;

    @FXML
    private PasswordField passwordText;

    @FXML
    public void loginHandler(ActionEvent event) throws IOException{

        //String filename = "login_activity.txt",
        String usernameAttempt = userIDText.getText();
        String passwordAttempt = passwordText.getText();

        if (usernameAttempt.equals("") || passwordAttempt.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, enterValidUserPass);
            alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }

        int userID = UserDAO.getUserByName(usernameAttempt).getUserID();
        String password = UserDAO.getUserByName(usernameAttempt).getPassword();

        if (passwordAttempt.equals(password)) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

            long timeDifference;
            for (Appointment appointment : AppointmentDAO.getUserAppointments(userID)) {
                timeDifference = ChronoUnit.MINUTES.between(appointment.getStartDate().toLocalTime(), LocalTime.now());

                if (timeDifference < 0 && timeDifference >= -15) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have an appointment coming up: \n" +
                            "Appointment ID: " + appointment.getAppointmentID() + "\n" +
                            dateFormat.format(appointment.getStartDate())+  " @ " + timeFormat.format(appointment.getStartDate()) + ".");
                    alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
                    Optional<ButtonType> result = alert.showAndWait();
                }

                else if(timeDifference >= 0 && timeDifference <= 15) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "One of your appointments just started! \n" +
                            "Appointment ID: " + appointment.getAppointmentID() + "\n" +
                            "Today @ " + timeFormat.format(appointment.getStartDate()) + "!");
                    alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
                    Optional<ButtonType> result = alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have no appointments coming up.");
                    alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
                    Optional<ButtonType> result = alert.showAndWait();
                    break;
                }
            }
        }

        else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, loginError);
                alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
                Optional<ButtonType> result = alert.showAndWait();
            }

            //TODO write these login attempts to a file
    }


    @FXML
    public void exitHandler(ActionEvent event) {

       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if(Locale.getDefault().getLanguage().equals("fr")) {
            alert = new Alert(Alert.AlertType.CONFIRMATION, exitConfirm);
            alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        }
       else if(Locale.getDefault().getLanguage().equals("en")) {
            alert = new Alert(Alert.AlertType.CONFIRMATION, exitConfirm);
            alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        }
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)  {
            System.exit(0);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        rb = ResourceBundle.getBundle("utils/Nat", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")){
            titleLabel.setText(rb.getString("Title"));
            localTimeLabel.setText(String.valueOf(ZoneId.systemDefault()));
            locationLabel.setText(rb.getString("Location"));
            usernameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            exitButton.setText(rb.getString("Exit"));
            loginButton.setText(rb.getString("Login"));
            loginError = rb.getString("LogError");
            enterValidUserPass = rb.getString("EnterValidUserPass");
            exitConfirm = rb.getString("ExitConfirm");
        }
        else {
            titleLabel.setText(rb.getString("Title"));
            localTimeLabel.setText(String.valueOf(ZoneId.systemDefault()));
            locationLabel.setText(rb.getString("Location"));
            usernameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            exitButton.setText(rb.getString("Exit"));
            loginButton.setText(rb.getString("Login"));
            loginError = rb.getString("LogError");
            enterValidUserPass = rb.getString("EnterValidUserPass");
            exitConfirm = rb.getString("ExitConfirm");
        }
    }

}
