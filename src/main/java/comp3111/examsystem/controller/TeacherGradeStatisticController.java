package comp3111.examsystem.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.entity.ExamAttempt;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.File;
import java.net.URL;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.util.Pair;

/**
 * The controller class for teacher grade statistic
 */
public class TeacherGradeStatisticController implements Initializable {


    /**
     * The field for course ID filter
     */

    @FXML
    public ChoiceBox<String> courseCombox;

    /**
     * The field for exam name filter
     */
    @FXML
    public ChoiceBox<String> examCombox;

    /**
     * The field for student name filter
     */
    @FXML
    public ChoiceBox<String> studentCombox;
    @FXML
    private TableView<ExamAttempt> gradeTable;
    @FXML
    private TableColumn<ExamAttempt, String> studentColumn;
    @FXML
    private TableColumn<ExamAttempt, String> courseColumn;
    @FXML
    private TableColumn<ExamAttempt, String> examColumn;
    @FXML
    private TableColumn<ExamAttempt, Integer> scoreColumn;
    @FXML
    private TableColumn<ExamAttempt, Integer> fullScoreColumn;
    @FXML
    private TableColumn<ExamAttempt, Integer> timeSpendColumn;
    /**
     * Button for resetting the filter and the table
     */
    public Button resetBtn;
    /**
     * Button for filtering the grade table
     */
    public Button filterBtn;

    @FXML
    BarChart<String, Number> barChart;
    @FXML
    CategoryAxis categoryAxisBar;
    @FXML
    NumberAxis numberAxisBar;
    @FXML
    LineChart<String, Number> lineChart;
    @FXML
    CategoryAxis categoryAxisLine;
    @FXML
    NumberAxis numberAxisLine;
    @FXML
    PieChart pieChart;

    private static final ObjectMapper mapper = new ObjectMapper();
    /**
     * The grade information retrieved from the database
     */
    public List<ExamAttempt> examAttempts;
    /**
     * The current grade information shown in the table
     */
    public List<ExamAttempt> gradeList;
    /**
     * The database path
     */
    public String jsonFileLocation = "src/main/resources/database/examAttempt.json";

    /**
     * For the page initialization
     * @param url: URL location
     * @param resourceBundle:
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        barChart.setLegendVisible(false);
        categoryAxisBar.setLabel("Course");
        numberAxisBar.setLabel("Avg. Score");
        pieChart.setLegendVisible(false);
        pieChart.setTitle("Student Scores");
        lineChart.setLegendVisible(false);
        categoryAxisLine.setLabel("Exam");
        numberAxisLine.setLabel("Avg. Score");
        refresh();
    }

    private void loadData(){
        try {
            File file = new File(jsonFileLocation);
            examAttempts = mapper.readValue(file, new TypeReference<List<ExamAttempt>>(){});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void initializeCourseFilterOptions(){
        List<String> courseOptions = new ArrayList<>();
        courseOptions.add(null);
        courseOptions.addAll(examAttempts.stream().map(ExamAttempt::getCourseName).distinct().sorted().toList());
        courseCombox.setItems(FXCollections.observableList(courseOptions));
        courseCombox.setValue(null);
    }

    private void initializeExamFilterOptions(){
        List<String> examOptions = new ArrayList<>();
        examOptions.add(null);
        examOptions.addAll(examAttempts.stream().map(ExamAttempt::getExamName).distinct().sorted().toList());
        examCombox.setItems(FXCollections.observableList(examOptions));
        examCombox.setValue(null);
    }

    private void initializeStudentFilterOptions(){
        List<String> studentOptions = new ArrayList<>();
        studentOptions.add(null);
        studentOptions.addAll(examAttempts.stream().map(ExamAttempt::getStudentName).distinct().sorted().toList());
        studentCombox.setItems(FXCollections.observableList(studentOptions));
        studentCombox.setValue(null);
    }

    private void initializeFilter(){
        initializeCourseFilterOptions();
        initializeExamFilterOptions();
        initializeStudentFilterOptions();
    }



    private void initializeExamAttemptTable(){
        studentColumn.setCellValueFactory(new PropertyValueFactory<ExamAttempt, String>("studentName"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<ExamAttempt, String>("courseName"));
        examColumn.setCellValueFactory(new PropertyValueFactory<ExamAttempt, String>("examName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<ExamAttempt, Integer>("ExamAttemptScore"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<ExamAttempt, Integer>("examTotalScore"));
        timeSpendColumn.setCellValueFactory(new PropertyValueFactory<ExamAttempt, Integer>("examAttemptTimeSpend"));
        gradeTable.setItems(FXCollections.observableList(examAttempts));
        gradeList = examAttempts;
    }






    @FXML
    void query() {
        gradeList = filterCourse(
                filterExam(
                        filterStudent(
                                examAttempts, studentCombox.getValue()
                        ), examCombox.getValue()
                ), courseCombox.getValue()
        );
        gradeTable.setItems(FXCollections.observableList(gradeList));
        loadChart();
    }




    private List<ExamAttempt> filterCourse(List<ExamAttempt>examAttempts, String text){
        if (text == null){
            return examAttempts;
        }
        ArrayList<ExamAttempt> results = new ArrayList<>();
        for(ExamAttempt i : examAttempts){
            if (Objects.equals(i.getCourseName(), text)) {
                results.add(i);
            }
        }
        return results;
    }

    private List<ExamAttempt> filterExam(List<ExamAttempt>examAttempts, String text){
        if (text == null){
            return examAttempts;
        }
        ArrayList<ExamAttempt> results = new ArrayList<>();
        for(ExamAttempt i : examAttempts){
            if (Objects.equals(i.getExamName(), text)) {
                results.add(i);
            }
        }
        return results;
    }

    private List<ExamAttempt> filterStudent(List<ExamAttempt>examAttempts, String text){
        if (text == null) return examAttempts;
        ArrayList<ExamAttempt> results = new ArrayList<>();
        for(ExamAttempt i : examAttempts){
            if (Objects.equals(i.getStudentName(), text)) {
                results.add(i);
            }
        }
        return results;
    }




    @FXML
    void reset() {
        initializeFilter();
        query();
    }


    @FXML
    void refresh() {
        loadData();
        initializeFilter();
        initializeExamAttemptTable();
        loadChart();
    }

    private void loadChart() {
        loadBarChart();
        loadLineChart();
        loadPieChart();
    }

    private void loadPieChart(){
        pieChart.getData().clear();
        Map<String, Integer> scoreMap = new HashMap<>();
        for (ExamAttempt attempt : gradeList) {
            scoreMap.put(attempt.getStudentName(),
                    scoreMap.getOrDefault(attempt.getStudentName(), 0) + attempt.getExamAttemptScore());
        }
        for (Map.Entry<String, Integer> entry : scoreMap.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private void loadBarChart(){
        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
        seriesBar.getData().clear();
        barChart.getData().clear();
        Map<String, List<Integer>> scoresByCourse = new HashMap<>();
        for (ExamAttempt attempt : gradeList) {
            scoresByCourse.computeIfAbsent(attempt.getCourseName(),
                    k -> new ArrayList<>()).add(attempt.getExamAttemptScore());
        }
        for (Map.Entry<String, List<Integer>> entry : scoresByCourse.entrySet()) {
            String courseName = entry.getKey();
            List<Integer> scores = entry.getValue();
            double averageScore = scores.stream().mapToInt(Integer::intValue).average().orElse(0);
            seriesBar.getData().add(new XYChart.Data<String, Number>(courseName, averageScore));
        }
        barChart.setAnimated(false);
        barChart.getData().add(seriesBar);
    }

    private void loadLineChart(){
        XYChart.Series<String, Number> seriesLine = new XYChart.Series<>();
        seriesLine.getData().clear();
        lineChart.getData().clear();
        Map<Pair<String, String>, List<Integer>> scoresByExam = new HashMap<>();
        for (ExamAttempt attempt : gradeList) {
            Pair<String, String> key = new Pair<>(attempt.getCourseName(), attempt.getExamName());
            scoresByExam.computeIfAbsent(key,
                    k -> new ArrayList<>()).add(attempt.getExamAttemptScore());
        }
        for (Map.Entry<Pair<String, String>, List<Integer>> entry : scoresByExam.entrySet()) {
            Pair<String, String> examName = entry.getKey();
            String catName = examName.getKey() + " " + examName.getValue();
            List<Integer> scores = entry.getValue();
            double averageScore = scores.stream().mapToInt(Integer::intValue).average().orElse(0);
            XYChart.Data<String, Number> a = new XYChart.Data<String, Number>(catName, averageScore);
            seriesLine.getData().add(a);
        }
        lineChart.setAnimated(false);
        lineChart.getData().add(seriesLine);
    }
}
