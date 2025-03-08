package comp3111.examsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.entity.Course;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Question;
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
import java.util.stream.Collectors;

/**
 * The controller for TeacherQuestionManagement.fxml
 */
public class TeacherExamManagementController implements Initializable {
    @FXML
    private TextField examField;
    @FXML
    private TextField timeField;
    @FXML
    private ChoiceBox<String> courseField;
    @FXML
    private CheckBox publishCheckbox;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button refreshBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private TextField examFilter;
    @FXML
    private ChoiceBox<String> courseFilter;
    @FXML
    private ChoiceBox<Boolean> publishFilter;
    @FXML
    private TableView<Exam> examTable;
    @FXML
    private TableColumn<Exam,String> examCol;
    @FXML
    private TableColumn<Exam,String> courseCol;
    @FXML
    private TableColumn<Exam,Integer> examTimeCol;
    @FXML
    private TableColumn<Exam,Boolean> publishCol;
    @FXML
    private TextField questionFilter;
    @FXML
    private ChoiceBox<String> typeFilter;
    @FXML
    private ChoiceBox<Integer> scoreFilter;
    @FXML
    private Button resetQuestionBtn;
    @FXML
    private Button filterQuestionBtn;
    @FXML
    private TableView<Question> examManagementTable;
    @FXML
    private TableColumn<Question,String> examQuestionCol;
    @FXML
    private TableColumn<Question,String> examQTypeCol;
    @FXML
    private TableColumn<Question,Integer> examQScoreCol;
    @FXML
    private TableView<Question> questionTable;
    @FXML
    private TableColumn<Question,String> qCol;
    @FXML
    private TableColumn<Question,String> qTypeCol;
    @FXML
    private TableColumn<Question,Integer> qScoreCol;
    @FXML
    private Button removeQBtn;
    @FXML
    private Button addQBtn;

    @FXML
    private Button resetExamBtn;
    @FXML
    private Button filterExamBtn;

    @FXML
    private Label examWarning;
    @FXML
    private Label timeWarning;
    @FXML
    private Label courseWarning;
    @FXML
    private Label publishWarning;

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * A list of {@link Exam} that holds the Exams loaded from the database
     */
    public List<Exam> exams;
    /**
     * A list of {@link Question} that temporary holds the questions of the exam being edited
     */
    public List<Question> examQuestions = new ArrayList<>();
    /**
     * A list of {@link Question} that holds the Questions loaded from the database
     */
    public List<Question> questions;
    /**
     * A list of {@link Course} that holds the Courses loaded from the database
     */
    public List<Course> courses;

    /**
     * The {@link Exam} being edited
     */
    public Exam activeExam = null;
    /**
     * The {@link Question} chosen from the Question Table of the exam being edited
     */
    public Question activeListedQ = null;
    /**
     * The {@link Question} chosen from the Question Table of the database
     */
    public Question activeQuestion = null;

    /**
     * The JSON file location of the list of questions
     */
    public String questionBankLocation = "src/main/resources/database/questionBank.json";
    /**
     * The JSON file location of the list of exams
     */
    public String examBankLocation = "src/main/resources/database/examBank.json";
    /**
     * The JSON file location of the list of courses
     */
    public String courseBankLocation = "src/main/resources/database/courseBank.json";
    /**
     * The flag variable that determines if confirmation screen and alert screen will pop up
     */
    public boolean disableAlert;

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
        initializePublishedFilterOptions();
        initializeTypeFilterOptions();

        initializeExamTable();
        initializeExamManagementTable();
        initializeQuestionTable();

        refresh();
    }

    private void initializePublishedFilterOptions(){
        publishFilter.getItems().addAll(null,true,false);
    }
    private void initializeTypeFilterOptions(){
        typeFilter.getItems().addAll(null,"Single", "Multiple");
    }

    private void initializeExamTable(){
        examCol.setCellValueFactory(new PropertyValueFactory<Exam, String>("examName"));
        courseCol.setCellValueFactory(new PropertyValueFactory<Exam, String>("course"));
        examTimeCol.setCellValueFactory(new PropertyValueFactory<Exam, Integer>("timeLimit"));
        publishCol.setCellValueFactory(new PropertyValueFactory<Exam, Boolean>("published"));

        examTable.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Exam>) change -> {
                    if (!change.getList().isEmpty())
                        setActiveExam(change.getList().getFirst());
                }
        );
    }
    private void initializeExamManagementTable(){
        examQuestionCol.setCellValueFactory(new PropertyValueFactory<Question, String>("questionName"));
        examQTypeCol.setCellValueFactory(new PropertyValueFactory<Question, String>("questionType"));
        examQScoreCol.setCellValueFactory(new PropertyValueFactory<Question, Integer>("score"));

        examManagementTable.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Question>) change -> {
                    if (!change.getList().isEmpty())
                        activeListedQ = change.getList().getFirst();
                }
        );
    }
    private void initializeWarningLabels(){
        examWarning.setVisible(false);
        courseWarning.setVisible(false);
        timeWarning.setVisible(false);
    }
    private void initializeQuestionTable(){
        qCol.setCellValueFactory(new PropertyValueFactory<Question, String>("questionName"));
        qTypeCol.setCellValueFactory(new PropertyValueFactory<Question, String>("questionType"));
        qScoreCol.setCellValueFactory(new PropertyValueFactory<Question, Integer>("score"));

        questionTable.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Question>) change -> {
                    if (!change.getList().isEmpty())
                        activeQuestion = change.getList().getFirst();
                }
        );
    }

    private void refreshCourseOptions(){
        List<String> courseOptions = courses.stream().map(Course::getCourseID).collect(Collectors.toList());
        courseOptions.addFirst(null);
        // Course Field
        courseField.setItems(FXCollections.observableList(courseOptions));
        courseField.setValue(null);

        // Course Filter
        courseFilter.setItems(FXCollections.observableList(courseOptions));
        courseFilter.setValue(null);
    }
    private void refreshScoreFilterOptions(){
        List<Integer> scoreOptions = new ArrayList<>();
        scoreOptions.add(null);
        scoreOptions.addAll(questions.stream().map(Question::getScore).distinct().sorted().toList());
        scoreFilter.setItems(FXCollections.observableList(scoreOptions));
        scoreFilter.setValue(null);
    }
    private void refreshQuestionTable(){
        List<Question> questionQueryResult = filterQuestionName(
                filterScore(
                        filterType(
                                questions, typeFilter.getValue()
                        ), scoreFilter.getValue()
                ), questionFilter.getText());
        questionTable.setItems(FXCollections.observableList(questionQueryResult.stream().filter((q)->
                !examQuestions.contains(q)
        ).toList()));
    }

    private void setActiveExam(Exam e) {
        if (e == null) {
            clearField();
        } else {
            activeExam = e;
            examField.setText(e.getExamName());
            courseField.setValue(e.getCourse());
            timeField.setText(Integer.toString(e.getTimeLimit()));
            publishCheckbox.setSelected(e.getPublished());
            examQuestions = e.getExamQuestions();
            examManagementTable.setItems(FXCollections.observableList(examQuestions));
            refreshQuestionTable();
        }
    }

    @FXML
    private void postExam(){
        if (!validateField()) return;

        if (!confirmAction("Add exam?")) return;

        exams.add(createExam());
        setActiveExam(null);
        writeToDatabase();
    }
    @FXML
    private void putExam(){
        if (!validateIfActiveExamExists("update")) return;
        if (!validatePublished()) return;
        if (!validateField()) return;

        if (!confirmAction("Update Exam?")) return;

        exams.set(exams.indexOf(activeExam),createExam());
        setActiveExam(null);
        writeToDatabase();
    }
    @FXML
    private void deleteExam(){
        if (!validateIfActiveExamExists("delete")) return;
        if (!validatePublished()) return;

        if (!confirmAction("Delete Exam?")) return;

        exams.remove(activeExam);
        setActiveExam(null);
        writeToDatabase();
    }
    private boolean confirmAction(String action){
        if (disableAlert) return true;
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(action);
        final Optional<ButtonType> opt = alert.showAndWait();
        if (opt.get() == null) return false;
        final ButtonType rtn = opt.get();
        if (rtn == ButtonType.OK) {
            return true;
        } else{
            // User close the panel without pressing any button
            return false;
        }
    }

    private boolean validateField(){
        initializeWarningLabels();
        boolean validated = true;
        if (examField.getText() == null || examField.getText().isEmpty()) {
            if (!disableAlert) new Alert(Alert.AlertType.ERROR, "Please fill in every blank!").showAndWait();
            setWarningLabel(examWarning, "Field cannot be empty");
            validated = false;
        }
        if (courseField.getValue() == null || courseField.getValue().isEmpty()) {
            if (!disableAlert && validated) new Alert(Alert.AlertType.ERROR, "Please fill in every blank!").showAndWait();
            setWarningLabel(courseWarning, "Please choose a course");
            validated = false;
        }
        if (timeField.getText() == null || timeField.getText().isEmpty()) {
            if (!disableAlert && validated) new Alert(Alert.AlertType.ERROR, "Please fill in every blank!").showAndWait();
            setWarningLabel(timeWarning, "Field cannot be empty");
            validated = false;
        } else if (!timeField.getText().matches("-?\\d+") || Integer.parseInt(timeField.getText()) <= 0) {
            if (!disableAlert && validated) new Alert(Alert.AlertType.ERROR, "Please check your Input again").showAndWait();
            setWarningLabel(timeWarning, "Please enter a positive Integer");
            validated = false;
        }
        if (publishCheckbox.isSelected() && examQuestions.isEmpty()) {
            if (!disableAlert && validated) new Alert(Alert.AlertType.ERROR, "Cannot publish Exams with no Question").showAndWait();
            setWarningLabel(publishWarning, "Please publish with at least 1 question");
            validated = false;
        }
        return validated;
    }
    private void setWarningLabel(Label label, String warning){
        label.setText(warning);
        label.setVisible(true);
    }
    private boolean validatePublished(){
        if (activeExam.getPublished()) {
            if (!disableAlert) new Alert(Alert.AlertType.ERROR, "Published exam is not modifiable!").showAndWait();
            return false;
        }
        return true;
    }
    private Exam createExam(){
        return new Exam(
                examField.getText(),
                courseField.getValue(),
                Integer.parseInt(timeField.getText()),
                publishCheckbox.isSelected(),
                examQuestions
        );
    }
    private boolean validateIfActiveExamExists(String action){
        if (activeExam == null) {
            if (!disableAlert) new Alert(Alert.AlertType.ERROR, "Please select a exam to " + action + "!").showAndWait();
            return false;
        }
        return true;
    }
    private boolean validateIfActiveListedQExists(){
        if (activeListedQ == null) {
            if (!disableAlert) new Alert(Alert.AlertType.ERROR, "Please select a question to remove from the exam!").showAndWait();
            return false;
        }
        return true;
    }
    private boolean validateIfActiveQuestionExists(){
        if (activeQuestion == null) {
            if (!disableAlert) new Alert(Alert.AlertType.ERROR, "Please select a question to add to the exam!").showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    private void addQuestion(){
        if (!validateIfActiveQuestionExists()) return;
        examQuestions.add(activeQuestion);
        activeQuestion = null;
        examManagementTable.setItems(FXCollections.observableList(examQuestions));
        refreshQuestionTable();
    }
    @FXML
    private void removeQuestion(){
        if (!validateIfActiveListedQExists()) return;
        examQuestions.remove(activeListedQ);
        activeListedQ = null;
        examManagementTable.setItems(FXCollections.observableList(examQuestions));
        refreshQuestionTable();
    }

    @FXML
    private void reset() {
        examFilter.clear();
        courseFilter.setValue(null);
        publishFilter.setValue(null);

        questionFilter.clear();
        typeFilter.setValue(null);
        scoreFilter.setValue(null);
        query();
    }

    @FXML
    private void query() {
        List<Exam> examQueryResult = filterExamName(
                filterCourse(
                        filterPublished(
                                exams, publishFilter.getValue()
                        ), courseFilter.getValue()
                ), examFilter.getText());
        examTable.setItems(FXCollections.observableList(examQueryResult));
        refreshQuestionTable();
    }
    private List<Exam> filterExamName(List<Exam>exams, String text){
        if (text == null) return exams;
        ArrayList<Exam> results = new ArrayList<>();
        for(Exam i : exams){
            if (!i.getExamName().contains(text)) continue;
            results.add(i);
        }
        return results;
    }
    private List<Exam> filterCourse(List<Exam>exams, String text){
        if (text == null) return exams;
        ArrayList<Exam> results = new ArrayList<>();
        for(Exam i : exams){
            if (!i.getCourse().equals(text)) continue;
            results.add(i);
        }
        return results;
    }
    private List<Exam> filterPublished(List<Exam>exams, Boolean published){
        if (published == null) return exams;
        ArrayList<Exam> results = new ArrayList<>();
        for(Exam i : exams) {
            if (i.getPublished() != published) continue;
            results.add(i);
        }
        return results;
    }
    private List<Question> filterQuestionName(List<Question>questions, String text){
        ArrayList<Question> results = new ArrayList<>();
        for(Question i : questions){
            if (!i.getQuestionName().contains(text)) continue;
            results.add(i);
        }
        return results;
    }
    private List<Question> filterType(List<Question>questions, String text){
        if (text == null) return questions;
        ArrayList<Question> results = new ArrayList<>();
        for(Question i : questions){
            if (!i.getQuestionType().equals(text)) continue;
            results.add(i);
        }
        return results;
    }
    private List<Question> filterScore(List<Question>questions, Integer score){
        if (score == null) return questions;
        ArrayList<Question> results = new ArrayList<>();
        for(Question i : questions) {
            if (i.getScore() != score) continue;
            results.add(i);
        }
        return results;
    }

    @FXML
    private void clear(){
        if (!confirmAction("Clear Fields?")) return;
        clearField();
    }
    private void clearField(){
        activeExam = null;
        examField.setText(null);
        courseField.setValue(null);
        timeField.setText(null);
        publishCheckbox.setSelected(false);
        examQuestions = new ArrayList<>();
        examManagementTable.setItems(FXCollections.observableList(new ArrayList<>()));
    }

    @FXML
    private void refresh(){
        // Reload file content
        File examFile = new File(examBankLocation);
        File questionFile = new File(questionBankLocation);
        File courseFile = new File(courseBankLocation);
        try{
            exams = mapper.readValue(examFile, new TypeReference<List<Exam>>(){});
            questions = mapper.readValue(questionFile, new TypeReference<List<Question>>(){});
            courses = mapper.readValue(courseFile, new TypeReference<List<Course>>(){});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        //  Refresh filter Fields
        initializeWarningLabels();
        refreshCourseOptions();
        refreshScoreFilterOptions();

        if (activeExam != null) {
            examQuestions = activeExam.getExamQuestions();
            examManagementTable.setItems(FXCollections.observableList(examQuestions));
        }
        else {
            examManagementTable.setItems(FXCollections.observableList(new ArrayList<>()));
        }

        // Query
        query();
    }

    private void writeToDatabase(){
        File examFile = new File(examBankLocation);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(examFile, exams);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refresh();
    }

    @FXML
    private void exit() {
        System.exit(0);
    }
}
