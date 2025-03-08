package comp3111.examsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.entity.Student;
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
 * The controller class for the student information management
 */
public class StudentManagementController implements Initializable {

    /**
     * The student information table
     */
    @FXML
    public TableView<Student> studentTable;
    @FXML
    private Label validAge;
    @FXML
    private Label fillUsername;
    @FXML
    private Label fillName;
    @FXML
    private Label fillAge;
    @FXML
    private Label fillGender;
    @FXML
    private Label fillDepartment;
    @FXML
    private Label fillPassword;
    @FXML
    private TableColumn<Student, String> usernameColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, Integer> ageColumn;
    @FXML
    private TableColumn<Student, String> genderColumn;
    @FXML
    private TableColumn<Student, String> departmentColumn;
    @FXML
    private TableColumn<Student, String> passwordColumn;

    /**
     * The button for adding a new student
     */
    @FXML
    public Button buttonAdd;

    /**
     * The button for deleting an existing student
     */

    @FXML
    public Button buttonDelete;

    /**
     * The button for filtering students
     */

    @FXML
    public Button buttonFilter;

    /**
     * The button for refreshing the page
     */

    @FXML
    public Button buttonRefresh;

    /**
     * The button for resetting the filter and the table
     */

    @FXML
    public Button buttonReset;

    /**
     * The button for updating the student information
     */

    @FXML
    public Button buttonUpdate;

    /**
     * The field for student username filter
     */

    @FXML
    public TextField usernameFilter;

    /**
     * The field for student name filter
     */

    @FXML
    public TextField nameFilter;

    /**
     * The field for student department filter
     */

    @FXML
    public TextField departmentFilter;

    /**
     * The field for student department input
     */

    @FXML
    public TextField departmentField;

    /**
     * The field for student gender input
     */

    @FXML
    public ComboBox<String> genderBox;

    /**
     * The field for student name input
     */
    @FXML
    public TextField nameField;

    /**
     * The field for student password input
     */

    @FXML
    public TextField passwordField;

    /**
     * The field for student username name input
     */

    @FXML
    public TextField usernameField;

    /**
     * The field for student age input
     */

    @FXML
    public TextField ageField;

    private static final ObjectMapper mapper = new ObjectMapper();
    /**
     * The student information retrieved from the database
     */
    public List<Student> students;
    /**
     * The student selected in the table list
     */
    public Student selectedStudent;
    /**
     * The database path
     */
    public String jsonFileLocation = "src/main/resources/database/userInfo.json";

    /**
     * The key for the UI protection
     */
    public boolean safetyKey = true;

    /**
     * For page initialization
     * @param location URL location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        genderBox.getItems().addAll("Male", "Female", "Others");
        genderBox.setValue(null);
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
            students = mapper.readValue(file, new TypeReference<List<Student>>(){});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeData(){
        File file = new File(jsonFileLocation);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, students);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        buttonRefreshPressed();
    }

    private void initializeLabel(){
        fillPassword.setVisible(false);
        fillDepartment.setVisible(false);
        fillAge.setVisible(false);
        fillName.setVisible(false);
        fillUsername.setVisible(false);
        fillGender.setVisible(false);
    }


    private void initializeTable(){
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("age"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("gender"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("department"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("password"));
        studentTable.setItems(FXCollections.observableList(students));
        studentTable.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Student>) change -> {
                    if (!change.getList().isEmpty())
                        setSelectedStudent(change.getList().getFirst());
                }
        );

    }

    private void setSelectedStudent(Student q){
        selectedStudent = q;
        usernameField.setText(selectedStudent.getUsername());
        genderBox.setValue(selectedStudent.getGender());
        nameField.setText(selectedStudent.getName());
        ageField.setText(Integer.toString(selectedStudent.getAge()));
        departmentField.setText(selectedStudent.getDepartment());
        passwordField.setText(selectedStudent.getPassword());

    }


    private static boolean isValidName(String text) {
        return Pattern.matches("[a-zA-Z0-9 ]+", text);
    }

    private static boolean isValidDepartment(String text) {
        return Pattern.matches("[a-zA-Z ]+", text);
    }


    private boolean isValidAge(String ageText) {
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
        if (nameField.getText() == null || nameField.getText().isEmpty() || !isValidName(nameField.getText())) {
            fillName.setVisible(true);
            res = false;
        }
        else {
            fillName.setVisible(false);
        }

        if (ageField.getText() == null || ageField.getText().isEmpty() || !isValidAge(ageField.getText())) {
            fillAge.setVisible(true);
            res = false;
        }
        else {
            fillAge.setVisible(false);
        }

        if (genderBox.getValue() == null) {
            fillGender.setVisible(true);
            res = false;
        }
        else {
            fillGender.setVisible(false);
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

    private boolean uniqueUsername(String username){
        for (Student student : students) {
            if (student.getUsername().equals(username)) {
                return false; // Username is not unique
            }
        }
        return true; // Username is unique
    }

    private List<Student> filterUsername(List<Student>students, String text){
        if (text == null) return students;
        ArrayList<Student> results = new ArrayList<>();
        for(Student i : students){
            if (i.getUsername().contains(text)) {
                results.add(i);
            }
        }
        return results;
    }

    private List<Student> filterName(List<Student>students, String text){
        if (text == null) return students;
        ArrayList<Student> results = new ArrayList<>();
        for(Student i : students){
            if (i.getName().contains(text)) {
                results.add(i);
            }
        }
        return results;
    }

    private List<Student> filterDepartment(List<Student>students, String text){
        if (text == null) return students;
        ArrayList<Student> results = new ArrayList<>();
        for(Student i : students){
            if (i.getDepartment().contains(text)) {
                results.add(i);
            }
        }
        return results;
    }



    @FXML
    void buttonFilterPressed() {
        List<Student> result = filterUsername(
                filterName(
                        filterDepartment(
                                students, departmentFilter.getText()
                        ), nameFilter.getText()
                ), usernameFilter.getText()
        );

        studentTable.setItems(FXCollections.observableList(result));

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
        selectedStudent = null;
        initializeLabel();
        initializeTable();
    }


    private Student createStudent(){
        return new Student(
                usernameField.getText(),
                nameField.getText(),
                Integer.parseInt(ageField.getText()),
                genderBox.getValue(),
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
        if (!confirmAction("Add the new student ?")) return;
        students.add(createStudent());
        writeData();
    }

    @FXML
    void buttonDeletePressed() {
        if (selectedStudent == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a student to delete !").showAndWait();
            return;
        }
        if (!confirmAction("Delete the selected student ?")) return;
        students.remove(selectedStudent);
        writeData();
    }

    @FXML
    void buttonUpdatePressed() {
        int index = students.indexOf(selectedStudent);
        if (selectedStudent == null){
            new Alert(Alert.AlertType.ERROR, "Please select a student to modify !").showAndWait();
            return;
        }
        else if (!validateInput()){
            return;
        }
        else if (!uniqueUsername(usernameField.getText()) &&
                !usernameField.getText().equals(students.get(index).getUsername())){
            new Alert(Alert.AlertType.ERROR, "The username exists, please change another one").showAndWait();
            return;
        }
        else {
            if (!confirmAction("Update the selected student ?")) return;
            students.set(index,createStudent());
            writeData();
        }
    }


}
