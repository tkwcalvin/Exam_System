package comp3111.examsystem.controller;

import comp3111.examsystem.entity.TeacherProcessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Teacher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.*;

import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherRegisterControllerTest extends ApplicationTest{

    TeacherRegisterController controller;
    TeacherProcessor userController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("TeacherRegisterUI.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));
        stage.show();
        controller = loader.<TeacherRegisterController>getController();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            File userListFile = new File("src/test/testDatabase/teacherUserInfoTest.json");
            TeacherProcessor.users = objectMapper.readValue(userListFile, new TypeReference<List<Teacher>>() {});
            TeacherProcessor.jsonFileLocation = "src/test/testDatabase/teacherUserInfoTest.json";
            TeacherProcessor.blockFileWriting = true;
        } catch (IOException e) {
            System.out.println("Failed to read json.");
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSuccessfulRegistrationFields(){
        assertEquals(2, TeacherProcessor.users.size());
        clickOn("#usernameTxt");
        write("TestingAgent");
        clickOn("#nameTxt");
        write("Lau Kin Lun");
        clickOn("#gender");
        moveBy(-60,30);
        clickOn();
        clickOn("#ageTxt");
        write("60");
        clickOn("#position");
        clickOn();
        clickOn("#departmentTxt");
        write("CSE");
        clickOn("#passwordTxt");
        write("P@ssWord");
        clickOn("#passwordConfirmTxt");
        write("P@ssWord");
        clickOn("#registerButton");

        assertEquals(3, TeacherProcessor.users.size());

        Teacher actual = TeacherProcessor.users.getLast();

        assertEquals("TestingAgent", actual.getUsername());
        assertEquals("Lau Kin Lun", actual.getName());
        assertEquals("Male", actual.getGender());
        assertEquals(60, actual.getAge());
        assertEquals("CSE", actual.getDepartment());
        assertEquals("P@ssWord", actual.getPassword());
    }

    @Test
    void testWrongUsernameField(){
        assertEquals(2, TeacherProcessor.users.size());
        clickOn("#nameTxt");
        write("Lau Kin Lun");
        clickOn("#gender");
        moveBy(-60,30);
        clickOn();
        clickOn("#ageTxt");
        write("60");
        clickOn("#position");
        clickOn();
        clickOn("#departmentTxt");
        write("CSE");
        clickOn("#passwordTxt");
        write("P@ssWord");
        clickOn("#passwordConfirmTxt");
        write("P@ssWord");
        clickOn("#registerButton");

        clickOn("#usernameTxt");
        write("teacher1");
        clickOn("#registerButton");

        clickOn("#usernameTxt");
        write("01.");
        clickOn("#registerButton");

        doubleClickOn("#usernameTxt");
        clickOn("#usernameTxt");
        write("teacher101");
        clickOn("#registerButton");
        assertEquals(3, TeacherProcessor.users.size());
    }

    @Test
    void testWrongNameField(){
        assertEquals(2, TeacherProcessor.users.size());
        clickOn("#usernameTxt");
        write("TestingAgent");
        clickOn("#gender");
        moveBy(-60,30);
        clickOn();
        clickOn("#ageTxt");
        write("60");
        clickOn("#position");
        clickOn();
        clickOn("#departmentTxt");
        write("CSE");
        clickOn("#passwordTxt");
        write("P@ssWord");
        clickOn("#passwordConfirmTxt");
        write("P@ssWord");
        clickOn("#registerButton");

        clickOn("#nameTxt");
        write("Lau Kin Lun 001#");
        clickOn("#registerButton");

        doubleClickOn("#nameTxt");
        clickOn("#nameTxt");
        doubleClickOn("#nameTxt");
        write("Lau Kin Lun 001");
        clickOn("#registerButton");


        assertEquals(3, TeacherProcessor.users.size());
    }

    @Test
    void testWrongAgeField(){
        assertEquals(2, TeacherProcessor.users.size());
        clickOn("#usernameTxt");
        write("TestingAgent");
        clickOn("#nameTxt");
        write("Lau Kin Lun");
        clickOn("#gender");
        moveBy(-60,30);
        clickOn();
        clickOn("#position");
        clickOn();
        clickOn("#departmentTxt");
        write("CSE");
        clickOn("#passwordTxt");
        write("P@ssWord");
        clickOn("#passwordConfirmTxt");
        write("P@ssWord");
        clickOn("#registerButton");

        clickOn("#ageTxt");
        write("6");
        clickOn("#registerButton");

        clickOn("#ageTxt");
        write("00");
        clickOn("#registerButton");

        doubleClickOn("#ageTxt");
        write("60a");
        clickOn("#registerButton");

        doubleClickOn("#ageTxt");
        write("60");
        clickOn("#registerButton");

        assertEquals(3, TeacherProcessor.users.size());
    }

    @Test
    void testWrongDepartmentField(){
        assertEquals(2, TeacherProcessor.users.size());
        clickOn("#usernameTxt");
        write("TestingAgent");
        clickOn("#nameTxt");
        write("Lau Kin Lun");
        clickOn("#ageTxt");
        write("60");
        clickOn("#gender");
        moveBy(-60,30);
        clickOn();
        clickOn("#position");
        clickOn();
        clickOn("#passwordTxt");
        write("P@ssWord");
        clickOn("#passwordConfirmTxt");
        write("P@ssWord");
        clickOn("#registerButton");

        clickOn("#departmentTxt");
        write("C6");
        clickOn("#registerButton");

        doubleClickOn("#departmentTxt");
        write("CSE#");
        clickOn("#registerButton");

        doubleClickOn("#departmentTxt");
        clickOn("#departmentTxt");
        write("CSE");
        clickOn("#registerButton");

        assertEquals(3, TeacherProcessor.users.size());
    }

    @Test
    void testWrongPasswordField(){
        assertEquals(2, TeacherProcessor.users.size());
        clickOn("#usernameTxt");
        write("TestingAgent");
        clickOn("#nameTxt");
        write("Lau Kin Lun");
        clickOn("#ageTxt");
        write("60");
        clickOn("#gender");
        moveBy(-60,30);
        clickOn();
        clickOn("#position");
        clickOn();
        clickOn("#departmentTxt");
        write("CSE");
        clickOn("#registerButton");

        clickOn("#passwordTxt");
        write("Pass");
        clickOn("#registerButton");

        clickOn("#passwordTxt");
        write("word?");
        clickOn("#registerButton");

        clickOn("#showPasswordButton");

        clickOn("#passwordTxtShow");
        doubleClickOn("#passwordTxtShow");
        write("Password!");
        clickOn("#registerButton");

        clickOn("#passwordConfirmTxt");
        write("Password_");
        clickOn("#registerButton");

        clickOn("#showPasswordConfirmButton");

        doubleClickOn("#passwordConfirmTxtShow");
        clickOn("#passwordConfirmTxtShow");
        write("Password!");
        clickOn("#registerButton");

        assertEquals(3, TeacherProcessor.users.size());
    }
    @Test
    void testEmptyGenderField(){
        assertEquals(2, TeacherProcessor.users.size());
        clickOn("#usernameTxt");
        write("TestingAgent");
        clickOn("#nameTxt");
        write("Lau Kin Lun");
        clickOn("#ageTxt");
        write("60");
        clickOn("#position");
        clickOn();
        clickOn("#departmentTxt");
        write("CSE");
        clickOn("#passwordTxt");
        write("P@ssWord");
        clickOn("#passwordConfirmTxt");
        write("P@ssWord");
        clickOn("#registerButton");

        clickOn("#gender");
        moveBy(-60,30);
        clickOn();
        clickOn("#registerButton");

        assertEquals(3, TeacherProcessor.users.size());
    }

    @Test
    void testEmptyPositionField(){
        assertEquals(2, TeacherProcessor.users.size());
        clickOn("#usernameTxt");
        write("TestingAgent");
        clickOn("#nameTxt");
        write("Lau Kin Lun");
        clickOn("#ageTxt");
        write("60");
        clickOn("#gender");
        moveBy(-60,30);
        clickOn();
        clickOn("#departmentTxt");
        write("CSE");
        clickOn("#passwordTxt");
        write("P@ssWord");
        clickOn("#passwordConfirmTxt");
        write("P@ssWord");
        clickOn("#registerButton");

        clickOn("#position");
        clickOn();
        clickOn("#registerButton");

        assertEquals(3, TeacherProcessor.users.size());
    }
}
