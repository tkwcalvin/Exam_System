package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Question;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TeacherExamManagementControllerTest extends ApplicationTest {

    TeacherExamManagementController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("TeacherExamManagement.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));
        stage.show();
        controller = loader.<TeacherExamManagementController>getController();
        controller.disableAlert = true;
        controller.questionBankLocation = "src/test/testDatabase/questionBankTest.json";
        controller.examBankLocation = "src/test/testDatabase/examBankTest.json";
        controller.courseBankLocation = "src/test/testDatabase/courseBankTest.json";
    }

    @BeforeEach
    void refresh(){
        clickOn("#refreshBtn");
    }

    @Test
    @Order(0)
    void addExam(){
        int sizeBefore = controller.exams.size();

        clickOn("#examField");
        write("Test Exam");
        clickOn("#courseField");
        moveBy(0,60);
        clickOn();
        clickOn("#timeField");
        write("100");

        clickOn("#addBtn");

        int sizeAfter = controller.exams.size();
        assertEquals(sizeBefore+1,sizeAfter);

        Exam actual = controller.exams.getLast();
        assertEquals("Test Exam", actual.getExamName());
        assertEquals(controller.courses.getFirst().getCourseID(), actual.getCourse());
        assertEquals(100, actual.getTimeLimit());
        assertFalse(actual.getPublished());
        assertEquals(0,actual.getExamQuestions().size());
    }

    @Test
    @Order(1)
    void addExamFailed(){
        int sizeBefore = controller.exams.size();

        clickOn("#addBtn");
        clickOn("#examField");
        write("Test Exam");
        clickOn("#addBtn");
        clickOn("#courseField");
        moveBy(0,60);
        clickOn();
        clickOn("#addBtn");
        clickOn("#timeField");
        write("0");
        clickOn("#addBtn");
        clickOn("#timeField");
        write("_is_a_number");

        clickOn("#addBtn");

        int sizeAfter = controller.exams.size();
        assertEquals(sizeBefore,sizeAfter);
    }

    @Test
    @Order(2)
    void updateExam(){
        int sizeBefore = controller.exams.size();

        selectBottomMostExam();

        clickOn("#examField");
        write(" Updating");
        clickOn("#courseField");
        moveBy(0,75);
        clickOn();
        clickOn("#timeField");
        write("0");

        clickOn("#updateBtn");

        int sizeAfter = controller.exams.size();
        assertEquals(sizeBefore,sizeAfter);

        Exam actual = controller.exams.getLast();
        assertEquals("Test Exam Updating", actual.getExamName());
        assertEquals(controller.courses.get(1).getCourseID(), actual.getCourse());
        assertEquals(1000, actual.getTimeLimit());
        assertFalse(actual.getPublished());
        assertEquals(0,actual.getExamQuestions().size());
    }

    @Test
    @Order(2)
    void updateExamFailed(){
        int sizeBefore = controller.exams.size();

        clickOn("#timeField");
        write("1");

        clickOn("#updateBtn");

        int sizeAfter = controller.exams.size();
        assertEquals(sizeBefore,sizeAfter);

        Exam actual = controller.exams.getLast();
        assertEquals("Test Exam Updating", actual.getExamName());
        assertEquals(controller.courses.get(1).getCourseID(), actual.getCourse());
        assertEquals(1000, actual.getTimeLimit());
        assertFalse(actual.getPublished());
        assertEquals(0,actual.getExamQuestions().size());
    }

    @Test
    @Order(3)
    void updateExamQuestion(){
        int sizeBefore = controller.exams.size();

        selectBottomMostExam();

        moveTo("#questionTable");
        moveBy(0,-110);
        clickOn();
        Question activeQuestion;
        do {
            activeQuestion = controller.activeQuestion;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (activeQuestion != controller.activeQuestion);

        clickOn("#addQBtn");
        clickOn("#updateBtn");

        int sizeAfter = controller.exams.size();
        assertEquals(sizeBefore,sizeAfter);

        Exam actual = controller.exams.getLast();
        assertEquals("Test Exam Updating", actual.getExamName());
        assertEquals(controller.courses.get(1).getCourseID(), actual.getCourse());
        assertEquals(1000, actual.getTimeLimit());
        assertFalse(actual.getPublished());
        assertEquals(1,actual.getExamQuestions().size());
    }

    @Test
    @Order(4)
    void testRemoveExamQuestion(){
        int sizeBefore = controller.exams.size();

        selectBottomMostExam();

        moveTo("#examManagementTable");
        moveBy(10,-105);
        clickOn();
        Question activeQuestion;
        do {
            activeQuestion = controller.activeListedQ;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (activeQuestion != controller.activeListedQ);

        clickOn("#removeQBtn");
        clickOn("#updateBtn");

        int sizeAfter = controller.exams.size();
        assertEquals(sizeBefore,sizeAfter);

        Exam actual = controller.exams.getLast();
        assertEquals("Test Exam Updating", actual.getExamName());
        assertEquals(controller.courses.get(1).getCourseID(), actual.getCourse());
        assertEquals(1000, actual.getTimeLimit());
        assertFalse(actual.getPublished());
        assertEquals(0,actual.getExamQuestions().size());
    }

    @Test
    @Order(5)
    void UpdateQuestionFailed(){
        clickOn("#addQBtn");
        assertEquals(0,controller.examQuestions.size());

        moveTo("#questionTable");
        moveBy(0,-110);
        clickOn();
        Question activeQuestion;
        do {
            activeQuestion = controller.activeQuestion;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (activeQuestion != controller.activeQuestion);

        clickOn("#removeQBtn");
        assertEquals(0,controller.examQuestions.size());
    }

    @Test
    @Order(6)
    void filterExam(){
        clickOn("#examFilter");
        write("Test");
        clickOn("#filterExamBtn");

        moveTo("#examTable");
        moveBy(0,-35);
        clickOn();
        Exam activeExam;
        do {
            activeExam = controller.activeExam;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
            assertTrue(activeExam.getExamName().contains("Test"));
        } while (activeExam != controller.activeExam);
    }

    @Test
    @Order(7)
    void filterCourse(){
        clickOn("#courseFilter");
        moveBy(0, 80);
        clickOn();
        write("Test");
        clickOn("#filterExamBtn");

        moveTo("#examTable");
        moveBy(0,-35);
        clickOn();
        Exam activeExam;
        do {
            activeExam = controller.activeExam;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
            assertEquals("ELEC 2100",activeExam.getCourse());
        } while (activeExam != controller.activeExam);
    }

    @Test
    @Order(8)
    void filterPublished(){
        clickOn("#publishFilter");
        moveBy(0, 60);
        clickOn();
        clickOn("#filterExamBtn");

        moveTo("#examTable");
        moveBy(0,-35);
        clickOn();
        Exam activeExam;
        do {
            activeExam = controller.activeExam;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
            assertFalse(activeExam.getPublished());
        } while (activeExam != controller.activeExam);
    }

    @Test
    @Order(9)
    void filterQuestion(){
        clickOn("#questionFilter");
        write("spell");
        clickOn("#filterQuestionBtn");

        moveTo("#questionTable");
        moveBy(0,-110);
        clickOn();
        Exam activeExam;
        Question activeQuestion;
        do {
            activeQuestion = controller.activeQuestion;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
            assertTrue(activeQuestion.getQuestionName().contains("spell"));
        } while (activeQuestion != controller.activeQuestion);
    }

    @Test
    @Order(10)
    void filterQuestionType(){
        clickOn("#typeFilter");
        moveBy(0, 20);
        clickOn();
        clickOn("#filterQuestionBtn");

        moveTo("#questionTable");
        moveBy(0,-110);
        clickOn();
        Question activeQuestion;
        do {
            activeQuestion = controller.activeQuestion;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
            assertEquals("Single",activeQuestion.getQuestionType());
        } while (activeQuestion != controller.activeQuestion);
    }

    @Test
    @Order(11)
    void filterScore(){
        clickOn("#scoreFilter");
        moveBy(-20, 80);
        clickOn();
        clickOn("#filterQuestionBtn");

        moveTo("#questionTable");
        moveBy(0,-110);
        clickOn();
        Exam activeExam;
        Question activeQuestion;
        do {
            activeQuestion = controller.activeQuestion;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
            assertEquals(20,activeQuestion.getScore());
        } while (activeQuestion != controller.activeQuestion);
    }

    @Test
    @Order(12)
    void testModifyPublishedExam(){
        moveTo("#examTable");
        moveBy(0,-40);
        clickOn();

        clickOn("#examField");
        write(" Updating");

        clickOn("#updateBtn");
        clickOn("#deleteBtn");

        Exam actual = controller.exams.getFirst();
        assertEquals("Water Quiz", actual.getExamName());
        assertTrue(actual.getPublished());
        assertEquals(1,actual.getExamQuestions().size());
    }

    @Order(14)
    @Test
    void testDeleteExam(){
        int sizeBefore = controller.exams.size();
        moveTo("#examTable");
        moveBy(0,-35);
        clickOn();

        Exam activeExam;
        do {
            activeExam = controller.activeExam;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (activeExam != controller.activeExam);

        clickOn("#deleteBtn");
        int sizeAfter = controller.exams.size();

        assertEquals(sizeBefore-1,sizeAfter);
    }

    @Test
    @Order(15)
    void addingEqualQuestions(){
        moveTo("#questionTable");
        moveBy(0,-110);
        clickOn();
        Question activeQuestion;
        int question_count_before = 1;
        do {
            question_count_before++;
            activeQuestion = controller.activeQuestion;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (activeQuestion != controller.activeQuestion);

        clickOn("#addQBtn");

        moveTo("#questionTable");
        moveBy(0,-110);
        clickOn();
        activeQuestion = null;
        int question_count_after = 1;
        do {
            question_count_after++;
            activeQuestion = controller.activeQuestion;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (activeQuestion != controller.activeQuestion);
        assertEquals(1, question_count_before - question_count_after);
    }

    void selectBottomMostExam(){
        moveTo("#examTable");
        moveBy(0,-35);
        clickOn();
        Exam activeExam;
        do {
            activeExam = controller.activeExam;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (activeExam != controller.activeExam);
    }
}