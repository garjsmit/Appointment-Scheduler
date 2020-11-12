package View_Controller;

import DAO.AppointmentDAO;
import DAO.UserDAO;
import Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.*;
import static utils.TimeUtils.dateFormat;
import static utils.TimeUtils.timeFormat;

/** Login Screen Controller */
public class LoginScreenController implements Initializable {

    Stage stage;
    Parent scene;
    private static String loginError, enterValidUserPass, exitConfirm;
    private static String loginLogFile = "src/login_activity.txt ";
    private static String loginLogEntry = "";

    
    @FXML
    private Button exitButton, loginButton;

    @FXML
    private Label titleLabel, localTimeLabel, locationLabel, usernameLabel, passwordLabel;

    @FXML
    private TextField userIDText;

    @FXML
    private PasswordField passwordText;

    /**
     * Login button event handler. Returns user's password from database
     * */
    @FXML
    public void loginHandler(ActionEvent event) throws IOException{

        FileWriter createLogFile = new FileWriter(loginLogFile, true);
        PrintWriter recLoginAttempts = new PrintWriter(createLogFile);
        String usernameAttempt = userIDText.getText();
        String passwordAttempt = passwordText.getText();
        int userID = -1;
        String username = "";

        //if username or password are left blank
        if (usernameAttempt.equals("") || passwordAttempt.equals("")) {

            if(usernameAttempt.equals("")) { usernameAttempt = "NULL"; }

            loginLogEntry = usernameAttempt + " on " + ZonedDateTime.now().format(dateFormat) + " @ " + ZonedDateTime.now().format(timeFormat) + " UNSUCCESSFUL";
            recLoginAttempts.println(loginLogEntry);
            recLoginAttempts.close();

            Alert alert = new Alert(Alert.AlertType.ERROR, enterValidUserPass);
            alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }

        //if user enter incorrect username
        if(UserDAO.getUserByName(usernameAttempt) != null){
            userID = UserDAO.getUserByName(usernameAttempt).getUserID();
            username = UserDAO.getUserByName(usernameAttempt).getUsername();
        }
        else{
            loginLogEntry = usernameAttempt + " on " + ZonedDateTime.now().format(dateFormat) + " @ " + ZonedDateTime.now().format(timeFormat) + " UNSUCCESSFUL";
            recLoginAttempts.println(loginLogEntry);
            recLoginAttempts.close();

            Alert alert = new Alert(Alert.AlertType.ERROR, enterValidUserPass);
            alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }
        String password = UserDAO.getUserByName(usernameAttempt).getPassword();

        //if user enters correct
        if (usernameAttempt.equals(username) && passwordAttempt.equals(password)) {

            loginLogEntry = usernameAttempt + " on " + ZonedDateTime.now().format(dateFormat) + " @ " + ZonedDateTime.now().format(timeFormat) + " SUCCESSFUL";
            recLoginAttempts.println(loginLogEntry);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

            boolean upcomingMeeting = false;
            long timeDifference;

            for (Appointment appointment : AppointmentDAO.getUserAppointments(userID)) {
                timeDifference = ChronoUnit.MINUTES.between(appointment.getStartDate().toLocalDateTime(), LocalDateTime.now());

                if (timeDifference < 0 && timeDifference >= -15) {
                    upcomingMeeting = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have an appointment coming up: \n" +
                            "Appointment ID: " + appointment.getAppointmentID() + "\n" +
                            dateFormat.format(appointment.getStartDate())+  " @ " + timeFormat.format(appointment.getStartDate()) + ".");
                    alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
                    Optional<ButtonType> result = alert.showAndWait();
                    break;
                }

                else if(timeDifference >= 0 && timeDifference <= 15) {
                    upcomingMeeting = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "One of your appointments just started! \n" +
                            "Appointment ID: " + appointment.getAppointmentID() + "\n" +
                            "Today @ " + timeFormat.format(appointment.getStartDate()) + "!");
                    alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
                    Optional<ButtonType> result = alert.showAndWait();
                    break;
                }
            }
            if (!upcomingMeeting){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have no appointments coming up.");
                    alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
                    Optional<ButtonType> result = alert.showAndWait();
            }
        }
        //if user enters correct username but incorrect password
        else
            {
                loginLogEntry = usernameAttempt + " on " + ZonedDateTime.now().format(dateFormat) + " @ " + ZonedDateTime.now().format(timeFormat) + " UNSUCCESSFUL";
                recLoginAttempts.println(loginLogEntry);
                Alert alert = new Alert(Alert.AlertType.ERROR, loginError);
                alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
                Optional<ButtonType> result = alert.showAndWait();
                return;
            }


        recLoginAttempts.close();
    }

    /**
     * Exit button event handler. Asks for confirmation before exiting.
     * */
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

    /** Initialize language settings */
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
