package comp3111.examsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.ExamAttempt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A controller for student main window
 * @author <a href=mailto:pakninlpn@gmail.com>Marco</a>
 * @version 1.5
 */
public class StudentMainController {
    @FXML
    ComboBox<String> examCombox;

    private List<Exam> examsList;

    /**
     * A javafx label to show warning of not selecting exam to start
     */
    @FXML
    public Label studentMainSelectExamWarning;

    /**
     * A javafx label to show warning of selecting a taken exam
     */
    @FXML
    public Label studentMainSelectTakenExamWarning;

    /**
     * A javafx button to start exam attempt event
     */
    @FXML
    public Button startButton;

    //Get Exam database
    private static final ObjectMapper mapper = new ObjectMapper();
    /**
     * A string of file path to exam database
     */
    public static String examsListJsonFileLocation = "src/main/resources/database/examBank.json";
    private ObservableList<String> exams;

    /**
     * A string of the name of this student
     */
    public String studentName;

    /**
     * A string of file path to the exam attempt database
     */
    //Access examAttempt database - for only displaying those non-taken exams
    public String jsonFileLocation = "src/main/resources/database/examAttempt.json";
    ObjectMapper objectMapper = new ObjectMapper();
    File examAttemptFile = new File(jsonFileLocation);
    File examsListFile = new File(examsListJsonFileLocation);
    private List<ExamAttempt> examAttempts;
    private ArrayList<String> examTaken;

    /**
     * A method to set the database path for ExamAttempt record
     * @param location  the path of ExamAttempt database
     */
    public static void setExamListJsonFileLocation(String location){
        examsListJsonFileLocation = location;
    }

    /**
     * Constructor for this controller
     */
    public StudentMainController(){

    }

    /**
     * A method to check if an Exam is not taken and already published
     * @param examTaken a list of Exam taken by this student
     * @param exam      the Exam to be checked
     * @return          <code>true</code> if the Exam has not taken and published
     *                  <code>false</code> if the Exam has taken or not published
     */
    public static boolean checkExamTakenAndPublished(ArrayList<String> examTaken, Exam exam){
        return !examTaken.contains(exam.getCourse()+"|"+exam.getExamName()) && exam.getPublished();
    }

    /**
     * A method to read the database containing all Exams
     * @param examsListFile the File of the database
     * @return              a list of Exam object
     */
    public static List<Exam> readExamListFile(File examsListFile){
        try{
            return mapper.readValue(examsListFile, new TypeReference<List<Exam>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A method to initialize this controller
     * Read the list of Exam available and put
     * those untaken and published exam to the
     * dropdown menu
     */
    public void initialize() {
        //Read Exam, examAttempt Database
        examsList = readExamListFile(examsListFile);
        /*
        try {
            examsList = mapper.readValue(examsListFile, new TypeReference<List<Exam>>(){});

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        */

        //Get a list of taken exam for this student
        examTaken = getExamTakenList();

        exams = FXCollections.observableArrayList();

        //Only open those non-taken exams to this student
        for(Exam exam : examsList){
            //if(!examTaken.contains(exam.getExamName()) && exam.getPublished()){
            if(checkExamTakenAndPublished(examTaken, exam)){
                exams.add(exam.getCourse() + " | " + exam.getExamName());
            }
        }

        examCombox.setItems(exams);
        studentMainSelectExamWarning.setVisible(false);
        studentMainSelectTakenExamWarning.setVisible(false);
    }

    /**
     * A method to check if the ExamAttempt is done by
     * this student
     * @param attempt       the ExamAttempt to be checked
     * @param studentName   the student name to be checked
     * @return              <code>true</code> if the ExamAttempt belongs
     *                                        to this student
     *                      <code>false</code> if the ExamAttempt do not
     *                                         belongs to this student
     */
    public static boolean checkSameStudentName(ExamAttempt attempt, String studentName){
        return attempt.getStudentName().equals(studentName);
    }


    /**
     * A method to get a list of exam taken by this student
     * @return  a list of exam name
     */
    public ArrayList<String> getExamTakenList(){
        try {
            List<ExamAttempt> examAttemptsTemp = objectMapper.readValue(examAttemptFile, new TypeReference<List<ExamAttempt>>() {
            });
            ArrayList<String> examTakenTemp = new ArrayList<>();
            for (ExamAttempt attempt : examAttemptsTemp) {
                //if(attempt.getStudentName().equals(this.studentName)){
                if(checkSameStudentName(attempt, this.studentName)){
                    examTakenTemp.add(attempt.getCourseName() + "|" + attempt.getExamName());
                }
                else{
                    continue;
                }
            }
            return examTakenTemp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A method to set the student name of this controller
     * @param studentName   the student username
     */
    public void setStudentName(String studentName){
        this.studentName = studentName;
        initialize();
    }

    /**
     * A method to check if we did not select an Exam object
     * @param selectedExamName  the name of the Exam
     * @return                  <code>true</code> if the exam name is null
     *                          <code>false</code> if the exam name is not null
     */
    public static boolean checkExamNameEmpty(String selectedExamName){
        return selectedExamName == null;
    }

    /**
     * A method to check if the Exam object matches with an exam name
     * @param exam              the exam object to be checked
     * @param selectedExamName  the exam name
     * @return                  <code>true</code> if the exam object has the
     *                                            same name with given name
     *                          <code>false</code> otherwise
     */
    public static boolean checkSameExam(Exam exam, String selectedExamName){
        return exam.getExamName().equals(selectedExamName);
    }

    /**
     * A method to check if an Exam has been taken by this student
     * @param examTakenList         a list Exam taken
     * @param selectedCourseName    the course name of Exam to be checked
     * @param selectedExamName      the exam name of Exam to be checked
     * @return                      <code>true</code> if the Exam has taken
     *                              <code>false</code> otherwise
     */
    public static boolean checkTakenExam(ArrayList<String> examTakenList, String selectedCourseName, String selectedExamName){
        return examTakenList.contains(selectedCourseName+"|"+selectedExamName);
    }

    /**
     * A method to check if an Exam object matches with the given course
     * and exam name
     * @param exam                  the Exam object to be checked
     * @param selectedCourseName    the course name
     * @param selectedExamName      the exam name
     * @return                      <code>true</code> if the names match
     *                              <code>false</code> otherwise
     */
    //checkSameExam(exam, selectedExamName) && exam.getCourse().equals(selectedCourseName)
    public static boolean checkSameCourseSameExam(Exam exam, String selectedCourseName, String selectedExamName){
        return checkSameExam(exam, selectedExamName) && exam.getCourse().equals(selectedCourseName);
    }

    /**
     * A string of file path to the exam attempt database
     */
    public String examAttemptJsonFileLocation = "src/main/resources/database/examAttempt.json";

    /**
     * A method to change the file path of database of ExamAttempt
     * @param location  the file path of the database
     */
    public void changeJsonFileLocation(String location){
        examAttemptJsonFileLocation = location;
        examAttemptFile = new File (location);
    }

    /**
     * A method to open the Exam UI for selected Exam
     */
    @FXML
    public void openExamUI() {
        String selectedExamName = examCombox.getValue();
        if(checkExamNameEmpty(selectedExamName)){
            studentMainSelectExamWarning.setVisible(true);
            studentMainSelectTakenExamWarning.setVisible(false);
        }
        else{
            String selectedCourseName = selectedExamName.split(" \\| ")[0];
            selectedExamName = selectedExamName.split(" \\| ")[1]; // Extract the exam name
            //if (getExamTakenList().contains(selectedCourseName+"|"+selectedExamName)){
            if(checkTakenExam(getExamTakenList(), selectedCourseName, selectedExamName)){
                studentMainSelectExamWarning.setVisible(false);
                studentMainSelectTakenExamWarning.setVisible(true);

            }
            else{
                studentMainSelectExamWarning.setVisible(false);
                studentMainSelectTakenExamWarning.setVisible(false);

                //Create exam attempt
                //final String jsonFileLocation = "src/main/resources/database/examAttempt.json";
                ObjectMapper objectMapper = new ObjectMapper();
                File examAttemptFile = new File(examAttemptJsonFileLocation);

                try {
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentTakeExam.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setTitle("Start Exam");
                    stage.setScene(scene);
                    StudentTakeExamController controller = fxmlLoader.getController();
                    for (Exam exam : examsList) {
                        //if (checkSameExam(exam, selectedExamName) && exam.getCourse().equals(selectedCourseName)) {
                        if(checkSameCourseSameExam(exam, selectedCourseName, selectedExamName)){
                            controller.setExamDetails(exam, studentName);
                            controller.initialize();

                            //Create exam attempt
                            List<ExamAttempt> examAttempts = objectMapper.readValue(examAttemptFile, new TypeReference<List<ExamAttempt>>() {
                            });
                            ExamAttempt newExamAttempt = new ExamAttempt(this.studentName, exam.getCourse(), exam.getExamName(), 0, exam.getFullScore(), exam.getTimeLimit());

                            // Add new user to the list
                            examAttempts.add(newExamAttempt);
                            objectMapper.writerWithDefaultPrettyPrinter().writeValue(examAttemptFile, examAttempts);
                            break;
                        }
                    }
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * A method to open Grade Statistic UI
     */
    @FXML
    public void openGradeStatistic() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentGradeStatsUI.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Grade Statistics");

            StudentGradeStatisticController controller = fxmlLoader.getController();
            controller.setStudentName(this.studentName);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method ot exit the system
     */
    @FXML
    public void exit() {
        System.exit(0);
    }
}
