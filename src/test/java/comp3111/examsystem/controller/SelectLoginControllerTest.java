package comp3111.examsystem.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectLoginControllerTest {
    /*
    static class TestApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            // This method is required to initialize the JavaFX runtime
        }
    }

    @BeforeAll
    static void initJFX() {
        Thread thread = new Thread(() -> Application.launch(TestApp.class));
        thread.setDaemon(true);
        thread.start();
    }

    @Test
    void studentLogin() {
        assertDoesNotThrow(() -> Platform.runLater(() -> {
            SelectLoginController controller = new SelectLoginController();
            controller.studentLogin();
        }), "Failed");
    }

    @Test
    void teacherLogin() {
        assertDoesNotThrow(() -> Platform.runLater(() -> {
            SelectLoginController controller = new SelectLoginController();
            controller.teacherLogin();
        }), "Failed");
    }

    @Test
    void managerLogin() {
        assertDoesNotThrow(() -> Platform.runLater(() -> {
            SelectLoginController controller = new SelectLoginController();
            controller.managerLogin();
        }), "Failed");
    }
    */
}