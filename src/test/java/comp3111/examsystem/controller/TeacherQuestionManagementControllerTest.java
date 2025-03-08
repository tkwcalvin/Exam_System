package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Question;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.*;

import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TeacherQuestionManagementControllerTest extends ApplicationTest {

    TeacherQuestionManagementController controller;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("TeacherQuestionManagement.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));
        stage.show();
        controller = loader.<TeacherQuestionManagementController>getController();
        controller.disableAlert = true;
        controller.jsonFileLocation = "src/test/testDatabase/questionBankTest.json";
    }

    @BeforeEach
    void refresh(){
        clickOn("#refreshBtn");
    }

    @Test
    @Order(0)
    void addQuestion() {
        int sizeBefore = controller.questions.size();
        clickOn("#questionField");
        write("Test Question");
        clickOn("#optionAField");
        write("Option A");
        clickOn("#optionBField");
        write("Option B");
        clickOn("#optionCField");
        write("Option C");
        clickOn("#optionDField");
        write("Option D");
        clickOn("#checkboxC");
        clickOn("#scoreField");
        write("100");
        clickOn("#addBtn");

        int sizeAfter = controller.questions.size();
        Question actual = controller.questions.getLast();

        assertEquals(sizeBefore+1, sizeAfter);
        assertEquals("Test Question", actual.getQuestionName());
        assertEquals("Single", actual.getQuestionType());
        assertEquals("Option A", actual.getOptionA());
        assertEquals("Option B", actual.getOptionB());
        assertEquals("Option C", actual.getOptionC());
        assertEquals("Option D", actual.getOptionD());
        assertEquals(Arrays.asList('C'), actual.getAnswer());
        assertEquals(100, actual.getScore());
    }

    @Test
    @Order(1)
    void addQuestionFailed() {
        int sizeBefore = controller.questions.size();
        clickOn("#addBtn");
        clickOn("#questionField");
        write("Test Question");
        clickOn("#addBtn");
        clickOn("#optionAField");
        write("Option A");
        clickOn("#addBtn");
        clickOn("#optionBField");
        write("Option B");
        clickOn("#addBtn");
        clickOn("#optionCField");
        write("Option C");
        clickOn("#addBtn");
        clickOn("#optionDField");
        write("Option D");
        clickOn("#addBtn");
        clickOn("#checkboxC");
        clickOn("#addBtn");
        clickOn("#scoreField");
        write("-1");
        clickOn("#addBtn");
        clickOn("#scoreField");
        write(" is Not a Score");
        clickOn("#addBtn");

        int sizeAfter = controller.questions.size();

        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    @Order(2)
    void updateQuestionFailed() {
        int sizeBefore = controller.questions.size();
        clickOn("#UpdateBtn");

        moveTo("#questionTable");
        moveBy(0,-300);
        clickOn();
        Question activeQuestion;
        do {
            activeQuestion = controller.activeQuestion;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (activeQuestion != controller.activeQuestion);

        clickOn("#clearBtn");
        int sizeAfter = controller.questions.size();

        clickOn("#UpdateBtn");

        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    @Order(3)
    void updateQuestion() {
        int sizeBefore = controller.questions.size();
        moveTo("#questionTable");
        moveBy(0,-300);
        clickOn();
        Question activeQuestion;
        do {
            activeQuestion = controller.activeQuestion;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (activeQuestion != controller.activeQuestion);

        doubleClickOn("#questionField");
        clickOn("#questionField");
        write("Test Update Question");
        clickOn("#typeField");
        moveBy(0, 30);
        clickOn();
        doubleClickOn("#optionAField");
        clickOn("#optionAField");
        write("Option A1");
        doubleClickOn("#optionBField");
        clickOn("#optionBField");
        write("Option B1");
        doubleClickOn("#optionCField");
        clickOn("#optionCField");
        write("Option C1");
        doubleClickOn("#optionDField");
        clickOn("#optionDField");
        write("Option D1");
        clickOn("#checkboxD");
        doubleClickOn("#scoreField");
        clickOn("#scoreField");
        write("50");
        clickOn("#UpdateBtn");

        int sizeAfter = controller.questions.size();
        Question actual = controller.questions.getLast();

        assertEquals(sizeBefore, sizeAfter);
        assertEquals("Test Update Question", actual.getQuestionName());
        assertEquals("Multiple", actual.getQuestionType());
        assertEquals("Option A1", actual.getOptionA());
        assertEquals("Option B1", actual.getOptionB());
        assertEquals("Option C1", actual.getOptionC());
        assertEquals("Option D1", actual.getOptionD());
        assertEquals(Arrays.asList('C','D'), actual.getAnswer());
        assertEquals(50, actual.getScore());
    }

    @Test
    @Order(4)
    void filterQuestion() {
        clickOn("#scoreFilter");
        moveBy(-30,60);
        clickOn();

        clickOn("#filterBtn");

        moveTo("#questionTable");
        moveBy(0,-300);
        clickOn();
        Question activeQuestion;
        do {
            activeQuestion = controller.activeQuestion;
            assertEquals(10, activeQuestion.getScore(), "Score Filter doesn't work");
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (activeQuestion != controller.activeQuestion);

        clickOn("#resetBtn");

        clickOn("#typeFilter");
        moveBy(0,60);
        clickOn();

        clickOn("#filterBtn");

        moveTo("#questionTable");
        moveBy(0,-300);
        clickOn();
        do {
            activeQuestion = controller.activeQuestion;
            assertEquals("Multiple", activeQuestion.getQuestionType(), "Type filter Doesn't work");
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (activeQuestion != controller.activeQuestion);

    }

    @Test
    @Order(5)
    void deleteQuestion() {
        int sizeBefore = controller.questions.size();
        moveTo("#questionTable");
        moveBy(0,-300);
        clickOn();
        Question activeQuestion;
        do {
            activeQuestion = controller.activeQuestion;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (activeQuestion != controller.activeQuestion);
        clickOn("#DeleteBtn");
        int sizeAfter = controller.questions.size();
        assertEquals(sizeBefore-1, sizeAfter);
    }

}