package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherMainControllerTest extends ApplicationTest{

    TeacherMainController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("TeacherMainUI.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));
        stage.show();
        controller = loader.<TeacherMainController>getController();
    }

    @Test
    void testButton1(){
        assertDoesNotThrow(()->{
            clickOn("#qManageBtn");
        });
    }

    @Test
    void testButton2(){
        assertDoesNotThrow(()->{
            clickOn("#eManageBtn");
        });
    }

    @Test
    void testButton3(){
        assertDoesNotThrow(()->{
            clickOn("#gStatsBtn");
        });
    }
}
