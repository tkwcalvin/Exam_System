package comp3111.examsystem.controller;

import comp3111.examsystem.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)


public class ManagerLoginControllerTest extends ApplicationTest{
    ManagerLoginController controller;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("ManagerLoginUI.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));
        stage.show();
        controller = loader.<ManagerLoginController>getController();
    }

    @Test
    @Order(0)
    void validateCredentials(){
        clickOn(controller.buttonLogin);
        clickOn(controller.usernameTxt);
        write("admin");
        clickOn(controller.passwordTxt);
        write("calvin");
        clickOn(controller.buttonLogin);
        assertTrue(controller.status);
    }


}

