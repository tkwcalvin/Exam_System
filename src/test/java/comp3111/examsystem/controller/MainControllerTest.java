package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainControllerTest extends ApplicationTest{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("LoginUI.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void testButton1(){
        assertDoesNotThrow(()->{
            clickOn("#studentBtn");
        });
    }

    @Test
    void testButton2(){
        assertDoesNotThrow(()->{
            clickOn("#teacherBtn");
        });
    }

    @Test
    void testButton3(){
        assertDoesNotThrow(()->{
            clickOn("#managerBtn");
        });
    }
}
