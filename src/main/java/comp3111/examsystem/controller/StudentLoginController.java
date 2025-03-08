package comp3111.examsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.Main;
import comp3111.examsystem.entity.StudentProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A controller to implement the login function for student
 * @author <a href=mailto:pakninlpn@gmail.com>Marco</a>
 * @version 1.3
 */
public class StudentLoginController implements Initializable {
    /**
     * A javafx text box for username input
     */
    @FXML
    public TextField usernameTxt;
    /**
     * A javafx text box for password input
     */
    @FXML
    public PasswordField passwordTxt;

    /**
     * A javafx text as warning to "user not found" event
     */
    @FXML
    public Text userNotFoundWarning;

    /**
     * A javafx check box to display password
     */
    @FXML
    public CheckBox showPasswordButton;

    /**
     * A javafx text box to display hidden password
     */
    @FXML
    public TextField passwordTxtShow;

    /**
     * A javafx button to start registration event
     */
    @FXML
    public Button registerButton;

    /**
     * A javafx button to start login event
     */
    @FXML
    public Button loginButton;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String jsonFileLocation = "src/main/resources/database/userInfo.json";
    private final StudentProcessor studentProcessor;

    /**
     * A constructor to construct the controller and instantiate the
     * studentProcessor object
     */
    public StudentLoginController(){
        studentProcessor = new StudentProcessor(jsonFileLocation);
    }

    /**
     * A method to initialize this controller
     * @param location  URL location from metadata
     * @param resources resources from metadata
     */
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println("Student Login Initialize");
        userNotFoundWarning.setVisible(false);

        passwordTxtShow.setManaged(false);
        passwordTxtShow.setVisible(false);

        passwordTxtShow.textProperty().bindBidirectional(passwordTxt.textProperty());
    }

    /**
     * A method to check if the show password button is selected
     * @param showPasswordButton    a boolean variable indicating if the button
     *                              is selected
     * @return                      a boolean
     */
    public static boolean checkShowPasswordButton(boolean showPasswordButton){
        return showPasswordButton;
    }

    /**
     * A method to show the hidden password
     * @param event Action event of the show password button
     */
    @FXML
    void showPassword(ActionEvent event) {
        if (checkShowPasswordButton(showPasswordButton.isSelected())) {
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

    /**
     * A method to check is the username is empty
     * @param username  the username to be checked
     * @return          a boolean indicating if the username is null
     */
    public static boolean checkUserNameEmpty(String username){
        return username.equals("");
    }

    /**
     * A method to check is the password is empty
     * @param password  the password to be checked
     * @return          a boolean indicating if the password is null
     */
    public static boolean checkPasswordEmpty(String password){
        return password.equals("");
    }

    /**
     * A method to check either the username and password is empty
     * @param username  the username to be checked
     * @param password  the password to be checked
     * @return          <code>true</code> if either one field is empty
     *                  <code>fakse</code> if both not empty
     */
    public static boolean checkUserNameEmptyAndPasswordEmpty(String username, String password){
        return username.equals("") || password.equals("");
    }

    /**
     * A method to check if the username exists in the database
     * @param studentProcessor the StudentProcessor
     * @param username              the username to be checked
     * @param password              the corresponding password
     * @return                      <code>true</code> if the username exists
     *                              <code>false</code> if either username or password
     *                                                  field is empty or the username
     *                                                  does not exist
     */
    public static boolean checkUserNameExist(StudentProcessor studentProcessor, String username, String password){
        return !username.equals("") && !password.equals("") && !studentProcessor.checkIfUserNameExists(username);
    }

    /**
     * A method to check if the username and password pair matched with an entry in database
     * @param studentProcessor the StudentProcessor
     * @param username              the username to be checked
     * @param password              the corresponding password
     * @return                      <code>true</code> if the username-password pair matched
     *                              <code>false</code> if either username or password
     *                                                 field is empty or the username-password
     *                                                 pair does not exist
     */
    public static boolean checkUserLogin(StudentProcessor studentProcessor, String username, String password){
        return !username.equals("") && !password.equals("") && studentProcessor.checkIfUserNameExists(username) && studentProcessor.loginUser(username, password);
    }

    /**
     * A method to check if the password is wrong for the given username
     * @param studentProcessor the StudentProcessor
     * @param username              the username to be checked
     * @param password              the password to be checked
     * @return                      <code>true</code> if the username-password pair matched
     *                              <code>false</code> if either username or password
     *                                                 field is empty or the username-password
     *                                                 pair does not exist
     */
    public static boolean checkWrongPassword(StudentProcessor studentProcessor, String username, String password){
        return !username.equals("") && !password.equals("") && studentProcessor.checkIfUserNameExists(username) && !studentProcessor.loginUser(username, password);
    }

    /**
     * A method to login the user when the login button is clicked
     * @param e the ActionEvent of the button
     */
    @FXML
    public void login(ActionEvent e) {
        //if(checkUserNameEmpty(usernameTxt.getText()) || checkPasswordEmpty(passwordTxt.getText())){
        if(checkUserNameEmptyAndPasswordEmpty(usernameTxt.getText(), passwordTxt.getText())){
            userNotFoundWarning.setVisible(true);
            userNotFoundWarning.setText("Please enter your username and password");
        }

        if(checkUserNameExist(studentProcessor, usernameTxt.getText(), passwordTxt.getText())){
            userNotFoundWarning.setVisible(true);
            userNotFoundWarning.setText("User does not exist :(");
        }

        if(checkUserLogin(studentProcessor, usernameTxt.getText(), passwordTxt.getText())){
            userNotFoundWarning.setVisible(false);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentMainUI.fxml"));
            try {
                Stage stage = new Stage();
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.setTitle("Hi " + usernameTxt.getText() +", Welcome to HKUST Examination System");

                StudentMainController controller = fxmlLoader.getController();
                controller.setStudentName(usernameTxt.getText());

                stage.show();
                ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if(checkWrongPassword(studentProcessor, usernameTxt.getText(), passwordTxt.getText())){
            userNotFoundWarning.setVisible(true);
            userNotFoundWarning.setText("Incorrect Password");
        }
    }

    /**
     * A method to handler registration
     */
    @FXML
    public void register() {
        //System.out.println("Student Login Register");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentRegisterUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Student Register");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
