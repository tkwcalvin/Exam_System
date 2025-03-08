package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The class for the manager main controller
 */
public class ManagerMainController implements Initializable {
    @FXML
    private VBox mainbox;

    /**
     * For the page initialization
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * The button for opening student management UI
     */

    @FXML
    public void openStudentManageUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentManagementUI.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Student Manager");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        stage.show();
    }

    /**
     * The button for opening teacher management UI
     */

    @FXML
    public void openTeacherManageUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherManagementUI.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Teacher Manager");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        stage.show();
    }

    /**
     * The button for opening course management UI
     */

    @FXML
    public void openCourseManageUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CourseManagementUI.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Course Manager");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        stage.show();
    }

    /**
     * Exit the current manager main controller UI
     */

    @FXML
    public void exit() {
        System.exit(0);
    }
}
