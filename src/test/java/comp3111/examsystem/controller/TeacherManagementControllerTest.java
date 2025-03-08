package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Teacher;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TeacherManagementControllerTest extends ApplicationTest{

    TeacherManagementController controller;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("TeacherManagementUI.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));
        stage.show();
        controller = loader.<TeacherManagementController>getController();
        controller.jsonFileLocation= "src/test/testDatabase/teacherInfoTest.json";
        controller.safetyKey = false;
    }


    @Test
    @Order(1)
    void addTeacherAndFail(){
        int sizeBefore = controller.teachers.size();

        clickOn(controller.buttonAdd);

        clickOn(controller.usernameField);
        write("@@@");
        clickOn(controller.buttonAdd);

        clickOn(controller.usernameField);
        doubleClickOn();
        write("test");
        clickOn(controller.buttonAdd);

        clickOn(controller.nameField);
        write("@@@");
        clickOn(controller.buttonAdd);

        clickOn(controller.nameField);
        doubleClickOn();
        write("test");
        clickOn(controller.buttonAdd);

        clickOn(controller.genderBox);
        moveBy(0, 30);
        clickOn();
        clickOn(controller.buttonAdd);

        clickOn(controller.ageField);
        write("-10");
        clickOn(controller.buttonAdd);

        clickOn(controller.ageField);
        doubleClickOn();
        write("test");
        clickOn(controller.buttonAdd);

        clickOn(controller.ageField);
        doubleClickOn();
        write("20");
        clickOn(controller.buttonAdd);

        clickOn(controller.positionBox);
        moveBy(0, 30);
        clickOn();
        clickOn(controller.buttonAdd);


        clickOn(controller.departmentField);
        write("123");
        clickOn(controller.buttonAdd);

        clickOn(controller.departmentField);
        doubleClickOn();
        write("TEST");
        clickOn(controller.buttonAdd);

        clickOn(controller.passwordField);
        write("test");
        clickOn(controller.buttonAdd);

        clickOn(controller.passwordField);
        doubleClickOn();
        write("test12345#");
        clickOn(controller.buttonAdd);

        clickOn(controller.usernameField);
        write("test");

        clickOn(controller.nameField);
        write("test");


        clickOn(controller.genderBox);
        moveBy(0, 30);
        clickOn();

        clickOn(controller.ageField);
        write("20");

        clickOn(controller.positionBox);
        moveBy(0, 30);
        clickOn();

        clickOn(controller.departmentField);
        write("TEST");

        clickOn(controller.passwordField);
        write("test12345#");
        clickOn(controller.buttonAdd);
        int sizeAfter = controller.teachers.size();
        Teacher target = controller.teachers.getLast();
        assertEquals(sizeBefore+1, sizeAfter);
        assertEquals("test", target.getUsername());
        assertEquals("test", target.getName());
        assertEquals("Male", target.getGender());
        assertEquals(20, target.getAge());
        assertEquals("TEST", target.getDepartment());
        assertEquals("test12345#", target.getPassword());
        assertEquals("Chair Professor", target.getPosition());
    }

    @Test
    @Order(2)
    void updateTeacherAndFail(){
        int sizeBefore = controller.teachers.size();
        moveTo(controller.teacherTable);
        //moveBy(0,0);
        clickOn();
        Teacher tableLast;
        do {
            tableLast = controller.selectedTeacher;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (tableLast != controller.selectedTeacher);
        clickOn(controller.passwordField);
        doubleClickOn();

        write("test12345#");
        clickOn(controller.buttonUpdate);
        clickOn(controller.buttonUpdate);
        int sizeAfter = controller.teachers.size();
        Teacher target = controller.teachers.getLast();
        assertEquals(sizeBefore, sizeAfter);
        assertEquals("test12345#", target.getPassword());

    }

    @Test
    @Order(3)
    void filterTeacherAndReset(){
        moveTo(controller.teacherTable);
        clickOn();
        Teacher tableLast;
        do {
            tableLast = controller.selectedTeacher;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (tableLast != controller.selectedTeacher);
        clickOn(controller.usernameFilter);
        write(tableLast.getUsername());
        clickOn(controller.nameFilter);
        write(tableLast.getName());
        clickOn(controller.departmentFilter);
        write(tableLast.getDepartment());
        clickOn(controller.buttonFilter);
        moveTo(controller.teacherTable);
        clickOn();
        Teacher tableFirst = controller.selectedTeacher;
        assertEquals(tableLast.getUsername(), tableFirst.getUsername());
        assertEquals(tableLast.getName(), tableFirst.getName());
        assertEquals(tableLast.getDepartment(), tableFirst.getDepartment());
        clickOn(controller.buttonReset);
        assertNull(controller.usernameFilter.getText());
        assertNull(controller.nameFilter.getText());
        assertNull(controller.departmentFilter.getText());
    }


    @Test
    @Order(4)
    void deleteTeacherAndFail(){
        int sizeBefore = controller.teachers.size();
        moveTo(controller.teacherTable);
        clickOn();
        Teacher tableLast;
        do {
            tableLast = controller.selectedTeacher;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (tableLast != controller.selectedTeacher);
        clickOn(controller.buttonDelete);
        clickOn(controller.buttonDelete);
        int sizeAfter = controller.teachers.size();
        assertEquals(sizeBefore-1, sizeAfter);
    }

}
