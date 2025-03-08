package comp3111.examsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * The controller for TeacherQuestionManagement.fxml
 */
public class TeacherQuestionManagementController implements Initializable {
    @FXML
    private TextField questionField;
    @FXML
    private ChoiceBox<String> typeField;
    @FXML
    private TextField optionAField;
    @FXML
    private TextField optionBField;
    @FXML
    private TextField optionCField;
    @FXML
    private TextField optionDField;
    @FXML
    private TextField scoreField;
    @FXML
    private Button addBtn;
    @FXML
    private Button UpdateBtn;
    @FXML
    private Button DeleteBtn;
    @FXML
    private Button refreshBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private TextField questionFilter;
    @FXML
    private ChoiceBox<String> typeFilter;
    @FXML
    private ChoiceBox<Integer> scoreFilter;
    @FXML
    private Button resetBtn;
    @FXML
    private Button filterBtn;
    @FXML
    private TableView<Question> questionTable;
    @FXML
    private TableColumn<Question,String> questionCol;
    @FXML
    private TableColumn<Question,String> typeCol;
    @FXML
    private TableColumn<Question,String> optionACol;
    @FXML
    private TableColumn<Question,String> optionBCol;
    @FXML
    private TableColumn<Question,String> optionCCol;
    @FXML
    private TableColumn<Question,String> optionDCol;
    @FXML
    private TableColumn<Question,List<Character>> answerCol;
    @FXML
    private TableColumn<Question,Integer> scoreCol;
    @FXML
    private final ToggleGroup answerFieldGroup = new ToggleGroup();
    @FXML
    private RadioButton checkboxA;
    @FXML
    private RadioButton checkboxB;
    @FXML
    private RadioButton checkboxC;
    @FXML
    private RadioButton checkboxD;
    @FXML
    private Label questionWarning;
    @FXML
    private Label optionWarning;
    @FXML
    private Label answerWarning;
    @FXML
    private Label scoreWarning;

    // Non-FXML variable
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * A list of {@link Question} that holds the Questions loaded from the database
     */
    public List<Question> questions;
    /**
     * A list of {@link RadioButton} for Answer input in the scene
     */
    public List<RadioButton> answerField;
    /**
     * The {@link Question} being edited
     */
    public Question activeQuestion = null;
    /**
     * The flag variable that determines if confirmation screen and alert screen will pop up
     */
    public boolean disableAlert = false;
    /**
     * The JSON file location of the list of questions
     */
    public String jsonFileLocation = "src/main/resources/database/questionBank.json";


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
        initializeTypeOptions();
        initializeAnswerFieldGroup();

        initializeTable();

        refresh();
    }


    private void initializeTypeOptions(){
        typeField.getItems().addAll("Single", "Multiple");
        typeField.setValue("Single");
        typeFilter.getItems().addAll(null,"Single", "Multiple");
        typeFilter.setValue(null);
    }
    private void initializeAnswerFieldGroup(){
        answerField = Arrays.asList(checkboxA, checkboxB, checkboxC, checkboxD);
        refreshAnswerField();
    }
    private void initializeWarningLabels(){
        questionWarning.setVisible(false);
        optionWarning.setVisible(false);
        answerWarning.setVisible(false);
        scoreWarning.setVisible(false);
    }
    private void initializeTable(){
        questionCol.setCellValueFactory(new PropertyValueFactory<Question, String>("questionName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Question, String>("questionType"));
        optionACol.setCellValueFactory(new PropertyValueFactory<Question, String>("optionA"));
        optionBCol.setCellValueFactory(new PropertyValueFactory<Question, String>("optionB"));
        optionCCol.setCellValueFactory(new PropertyValueFactory<Question, String>("optionC"));
        optionDCol.setCellValueFactory(new PropertyValueFactory<Question, String>("optionD"));
        answerCol.setCellValueFactory(new PropertyValueFactory<Question, List<Character>>("answer"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<Question, Integer>("score"));

        questionTable.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Question>) change -> {
                    if (!change.getList().isEmpty())
                        setActiveQuestion(change.getList().getFirst());
                }
        );
    }

    private void setActiveQuestion(Question q){
        if (q == null){
            clearField();
        }
        else{
            activeQuestion = q;
            questionField.setText(activeQuestion.getQuestionName());
            typeField.setValue(activeQuestion.getQuestionType());
            optionAField.setText(activeQuestion.getOptionA());
            optionBField.setText(activeQuestion.getOptionB());
            optionCField.setText(activeQuestion.getOptionC());
            optionDField.setText(activeQuestion.getOptionD());
            answerField.forEach(option->{
                option.setSelected(activeQuestion.getAnswer().contains(option.getText().charAt(0)));
            });
            scoreField.setText(Integer.toString(activeQuestion.getScore()));
        }
    }

    @FXML
    private void refreshAnswerField(){
        String type = typeField.getValue();
        if(type.equals("Single")){
            checkboxA.setToggleGroup(answerFieldGroup);
            checkboxB.setToggleGroup(answerFieldGroup);
            checkboxC.setToggleGroup(answerFieldGroup);
            checkboxD.setToggleGroup(answerFieldGroup);
        }
        else {
            checkboxA.setToggleGroup(null);
            checkboxB.setToggleGroup(null);
            checkboxC.setToggleGroup(null);
            checkboxD.setToggleGroup(null);
        }
    }
    private void refreshScoreFilterOptions(){
        List<Integer> scoreOptions = new ArrayList<>();
        scoreOptions.add(null);
        scoreOptions.addAll(questions.stream().map(Question::getScore).distinct().sorted().toList());
        scoreFilter.setItems(FXCollections.observableList(scoreOptions));
        scoreFilter.setValue(null);
    }


    @FXML
    private void postQuestion(){
        if (!validateField()) return;

        if (!confirmAction("Add Question?")) return;

        questions.add(createQuestion());
        setActiveQuestion(null);
        writeToDatabase();
        System.out.println("Question Posted");
    }
    @FXML
    private void putQuestion(){
        if (!validateIfActiveQuestionExists("update")) return;
        if (!validateField()) return;

        if (!confirmAction("Update Question?")) return;

        questions.set(questions.indexOf(activeQuestion),createQuestion());
        setActiveQuestion(null);
        writeToDatabase();
        System.out.println("Question Updated");
    }
    @FXML
    private void deleteQuestion() {
        if (!validateIfActiveQuestionExists("delete")) return;

        if (!confirmAction("Delete Question?")) return;

        questions.remove(activeQuestion);
        setActiveQuestion(null);
        writeToDatabase();
        System.out.println("Question Deleted");
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
        } else {
            // User close the panel without pressing any button
            return false;
        }
    }

    private boolean validateField(){
        initializeWarningLabels();
        boolean validated = true;
        if (questionField.getText() == null || questionField.getText().isEmpty()) {
            if (!disableAlert) new Alert(Alert.AlertType.ERROR, "Please fill in every blank!").showAndWait();
            setWarningLabel(questionWarning, "This Field cannot be Empty");
            validated = false;
        }
        if (optionAField.getText().isEmpty() || optionBField.getText().isEmpty()
         || optionCField.getText().isEmpty() || optionDField.getText().isEmpty()) {
            if (!disableAlert && validated) new Alert(Alert.AlertType.ERROR, "Please fill in every blank!").showAndWait();
            setWarningLabel(optionWarning, "At least 1 option should be chosen");
            validated = false;
        }
        if (getAnswerFieldValue().isEmpty()) {
            if (!disableAlert && validated) new Alert(Alert.AlertType.ERROR, "Please fill in every blank!").showAndWait();
            setWarningLabel(answerWarning, "Please choose an Option");
            validated = false;
        }
        if (scoreField.getText().isEmpty()) {
            if (!disableAlert && validated) new Alert(Alert.AlertType.ERROR, "Please fill in every blank!").showAndWait();
            setWarningLabel(scoreWarning, "This Field cannot be Empty");
            validated = false;
        } else if (!scoreField.getText().matches("-?\\d+") || Integer.parseInt(scoreField.getText()) < 0){
            if (!disableAlert && validated) new Alert(Alert.AlertType.ERROR, "Please check your Input again").showAndWait();
            setWarningLabel(scoreWarning, "Please Enter a positive Integer");
            validated = false;
        }
        return validated;
    }
    private void setWarningLabel(Label label, String warning){
        label.setText(warning);
        label.setVisible(true);
    }
    private List<Character> getAnswerFieldValue(){
        return answerField.stream().map(option->{
            if (!option.isSelected()) return null;
            else return option.getText().charAt(0);
        }).filter(Objects::nonNull).toList();
    }
    private Question createQuestion(){
        return new Question(
                questionField.getText(),
                typeField.getValue(),
                optionAField.getText(),
                optionBField.getText(),
                optionCField.getText(),
                optionDField.getText(),
                getAnswerFieldValue(),
                Integer.parseInt(scoreField.getText())
        );
    }
    private boolean validateIfActiveQuestionExists(String action){
        if (activeQuestion == null) {
            if (!disableAlert) new Alert(Alert.AlertType.ERROR, "Please select a question to " + action + "!").showAndWait();
            return false;
        }
        return true;
    }


    @FXML
    private void reset() {
        questionFilter.clear();
        typeFilter.setValue(null);
        scoreFilter.setValue(null);
        query();
    }

    @FXML
    private void query() {
        List<Question> result = filterQuestionName(
                filterScore(
                        filterType(
                                questions, typeFilter.getValue()
                        ), scoreFilter.getValue()
                ), questionFilter.getText());
        questionTable.setItems(FXCollections.observableList(result));
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
        activeQuestion = null;
        questionField.setText(null);
        typeField.setValue("Single");
        optionAField.setText(null);
        optionBField.setText(null);
        optionCField.setText(null);
        optionDField.setText(null);
        answerField.forEach(option->{
            option.setSelected(false);
        });
        scoreField.setText(null);
    }

    @FXML
    private void refresh() {
        // Reload file content
        try {
            File file = new File(jsonFileLocation);
            questions = mapper.readValue(file, new TypeReference<List<Question>>(){});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Refresh filter Fields
        initializeWarningLabels();
        refreshScoreFilterOptions();

        // Query
        query();
    }

    private void writeToDatabase(){
        File file = new File(jsonFileLocation);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, questions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refresh();
    }

}
