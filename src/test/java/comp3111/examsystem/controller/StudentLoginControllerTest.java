package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import comp3111.examsystem.entity.StudentProcessor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.framework.junit5.ApplicationTest;


import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentLoginControllerTest extends ApplicationTest {

    StudentLoginController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("StudentLoginUI.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));
        stage.show();
        controller = loader.<StudentLoginController>getController();
    }

    @Test
    @Order(0)
    void loginAttemptNoNameNoPw(){
        clickOn(controller.loginButton);

        assertEquals("Please enter your username and password", controller.userNotFoundWarning.getText());
    }

    @Order(1)
    void loginAttemptHvNameNoPw(){
        controller.usernameTxt.setText("test");
        clickOn(controller.loginButton);

        assertEquals("Please enter your username and password", controller.userNotFoundWarning.getText());
    }

    @Order(2)
    void loginAttemptHvNameAndPwButWrongName(){
        controller.usernameTxt.setText("test");
        controller.passwordTxt.setText("password!");
        clickOn(controller.loginButton);

        assertEquals("User does not exist :(", controller.userNotFoundWarning.getText());
    }

    @Order(3)
    void loginAttemptTestCorrectNameWrongPw(){
        controller.usernameTxt.setText("marco");
        controller.passwordTxt.setText("wrongpassword!");
        clickOn(controller.loginButton);

        assertEquals("Incorrect Password", controller.userNotFoundWarning.getText());
    }

    @Order(4)
    void loginAttemptTestCorrectNameCorrectPw(){
        controller.usernameTxt.setText("marco");
        controller.passwordTxt.setText("marco!!!");
        clickOn(controller.loginButton);

        assertFalse(controller.userNotFoundWarning.isVisible());
    }



    //Test Logic
    //////////////////////////////////////////////////////////////////////////////////////

    @Test
    void validateUsername1(){
        assertEquals(true, StudentLoginController.checkUserNameEmpty(""));
    }

    @Test
    void validateUsername2(){
        assertEquals(false, StudentLoginController.checkUserNameEmpty("test"));
    }

    @Test
    void validatePassword1(){
        assertEquals(true, StudentLoginController.checkPasswordEmpty(""));
    }

    @Test
    void validatePassword2(){
        assertEquals(false, StudentLoginController.checkPasswordEmpty("test"));
    }

    @Test
    void validateUsernamePassword1(){
        assertTrue(StudentLoginController.checkUserNameEmptyAndPasswordEmpty("", ""));
    }

    @Test
    void validateUsernamePassword2(){
        assertTrue(StudentLoginController.checkUserNameEmptyAndPasswordEmpty("a", ""));
    }

    @Test
    void validateUsernamePassword3(){
        assertTrue(StudentLoginController.checkUserNameEmptyAndPasswordEmpty("", "a"));
    }

    @Test
    void validateUsernamePassword4(){
        assertFalse(StudentLoginController.checkUserNameEmptyAndPasswordEmpty("a", "a"));
    }

    @Test
    void validateUsernameExist1(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertFalse(StudentLoginController.checkUserNameExist(studentProcessor, "alreadyExist", "not important"));
    }

    @Test
    void validateUsernameExist2(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertTrue(StudentLoginController.checkUserNameExist(studentProcessor, "skldjflas", "not important"));
    }

    @Test
    void validateUsernameExist3(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertFalse(StudentLoginController.checkUserNameExist(studentProcessor, "", ""));
    }

    @Test
    void validateUsernameExist4(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertFalse(StudentLoginController.checkUserNameExist(studentProcessor, "test", ""));
    }

    @Test
    void validateUsernameExist5(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertFalse(StudentLoginController.checkUserNameExist(studentProcessor, "", "test"));
    }

    @Test
    void validateUsernameExist6(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertFalse(StudentLoginController.checkUserNameExist(studentProcessor, "", "test"));
    }

    //change userInfo to userInfoTest.json

    @Test
    void validateLogin1(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertFalse(StudentLoginController.checkUserLogin(studentProcessor, "", ""));
    }

    @Test
    void validateLogin2(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertFalse(StudentLoginController.checkUserLogin(studentProcessor, "test", ""));
    }

    @Test
    void validateLogin3(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertFalse(StudentLoginController.checkUserLogin(studentProcessor, "test", "test"));
    }

    @Test
    void validateLogin4(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertFalse(StudentLoginController.checkUserLogin(studentProcessor, "alreadyExist", "test"));
    }

    @Test
    void validateLogin5(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertTrue(StudentLoginController.checkUserLogin(studentProcessor, "alreadyExist", "password!"));
    }

    @Test
    void validateWrongPassword1(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertFalse(StudentLoginController.checkWrongPassword(studentProcessor, "", ""));
    }

    @Test
    void validateWrongPassword2(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertFalse(StudentLoginController.checkWrongPassword(studentProcessor, "a", ""));
    }

    @Test
    void validateWrongPassword3(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertFalse(StudentLoginController.checkWrongPassword(studentProcessor, "a", "a"));
    }

    @Test
    void validateWrongPassword4(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertTrue(StudentLoginController.checkWrongPassword(studentProcessor, "alreadyExist", "dummy"));
    }

    @Test
    void validateWrongPassword5(){
        StudentProcessor studentProcessor = new StudentProcessor("src/test/testDatabase/userIntoTestForStudent.json");
        assertFalse(StudentLoginController.checkWrongPassword(studentProcessor, "alreadyExist", "password!"));
    }

    @Test
    void dummyTest1(){
        assertTrue(StudentLoginController.checkShowPasswordButton(true));
    }

    @Test
    void dummyTest2(){
        assertFalse(StudentLoginController.checkShowPasswordButton(false));
    }


}