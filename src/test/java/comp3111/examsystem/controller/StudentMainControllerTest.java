package comp3111.examsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.ExamAttempt;
import comp3111.examsystem.entity.Question;
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

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentMainControllerTest extends ApplicationTest  {

    @Test
    void initialize() throws IOException {
    }

    @Test
    void validateExamTakenAndPublished1(){
        ArrayList<String> examTaken = new ArrayList<>();
        Exam exam = new Exam("Quiz", "COMP3111", 60, true, List.of(new Question()));
        assertTrue(StudentMainController.checkExamTakenAndPublished(examTaken, exam));
    }

    @Test
    void validateExamTakenAndPublished2(){
        ArrayList<String> examTaken = new ArrayList<>();
        examTaken.add("COMP3111|Quiz");
        Exam exam = new Exam("Quiz", "COMP3111", 60, true, List.of(new Question()));
        assertFalse(StudentMainController.checkExamTakenAndPublished(examTaken, exam));
    }

    @Test
    void validateExamTakenAndPublished3(){
        ArrayList<String> examTaken = new ArrayList<>();
        Exam exam = new Exam("Quiz", "COMP3111", 60, false, List.of(new Question()));
        assertFalse(StudentMainController.checkExamTakenAndPublished(examTaken, exam));
    }


    @Test
    void validateExamTakenAndPublished4(){
        ArrayList<String> examTaken = new ArrayList<>();
        examTaken.add("COMP3111|Quiz");
        Exam exam = new Exam("Quiz", "COMP3111", 60, false, List.of(new Question()));
        assertFalse(StudentMainController.checkExamTakenAndPublished(examTaken, exam));
    }

    @Test
    void checkReadExamListFile1(){
        String examsListJsonFileLocation = "src/test/testDatabase/examBankTestForStudent.json";
        File examsListFile = new File(examsListJsonFileLocation);
        ObjectMapper mapper = new ObjectMapper();
        List<Exam> expected;
        try{
            expected = mapper.readValue(examsListFile, new TypeReference<List<Exam>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Exam> actual = StudentMainController.readExamListFile(examsListFile);

        assertEquals(expected.get(0).getExamName(), actual.get(0).getExamName());
    }

    @Test
    void checkReadExamListFile2(){
        String invalidFilePath = "src/test/testDatabase/NonExistentFile.json";
        File invalidFile = new File(invalidFilePath);

        // Assert that the method throws a RuntimeException
        assertThrows(RuntimeException.class, () -> {
            StudentMainController.readExamListFile(invalidFile);
        });
    }

    @Test
    void validateSameStudentName1(){
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        assertTrue(StudentMainController.checkSameStudentName(attempt, "Kenneth"));
    }

    @Test
    void validateSameStudentName2(){
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        assertFalse(StudentMainController.checkSameStudentName(attempt, "Leung"));
    }

    @Test
    void validateExam1(){
        assertEquals(true, StudentMainController.checkExamNameEmpty(null));
    }

    @Test
    void validateExam2(){
        assertEquals(false, StudentMainController.checkExamNameEmpty("COMP3111"));
    }

    @Test
    void validateExam3(){
        assertEquals(false, StudentMainController.checkSameExam(new Exam("Quiz", "COMP3111", 10, true, Arrays.asList(new Question())), ""));
    }

    @Test
    void validateExam4(){
        assertEquals(true, StudentMainController.checkSameExam(new Exam("Quiz", "COMP3111", 10, true, Arrays.asList(new Question())), "Quiz"));
    }

    @Test
    void checkTakenExam1(){
        ArrayList<String> examTaken = new ArrayList<>();
        examTaken.add("COMP3111|Quiz");
        assertTrue(StudentMainController.checkTakenExam(examTaken, "COMP3111", "Quiz"));
    }

    @Test
    void checkTakenExam2(){
        ArrayList<String> examTaken = new ArrayList<>();
        assertFalse(StudentMainController.checkTakenExam(examTaken, "COMP3111", "Quiz"));
    }

    @Test
    void checkSameCourseSameExam1(){
        Exam exam = new Exam("Quiz", "COMP3111", 60, false, List.of(new Question()));
        assertTrue(StudentMainController.checkSameCourseSameExam(exam, "COMP3111", "Quiz"));
    }

    @Test
    void checkSameCourseSameExam2(){
        Exam exam = new Exam("Quiz", "COMP3111", 60, false, List.of(new Question()));
        assertFalse(StudentMainController.checkSameCourseSameExam(exam, "COMP3211", "Quiz"));
    }

    @Test
    void checkSameCourseSameExam3(){
        Exam exam = new Exam("Quiz", "COMP3111", 60, false, List.of(new Question()));
        assertFalse(StudentMainController.checkSameCourseSameExam(exam, "COMP3111", "Quiz1"));
    }

    @Test
    void checkSameCourseSameExam4(){
        Exam exam = new Exam("Quiz", "COMP3111", 60, false, List.of(new Question()));
        assertFalse(StudentMainController.checkSameCourseSameExam(exam, "COMP3211", "Quiz1"));
    }

    /////////////////////////////////////////////////////////////////////////////////
    StudentMainController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("StudentMainUI.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));

        StudentMainController controllerInit = loader.getController();
        controllerInit.setExamListJsonFileLocation("src/test/testDatabase/examBankTestForStudent.json");
        controllerInit.initialize();

        stage.show();
        controller = loader.<StudentMainController>getController();
        controller.setExamListJsonFileLocation("src/test/testDatabase/examBankTestForStudent.json");

    }

    @Test
    @Order(0)
    void startNullExam(){
        moveTo(controller.startButton);
        clickOn();

        assertTrue(controller.studentMainSelectExamWarning.isVisible());
    }

    @Test
    @Order(1)
    void selectCorrectExam(){
        clickOn(controller.examCombox);
        sleep(50);
        moveBy(0, 30);

        clickOn();

        ObservableList<String> examList = controller.examCombox.getItems();
        String expected = examList.getFirst();

        assertEquals(expected, controller.examCombox.getValue());
        assertFalse(controller.studentMainSelectExamWarning.isVisible());
    }

    @Test
    @Order(2)
    void startCorrectExam(){
        clickOn(controller.examCombox);
        sleep(50);
        moveBy(0, 30);

        clickOn();

        ObservableList<String> examList = controller.examCombox.getItems();
        String expected = examList.getFirst();

        //controller.examAttemptJsonFileLocation = "src/test/testDatabase/examAttemptTest.json";
        //controller.jsonFileLocation = "src/test/testDatabase/examAttemptTest.json";
        controller.changeJsonFileLocation("src/test/testDatabase/examAttemptTest.json");

        controller.studentName = "dummy";
        clickOn(controller.startButton);

        assertEquals(expected, controller.examCombox.getValue());
        assertFalse(controller.studentMainSelectExamWarning.isVisible());
        assertFalse(controller.studentMainSelectTakenExamWarning.isVisible());
    }

    @Test
    @Order(3)
    void startRepeatedExam(){
        clickOn(controller.examCombox);
        sleep(50);
        moveBy(0, 30);

        clickOn();

        ObservableList<String> examList = controller.examCombox.getItems();
        String expected = examList.getFirst();

        controller.changeJsonFileLocation("src/test/testDatabase/examAttemptTest.json");

        controller.studentName = "dummy";
        clickOn(controller.startButton);

        assertEquals(expected, controller.examCombox.getValue());
        assertFalse(controller.studentMainSelectExamWarning.isVisible());
        assertTrue(controller.studentMainSelectTakenExamWarning.isVisible());

        //Remove dummy testAttempt afterward
        //Create exam attempt
        ObjectMapper objectMapper = new ObjectMapper();
        File examAttemptFile = new File("src/test/testDatabase/examAttemptTest.json");
        try{
            List<ExamAttempt> examAttempts = objectMapper.readValue(examAttemptFile, new TypeReference<List<ExamAttempt>>() {
            });

            examAttempts.removeLast();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(examAttemptFile, examAttempts);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}