package comp3111.examsystem.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.entity.Course;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.management.MBeanAttributeInfo;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;


/**
 *The controller class for the course management
 */


public class CourseManagementController implements Initializable {


    /**
     * The table for the course information
     */
    @FXML
    public TableView<Course> courseTable;
    @FXML
    private Label fillID;
    @FXML
    private Label fillName;
    @FXML
    private Label fillDepartment;
    @FXML
    private TableColumn<Course, String> courseIDColumn;
    @FXML
    private TableColumn<Course, String> courseNameColumn;
    @FXML
    private TableColumn<Course, String> departmentColumn;
    /**
     * The button for add
     */
    @FXML
    public Button buttonAdd;
    /**
     * The button for delete
     */
    @FXML
    public Button buttonDelete;
    /**
     * The button for filter
     */
    @FXML
    public Button buttonFilter;
    /**
     * The button for reset
     */
    @FXML
    public Button buttonReset;
    /**
     * The button for refresh
     */
    @FXML
    public Button buttonRefresh;

    /**
     * The button for update
     */
    @FXML
    public Button buttonUpdate;
    /**
     * The field for course department input
     */
    @FXML
    public TextField departmentField;
    /**
     * The field for course department filter
     */
    @FXML
    public TextField departmentFilter;
    /**
     * The field for course name input
     */
    @FXML
    public TextField courseNameField;
    /**
     * The field for course name filter
     */
    @FXML
    public TextField courseNameFilter;
    /**
     * The field for course ID input
     */
    @FXML
    public TextField courseIDField;
    /**
     * The field for course ID filter
     */
    @FXML
    public TextField courseIDFilter;


    private static final ObjectMapper mapper = new ObjectMapper();
    /**
     * The courses data retrieved from the database
     */
    public List<Course> courses;
    /**
     * The course selected in the table
     */
    public Course selectedCourse;
    /**
     * The database path
     */
    public String jsonFileLocation = "src/main/resources/database/courseBank.json";

    /**
     * The key for the UI protection
     */
    public boolean safetyKey = true;

    /**
     *
     * @param location URL location
     * @param resources
     */

    public void initialize(URL location, ResourceBundle resources) {
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
            courses = mapper.readValue(file, new TypeReference<List<Course>>(){});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeData(){
        File file = new File(jsonFileLocation);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, courses);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        buttonRefreshPressed();
    }

    private void initializeLabel(){
        fillID.setVisible(false);
        fillName.setVisible(false);
        fillDepartment.setVisible(false);
    }


    private void initializeTable(){
        courseIDColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("courseID"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("courseName"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("department"));
        courseTable.setItems(FXCollections.observableList(courses));
        courseTable.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Course>) change -> {
                    if (!change.getList().isEmpty())
                        setSelectedCourse(change.getList().getFirst());
                }
        );
    }

    private void setSelectedCourse(Course q){
        selectedCourse = q;
        courseIDField.setText(selectedCourse.getCourseID());
        courseNameField.setText(selectedCourse.getCourseName());
        departmentField.setText(selectedCourse.getDepartment());
    }


    private static boolean isValidName(String text) {
        return Pattern.matches("[a-zA-Z0-9 ]+", text);
    }


    private static boolean isValidCourseID(String text) {
        return Pattern.matches("[A-Z0-9 ]+", text);
    }

    private static boolean isValidDepartment(String text) {
        return Pattern.matches("[a-zA-Z ]+", text);
    }


    private boolean validateInput() {

        boolean res = true;
        if (courseIDField.getText() == null || courseIDField.getText().isEmpty() || !isValidCourseID(courseIDField.getText())) {
            fillID.setVisible(true);
            res = false;
        }
        else {
            fillID.setVisible(false);
        }
        if (courseNameField.getText() == null || courseNameField.getText().isEmpty() || !isValidName(courseNameField.getText())) {
            fillName.setVisible(true);
            res = false;
        }
        else {
            fillName.setVisible(false);
        }
        if (departmentField.getText() == null || departmentField.getText().isEmpty() || !isValidDepartment(departmentField.getText())) {
            fillDepartment.setVisible(true);
            res = false;
        }
        else {
            fillDepartment.setVisible(false);
        }
        return res;
    }

    private boolean uniqueID(String courseID){
        for (Course course: courses) {
            if (course.getCourseID().equals(courseID)) {
                return false; // Username is not unique
            }
        }
        return true; // Username is unique
    }


    private List<Course> filterCourseID(List<Course>courses, String text){
        if (text == null) return courses;
        ArrayList<Course> results = new ArrayList<>();
        for(Course i : courses){
            if (i.getCourseID().contains(text)) {
                results.add(i);
            }
        }
        return results;
    }

    private List<Course> filterCourseName(List<Course>courses, String text){
        if (text == null) return courses;
        ArrayList<Course> results = new ArrayList<>();
        for(Course i : courses){
            if (i.getCourseName().contains(text)) {
                results.add(i);
            }
        }
        return results;
    }

    private List<Course> filterDepartment(List<Course>courses, String text){
        if (text == null) return courses;
        ArrayList<Course> results = new ArrayList<>();
        for(Course i : courses){
            if (i.getDepartment().contains(text)) {
                results.add(i);
            }
        }
        return results;
    }

    @FXML
    void buttonFilterPressed() {
        List<Course> result = filterCourseID(
                filterCourseName(
                        filterDepartment(
                                courses, departmentFilter.getText()
                        ), courseNameFilter.getText()
                ), courseIDFilter.getText()
        );
        courseTable.setItems(FXCollections.observableList(result));
    }

    private void clearFilter(){
        courseIDFilter.setText(null);
        courseNameFilter.setText(null);
        departmentFilter.setText(null);
    }

    private void clearFields(){
        courseIDField.setText(null);
        courseNameField.setText(null);
        departmentField.setText(null);
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
        selectedCourse = null;
        initializeLabel();
        initializeTable();
    }


    private Course createCourse(){
        return new Course(
                courseIDField.getText(),
                courseNameField.getText(),
                departmentField.getText()
        );
    }




    @FXML
    void buttonAddPressed() {
        if (!validateInput()){
            return;
        }
        else if (!uniqueID(courseIDField.getText())){
            new Alert(Alert.AlertType.ERROR, "The username exists, please change another one").showAndWait();
            return;
        }
        if (!confirmAction("Add the new course ?")) return;
        courses.add(createCourse());
        writeData();
    }

    @FXML
    void buttonDeletePressed() {
        if (selectedCourse == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a course to delete !").showAndWait();
            return;
        }
        else {
            if (!confirmAction("Delete the selected course ?")) return;
            courses.remove(selectedCourse);
            writeData();
        }

    }

    @FXML
    void buttonUpdatePressed() {
        int index = courses.indexOf(selectedCourse);
        if (selectedCourse == null){
            new Alert(Alert.AlertType.ERROR, "Please select a student to modify !").showAndWait();
            return;
        }
        else if (!validateInput()){
            return;
        }
        else if (!uniqueID(courseIDField.getText()) &&
                !courseIDField.getText().equals(courses.get(index).getCourseID())){
            new Alert(Alert.AlertType.ERROR, "The username exists, please change another one").showAndWait();
            return;
        }
        else {
            if (!confirmAction("Update the selected course ?")) return;
            courses.set(index,createCourse());
            writeData();
        }

    }

}

