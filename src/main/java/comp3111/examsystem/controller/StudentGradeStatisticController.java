package comp3111.examsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.ExamAttempt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * A controller for student grade statistic window.
 * @author <a href=mailto:pakninlpn@gmail.com>Marco</a>
 * @version 1.1
 */
public class StudentGradeStatisticController {

    /**
     * A javafx field for the bar chart
     */
    @FXML
    public BarChart<String, Number> gradeStatsBarChart;

    /**
     * A javafx field for bar chart's X-axis
     */
    @FXML
    public CategoryAxis gradeStatsBarChartCategoryAxis;

    /**
     * A javafx field for bar chart's Y-axis
     */
    @FXML
    public NumberAxis gradeStatsBarChartNumberAxis;

    /**
     * A javafx menu for course selection
     */
    @FXML
    public ComboBox<String> gradeStatsCourseMenuButton;

    /**
     * A javafx button for filtering courses
     */
    @FXML
    public Button gradeStatsFilterButton;

    /**
     * A javafx button for refreshing the page
     */
    @FXML
    public Button gradeStatsRefreshButton;

    /**
     * A javafx button for resetting grades
     */
    @FXML
    public Button gradeStatsResetButton;

    /**
     * A javafx field for the table
     */
    @FXML
    public TableView<ExamAttempt> gradeStatsTableView;

    /**
     * A javafx table column for courses
     */
    @FXML
    public TableColumn<ExamAttempt, String> gradeStatsTableViewCourseColumn;

    /**
     * A javafx table column for exams
     */
    @FXML
    public TableColumn<ExamAttempt, String>  gradeStatsTableViewExamColumn;

    /**
     * A javafx table column for total scores
     */
    @FXML
    public TableColumn<ExamAttempt, String>  gradeStatsTableViewFullScoreColumn;

    /**
     * A javafx table column for score attained
     */
    @FXML
    public TableColumn<ExamAttempt, String>  gradeStatsTableViewScoreColumn;

    /**
     * A javafx table column for time used in this exam attempt
     */
    @FXML
    public TableColumn<ExamAttempt, String>  gradeStatsTableViewTimeUsedColumn;

    /**
     * A javafx label to show warning related to course filtering
     */
    @FXML
    public Label selectCourseWarning;

    /**
     * A javafx label to show warning related to grades resetting
     */
    @FXML
    public Label resetWarning;

    private String studentName;
    private ObservableList<String> courseTaken = FXCollections.observableArrayList();;

    /**
     * Default constructor
     */
    public StudentGradeStatisticController(){}

    /**
     * A method to initialize this controller by setting corresponding event
     * handler to buttons, initializing the table view for
     * displaying questions, and also the bar chart.
     */
    public void initialize(){
        //System.out.println(this.studentName);
        gradeStatsResetButton.setOnAction(e -> resetCourse());
        gradeStatsFilterButton.setOnAction(e -> filterCourse());
        gradeStatsRefreshButton.setOnAction(e -> refreshPage());

        //Initalize tableview
        gradeStatsTableViewCourseColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        gradeStatsTableViewExamColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        gradeStatsTableViewScoreColumn.setCellValueFactory(new PropertyValueFactory<>("examAttemptScore"));
        gradeStatsTableViewFullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("examTotalScore"));
        gradeStatsTableViewTimeUsedColumn.setCellValueFactory(new PropertyValueFactory<>("examAttemptTimeSpend"));

        ObservableList<ExamAttempt> data = getExamPerformanceData();
        gradeStatsTableView.setItems(data);

        // Initialize BarChart
        gradeStatsBarChartCategoryAxis = new CategoryAxis();
        gradeStatsBarChartCategoryAxis.setLabel("Exam");
        gradeStatsBarChartNumberAxis = new NumberAxis();
        gradeStatsBarChartNumberAxis.setLabel("Score");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Scores");

        for (ExamAttempt exam : data) {
            //System.out.println(exam.getCourseName());
            //System.out.println(exam.getExamAttemptScore());
            series.getData().add(new XYChart.Data<>(exam.getCourseName() + "-" + exam.getExamName(), exam.getExamAttemptScore()));
        }

        gradeStatsBarChart.getData().add(series);
        gradeStatsBarChart.setLegendVisible(false);

        gradeStatsBarChart.setCategoryGap(10);

        gradeStatsCourseMenuButton.setItems(courseTaken);
        selectCourseWarning.setVisible(false);
        resetWarning.setVisible(false);

        refreshPage();
    }

    /**
     * A method to set the student name of this controller to
     * retrieve relevant data from the databases
     * @param studentName   the name of student we are handling, in form of String
     */
    public void setStudentName(String studentName){
        this.studentName = studentName;
        initialize();
    }

    /**
     * A method to check if the ExamAttempt object belongs to
     * the given student
     * @param attempt       the ExamAttempt object to be checked
     * @param studentName   the username of the student
     * @return              a boolean indicating if the ExamAttempt belongs
*                           to the given student
     */
    public static boolean checkExamAttemptStudentName(ExamAttempt attempt, String studentName){
        return attempt.getStudentName().equals(studentName);
    }

    /**
     * A method to check if the Exam has taken
     * @param examTakenTemp the list of ExamAttempt taken
     * @param attempt       the ExamAttempt to be checked
     * @return              a boolean indicating if the Exam has taken
     */
    public static boolean checkSameExam(ObservableList<ExamAttempt> examTakenTemp, ExamAttempt attempt){
        return !examTakenTemp.contains(attempt);
    }

    /**
     * A method to check if this course has taken
     * @param courseTaken   the list of Course taken
     * @param courseName    the Course to be checked
     * @return              a boolean indicating if the Course has taken
     */
    public static boolean checkSameCourse(ObservableList<String> courseTaken, String courseName){
        return !courseTaken.contains(courseName);
    }

    /**
     * A method to check if the ExamAttempt matches with given
     * student name and check if taken yet
     * @param attempt       the ExamAttempt to be checked
     * @param examTakenTemp the list of ExamAttempt taken
     * @param studentName   the student name to be checked
     * @return              a boolean indicating if the exam has been taken
     *                      and belongs to the student
     */
    public static boolean checkStudentNameAndSameExam(ExamAttempt attempt, ObservableList<ExamAttempt> examTakenTemp, String studentName){
        return attempt.getStudentName().equals(studentName) && !examTakenTemp.contains(attempt);
    }

    /**
     * A method to check if a ExamAttempt belongs to the given
     * student and from the given course
     * @param attempt       the ExamAttempt to be checked
     * @param courseTaken   the list of Course taken
     * @param studentName   the student name to be checked
     * @return              a boolean indicating if the course has been taken
     *      *               and belongs to the student
     */
    public static boolean checkStudentNameAndSameCourse(ExamAttempt attempt, ObservableList<String> courseTaken, String studentName){
        return attempt.getStudentName().equals(studentName) && !courseTaken.contains(attempt.getCourseName());
    }

    /**
     * A String of file path to exam attempt database
     */
    public static String jsonFileLocation = "src/main/resources/database/examAttempt.json";

    /**
     * A method to set the database location for storing
     * ExamAttempts
     * @param location  the location to be set
     */
    public static void setExamAttemptJsonFileLocation(String location){
        jsonFileLocation = location;
    }

    /**
     * A method to get a list of exam taken by this student
     * @return  a list of ExamAttempt indicating what exam
     *          this student has taken
     */
    public ObservableList<ExamAttempt> getExamPerformanceData() {
        //final String jsonFileLocation = "src/main/resources/database/examAttempt.json"
        ObjectMapper objectMapper = new ObjectMapper();
        File examAttemptFile = new File(jsonFileLocation);

        try {
            List<ExamAttempt> examAttemptsTemp = objectMapper.readValue(examAttemptFile, new TypeReference<List<ExamAttempt>>() {
            });
            ObservableList<ExamAttempt> examTakenTemp = FXCollections.observableArrayList();

            for (ExamAttempt attempt : examAttemptsTemp) {
                //if(checkExamAttemptStudentName(attempt, this.studentName) && checkSameExam(examTakenTemp, attempt)){
                if(checkStudentNameAndSameExam(attempt, examTakenTemp, this.studentName)){
                    examTakenTemp.add(attempt);
                }
                //if(checkExamAttemptStudentName(attempt, this.studentName) && checkSameCourse(courseTaken, attempt.getCourseName())){
                if(checkStudentNameAndSameCourse(attempt, courseTaken, this.studentName)){
                    courseTaken.add(attempt.getCourseName());
                }
            }
            return examTakenTemp;

        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * A method to check if the selected course name is null
     * @param selectedCourseName    the selected course name in String
     * @return                      a boolean indicating if the selected
     *                              course name is null
     */
    public static boolean checkSelectedCourseNmeEmpty(String selectedCourseName){
        return selectedCourseName != null;
    }

    /**
     * A method to reset the selected course in the dropdown menu
     */
    public void resetCourse(){
        String selectedCourseName = gradeStatsCourseMenuButton.getValue();
        if(checkSelectedCourseNmeEmpty(selectedCourseName)) {
            resetWarning.setVisible(false);
            selectCourseWarning.setVisible(false);

            gradeStatsCourseMenuButton.setValue(null);
            refreshPage();
        }
        else{
            resetWarning.setVisible(true);
            selectCourseWarning.setVisible(false);
        }
    }

    /**
     * A method to invoke the filter course handler
     */
    public void filterCourse(){
        filterCourseHandler();
    }

    /**
     * A method to check if the selected course name is null
     * @param selectedCourseName    the course name in String
     * @return                      a boolean indicating if the selected
     *                              course name is null
     */
    public static boolean checkSelectedCourseNmeEmpty1(String selectedCourseName){
        return selectedCourseName != null;
    }

    /**
     * A method to check is the ExamAttempt matched with the
     * selected course name
     * @param examAttempt           the ExamAttempt object to be checked
     * @param selectedCourseName    the selected course name in String
     * @return                      a boolean indicating if the ExamAttempt
     *                              is from this course
     */
    public static boolean checkSelectedCourseNameEqual(ExamAttempt examAttempt, String selectedCourseName){
        return examAttempt.getCourseName().equals(selectedCourseName);
    }

    /**
     * A method to filter the exam table and bar chart to the
     * course selected in the dropdown menu
     */
    public void filterCourseHandler(){
        String selectedCourseName = gradeStatsCourseMenuButton.getValue();
        if (checkSelectedCourseNmeEmpty1(selectedCourseName)) {
            selectCourseWarning.setVisible(false);
            resetWarning.setVisible(false);

            ObservableList<ExamAttempt> filteredExam = FXCollections.observableArrayList();
            ObservableList<ExamAttempt> examTaken = getExamPerformanceData();

            for(ExamAttempt examAttempt : examTaken){
                if(checkSelectedCourseNameEqual(examAttempt, selectedCourseName)){
                    filteredExam.add(examAttempt);
                }
                else{
                    continue;
                }
            }
            //System.out.println(filteredExam);

            updateTableAndChart(filteredExam);
        }
        else{
            selectCourseWarning.setVisible(true);
            resetWarning.setVisible(false);
        }
    }

    /**
     * A method to refresh the whole page and read new
     * exam attempt records if there are any.
     */
    public void refreshPage() {
        ObservableList<ExamAttempt> data = getExamPerformanceData();
        updateTableAndChart(data);

        data = getExamPerformanceData();
        updateTableAndChart(data);
    }

    /**
     * A method to update the exam table and bar chart
     * @param data  a list of ExamAttempt to be displayed in
     *              the table and bar chart
     */
    public void updateTableAndChart(ObservableList<ExamAttempt> data) {
        gradeStatsTableView.setItems(data);

        gradeStatsBarChart.getData().clear();

        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
        seriesBar.getData().clear();

        seriesBar.setName("Scores");

        for (ExamAttempt exam : data) {
            seriesBar.getData().add(new XYChart.Data<>(exam.getCourseName() + "-" + exam.getExamName(), exam.getExamAttemptScore()));
        }

        gradeStatsBarChart.getData().add(seriesBar);

        gradeStatsBarChart.setCategoryGap(10);

    }
}
