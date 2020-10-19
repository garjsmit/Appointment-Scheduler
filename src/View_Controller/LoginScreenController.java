package View_Controller;

import DAO.CustomerDAO;
import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.*;

public class LoginScreenController implements Initializable {

    @FXML
    private Button exitButton, loginButton;

    @FXML
    private Label titleLabel, localTimeLabel, locationLabel, usernameLabel, passwordLabel;

    @FXML
    private TextField userIDText;

    @FXML
    private PasswordField passwordText;

    Stage stage;
    Parent scene;

    @FXML
    public void loginHandler(ActionEvent event) throws IOException, SQLException {
/*
        String filename = "login_activity.txt", password = null;
        String usernameAttempt = userIDText.getText();
        String passwordAttempt = passwordText.getText(); 

        password = UserDAO.getUserByName(usernameAttempt).getPassword();
        if(password==null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "That username/password combination does not exist.");
                Optional<ButtonType> result = alert.showAndWait();
        }
        else if(passwordAttempt.equals(password)){

 */
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/UserHubScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
/*
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The username/password combination does not exist.");
            Optional<ButtonType> result = alert.showAndWait();
        }

*/
        //TODO write these login attempts to a file
    }

    @FXML
    public void exitHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)  {
            System.exit(0);
        }
    }

    interface myLamda {
        void foo();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //TODO translate errors into French

        rb = ResourceBundle.getBundle("utils/Nat", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")){
            titleLabel.setText(rb.getString("Title"));
            localTimeLabel.setText(String.valueOf(ZoneId.systemDefault()));
            locationLabel.setText(rb.getString("Location"));
            usernameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            exitButton.setText(rb.getString("Exit"));
            loginButton.setText(rb.getString("Login"));
        }
        else {
            titleLabel.setText(rb.getString("Title"));
            localTimeLabel.setText(String.valueOf(ZoneId.systemDefault()));
            locationLabel.setText(rb.getString("Location"));
            usernameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            exitButton.setText(rb.getString("Exit"));
            loginButton.setText(rb.getString("Login"));
        }
    }

}
