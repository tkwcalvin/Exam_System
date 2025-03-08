package comp3111.examsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.Main;
import comp3111.examsystem.entity.TeacherProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The controller for TeacherLoginUI.fxml
 */
public class TeacherLoginController implements Initializable {
    @FXML
    private Button loginBtn;
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Text userNotFoundWarning;
    @FXML
    private CheckBox showPasswordButton;
    @FXML
    private TextField passwordTxtShow;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String jsonFileLocation = "src/main/resources/database/teacherUserInfo.json";

    /**
     * The login status of the system
     */
    public boolean loggedIn = false;

    /**
     * Default constructor
     */
    public TeacherLoginController(){
        new TeacherProcessor();
    }

    /**
     * The initialization method called when starting the scene controller.
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("teacher Login Initialize");
        userNotFoundWarning.setVisible(false);

        passwordTxtShow.setManaged(false);
        passwordTxtShow.setVisible(false);

        passwordTxtShow.textProperty().bindBidirectional(passwordTxt.textProperty());
    }

    @FXML
    void showPassword(ActionEvent event) {
        if (showPasswordButton.isSelected()) {
            passwordTxtShow.setVisible(true);
            passwordTxtShow.setManaged(true);
            passwordTxt.setVisible(false);
            passwordTxt.setManaged(false);
        } else {
            passwordTxt.setVisible(true);
            passwordTxt.setManaged(true);
            passwordTxtShow.setVisible(false);
            passwordTxtShow.setManaged(false);
        }
    }

    @FXML
    void login(ActionEvent e) {
        if(usernameTxt.getText().isEmpty() || passwordTxt.getText().isEmpty()){
            userNotFoundWarning.setVisible(true);
            userNotFoundWarning.setText("Please enter your username and password");
        }
        else if(!TeacherProcessor.checkIfUserNameExists(usernameTxt.getText())){
            userNotFoundWarning.setVisible(true);
            userNotFoundWarning.setText("User does not exist :(");
        }
        else if(TeacherProcessor.loginUser(usernameTxt.getText(), passwordTxt.getText())){
            userNotFoundWarning.setVisible(false);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("teacherMainUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hi " + usernameTxt.getText() +", Welcome to HKUST Examination System");
            try {
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException error) {
                throw new RuntimeException(error);
            }
            stage.show();
            loggedIn = true;
            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
        }
        else{
            userNotFoundWarning.setVisible(true);
            userNotFoundWarning.setText("Incorrect Password");
        }
    }

    @FXML
    void register() {
        System.out.println("teacher Login Register");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("teacherRegisterUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("teacher Register");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
