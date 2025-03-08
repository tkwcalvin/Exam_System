package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.*;

import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherLoginControllerTest extends ApplicationTest{

    TeacherLoginController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("TeacherLoginUI.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));
        stage.show();
        controller = loader.<TeacherLoginController>getController();
    }

    /**
     * This test
     */
    @Test
    void testSuccessfulLogin(){
        clickOn("#usernameTxt");
        write("teacher1");
        clickOn("#passwordTxt");
        write("teacher#1");

        clickOn("#loginBtn");

        assertTrue(controller.loggedIn);
    }

    @Test
    void testSuccessfulLoginWithPasswordShown(){
        clickOn("#usernameTxt");
        write("teacher1");
        clickOn("#showPasswordButton");
        clickOn("#passwordTxtShow");
        write("teacher#1");

        clickOn("#loginBtn");

        assertTrue(controller.loggedIn);
    }

    /**
     * This test
     */
    @Test
    void testWrongUsernameLogin(){
        clickOn("#passwordTxt");
        write("teacher#1");
        clickOn("#loginBtn");
        assertFalse(controller.loggedIn);

        clickOn("#usernameTxt");
        write("teacherx");
        clickOn("#loginBtn");
        assertFalse(controller.loggedIn);
    }

    @Test
    void testWrongPasswordLogin(){
        clickOn("#usernameTxt");
        write("teacher1");

        clickOn("#passwordTxt");
        write("teacher");

        clickOn("#loginBtn");

        assertFalse(controller.loggedIn);

        clickOn("#showPasswordButton");
        clickOn("#passwordTxtShow");
        write("#");

        clickOn("#loginBtn");

        assertFalse(controller.loggedIn);

        clickOn("#showPasswordButton");
        clickOn("#passwordTxt");
        write("x");

        clickOn("#loginBtn");

        assertFalse(controller.loggedIn);
    }
}
