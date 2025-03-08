package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Course;

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
public class CourseManagementControllerTest extends ApplicationTest{

    CourseManagementController controller;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("CourseManagementUI.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));
        stage.show();
        controller = loader.<CourseManagementController>getController();
        controller.jsonFileLocation = "src/test/testDatabase/courseTest.json";
        controller.safetyKey = false;
    }


    @Test
    @Order(1)
    void addCourseAndFail(){
        int sizeBefore = controller.courses.size();
        clickOn(controller.buttonAdd);

        clickOn(controller.courseIDField);
        write("test");
        clickOn(controller.buttonAdd);

        clickOn(controller.courseIDField);
        doubleClickOn();
        write("TEST 1000");
        clickOn(controller.buttonAdd);

        clickOn(controller.courseNameField);
        write("@@@");
        clickOn(controller.buttonAdd);

        clickOn(controller.courseNameField);
        doubleClickOn();
        write("test");
        clickOn(controller.buttonAdd);

        clickOn(controller.departmentField);
        write("123");
        clickOn(controller.buttonAdd);

        clickOn(controller.departmentField);
        doubleClickOn();
        write("TEST");
        clickOn(controller.buttonAdd);

        clickOn(controller.courseIDField);
        write("TEST 1000");


        clickOn(controller.courseNameField);
        write("test");


        clickOn(controller.departmentField);
        write("TEST");

        clickOn(controller.buttonAdd);

        int sizeAfter = controller.courses.size();
        assertEquals(sizeBefore+1, sizeAfter);
        Course target = controller.courses.getLast();
        assertEquals("TEST 1000", target.getCourseID());
        assertEquals("test", target.getCourseName());
        assertEquals("TEST", target.getDepartment());
    }

    @Test
    @Order(2)
    void updateCourse(){
        int sizeBefore = controller.courses.size();
        moveTo(controller.courseTable);
        clickOn();

        Course tableLast ;
        do {
            tableLast = controller.selectedCourse;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (tableLast != controller.selectedCourse);

        clickOn(controller.courseNameField);

        doubleClickOn();
        write("test");

        clickOn(controller.buttonUpdate);
        int sizeAfter = controller.courses.size();
        Course target = controller.courses.getLast();
        assertEquals(sizeBefore, sizeAfter);
        assertEquals(tableLast.getCourseID(), target.getCourseID());
        assertEquals("test", target.getCourseName());
        assertEquals(tableLast.getDepartment(), target.getDepartment());
        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    @Order(3)
    void filterCourseAndReset(){
        moveTo(controller.courseTable);
        clickOn();
        Course tableLast;
        do {
            tableLast = controller.selectedCourse;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (tableLast != controller.selectedCourse);
        clickOn(controller.courseIDFilter);
        write(tableLast.getCourseID());

        clickOn(controller.courseNameFilter);
        write(tableLast.getCourseName());

        clickOn(controller.departmentFilter);
        write(tableLast.getDepartment());

        clickOn(controller.buttonFilter);
        moveTo(controller.courseTable);
        clickOn();
        Course tableFirst = controller.selectedCourse;

        assertEquals(tableLast.getCourseID(), tableFirst.getCourseID());
        assertEquals(tableLast.getCourseName(), tableFirst.getCourseName());
        assertEquals(tableLast.getDepartment(), tableFirst.getDepartment());
        clickOn(controller.buttonReset);
        assertNull(controller.courseIDFilter.getText());
        assertNull(controller.courseNameFilter.getText());
        assertNull(controller.departmentFilter.getText());
    }


    @Test
    @Order(4)
    void deleteCourse(){
        int sizeBefore = controller.courses.size();
        moveTo(controller.courseTable);
        clickOn();
        Course activeCourse;
        do {
            activeCourse = controller.selectedCourse;
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        } while (activeCourse != controller.selectedCourse);
        clickOn(controller.buttonDelete);
        int sizeAfter = controller.courses.size();
        assertEquals(sizeBefore-1, sizeAfter);
    }

}
