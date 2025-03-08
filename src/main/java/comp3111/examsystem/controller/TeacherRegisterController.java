package comp3111.examsystem.controller;

import comp3111.examsystem.entity.TeacherProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The controller for TeacherRegisterUI.fxml
 */
public class TeacherRegisterController {
    @FXML
    private TextField ageTxt;
    @FXML
    private Button closeButton;
    @FXML
    private TextField departmentTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField passwordConfirmTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private TextField passwordConfirmTxtShow;
    @FXML
    private TextField passwordTxtShow;
    @FXML
    private Button registerButton;
    @FXML
    private TextField usernameTxt;
    @FXML
    private Text passwordConfirmWarning;
    @FXML
    private MenuButton gender;
    @FXML
    private MenuButton position;

    //Warnings
    @FXML
    private Text usernameWarning;
    @FXML
    private Text nameWarning;
    @FXML
    private Text ageWarning;
    @FXML
    private Text positionWarning;
    @FXML
    private Text departmentWarning;
    @FXML
    private Text genderWarning;
    @FXML
    private Text passwordWarning;
    @FXML
    private CheckBox showPasswordButton;
    @FXML
    private CheckBox showPasswordConfirmButton;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String jsonFileLocation = "src/main/resources/database/userInfo.json";

    /**
     * Default constructor
     */
    public TeacherRegisterController(){}

    /**
     * The initialization method called when starting the scene controller.
     */
    @FXML
    public void initialize() {
        passwordTxtShow.setManaged(false);
        passwordTxtShow.setVisible(false);

        passwordConfirmTxtShow.setManaged(false);
        passwordConfirmTxtShow.setVisible(false);

        passwordTxtShow.textProperty().bindBidirectional(passwordTxt.textProperty());
        passwordConfirmTxtShow.textProperty().bindBidirectional(passwordConfirmTxt.textProperty());

        initializeWarningLabels();
    }

    void initializeWarningLabels(){
        usernameWarning.setVisible(false);
        nameWarning.setVisible(false);
        ageWarning.setVisible(false);
        positionWarning.setVisible(false);
        departmentWarning.setVisible(false);
        genderWarning.setVisible(false);
        passwordConfirmWarning.setVisible(false);
        passwordWarning.setVisible(false);
    }

    @FXML
    void selectGender(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getTarget();
        gender.setText(menuItem.getText());
    }
    @FXML
    void selectPosition(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getTarget();
        position.setText(menuItem.getText());
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
    void showPasswordConfirm(ActionEvent event) {
        if (showPasswordConfirmButton.isSelected()) {
            passwordConfirmTxtShow.setVisible(true);
            passwordConfirmTxtShow.setManaged(true);
            passwordConfirmTxt.setVisible(false);
            passwordConfirmTxt.setManaged(false);
        } else {
            passwordConfirmTxt.setVisible(true);
            passwordConfirmTxt.setManaged(true);
            passwordConfirmTxtShow.setVisible(false);
            passwordConfirmTxtShow.setManaged(false);
        }
    }

    @FXML
    void closeButtonPressed(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    private void setWarning(Text warning, String status){
        warning.setVisible(true);
        warning.setText(status);
    }

    private boolean validateAcc(String username, String name, String gender, String ageString,
                               String position, String department, String password, String passwordConfirm) {
        /*
        1. Username check foul languages, unknown characters
        2. Name unknown characters
        3. Age check non-numerical input, range
        4. Department unknown characters
        5. Password do basic security measures
        6. PasswordConfirm check if password matches
        */
        int age = -1;
        boolean agePresent = false;
        if (!ageString.isEmpty()) {
            try {
                age = Integer.parseInt(ageString);
            } catch (RuntimeException e) {
                ageWarning.setVisible(true);
                ageWarning.setText("Please enter a valid age value");
            }
            agePresent = true;
        }

        boolean validated = true;
        initializeWarningLabels();

        if (username.isEmpty()) {
            setWarning(usernameWarning, "Please Enter a username");
            validated = false;
        } else if (TeacherProcessor.containsFoulLanguage(username) || !TeacherProcessor.isAlphaNumeric(username)) {
            setWarning(usernameWarning, "Please Enter a valid username");
            validated = false;
        } else if (TeacherProcessor.checkIfUserNameExists(username)) {
            setWarning(usernameWarning, "This username is already in use, please use a new one");
            validated = false;
        }

        if (name.isEmpty()){
            setWarning(nameWarning, "Please Enter your name");
            validated = false;
        } else if (TeacherProcessor.containsFoulLanguage(name) || (!TeacherProcessor.isAlphaNumeric(name.strip()))){
            setWarning(nameWarning, "Please Enter a valid name");
            validated = false;
        }

        if(gender.equals("Gender")){
            setWarning(genderWarning,"Please choose your Gender");
            validated = false;
        }

        if(!agePresent) {
            setWarning(ageWarning,"Please Enter an age");
            validated = false;
        } else if(age <= 12 || age >= 150){
            setWarning(ageWarning,  "Please Enter a valid age value");
            validated = false;
        }

        if(position.equals("Position")){
            setWarning(positionWarning,"Please enter your position");
            validated = false;
        }

        if(department.isEmpty()){
            setWarning(departmentWarning, "Please enter your department");
            validated = false;
        } else if( !TeacherProcessor.isAlpha(department)){
            setWarning(departmentWarning, "Please Enter a valid department name");
            validated = false;
        }


        if(password.isEmpty()){
            setWarning(passwordWarning,"Password cannot be empty");
            validated = false;
        } else if(!TeacherProcessor.isSecurePassword(password)){
            setWarning(passwordWarning,"Password must be more than 8 characters and at least one special character(s) (.*[!@#$%^&*].*) ");
            validated = false;
        }

        if(passwordConfirm.isEmpty()){
            setWarning(passwordConfirmWarning,"Please Re-enter your password");
            validated = false;
        }
        else if(!password.equals(passwordConfirm)){
            setWarning(passwordConfirmWarning,"Re-entered password doesn't match!");
            validated = false;
        }
        return validated;
    }

    //"src/main/resources/userCredentials/userInfo.json"
    @FXML
    void registerButtonPressed(ActionEvent event) throws IOException {
        if(!validateAcc(usernameTxt.getText(), nameTxt.getText(), gender.getText(), ageTxt.getText(), position.getText(),departmentTxt.getText(), passwordTxt.getText(), passwordConfirmTxt.getText())){
            System.out.println("Failed");
        }
        else{
            if(TeacherProcessor.addUser(usernameTxt.getText(), nameTxt.getText(), gender.getText(), Integer.parseInt(ageTxt.getText()),
                    position.getText() ,departmentTxt.getText(), passwordTxt.getText())){
//                //System.out.println("Register Successful");
//                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherLoginUI.fxml"));
//                Stage stage = new Stage();
//                try {
//                    stage.setScene(new Scene(fxmlLoader.load()));
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                stage.show();
                ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
            }
            else{
                System.out.println("Register Unsuccessful");
            }
        }
    }

}
