package comp3111.examsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.Main;
import comp3111.examsystem.entity.StudentProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * A controller to handler student registration
 * @author <a href=mailto:pakninlpn@gmail.com>Marco</a>
 * @version 2.1
 */
public class StudentRegisterController {

    /**
     * A javafx text box for age input
     */
    @FXML
    public TextField ageTxt;

    /**
     * A javafx button for exiting this event
     */
    @FXML
    public Button closeButton;

    /**
     * A javafx text box for department input
     */
    @FXML
    public TextField departmentTxt;

    /**
     * A javafx text box for name input
     */
    @FXML
    public TextField nameTxt;

    /**
     * A javafx text box for "password confirm" input
     */
    @FXML
    public TextField passwordConfirmTxt;

    /**
     * A javafx text box for password input
     */
    @FXML
    public TextField passwordTxt;

    /**
     * A javafx text field to show "password confirm"
     */
    @FXML
    public TextField passwordConfirmTxtShow;

    /**
     * A javafx text field to show "password"
     */
    @FXML
    public TextField passwordTxtShow;

    /**
     * A javafx button to finish this registration event
     */
    @FXML
    public Button registerButton;

    /**
     * A javafx text box for username input
     */
    @FXML
    public TextField usernameTxt;

    /**
     * A menu item for gender input
     */
    @FXML
    public MenuItem female;

    /**
     * A dropdown menu for gender input
     */
    @FXML
    public MenuButton gender;

    /**
     * A menu item for gender input
     */
    @FXML
    public MenuItem male;

    /**
     * A javafx text for username related warnings
     */
    //Warnings
    @FXML
    public Text usernameWarning;

    /**
     * A javafx text for name related warnings
     */
    @FXML
    public Text nameWarning;

    /**
     * A javafx text for age field empty warning
     */
    @FXML
    public Text ageMissingWarning;

    /**
     * A javafx text for age input not in valid range
     * warning
     */
    @FXML
    public Text ageNotInRangeWarning;

    /**
     * A javafx text for department field empty warning
     */
    @FXML
    public Text departmentMissingWarning;

    /**
     * A javafx text for gender field empty warning
     */
    @FXML
    public Text genderMissingWarning;

    /**
     * A javafx text for "password confirm" field empty warning
     */
    @FXML
    public Text passwordConfirmMissingWarning;

    /**
     * A javafx text for password and "password confirm" input
     * not match warning
     */
    @FXML
    public Text passwordConfirmNotMatchWarning;

    /**
     * A javafx text for password field empty warning
     */
    @FXML
    public Text passwordMissingWarning;

    /**
     * A javafx text for password input not match security
     * requirement warning
     */
    @FXML
    public Text passwordNotSecureWarning;

    /**
     * A javafx text for department input consist of invalid
     * characters warning
     */
    @FXML
    public Text departmentInvalidCharWarning;

    /**
     * A javafx text for username input already exist warning
     */
    @FXML
    public Text usernameExistWarning;

    /**
     * An ObjectMapper object
     */
    public final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * A string of file path to user information database
     */
    public static String jsonFileLocation = "src/main/resources/database/userInfo.json";
    /**
     * A StudentProcessor instance
     */
    public static StudentProcessor studentProcessor;

    /**
     * A method to set file path of user information database
     * @param location  the file path
     */
    public static void setStudentInfoJsonFileLocation(String location){
        jsonFileLocation = location;
    }

    //public StudentRegisterController(){
    //    studentProcessor = new StudentProcessor(jsonFileLocation);
    //}

    /**
     * A method to initialize the controller
     * Set all warning to invisible
     */
    @FXML
    public void initialize() {
        studentProcessor = new StudentProcessor(jsonFileLocation);

        passwordTxtShow.setManaged(false);
        passwordTxtShow.setVisible(false);

        passwordConfirmTxtShow.setManaged(false);
        passwordConfirmTxtShow.setVisible(false);

        passwordTxtShow.textProperty().bindBidirectional(passwordTxt.textProperty());
        passwordConfirmTxtShow.textProperty().bindBidirectional(passwordConfirmTxt.textProperty());

        usernameWarning.setVisible(false);
        nameWarning.setVisible(false);
        ageMissingWarning.setVisible(false);
        ageNotInRangeWarning.setVisible(false);
        departmentMissingWarning.setVisible(false);
        departmentInvalidCharWarning.setVisible(false);
        genderMissingWarning.setVisible(false);
        passwordConfirmMissingWarning.setVisible(false);
        passwordConfirmNotMatchWarning.setVisible(false);
        passwordMissingWarning.setVisible(false);
        passwordNotSecureWarning.setVisible(false);
        usernameExistWarning.setVisible(false);
    }


    /**
     * An event handler for selecting gender dropdown menu
     * @param event ActionEvent of the dropdown menu
     */
    @FXML
    void selectGender(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getTarget();
        gender.setText(menuItem.getText());
    }

    /**
     * A javafx check box to show password field input
     */
    @FXML
    public CheckBox showPasswordButton;

    /**
     * A javafx check box to show "password confirm"
     * field input
     */
    @FXML
    public CheckBox showPasswordConfirmButton;


    /**
     * A method to check if the password button is visible
     * @param isSelected    a boolean
     * @return              <code>true</code> if isSelected is false
     *                      <code>false</code> otherwise
     */
    public static boolean setShowPasswordButton(boolean isSelected){
        return !!isSelected;
    }

    /**
     * A method to show the hidden password in password
     * input field
     * @param event ActionEvent of the show password button
     */
    @FXML
    void showPassword(ActionEvent event) {
        //if (showPasswordButton.isSelected()) {
        if(setShowPasswordButton(showPasswordButton.isSelected())){
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
     * A method to show the hidden password in confirm
     * password input field
     * @param event ActionEvent of the show confirm password button
     */
    @FXML
    void showPasswordConfirm(ActionEvent event) {
        //if (showPasswordConfirmButton.isSelected()) {
        if(setShowPasswordButton(showPasswordConfirmButton.isSelected())){
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

    /**
     * A method to handle the close of window
     * @param event ActionEvent of the close button
     */
    @FXML
    void closeButtonPressed(ActionEvent event) {
        /*
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentRegisterCloseWarningUI.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        stage.show();
        //
        */
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    /**
     * A method to set the visibility of a warning label
     * @param warning   the warning label
     * @param status    boolean indicating visible or not
     * @return          boolean indicating visible or not
     */
    public static boolean setWarningVisibility(Text warning, boolean status){
        warning.setVisible(status);
        return status;
    }

    /**
     * A method to check if the username field is valid
     * @param username  the username field content
     * @return          <code>true</code> if the username is valid
     *                  <code>false</code> if the username is empty
     *                                     or contains foul languages
     *                                     or not alphanumeric characters
     */
    public static boolean checkUsernameValid(String username){
        return username.equals("") || StudentProcessor.containsFoulLanguage(username) || !StudentProcessor.isAlphaNumeric(username);
    }

    /**
     * A method to check if the username exits already
     * @param username  the username field content
     * @return          <code>true</code> if the username does not exit
     *                  <code>false</code> otherwise
     */
    public static boolean checkUsernameExit(String username){
        return StudentProcessor.checkIfUserNameExists(username);
    }

    /**
     * A method to cehck if the name field is valid
     * @param name  the name field content
     * @return      <code>true</code> if the name is valid
     *              <code>false</code> if the name is empty
     *                                 or contains foul languages
     *                                 or not alphanumeric characters
     */
    public static boolean checkNameValid(String name){
        return name.equals("") || StudentProcessor.containsFoulLanguage(name) || !StudentProcessor.isAlphaNumeric(name);
    }

    /**
     * A method to check if gender is not selected
     * @param gender    the gender field content
     * @return          <code>true</code> if the gender is empty
     *                  <code>false</code> if the name is selected
     */
    public static boolean checkGenderEmpty(String gender){
        return gender.equals("Gender");
    }

    /**
     * A method to check if the age is not inputted
     * @param agePresent    boolean indicating if age is inputted
     * @return              <code>true</code> if the age is empty
     *                      <code>false</code> if the age is inputted
     */
    public static boolean checkAgeEmpty(boolean agePresent){
        return !agePresent;
    }

    /**
     * A method to check if the age is valid
     * @param age   the age field content
     * @return      <code>true</code> if the 1 < age < 80
     *              <code>false</code> otherwise
     */
    public static boolean checkAgeRange(int age){
        return age < 1 || age > 80;
    }

    /**
     * A method to check if the department field is inputted
     * @param department    the department field content
     * @return              <code>true</code> if department is empty
     *                      <code>false</code> otherwise
     */
    public static boolean checkDepartmentEmpty(String department){
        return department.equals("");
    }

    /**
     * A method to check if department format is invalid
     * @param department    the department field content
     * @return              <code>true</code> if department is not alphanumerical
     *                                        characters
     *                      <code>false</code> if department is valid
     */
    public static boolean checkDepartmentFormat(String department){
        return !StudentProcessor.isAlpha(department);
    }

    /**
     * A method to check if the password field is empty
     * @param password   the password field content
     * @return                  <code>true</code> if the content is empty
     *                          <code>false</code> otherwise
     */
    public static boolean checkPasswordEmpty(String password){
        return password.equals("");
    }

    /**
     * A method to check if the password field content is not secure
     * @param password  the password field content
     * @return          <code>true</code> if password is either less than
     *                                    8 characters or without any special
     *                                    characters
     *                  <code>false</code> if password is secure
     */
    public static boolean checkPasswordSecure(String password){
        return !StudentProcessor.isSecurePassword(password);
    }

    /**
     * AA method to check if the password confirm field is empty
     * @param passwordConfirm   the password confirm field content
     * @return                  <code>true</code> if the content is empty
     *                          <code>false</code> if department is valid
     */
    public static boolean checkConfirmPasswordEmpty(String passwordConfirm){
        return passwordConfirm.equals("");
    }

    /**
     * A method to check if password and confirm password are not equal
     * @param password          the password field content
     * @param passwordConfirm   the password confirm field content
     * @return                  <code>true</code> if not match
     *                          <code>false</code> if match
     */
    public static boolean checkConfirmPasswordEqual(String password, String passwordConfirm){
        return !password.equals(passwordConfirm);
    }

    /**
     * A method to check if age field is not empty
     * @param userInfo  the age field content
     * @return          <code>true</code> if not empty
     *                  <code>false</code> if empty
     */
    public static boolean checkAgePresented(List<String> userInfo){
        return !userInfo.get(3).equals("");
    }

    /**
     * A method to set name invalid warning
     * @param nameWarning   the name warning label
     * @param name          the name to be checked
     * @return              <code>true</code> if name invalid
     *                      <code>false</code> otherwise
     */
    public static boolean checkNameWarning(Text nameWarning, String name){
        return setWarningVisibility(nameWarning, checkNameValid(name));
    }

    /**
     * A method to set gender missing warning
     * @param genderMissingWarning  the warning label
     * @param gender                the gender field content
     * @return                      <code>true</code> if gender field is empty
     *                              <code>false</code> otherwise
     */
    public static boolean checkGenderMissingWarning(Text genderMissingWarning, String gender){
        return setWarningVisibility(genderMissingWarning, checkGenderEmpty(gender));
    }

    /**
     * A method to validate if all account information is valid
     * @param userInfo  a list of account information from register fields
     * @return          <code>true</code> if all fields are valid
     *                  <code>false</code> otherwise
     */
    public boolean validateAcc(List<String> userInfo) {
        /*
        1. Username check foul languages, unknown characters
        2. Name unknown characters
        3. Age check non-numerical input, range
        4. Department unknown characters
        5. Password do basic security measures
        6. PasswordConfirm check if password matches
        */
        String username = userInfo.get(0);
        String name = userInfo.get(1);
        String gender = userInfo.get(2);

        int age = 0;
        boolean agePresent = true;
        //if(!userInfo.get(3).equals(""))
        if(checkAgePresented(userInfo))
            age = Integer.valueOf(userInfo.get(3));
        else
            agePresent = false;


        String department = userInfo.get(4);
        String password = userInfo.get(5);
        String passwordConfirm = userInfo.get(6);

        boolean validated = true;

        if(setWarningVisibility(usernameWarning, checkUsernameValid(username)) || setWarningVisibility(usernameExistWarning, checkUsernameExit(username)))
            validated = false;

        //if(setWarningVisibility(nameWarning, checkNameEmpty(name)))
        if(checkNameWarning(nameWarning, name))
            validated = false;

        //if(setWarningVisibility(genderMissingWarning, checkGenderEmpty(gender)))
        if(checkGenderMissingWarning(genderMissingWarning, gender))
            validated = false;

        if(setWarningVisibility(ageMissingWarning,   checkAgeEmpty(agePresent)))
            validated = false;

        if(setWarningVisibility(ageNotInRangeWarning,  checkAgeRange(age) && !checkAgeEmpty(agePresent) ))
            validated = false;

        if(setWarningVisibility(departmentMissingWarning, checkDepartmentEmpty(department)))
            validated = false;

        if(setWarningVisibility(departmentInvalidCharWarning, checkDepartmentFormat(department) && !checkDepartmentEmpty(department)))
            validated = false;

        if(setWarningVisibility(passwordMissingWarning, checkPasswordEmpty(password)))
            validated = false;

        if(setWarningVisibility(passwordNotSecureWarning, checkPasswordSecure(password) && !checkPasswordEmpty(password)))
            validated = false;

        if(setWarningVisibility(passwordConfirmMissingWarning, checkConfirmPasswordEmpty(passwordConfirm)))
            validated = false;

        if(setWarningVisibility(passwordConfirmNotMatchWarning, checkConfirmPasswordEqual(password, passwordConfirm) && !checkConfirmPasswordEmpty(passwordConfirm)))
            validated = false;

        return validated;
    }


    /**
     * A method to handler register user
     * @param event         ActionEvent of the register button
     * @throws IOException  Exception if any error happens
     */
    //"src/main/resources/userCredentials/userInfo.json"
    @FXML
    void registerButtonPressed(ActionEvent event) throws IOException {
        List<String> userInfo = Arrays.asList(usernameTxt.getText(), nameTxt.getText(), gender.getText(), ageTxt.getText(), departmentTxt.getText(), passwordTxt.getText(), passwordConfirmTxt.getText());
        if(!validateAcc(userInfo)){
            //System.out.println("Failed");
        }
        else{
            if(StudentProcessor.addUser(userInfo)){
                //System.out.println("Register Successful");
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentLoginUI.fxml"));
                Stage stage = new Stage();
                try {
                    stage.setScene(new Scene(fxmlLoader.load()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                stage.show();
                ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
            }
            else{
                //System.out.println("Register Unsuccessful");
            }
        }
    }

}
