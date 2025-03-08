package comp3111.examsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.ExamAttempt;
import comp3111.examsystem.entity.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentGradeStatisticControllerTest extends ApplicationTest  {

    @Test
    void initialize() {
    }

    @Test
    void setStudentName() {
    }

    @Test
    void resetCourse() {
    }

    @Test
    void filterCourse() {
    }

    @Test
    void filterCourseHandler() {
    }

    @Test
    void refreshPage() {
    }

    @Test
    void validateExamAttemptStudentName1(){
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        assertTrue(StudentGradeStatisticController.checkExamAttemptStudentName(attempt, "Kenneth"));
    }

    @Test
    void validateExamAttemptStudentName2(){
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        assertFalse(StudentGradeStatisticController.checkExamAttemptStudentName(attempt, "Leung"));
    }

    @Test
    void validateSameExam1(){
        ObservableList<ExamAttempt> examTakenTemp = FXCollections.observableArrayList();
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        examTakenTemp.add(attempt);

        assertFalse(StudentGradeStatisticController.checkSameExam(examTakenTemp, attempt));
    }

    @Test
    void validateSameExam2(){
        ObservableList<ExamAttempt> examTakenTemp = FXCollections.observableArrayList();
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);

        assertTrue(StudentGradeStatisticController.checkSameExam(examTakenTemp, attempt));
    }

    @Test
    void validateSameCourse1(){
        ObservableList<String> courseTaken = FXCollections.observableArrayList();
        courseTaken.add("COMP3111");
        assertFalse(StudentGradeStatisticController.checkSameCourse(courseTaken, "COMP3111"));
    }

    @Test
    void validateSameCourse2(){
        ObservableList<String> courseTaken = FXCollections.observableArrayList();
        assertTrue(StudentGradeStatisticController.checkSameCourse(courseTaken, "COMP3111"));
    }

    @Test
    void validateSameNameSameExam1(){
        ObservableList<ExamAttempt> examTakenTemp = FXCollections.observableArrayList();
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        assertTrue(StudentGradeStatisticController.checkStudentNameAndSameExam(attempt, examTakenTemp, "Kenneth"));
    }

    @Test
    void validateSameNameSameExam2(){
        ObservableList<ExamAttempt> examTakenTemp = FXCollections.observableArrayList();
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        assertFalse(StudentGradeStatisticController.checkStudentNameAndSameExam(attempt, examTakenTemp, "Leung"));
    }

    @Test
    void validateSameNameSameExam3(){
        ObservableList<ExamAttempt> examTakenTemp = FXCollections.observableArrayList();
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        examTakenTemp.add(attempt);

        assertFalse(StudentGradeStatisticController.checkStudentNameAndSameExam(attempt, examTakenTemp, "Kenneth"));
    }

    @Test
    void validateSameNameSameExam4(){
        ObservableList<ExamAttempt> examTakenTemp = FXCollections.observableArrayList();
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        examTakenTemp.add(attempt);
        ExamAttempt attempt1 = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        assertFalse(StudentGradeStatisticController.checkStudentNameAndSameExam(attempt1, examTakenTemp, "Leung"));
    }

    @Test
    void validateSameNameSameCourse1(){
        ObservableList<ExamAttempt> examTakenTemp = FXCollections.observableArrayList();
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        ObservableList<String> courseTaken = FXCollections.observableArrayList();
        courseTaken.add("COMP1111");
        assertTrue(StudentGradeStatisticController.checkStudentNameAndSameCourse(attempt, courseTaken, "Kenneth"));
    }

    @Test
    void validateSameNameSameCourse2(){
        ObservableList<ExamAttempt> examTakenTemp = FXCollections.observableArrayList();
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        ObservableList<String> courseTaken = FXCollections.observableArrayList();
        courseTaken.add("COMP3111");
        assertFalse(StudentGradeStatisticController.checkStudentNameAndSameCourse(attempt, courseTaken, "Leung"));
    }

    @Test
    void validateSameNameSameCourse3(){
        ObservableList<ExamAttempt> examTakenTemp = FXCollections.observableArrayList();
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        ObservableList<String> courseTaken = FXCollections.observableArrayList();
        courseTaken.add("COMP3111");
        assertFalse(StudentGradeStatisticController.checkStudentNameAndSameCourse(attempt, courseTaken, "Kenneth"));
    }

    @Test
    void validateSameNameSameCourse4(){
        ObservableList<ExamAttempt> examTakenTemp = FXCollections.observableArrayList();
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        ObservableList<String> courseTaken = FXCollections.observableArrayList();
        courseTaken.add("COMP1111");
        assertFalse(StudentGradeStatisticController.checkStudentNameAndSameCourse(attempt, courseTaken, "Leung"));
    }


    @Test
    void validateSelectedCourseEmpty1(){
        assertTrue(StudentGradeStatisticController.checkSelectedCourseNmeEmpty("COMP3111"));
    }

    @Test
    void validateSelectedCourseEmpty2(){
        assertFalse(StudentGradeStatisticController.checkSelectedCourseNmeEmpty(null));
    }

    @Test
    void validateSelectedCourseEmpty1_1(){
        assertTrue(StudentGradeStatisticController.checkSelectedCourseNmeEmpty1("COMP3111"));
    }

    @Test
    void validateSelectedCourseEmpty1_2(){
        assertFalse(StudentGradeStatisticController.checkSelectedCourseNmeEmpty1(null));
    }

    @Test
    void validateSelectedCourseEqual1(){
        ExamAttempt examAttempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);

        assertTrue(StudentGradeStatisticController.checkSelectedCourseNameEqual(examAttempt, "COMP3111"));
    }

    @Test
    void validateSelectedCourseEqual2(){
        ExamAttempt examAttempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);

        assertFalse(StudentGradeStatisticController.checkSelectedCourseNameEqual(examAttempt, ""));
    }

    /////////////////////////////////////////////////////////////////////////////////
    StudentGradeStatisticController controller;
    List<ExamAttempt> examAttemptList;;
    ObservableList<ExamAttempt> dummyStudentExamAttemptList = FXCollections.observableArrayList();;
    String studentName = "dummy";

    @Override
    public void start(Stage stage) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
            examAttemptList = objectMapper.readValue(new File("src/test/testDatabase/examAttemptTest.json"), new TypeReference<List<ExamAttempt>>() {
        });

        for(ExamAttempt exam: examAttemptList){
            if(exam.getStudentName().equals(studentName)){
                dummyStudentExamAttemptList.add(exam);
            }
        }

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("StudentGradeStatsUI.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));

        StudentGradeStatisticController controllerInit = loader.getController();
        controllerInit.setStudentName("dummy");
        controllerInit.setExamAttemptJsonFileLocation("src/test/testDatabase/examAttemptTest.json");
        controllerInit.initialize();
        stage.show();

        controller = loader.<StudentGradeStatisticController>getController();
    }

    @Test
    @Order(0)
    void testDisplay(){

        ExamAttempt examAttemptToTest = dummyStudentExamAttemptList.getFirst();

        assertEquals(5, controller.gradeStatsTableView.getColumns().size(), "TableView should have 5 columns");
        ObservableList<ExamAttempt> items = controller.gradeStatsTableView.getItems();
        assertFalse(items.isEmpty(), "TableView should have items");

        ExamAttempt firstItem = items.getFirst();
        assertEquals(examAttemptToTest.getCourseName(), firstItem.getCourseName());
        assertEquals(examAttemptToTest.getExamName(), firstItem.getExamName());
        assertEquals(examAttemptToTest.getExamAttemptScore(), firstItem.getExamAttemptScore());
        assertEquals(examAttemptToTest.getExamTotalScore(), firstItem.getExamTotalScore());
        assertEquals(examAttemptToTest.getExamAttemptTimeSpend(), firstItem.getExamAttemptTimeSpend());
    }

    @Test
    @Order(1)
    void testResetEmpty() {
        moveTo(controller.gradeStatsResetButton);
        clickOn();

        assertTrue(controller.resetWarning.isVisible());
        assertFalse(controller.selectCourseWarning.isVisible());
    }

    @Test
    @Order(2)
    void testResetCourse() {
        moveTo(controller.gradeStatsCourseMenuButton);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveBy(0, 30);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveTo(controller.gradeStatsResetButton);
        clickOn();

        assertFalse(controller.resetWarning.isVisible());
        assertFalse(controller.selectCourseWarning.isVisible());
        assertEquals(null, controller.gradeStatsCourseMenuButton.getValue());
    }

    @Test
    @Order(3)
    void testFilterEmpty() {
        moveTo(controller.gradeStatsFilterButton);
        clickOn();

        assertFalse(controller.resetWarning.isVisible());
        assertTrue(controller.selectCourseWarning.isVisible());
    }

    @Test
    @Order(4)
    void testFilterCourse() {
        moveTo(controller.gradeStatsCourseMenuButton);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveBy(0, 30);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveTo(controller.gradeStatsFilterButton);
        clickOn();

        assertFalse(controller.resetWarning.isVisible());
        assertFalse(controller.selectCourseWarning.isVisible());
    }
}