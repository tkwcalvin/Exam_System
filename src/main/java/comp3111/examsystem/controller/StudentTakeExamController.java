package comp3111.examsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.entity.ExamAttempt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A controller to handle taking exam process
 * @author <a href=mailto:pakninlpn@gmail.com>Marco</a>
 * @version 1.5
 */
public class StudentTakeExamController {

    /**
     * A javafx label to display course name
     */
    @FXML
    public Label courseName;

    /**
     * A javafx label to display exam name
     */
    @FXML
    public Label examName;

    /**
     * A javafx button to handle moving to
     * next question event
     */
    @FXML
    public Button examNextButton;

    /**
     * A javafx button to handle moving to
     * next question event
     */
    @FXML
    public Button examNextButton_1;

    /**
     * A javafx button to handle moving to
     * previous question event
     */
    @FXML
    public Button examPrevButton;

    /**
     * A javafx button to handle exam submission
     * event
     */
    @FXML
    public Button examSubmitButton;

    /**
     * A javafx radio button to select optionA
     */
    @FXML
    public RadioButton examQuestionChoiceA;

    /**
     * A javafx radio button to select optionB
     */
    @FXML
    public RadioButton examQuestionChoiceB;

    /**
     * A javafx radio button to select optionC
     */
    @FXML
    public RadioButton examQuestionChoiceC;

    /**
     * A javafx radio button to select optionD
     */
    @FXML
    public RadioButton examQuestionChoiceD;

    /**
     * A javafx label to display current question content
     */
    @FXML
    public Label examQuestionContent;

    /**
     * A javafx scrollable pane to select questions
     */
    @FXML
    public ScrollPane examQuestionScrollPane;

    /**
     * A javafx pane to hold the scrollable pane
     */
    @FXML
    public AnchorPane examQuestionScrollPane_Pane;

    /**
     * A javafx label to display index of current
     * question attempting
     */
    @FXML
    public Label examQuestionNumber;

    /**
     * A javafx label to display time remained for
     * this exam
     */
    @FXML
    public Label examRemainTime;

    /**
     * A javafx label to display total number of question
     * in this exam
     */
    @FXML
    public Label examTotalQuestion;

    /**
     * A javafx button to handle canceling exam submission
     * event
     */
    @FXML
    public Button examSubmitCancelButton;

    /**
     * A javafx button to handle exam submission event
     * during the exam attempt (when still have time left)
     */
    @FXML
    public Button examSubmitConfirmButton;

    /**
     * A javafx button to handle exam submission event
     * when time is up
     */
    @FXML
    public Button examSubmitOkButton;

    /**
     * A javafx element to show warning for submitting
     * exam
     */
    @FXML
    public VBox examSubmitWarning;

    /**
     * A javafx label to show warning content for submitting
     * exam
     */
    @FXML
    public Label examSubmitWarningLabel;

    /**
     * A javafx label to indicate if this question is
     * multiple answering or single answering
     */
    @FXML
    public Label examQuestionMultipleAnswerWarning;

    /**
     * A javafx label to indicate the score of current
     * question
     */
    @FXML
    public Label examQuestionScore;

    /**
     * A javafx element to show warning for submitting
     * the exam
     */
    @FXML
    public VBox examExitWarning;

    /**
     * A javafx button to handle cancelling existing
     * exam event
     */
    @FXML
    public Button examExitCancelButton;

    /**
     * A javafx button to handle existing exam event
     */
    @FXML
    public Button examExitConfirmButton;

    /**
     * A field to store starting time of exam
     */
    public long startTime;
    /**
     * An Exam object of this exam
     */
    public Exam selectedExam;
    /**
     * A string of name of the student
     */
    public String studentName;
    /**
     * A field to store time allowed for this
     * exam
     */
    public int examTimeLimitInSecond;
    /**
     * A field to store total number of questions
     * in this exam
     */
    public int examTotalQuestionCount;
    /**
     * An arraylist to store all exam attempt records
     */
    public ArrayList<List<Character>> examAttemptRecord;
    /**
     * A field to store index of current attempting
     * question
     */
    public int currentQuestionNumber = 0;
    /**
     * A list of question object in this exam
     */
    public List<Question> examQuestionsInfoList = new ArrayList<>();
    /**
     * A list of question content in this exam
     */
    public List<String> examQuestionContentList = new ArrayList<>();
    /**
     * A field to keep track of time used for this exam
     */
    public int examTimeUsed = 0;

    /**
     * A list of exam attempt result
     */
    public List<String> examAttemptResult;

    /**
     * A method to check if the Exam object is empty
     * @param selectedExam  Exam object
     * @return              <code>true</code> if object is null
     *                      <code>false</code> otherwise
     */
    public static boolean checkExamEmpty(Exam selectedExam){
        return selectedExam == null;
    }

    /**
     * A method to check if an item is empty
     * @param item  the item in String
     * @return      <code>true</code> if item is null
     *              <code>false</code> otherwise
     */
    public static boolean checkItemEmpty(String item){
        return item == null;
    }

    /**
     * A method to check if the selected index is valid
     * @param selectedIndex the selected index
     * @return              <code>true</code> if index not equal to -1
     *                      <code>false</code> otherwise
     */
    public static boolean checkIndex(int selectedIndex){
        return selectedIndex != -1;
    }

    /**
     * A method to check if student still have time left
     * @param remainingTime time remaining
     * @return              <code>true</code> if remaining time larger
     *                                        then 0
     *                      <code>false</code> otherwise
     */
    public static boolean checkRemainingTime(int remainingTime){
        return remainingTime >= 0;
    }


    /**
     * A method to initialze this controller
     * Retrive the Exam object and set up the scene
     * Start the timer
     */
    public void initialize() {
        if(checkExamEmpty(selectedExam)){
            //System.out.println("null");
            return;
        }

        //update exam information
        examQuestionsInfoList = selectedExam.getExamQuestions();
        for(Question question: examQuestionsInfoList){
            examQuestionContentList.add(question.getQuestionName());
        }

        examAttemptRecord = new ArrayList<>();
        for (int i = 0; i < examQuestionContentList.size(); i++) {
            examAttemptRecord.add(new ArrayList<>());
        }

        examTimeLimitInSecond = selectedExam.getTimeLimit();
        examTotalQuestionCount = examQuestionsInfoList.size();
        examName.setText(selectedExam.getExamName());
        courseName.setText(selectedExam.getCourse());
        
        examTotalQuestion.setText(String.valueOf(examTotalQuestionCount));
        examQuestionNumber.setText(String.valueOf(currentQuestionNumber + 1));

        examPrevButton.setVisible(false);
        examNextButton.setVisible(false);
        examNextButton_1.setVisible(true);
        examSubmitButton.setVisible(false);

        examSubmitWarning.setVisible(false);
        examSubmitOkButton.setVisible(false);

        examNextButton.setOnAction(e -> nextQuestion());
        examNextButton_1.setOnAction(e -> nextQuestion());
        examPrevButton.setOnAction(e -> prevQuestion());
        examSubmitButton.setOnAction(e -> submitExam());

        ObservableList<String> questions = FXCollections.observableArrayList(examQuestionContentList);
        ListView<String> listView = new ListView<>(questions);
        listView.setPrefWidth(300);
        listView.setPrefHeight(450);
        listView.setCellFactory(lv -> {
            return new javafx.scene.control.ListCell<String>() {
                private final Text text;

                {
                    text = new Text();
                    text.wrappingWidthProperty().bind(listView.widthProperty().add(50)); // Adjust padding as needed;
                    setGraphic(text);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || checkItemEmpty(item)) {
                        text.setText(null);
                    } else {
                        text.setText(item);
                    }
                }
            };
        });

        listView.setOnMouseClicked(event -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (checkIndex(selectedIndex)) {
                recordAnswer();
                currentQuestionNumber = selectedIndex;
                updateQuestion();
                //listView.getSelectionModel().clearSelection();
            }
        });

       // examQuestionScrollPane_Pane = new AnchorPane();
        examQuestionScrollPane_Pane.getChildren().add(listView);

        /*
        examQuestionScrollPane_Pane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
                    if (!listView.getBoundsInParent().contains(event.getSceneX(), event.getSceneY())) {
                        listView.getSelectionModel().clearSelection();
                    }
                });
            }
        });
        */

        startTimer();
        updateQuestion();

        Stage stageTemp = (Stage) examRemainTime.getScene().getWindow();
        stageTemp.setOnCloseRequest(event -> {
            event.consume(); // Prevent the window from closing immediately
            closeWindowEvent(event);
        });

        examExitWarning.setVisible(false);
    }

    /**
     * A method to set the Exam object and student name
     * @param studentSelectedExam   Exam object
     * @param studentName           student name
     */
    public void setExamDetails(Exam studentSelectedExam, String studentName){
        selectedExam = studentSelectedExam;
        this.studentName = studentName;
    }

    /**
     * A method to start the timer to count down
     */
    private void startTimer() {
        startTime = System.currentTimeMillis();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                int remainingTime = examTimeLimitInSecond - (int) elapsedTime;

                if (checkRemainingTime(remainingTime)) {
                    examRemainTime.setText(remainingTime + "s");
                    examTimeUsed = examTimeLimitInSecond - remainingTime;
                } else {
                    examRemainTime.setText("-");
                    examTimeUsed = examTimeLimitInSecond;
                    timeIsUpHandler();
                }
            }
        };

        timer.start();
    }

    /**
     * A method to handle exam window closing during
     * the exam
     * @param event WindowEvent of the close window button
     */
    private void closeWindowEvent(WindowEvent event) {
        //System.out.println("Window close request ...");
        examExitWarning.setVisible(true);

        // Disable all other buttons
        examNextButton.setDisable(true);
        examNextButton_1.setDisable(true);
        examPrevButton.setDisable(true);
        examSubmitButton.setDisable(true);
        examQuestionChoiceA.setDisable(true);
        examQuestionChoiceB.setDisable(true);
        examQuestionChoiceC.setDisable(true);
        examQuestionChoiceD.setDisable(true);
        examQuestionScrollPane_Pane.setDisable(true);

        examExitCancelButton.setOnAction(e -> examExitCancelHandler());
        examExitConfirmButton.setOnAction(e -> examExitHandler());
    }

    /**
     * A method to display Exam exist warning label
     */
    private void examExitCancelHandler(){
        examExitWarning.setVisible(false);

        // Enable all other buttons
        examNextButton.setDisable(false);
        examNextButton_1.setDisable(false);
        examPrevButton.setDisable(false);
        examSubmitButton.setDisable(false);
        examQuestionChoiceA.setDisable(false);
        examQuestionChoiceB.setDisable(false);
        examQuestionChoiceC.setDisable(false);
        examQuestionChoiceD.setDisable(false);
        examQuestionScrollPane_Pane.setDisable(false);
    }

    /**
     * A method to handle early exam exist via closing
     * the exam window
     */
    private void examExitHandler(){
        recordAnswer();
        //Still record student's answer
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentExamPopUpResult.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Exam Result");
            stage.setScene(new Scene(fxmlLoader.load()));

            // Access the controller
            StudentExamPopUpResultController controller = fxmlLoader.getController();
            //List<String> examAttemptResult = examEvaluation();
            examEvaluation();
            controller.setExamResult(examAttemptResult.get(0), examAttemptResult.get(1), examAttemptResult.get(2));

            stageClose = true;

            stage.show();

            Stage thisStage = (Stage) courseName.getScene().getWindow();
            thisStage.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * A method to check if a question is with multiple answers
     * @param examQuestionsInfoList the list of questions in this exam
     * @param currentQuestionNumber current question number
     * @return                      <code>true</code> if this question is with multiple answer
     *                              <code>false</code> otherwise
     */
    public static boolean checkMultipleQuestion(List<Question> examQuestionsInfoList, int currentQuestionNumber){
        return examQuestionsInfoList.get(currentQuestionNumber).getQuestionType().equals("Multiple");
    }

    /**
     * A method to check if the question is not the first nor last
     * @param currentQuestionNumber     current question index
     * @param examTotalQuestionCount    total number of questions
     * @return                          <code>true</code> if this question is not first nor last
     *                                  <code>false</code> otherwise
     */
    public static boolean checkQuestionNotStartNotEnd(int currentQuestionNumber, int examTotalQuestionCount){
        return currentQuestionNumber > 0 && currentQuestionNumber < (examTotalQuestionCount - 1);
    }


    /**
     * A method to check if the exam only has one question
     * @param examTotalQuestionCount    total number of question in this exam
     * @return                          <code>true</code> if only 1 question
     *                                  <code>false</code> otherwise
     */
    public static boolean checkQuestionOnlyOne(int examTotalQuestionCount){
        return examTotalQuestionCount == 1;
    }

    /**
     * A method to check if the question is last one
     * @param currentQuestionNumber     index of this question
     * @param examTotalQuestionCount    total number of questions
     * @return                          <code>true</code> if this is last question
     *                                  <code>false</code> otherwise
     */
    public static boolean checkQuestionIsLast(int currentQuestionNumber, int examTotalQuestionCount){
        return currentQuestionNumber == (examTotalQuestionCount - 1);
    }

    /**
     * A method to cehck if the Exam Attempt Record is empty
     * @param examAttemptRecord a list of student's answer recorded for attempted quetions
     * @return                  <code>true</code> if record is empty
     *                          <code>false</code> otherwise
     */
    public static boolean checkExamRecordEmpty(ArrayList<List<Character>> examAttemptRecord){
        return examAttemptRecord.isEmpty() == false;
    }


    /**
     * A method to check if the question has answered before
     * @param currentQuestionNumber current question index
     * @param examAttemptRecord     a list of student's answer recorded for attempted quetions
     * @return                      <code>true</code> if this quesiton has attempted
     *                              <code>false</code> otherwise
     */
    public static boolean checkIfAnswered(int currentQuestionNumber, ArrayList<List<Character>> examAttemptRecord){
        return currentQuestionNumber < examAttemptRecord.size();
    }

    /**
     * A method to check if the student's attempt on a question
     * contains option A
     * @param answers   student's answer
     * @return          <code>true</code> if contains A
     *                  <code>false</code> otherwise
     */
    public static boolean checkIfContainsA(List<Character> answers){
        return answers.contains('A');
    }

    /**
     * A method to check if the student's attempt on a question
     * contains option B
     * @param answers   student's answer
     * @return          <code>true</code> if contains B
     *                  <code>false</code> otherwise
     */
    public static boolean checkIfContainsB(List<Character> answers){
        return answers.contains('B');
    }

    /**
     * A method to check if the student's attempt on a question
     * contains option C
     * @param answers   student's answer
     * @return          <code>true</code> if contains C
     *                  <code>false</code> otherwise
     */
    public static boolean checkIfContainsC(List<Character> answers){
        return answers.contains('C');
    }

    /**
     * A method to check if the student's attempt on a question
     * contains option D
     * @param answers   student's answer
     * @return          <code>true</code> if contains D
     *                  <code>false</code> otherwise
     */
    public static boolean checkIfContainsD(List<Character> answers){
        return answers.contains('D');
    }

    /**
     * A method to update the question and options displaying
     * on the exam UI
     */
    private void updateQuestion(){
        examQuestionContent.setText(examQuestionContentList.get(currentQuestionNumber));
        examQuestionNumber.setText(String.valueOf(currentQuestionNumber + 1));
        examQuestionScore.setText(String.valueOf(examQuestionsInfoList.get(currentQuestionNumber).getScore()));

        ToggleGroup toggleGroup = new ToggleGroup();

        if(checkMultipleQuestion(examQuestionsInfoList, currentQuestionNumber)){
            examQuestionMultipleAnswerWarning.setText(" (Multiple Answers)");
            // Remove all RadioButtons from the ToggleGroup for multiple-answer
            examQuestionChoiceA.setToggleGroup(null);
            examQuestionChoiceB.setToggleGroup(null);
            examQuestionChoiceC.setToggleGroup(null);
            examQuestionChoiceD.setToggleGroup(null);
        }
        else{
            examQuestionMultipleAnswerWarning.setText(" ");

            // Add RadioButtons to the ToggleGroup
            examQuestionChoiceA.setToggleGroup(toggleGroup);
            examQuestionChoiceB.setToggleGroup(toggleGroup);
            examQuestionChoiceC.setToggleGroup(toggleGroup);
            examQuestionChoiceD.setToggleGroup(toggleGroup);
        }

        examQuestionChoiceA.setText(examQuestionsInfoList.get(currentQuestionNumber).getOptionA());
        examQuestionChoiceB.setText(examQuestionsInfoList.get(currentQuestionNumber).getOptionB());
        examQuestionChoiceC.setText(examQuestionsInfoList.get(currentQuestionNumber).getOptionC());
        examQuestionChoiceD.setText(examQuestionsInfoList.get(currentQuestionNumber).getOptionD());

        examQuestionChoiceA.setSelected(false);
        examQuestionChoiceB.setSelected(false);
        examQuestionChoiceC.setSelected(false);
        examQuestionChoiceD.setSelected(false);

        if(checkQuestionNotStartNotEnd(currentQuestionNumber, examTotalQuestionCount)){
            examPrevButton.setVisible(true);
            examNextButton.setVisible(true);
            examNextButton_1.setVisible(false);
            examSubmitButton.setVisible(false);
        }
        else if(checkQuestionIsLast(currentQuestionNumber, examTotalQuestionCount)){
            if(checkQuestionOnlyOne(examTotalQuestionCount)){
                examPrevButton.setVisible(false);
                examNextButton.setVisible(false);
                examNextButton_1.setVisible(false);
                examSubmitButton.setVisible(true);
            }
            else{
                examPrevButton.setVisible(true);
                examNextButton.setVisible(false);
                examNextButton_1.setVisible(false);
                examSubmitButton.setVisible(true);
            }
        }
        else{
            examPrevButton.setVisible(false);
            examNextButton.setVisible(false);
            examNextButton_1.setVisible(true);
            examSubmitButton.setVisible(false);
        }

        //Load previous answers
        if(checkExamRecordEmpty(examAttemptRecord)){
            if (checkIfAnswered(currentQuestionNumber, examAttemptRecord)) {
                List<Character> answers = examAttemptRecord.get(currentQuestionNumber);
                if (checkIfContainsA(answers)) examQuestionChoiceA.setSelected(true);
                if (checkIfContainsB(answers)) examQuestionChoiceB.setSelected(true);
                if (checkIfContainsC(answers)) examQuestionChoiceC.setSelected(true);
                if (checkIfContainsD(answers)) examQuestionChoiceD.setSelected(true);
            }
        }
    }

    /**
     * A method to check if the next question exists
     * @param currentQuestionNumber     index of current question
     * @param examTotalQuestionCount    total numenr of questions
     * @return                          <code>true</code> if next question exists
     *                                  <code>false</code> otherwise
     */
    public static boolean checkNextQuestion(int currentQuestionNumber, int examTotalQuestionCount){
        return currentQuestionNumber < examTotalQuestionCount - 1;
    }

    /**
     * A method to move to next sequential question
     */
    private void nextQuestion(){
        recordAnswer();

        if(checkNextQuestion(currentQuestionNumber, examTotalQuestionCount)){
            currentQuestionNumber ++;
            updateQuestion();
        }
    }

    /**
     * A method to check if the previous question exists
     * @param currentQuestionNumber index of current question
     * @return                      <code>true</code> if previous question exists
     *                              <code>false</code> otherwise
     */
    public static boolean checkPrevQuestion(int currentQuestionNumber){
        return currentQuestionNumber > 0;
    }

    /**
     * A method to move to previous sequential question
     */
    private void prevQuestion(){

        recordAnswer();

        if(checkPrevQuestion(currentQuestionNumber)){
            currentQuestionNumber --;
            updateQuestion();
        }
    }

    /**
     * A method to check if the option A is selected
     * @param selected  boolean indicating if option A is selected
     * @return          <code>true</code> if selected
     *                  <code>false</code> otherwise
     */
    public static boolean checkQuestionSelectedA(boolean selected){
        return selected;
    }

    /**
     * A method to check if the option B is selected
     * @param selected  boolean indicating if option B is selected
     * @return          <code>true</code> if selected
     *                  <code>false</code> otherwise
     */
    public static boolean checkQuestionSelectedB(boolean selected){
        return selected;
    }

    /**
     * A method to check if the option C is selected
     * @param selected  boolean indicating if option C is selected
     * @return          <code>true</code> if selected
     *                  <code>false</code> otherwise
     */
    public static boolean checkQuestionSelectedC(boolean selected){
        return selected;
    }

    /**
     * A method to check if the option D is selected
     * @param selected  boolean indicating if option D is selected
     * @return          <code>true</code> if selected
     *                  <code>false</code> otherwise
     */
    public static boolean checkQuestionSelectedD(boolean selected){
        return selected;
    }

    /**
     * A method to record student's answer of this question
     */
    private void recordAnswer(){
        List<Character> selectedAnswers = new ArrayList<>();
        if (checkQuestionSelectedA(examQuestionChoiceA.isSelected())) selectedAnswers.add('A');
        if (checkQuestionSelectedB(examQuestionChoiceB.isSelected())) selectedAnswers.add('B');
        if (checkQuestionSelectedC(examQuestionChoiceC.isSelected())) selectedAnswers.add('C');
        if (checkQuestionSelectedD(examQuestionChoiceD.isSelected())) selectedAnswers.add('D');

        examAttemptRecord.set(currentQuestionNumber, selectedAnswers);
    }

    /**
     * An event handler for exam submission
     */
    private void submitExam(){
        recordAnswer();
        examSubmitWarning.setVisible(true);
        examSubmitWarningLabel.setText("Confirm Submission");

        // Disable all other buttons
        examNextButton.setDisable(true);
        examNextButton_1.setDisable(true);
        examPrevButton.setDisable(true);
        examSubmitButton.setDisable(true);
        examQuestionChoiceA.setDisable(true);
        examQuestionChoiceB.setDisable(true);
        examQuestionChoiceC.setDisable(true);
        examQuestionChoiceD.setDisable(true);
        examQuestionScrollPane_Pane.setDisable(true);

        examSubmitCancelButton.setOnAction(e -> cancelExamSubmission());
        examSubmitConfirmButton.setOnAction(e -> confirmExamSubmission(e));
    }

    /**
     * A method to cancel the submission
     */
    private void cancelExamSubmission(){
        examSubmitWarning.setVisible(false);

        // Enable all other buttons
        examNextButton.setDisable(false);
        examNextButton_1.setDisable(false);
        examPrevButton.setDisable(false);
        examSubmitButton.setDisable(false);
        examQuestionChoiceA.setDisable(false);
        examQuestionChoiceB.setDisable(false);
        examQuestionChoiceC.setDisable(false);
        examQuestionChoiceD.setDisable(false);
        examQuestionScrollPane_Pane.setDisable(false);
    }

    public boolean stageClose = false;

    /**
     * A method to check is the stage is closed
     * @return  <code>true</code> if the stage is closed
     *          <code>false</code> otherwise
     */
    public boolean checkIfStageClose(){
        return stageClose;
    }


    /**
     * A method to confirm exam submission
     * @param event ActionEvent of confirm submission button
     */
    private void confirmExamSubmission(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentExamPopUpResult.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Exam Result");
            stage.setScene(new Scene(fxmlLoader.load()));

            // Access the controller
            StudentExamPopUpResultController controller = fxmlLoader.getController();
            //List<String> examAttemptResult = examEvaluation();
            examEvaluation();
            controller.setExamResult(examAttemptResult.get(0), examAttemptResult.get(1), examAttemptResult.get(2));

            stageClose = true;

            stage.show();
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * An event handler when time is up
     */
    private void timeIsUpHandler(){
        examSubmitWarning.setVisible(true);
        examSubmitOkButton.setVisible(true);
        examSubmitCancelButton.setVisible(false);
        examSubmitConfirmButton.setVisible(false);
        examSubmitWarningLabel.setText("Time Is Up");

        // Disable all other buttons
        examNextButton.setDisable(true);
        examNextButton_1.setDisable(true);
        examPrevButton.setDisable(true);
        examSubmitButton.setDisable(true);
        examQuestionChoiceA.setDisable(true);
        examQuestionChoiceB.setDisable(true);
        examQuestionChoiceC.setDisable(true);
        examQuestionChoiceD.setDisable(true);
        examQuestionScrollPane_Pane.setDisable(true);

        examSubmitOkButton.setOnAction(e -> confirmExamSubmission(e));
    }

    /**
     * A methid to to check answer for a question
     * @param correctAnswer a list of correct options
     * @param studentAnswer a list of chosen options
     * @return              <code>true</code> if matches
     *                      <code>true</code> otherwise
     */
    public static boolean checkAnswer(List<Character> correctAnswer, List<Character> studentAnswer){
        return correctAnswer.size() == studentAnswer.size() && correctAnswer.containsAll(studentAnswer);
    }

    /**
     * A method to check is a ExamAttempt match then given exam and student name
     * @param attempt       ExamAttempt object
     * @param selectedExam  exam name
     * @param studentName   student name
     * @return              <code>true</code> if the ExamAttempt belongs to this exam and student
     *                      <code>false</code> otherwise
     */
    public static boolean checkStudentExamAttemptReacord(ExamAttempt attempt, Exam selectedExam, String studentName){
        return attempt.getCourseName().equals(selectedExam.getCourse()) && attempt.getExamName().equals(selectedExam.getExamName()) && attempt.getStudentName().equals(studentName);
    }

    /**
     * A method to check if an ExamAttempt is not found
     * @param found boolean indicating if an exam attempt is found
     * @return      <code>true</code> if the exam attempt is not found
     *              <code>false</code> otherwise
     */
    public static boolean checkFound(boolean found){
        return !found;
    }

    /**
     * A string of file path to exam attempt database
     */
    public static String jsonFileLocation = "src/main/resources/database/examAttempt.json";

    /**
     * A method to set file path for exam attempt database
     * @param location  file path
     */
    public static void setExamAttemptJsonFile(String location){
        jsonFileLocation = location;
    }

    /**
     * A method to evalaute this exam attempt with number of
     * quetions answered correctly, accuracy, and total score
     */
    //public List<String> examEvaluation(){
    public void examEvaluation(){
        int correctCount = 0;
        int totalQuestions = examQuestionsInfoList.size();
        int totalScore = 0;
        int receivedScore = 0;

        for (int i = 0; i < totalQuestions; i++) {
            List<Character> correctAnswer = examQuestionsInfoList.get(i).getAnswer();
            List<Character> studentAnswer = examAttemptRecord.get(i);

            if (checkAnswer(correctAnswer, studentAnswer)) {
                correctCount++;
                receivedScore += examQuestionsInfoList.get(i).getScore();
            }
            totalScore += examQuestionsInfoList.get(i).getScore();
        }
        float percentage = (correctCount / (float) totalQuestions) * 100;

        List<String> evaluationScores = new ArrayList<>(List.of(
                correctCount + "/" + totalQuestions,
                String.format("%.2f", percentage),
                receivedScore + "/" + totalScore
        ));

        //Save examAttempt to database
        //final String jsonFileLocation = "src/main/resources/database/examAttempt.json";
        ObjectMapper objectMapper = new ObjectMapper();
        File examAttemptFile = new File(jsonFileLocation);


        try {
            List<ExamAttempt> examAttempts = objectMapper.readValue(examAttemptFile, new TypeReference<List<ExamAttempt>>() {
            });
            ExamAttempt newExamAttempt = new ExamAttempt(this.studentName, selectedExam.getCourse(), selectedExam.getExamName(), receivedScore, totalScore, examTimeUsed);

            // Add new user to the list
            // Find and update the existing exam attempt
            boolean found = false;
            for (int i = 0; i < examAttempts.size(); i++) {
                ExamAttempt attempt = examAttempts.get(i);
                if (checkStudentExamAttemptReacord(attempt, selectedExam, this.studentName)) {
                    examAttempts.set(i, newExamAttempt);
                    found = true;
                    break;
                }
            }

            // Optionally, add a new attempt if not found
            if (checkFound(found)) {
                examAttempts.add(newExamAttempt);
            }

            // Write the updated list back to the file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(examAttemptFile, examAttempts);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //return evaluationScores;
        examAttemptResult = evaluationScores;
    }
}