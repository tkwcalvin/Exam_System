package comp3111.examsystem.controller;


import com.fasterxml.jackson.databind.ObjectMapper;

import comp3111.examsystem.entity.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * The controller class for teacher information management
 */
public class TeacherManagementController implements Initializable {

    /**
     * The table list for teachers information
     */
    @FXML
    public TableView<Teacher> teacherTable;
    @FXML
    private Label fillUsername;
    @FXML
    private Label fillName;
    @FXML
    private Label fillGender;
    @FXML
    private Label fillAge;
    @FXML
    private Label fillPosition;
    @FXML
    private Label fillDepartment;
    @FXML
    private Label fillPassword;
    @FXML
    private TableColumn<Teacher, String> usernameColumn;
    @FXML
    private TableColumn<Teacher, String> nameColumn;
    @FXML
    private TableColumn<Teacher, Integer> ageColumn;
    @FXML
    private TableColumn<Teacher, String> genderColumn;
    @FXML
    private TableColumn<Teacher, String> positionColumn;
    @FXML
    private TableColumn<Teacher, String> departmentColumn;
    @FXML
    private TableColumn<Teacher, String> passwordColumn;

    /**
     * The button for adding a new teacher
     */
    @FXML
    public Button buttonAdd;

    /**
     * The button for deleting an existing teacher
     */
    @FXML
    public Button buttonDelete;

    /**
     * The button for filtering the teachers
     */

    @FXML
    public Button buttonFilter;
    /**
     * The button for refreshing the page
     */

    @FXML
    public Button buttonRefresh;

    /**
     * The button for reset the filter and the table
     */

    @FXML
    public Button buttonReset;

    /**
     * The button for updating a teacher's information
     */

    @FXML
    public Button buttonUpdate;

    /**
     * The field for the teacher department filter
     */

    @FXML
    public TextField departmentFilter;

    /**
     * The field for the teacher username filter
     */

    @FXML
    public TextField usernameFilter;

    /**
     * The field for the teacher name filter
     */

    @FXML
    public TextField nameFilter;

    /**
     * The field for the teacher age input
     */

    @FXML
    public TextField ageField;

    /**
     * The field for the teacher department input
     */

    @FXML
    public TextField departmentField;

    /**
     * The field for the teacher gender input
     */

    @FXML
    public ComboBox<String> genderBox;

    /**
     * The field for the teacher position input
     */

    @FXML
    public ComboBox<String> positionBox;

    /**
     * The field for the teacher name input
     */

    @FXML
    public TextField nameField;

    /**
     * The field for the teacher password input
     */

    @FXML
    public TextField passwordField;

    /**
     * The field for the teacher username input
     */

    @FXML
    public TextField usernameField;


    private static final ObjectMapper mapper = new ObjectMapper();


    /**
     * The teachers information retrieved from the database
     */
    public List<Teacher> teachers;

    /**
     * The teacher selected in the table
     */
    public Teacher selectedTeacher;

    /**
     * The database path
     */
    public String jsonFileLocation = "src/main/resources/database/teacherUserInfo.json";

    /**
     * The key for the UI protection
     */
    public boolean safetyKey = true;

    /**
     * For the page initialization
     * @param location URL location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        genderBox.getItems().addAll("Male", "Female", "Others");
        genderBox.setValue(null);
        positionBox.getItems().addAll("Chair Professor","Emeritus Principle","Professor","Associate professor"
                ,"Assistant professor","Senior Lecturer" ,"Lecturer","Research Assistant Lecturer","Post-doctoral Fellow",
                "Adjunct Professor","Honorary Professor","Associate Professor","Assistant Professor","Professional  Consultant",
                "Teaching Consultant","Research Associate","Research Assistant","Postgraduate","Undergraduate");
        positionBox.setValue(null);
        buttonRefreshPressed();
    }


    private boolean confirmAction (String action){
        if (!safetyKey) return true;
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(action);
        final Optional<ButtonType> opt = alert.showAndWait();
        if (opt.get() == null) return false;
        final ButtonType rtn = opt.get();
        if (rtn == ButtonType.OK) {
            return true;
        } else if(rtn == ButtonType.CANCEL){
            return false;
        } else{
            // User close the panel without pressing any button
            return false;
        }
    }

    private void readData(){
        try {
            File file = new File(jsonFileLocation);
            teachers = mapper.readValue(file, new TypeReference<List<Teacher>>(){});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeData(){
        File file = new File(jsonFileLocation);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, teachers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        buttonRefreshPressed();
    }

    private void initializeLabel(){
        fillPassword.setVisible(false);
        fillDepartment.setVisible(false);
        fillPosition.setVisible(false);
        fillAge.setVisible(false);
        fillName.setVisible(false);
        fillUsername.setVisible(false);
        fillGender.setVisible(false);
    }



    private void initializeTable(){
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<Teacher, Integer>("age"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("gender"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("position"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("department"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("password"));
        teacherTable.setItems(FXCollections.observableList(teachers));
        teacherTable.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Teacher>) change -> {
                    if (!change.getList().isEmpty())
                        setSelectedTeacher(change.getList().getFirst());
                }
        );

    }

    private void setSelectedTeacher(Teacher q){
        selectedTeacher = q;
        usernameField.setText(selectedTeacher.getUsername());
        genderBox.setValue(selectedTeacher.getGender());
        nameField.setText(selectedTeacher.getName());
        ageField.setText(Integer.toString(selectedTeacher.getAge()));
        positionBox.setValue(selectedTeacher.getPosition());
        departmentField.setText(selectedTeacher.getDepartment());
        passwordField.setText(selectedTeacher.getPassword());
    }

    private boolean uniqueUsername(String username){
        for (Teacher teacher : teachers) {
            if (teacher.getUsername().equals(username)) {
                return false; // Username is not unique
            }
        }
        return true; // Username is unique
    }

    private static boolean isValidName(String text) {
        return Pattern.matches("[a-zA-Z0-9 ]+", text);
    }

    private static boolean isValidDepartment(String text) {
        return Pattern.matches("[a-zA-Z ]+", text);
    }

    private boolean isValidAge(String ageText) {

        // Validate whether the text is an integer
        try {
            int age = Integer.parseInt(ageText);
            return age > 0 && age < 60;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isSecurePassword(String password) {
        // Check for basic security measures: length and complexity
        return password.length() >= 8 && Pattern.matches(".*[!@#$%^&*].*", password);
    }

    private boolean validateInput(){
        boolean res = true;
        if (usernameField.getText() == null || usernameField.getText().isEmpty() || !isValidName(usernameField.getText())) {
            fillUsername.setVisible(true);
            res = false;
        }
        else {
            fillUsername.setVisible(false);
        }
        if (nameField.getText() == null || nameField.getText().isEmpty() || !isValidName((nameField.getText()))) {
            fillName.setVisible(true);
            res = false;
        }
        else {
            fillName.setVisible(false);
        }

        if (genderBox.getValue() == null) {
            fillGender.setVisible(true);
            res = false;
        }
        else {
            fillGender.setVisible(false);
        }
        if (ageField.getText() == null || ageField.getText().isEmpty() || !isValidAge(ageField.getText())) {
            fillAge.setVisible(true);
            res = false;
        }
        else {
            fillAge.setVisible(false);
        }

        if (positionBox.getValue() == null) {
            fillPosition.setVisible(true);
            res = false;
        }
        else {
            fillPosition.setVisible(false);
        }

        if (departmentField.getText() == null || departmentField.getText().isEmpty() || !isValidDepartment(departmentField.getText())) {
            fillDepartment.setVisible(true);
            res = false;
        }
        else {
            fillDepartment.setVisible(false);
        }
        if (passwordField.getText() == null || passwordField.getText().isEmpty() || !isSecurePassword(passwordField.getText())) {
            fillPassword.setVisible(true);
            res = false;
        }
        else {
            fillPassword.setVisible(false);
        }

        return res;
    }



    @FXML
    void buttonFilterPressed() {
        List<Teacher> result = filterUsername(
                filterName(
                        filterDepartment(
                                teachers, departmentFilter.getText()
                        ), nameFilter.getText()
                ), usernameFilter.getText()
        );
        teacherTable.setItems(FXCollections.observableList(result));
    }

    private List<Teacher> filterUsername(List<Teacher>students, String text){
        if (text == null) return students;
        ArrayList<Teacher> results = new ArrayList<>();
        for(Teacher i : students){
            if (i.getUsername().contains(text)) {
                results.add(i);
            }
        }
        return results;
    }

    private List<Teacher> filterName(List<Teacher>students, String text){
        if (text == null) return students;
        ArrayList<Teacher> results = new ArrayList<>();
        for(Teacher i : students){
            if (i.getName().contains(text)) {
                results.add(i);
            }
        }
        return results;
    }

    private List<Teacher> filterDepartment(List<Teacher>students, String text){
        if (text == null) return students;
        ArrayList<Teacher> results = new ArrayList<>();
        for(Teacher i : students){
            if (i.getDepartment().contains(text)) {
                results.add(i);
            }
        }
        return results;
    }

    private void clearFilter(){
        usernameFilter.setText(null);
        nameFilter.setText(null);
        departmentFilter.setText(null);
    }

    private void clearFields(){
        usernameField.setText(null);
        nameField.setText(null);
        ageField.setText(null);
        genderBox.setValue(null);
        positionBox.setValue(null);
        departmentField.setText(null);
        passwordField.setText(null);
    }

    @FXML
    void buttonResetPressed() {
        clearFilter();
        buttonFilterPressed();
    }

    @FXML
    void buttonRefreshPressed() {
        // Reload file content
        readData();
        clearFilter();
        clearFields();
        selectedTeacher = null;
        initializeLabel();
        initializeTable();
    }



    private Teacher createTeacher(){
        return new Teacher(
                usernameField.getText(),
                nameField.getText(),
                genderBox.getValue(),
                Integer.parseInt(ageField.getText()),
                positionBox.getValue(),
                departmentField.getText(),
                passwordField.getText()
        );
    }


    @FXML
    void buttonAddPressed() {
        if (!validateInput()){
            return;
        }
        else if (!uniqueUsername(usernameField.getText())){
            new Alert(Alert.AlertType.ERROR, "The username exists, please change another one").showAndWait();
            return;
        }
        if (!confirmAction("Add the new teacher ?")) return;
        teachers.add(createTeacher());
        writeData();
    }

    @FXML
    void buttonDeletePressed() {
        if (selectedTeacher == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a course to delete !").showAndWait();
            return;
        }
        else {
            if (!confirmAction("Delete the selected teacher ?")) return;
            teachers.remove(selectedTeacher);
            writeData();
        }
    }


    @FXML
    void buttonUpdatePressed() {
        int index = teachers.indexOf(selectedTeacher);
        if (selectedTeacher == null){
            new Alert(Alert.AlertType.ERROR, "Please select a student to modify !").showAndWait();
            return;
        }
        else if (!validateInput()){
            return;
        }
        else if (!uniqueUsername(usernameField.getText()) &&
                !usernameField.getText().equals(teachers.get(index).getUsername())){
            new Alert(Alert.AlertType.ERROR, "The username exists, please change another one").showAndWait();
            return;
        }
        else {
            if (!confirmAction("Update the selected teacher ?")) return;
            teachers.set(index,createTeacher());
            writeData();
        }
    }

}
