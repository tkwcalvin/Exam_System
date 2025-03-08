package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for manager login
 */
public class ManagerLoginController implements Initializable {
    @FXML
    private Label errorLabel;
    /**
     * The field for manager username input
     */
    @FXML
    public TextField usernameTxt;
    /**
     * The field for manager password input
     */
    @FXML
    public PasswordField passwordTxt;
    /**
     * The button for manager login
     */
    @FXML
    public Button buttonLogin;
    /**
     * The status indicates whether the login is successful
     */
    public boolean status = false;

    /**
     * For the page initialization
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setVisible(false);

    }

    private boolean validateCredentials(String username, String password) {
        // Define your target username and password
        String targetUsername = "admin";   // Replace with your target username
        String targetPassword = "calvin"; // Replace with your target password

        // Check if the provided username and password match the target values
        return targetUsername.equals(username) && targetPassword.equals(password);
        //return true;
    }


    @FXML
    void login(ActionEvent e) throws InterruptedException {
        // Retrieve username and password
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        // Validate credentials (replace with your actual validation logic)
        if (validateCredentials(username, password)) {
            status = true;
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ManagerMainUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hi " + username + ", Welcome to HKUST Examination System");
            try {
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            stage.show();
            // Close the login window
            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
        }
        else {
            status = false;
            // Handle login failure (e.g., show an alert)
            errorLabel.setVisible(true);
        }

    }


}
