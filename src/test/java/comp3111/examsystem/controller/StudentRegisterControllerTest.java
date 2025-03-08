package comp3111.examsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.testfx.framework.junit5.ApplicationTest;


import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentRegisterControllerTest extends ApplicationTest  {

    @FXML
    private Text passwordNotSecureWarning = new Text("hi");

    List<String> sampleUser = Arrays.asList("Kenneth Leung", "Kenneth Leung", "Male", "40", "CSE", "kenneth!", "kenneth!");

    @Test
    void checkSetShowPasswordButton1(){
        assertTrue(StudentRegisterController.setShowPasswordButton(true));
    }

    @Test
    void checkSetShowPasswordButton2(){
        assertFalse(StudentRegisterController.setShowPasswordButton(false));
    }


    @Test
    void setWarningVisibility1(){
        boolean expected = true;
        boolean actual = StudentRegisterController.setWarningVisibility(passwordNotSecureWarning, true);

        assertEquals(expected, actual);
    }

    @Test
    void setWarningVisibility2(){
        boolean expected = false;
        boolean actual = StudentRegisterController.setWarningVisibility(passwordNotSecureWarning, false);

        assertEquals(expected, actual);
    }

    @Test
    void validateAgePresented1(){
        List<String> userInfo = List.of("Kenneth", "Kenneth", "Male", "12");
        assertTrue(StudentRegisterController.checkAgePresented(userInfo));
    }

    @Test
    void validateAgePresented2(){
        List<String> userInfo = List.of("Kenneth", "Kenneth", "Male", "");
        assertFalse(StudentRegisterController.checkAgePresented(userInfo));
    }

    @Test
    void validateUsername1() {
        assertEquals(false, StudentRegisterController.checkUsernameValid(sampleUser.get(0)));
    }

    @Test
    void validateUsername2() {
        assertEquals(true, StudentRegisterController.checkUsernameValid(""));
    }

    @Test
    void validateUsername3() {
        assertEquals(true, StudentRegisterController.checkUsernameValid("fuck"));
    }

    @Test
    void validateUsername4() {
        assertEquals(true, StudentRegisterController.checkUsernameValid("!!@"));
    }


    //@Test
    //void validateUsername5() {
    //    assertTrue(StudentRegisterController.checkUsernameExit("test"));
    //}
    //@Test
    //void validateUsername6() {
    //    assertEquals(true, StudentRegisterController.checkUsernameExit("test"));
    //}

    //@Test
    //void validateUsername7() {
    //    assertEquals(false, StudentRegisterController.checkUsernameExit(sampleUser.get(0)));
    //}


    @Test
    void validateName1() {
        assertEquals(false, StudentRegisterController.checkNameValid(sampleUser.get(1)));
    }

    @Test
    void validateName2() {
        assertEquals(true, StudentRegisterController.checkNameValid(""));
    }

    @Test
    void validateName3() {
        assertEquals(true, StudentRegisterController.checkNameValid("fuck"));
    }

    @Test
    void validateGender1() {
        assertEquals(true, StudentRegisterController.checkGenderEmpty("Gender"));
    }

    @Test
    void validateGender2() {
        assertEquals(false, StudentRegisterController.checkGenderEmpty("Male"));
    }

    @Test
    void validateAge1() {
        assertEquals(false, StudentRegisterController.checkAgeEmpty(true));
    }

    @Test
    void validateAge2() {
        assertEquals(true, StudentRegisterController.checkAgeEmpty(false));
    }

    @Test
    void validateAge3() {
        assertEquals(true, StudentRegisterController.checkAgeRange(0));
    }

    @Test
    void validateAge4() {
        assertEquals(true, StudentRegisterController.checkAgeRange(81));
    }

    @Test
    void validateAge5() {
        assertEquals(false, StudentRegisterController.checkAgeRange(1));
    }

    @Test
    void validateAge6() {
        assertEquals(false, StudentRegisterController.checkAgeRange(79));
    }

    @Test
    void validateDepartment1() {
        assertEquals(true, StudentRegisterController.checkDepartmentEmpty(""));
    }

    @Test
    void validateDepartment2() {
        assertEquals(false, StudentRegisterController.checkDepartmentEmpty("CSE"));
    }

    @Test
    void validateDepartment3() {
        assertEquals(true, StudentRegisterController.checkDepartmentFormat("CSE101"));
    }

    @Test
    void validatePassword1() {
        assertEquals(true, StudentRegisterController.checkPasswordEmpty(""));
    }

    @Test
    void validatePassword2() {
        assertEquals(false, StudentRegisterController.checkPasswordEmpty("password"));
    }

    @Test
    void validatePassword3() {
        assertEquals(true, StudentRegisterController.checkPasswordSecure("password"));
    }

    @Test
    void validatePassword4() {
        assertEquals(false, StudentRegisterController.checkPasswordSecure("password!"));
    }




    @Test
    void validateNameWarning1(){
        assertFalse(StudentRegisterController.checkNameWarning(passwordNotSecureWarning, "username"));
    }

    @Test
    void validateNameWarning2(){
        assertTrue(StudentRegisterController.checkNameWarning(passwordNotSecureWarning, ""));
    }

    @Test
    void validateGenderMissingWarning1(){
        assertFalse(StudentRegisterController.checkGenderMissingWarning(passwordNotSecureWarning, "male"));
    }

    @Test
    void validateGenderMissingWarning2(){
        assertTrue(StudentRegisterController.checkGenderMissingWarning(passwordNotSecureWarning, "Gender"));
    }




    @Test
    void validateConfirmedPassword1() {
        assertEquals(true, StudentRegisterController.checkConfirmPasswordEmpty(""));
    }

    @Test
    void validateConfirmedPassword2() {
        assertEquals(false, StudentRegisterController.checkConfirmPasswordEmpty("password"));
    }

    @Test
    void validateConfirmedPassword3() {
        assertEquals(true, StudentRegisterController.checkConfirmPasswordEqual("password!", "password"));
    }

    @Test
    void validateConfirmedPassword4() {
        assertEquals(false, StudentRegisterController.checkConfirmPasswordEqual("password!", "password!"));
    }


    @Test
    void registerButtonPressed() {
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    StudentRegisterController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("StudentRegisterUI.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));

        StudentRegisterController controllerInit = loader.getController();
        controllerInit.setStudentInfoJsonFileLocation("src/test/testDatabase/userIntoTestForStudent.json");
        controllerInit.initialize();

        stage.show();
        controller = loader.<StudentRegisterController>getController();
        controller.setStudentInfoJsonFileLocation("src/test/testDatabase/userIntoTestForStudent.json");
    }

    @Test
    @Order(0)
    void registerAllNoEntry(){
        clickOn(controller.registerButton);

        assertTrue(controller.usernameWarning.isVisible());
        assertTrue(controller.nameWarning.isVisible());
        assertTrue(controller.ageMissingWarning.isVisible());
        assertTrue(controller.departmentMissingWarning.isVisible());
        assertTrue(controller.genderMissingWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(1)
    void registerFoulLang(){
        controller.usernameTxt.setText("Fuck");
        clickOn(controller.registerButton);

        assertTrue(controller.usernameWarning.isVisible());
        assertTrue(controller.nameWarning.isVisible());
        assertTrue(controller.ageMissingWarning.isVisible());
        assertTrue(controller.departmentMissingWarning.isVisible());
        assertTrue(controller.genderMissingWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(2)
    void registerInvalidCharacter(){
        controller.usernameTxt.setText("!!!!");
        clickOn(controller.registerButton);

        assertTrue(controller.usernameWarning.isVisible());
        assertTrue(controller.nameWarning.isVisible());
        assertTrue(controller.ageMissingWarning.isVisible());
        assertTrue(controller.departmentMissingWarning.isVisible());
        assertTrue(controller.genderMissingWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(3)
    void registerSameUsername(){
        controller.usernameTxt.setText("alreadyExist");
        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertTrue(controller.usernameExistWarning.isVisible());
        assertTrue(controller.nameWarning.isVisible());
        assertTrue(controller.ageMissingWarning.isVisible());
        assertTrue(controller.departmentMissingWarning.isVisible());
        assertTrue(controller.genderMissingWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(4)
    void registerCorrectUsername(){
        controller.usernameTxt.setText("dummy");
        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertFalse(controller.usernameExistWarning.isVisible());
        assertTrue(controller.nameWarning.isVisible());
        assertTrue(controller.ageMissingWarning.isVisible());
        assertTrue(controller.departmentMissingWarning.isVisible());
        assertTrue(controller.genderMissingWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(5)
    void registerNameFoulLang(){
        controller.usernameTxt.setText("dummy");
        controller.nameTxt.setText("fuck");
        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertTrue(controller.nameWarning.isVisible());
        assertTrue(controller.ageMissingWarning.isVisible());
        assertTrue(controller.departmentMissingWarning.isVisible());
        assertTrue(controller.genderMissingWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(6)
    void registerNameInvalidChar(){
        controller.usernameTxt.setText("dummy");
        controller.nameTxt.setText("!!!");
        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertTrue(controller.nameWarning.isVisible());
        assertTrue(controller.ageMissingWarning.isVisible());
        assertTrue(controller.departmentMissingWarning.isVisible());
        assertTrue(controller.genderMissingWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(7)
    void registerCorrectName(){
        controller.usernameTxt.setText("dummy");
        controller.nameTxt.setText("dummy");
        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertFalse(controller.nameWarning.isVisible());
        assertTrue(controller.ageMissingWarning.isVisible());
        assertTrue(controller.departmentMissingWarning.isVisible());
        assertTrue(controller.genderMissingWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(8)
    void registerCorrectGenderMale(){
        controller.usernameTxt.setText("dummy");
        controller.nameTxt.setText("dummy");

        moveTo(controller.gender);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveBy(-50, 30);
        clickOn();

        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertFalse(controller.nameWarning.isVisible());
        assertFalse(controller.genderMissingWarning.isVisible());
        assertTrue(controller.ageMissingWarning.isVisible());
        assertTrue(controller.departmentMissingWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(9)
    void registerCorrectGenderFemale(){
        controller.usernameTxt.setText("dummy");
        controller.nameTxt.setText("dummy");

        moveTo(controller.gender);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveBy(-50, 60);
        clickOn();

        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertFalse(controller.nameWarning.isVisible());
        assertFalse(controller.genderMissingWarning.isVisible());
        assertTrue(controller.ageMissingWarning.isVisible());
        assertTrue(controller.departmentMissingWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(10)
    void registerInvalidAgeRange1(){
        controller.usernameTxt.setText("dummy");
        controller.nameTxt.setText("dummy");

        moveTo(controller.gender);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveBy(-50, 60);
        clickOn();

        moveTo(controller.ageTxt);
        clickOn();
        write("-1");

        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertFalse(controller.nameWarning.isVisible());
        assertFalse(controller.ageMissingWarning.isVisible());
        assertTrue(controller.ageNotInRangeWarning.isVisible());
        assertTrue(controller.departmentMissingWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(11)
    void registerInvalidAgeRange2(){
        controller.usernameTxt.setText("dummy");
        controller.nameTxt.setText("dummy");

        moveTo(controller.gender);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveBy(-50, 60);
        clickOn();

        moveTo(controller.ageTxt);
        clickOn();
        write("100");

        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertFalse(controller.nameWarning.isVisible());
        assertFalse(controller.genderMissingWarning.isVisible());
        assertFalse(controller.ageMissingWarning.isVisible());
        assertTrue(controller.ageNotInRangeWarning.isVisible());
        assertTrue(controller.departmentMissingWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(12)
    void registerInvalidAgeRange3(){
        controller.usernameTxt.setText("dummy");
        controller.nameTxt.setText("dummy");

        moveTo(controller.gender);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveBy(-50, 60);
        clickOn();

        moveTo(controller.ageTxt);
        clickOn();
        write("30");

        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertFalse(controller.nameWarning.isVisible());
        assertFalse(controller.genderMissingWarning.isVisible());
        assertFalse(controller.ageMissingWarning.isVisible());
        assertFalse(controller.ageNotInRangeWarning.isVisible());
        assertTrue(controller.departmentMissingWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(13)
    void registerInvalidDepart(){
        controller.usernameTxt.setText("dummy");
        controller.nameTxt.setText("dummy");

        moveTo(controller.gender);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveBy(-50, 60);
        clickOn();

        moveTo(controller.ageTxt);
        clickOn();
        write("30");

        moveTo(controller.departmentTxt);
        clickOn();
        write("!!!");

        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertFalse(controller.nameWarning.isVisible());
        assertFalse(controller.genderMissingWarning.isVisible());
        assertFalse(controller.ageMissingWarning.isVisible());
        assertFalse(controller.ageNotInRangeWarning.isVisible());
        assertFalse(controller.departmentMissingWarning.isVisible());
        assertTrue(controller.departmentInvalidCharWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(14)
    void registerValidDepart(){
        controller.usernameTxt.setText("dummy");
        controller.nameTxt.setText("dummy");

        moveTo(controller.gender);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveBy(-50, 60);
        clickOn();

        moveTo(controller.ageTxt);
        clickOn();
        write("30");

        moveTo(controller.departmentTxt);
        clickOn();
        write("CSE");

        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertFalse(controller.nameWarning.isVisible());
        assertFalse(controller.genderMissingWarning.isVisible());
        assertFalse(controller.ageMissingWarning.isVisible());
        assertFalse(controller.ageNotInRangeWarning.isVisible());
        assertFalse(controller.departmentMissingWarning.isVisible());
        assertFalse(controller.departmentInvalidCharWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordMissingWarning.isVisible());
    }

    @Test
    @Order(15)
    void registerNotSecurePw(){
        controller.usernameTxt.setText("dummy");
        controller.nameTxt.setText("dummy");

        moveTo(controller.gender);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveBy(-50, 60);
        clickOn();

        moveTo(controller.ageTxt);
        clickOn();
        write("30");

        moveTo(controller.departmentTxt);
        clickOn();
        write("CSE");

        moveTo(controller.passwordTxt);
        clickOn();
        write("password");

        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertFalse(controller.nameWarning.isVisible());
        assertFalse(controller.genderMissingWarning.isVisible());
        assertFalse(controller.ageMissingWarning.isVisible());
        assertFalse(controller.ageNotInRangeWarning.isVisible());
        assertFalse(controller.departmentMissingWarning.isVisible());
        assertFalse(controller.departmentInvalidCharWarning.isVisible());
        assertFalse(controller.passwordMissingWarning.isVisible());
        assertTrue(controller.passwordNotSecureWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
    }

    @Test
    @Order(16)
    void registerSecurePw(){
        controller.usernameTxt.setText("dummy");
        controller.nameTxt.setText("dummy");

        moveTo(controller.gender);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveBy(-50, 60);
        clickOn();

        moveTo(controller.ageTxt);
        clickOn();
        write("30");

        moveTo(controller.departmentTxt);
        clickOn();
        write("CSE");

        moveTo(controller.passwordTxt);
        clickOn();
        write("password!");

        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertFalse(controller.nameWarning.isVisible());
        assertFalse(controller.genderMissingWarning.isVisible());
        assertFalse(controller.ageMissingWarning.isVisible());
        assertFalse(controller.ageNotInRangeWarning.isVisible());
        assertFalse(controller.departmentMissingWarning.isVisible());
        assertFalse(controller.departmentInvalidCharWarning.isVisible());
        assertFalse(controller.passwordMissingWarning.isVisible());
        assertFalse(controller.passwordNotSecureWarning.isVisible());
        assertTrue(controller.passwordConfirmMissingWarning.isVisible());
    }

    @Test
    @Order(17)
    void registerConfirmPwNotMatch(){
        controller.usernameTxt.setText("dummy");
        controller.nameTxt.setText("dummy");

        moveTo(controller.gender);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveBy(-50, 60);
        clickOn();

        moveTo(controller.ageTxt);
        clickOn();
        write("30");

        moveTo(controller.departmentTxt);
        clickOn();
        write("CSE");

        moveTo(controller.passwordTxt);
        clickOn();
        write("password!");

        moveTo(controller.passwordConfirmTxt);
        clickOn();
        write("password");

        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertFalse(controller.nameWarning.isVisible());
        assertFalse(controller.genderMissingWarning.isVisible());
        assertFalse(controller.ageMissingWarning.isVisible());
        assertFalse(controller.ageNotInRangeWarning.isVisible());
        assertFalse(controller.departmentMissingWarning.isVisible());
        assertFalse(controller.departmentInvalidCharWarning.isVisible());
        assertFalse(controller.passwordMissingWarning.isVisible());
        assertFalse(controller.passwordNotSecureWarning.isVisible());
        assertFalse(controller.passwordConfirmMissingWarning.isVisible());
        assertTrue(controller.passwordConfirmNotMatchWarning.isVisible());
    }

    @Test
    @Order(18)
    void registerConfirmPwMatch(){
        controller.usernameTxt.setText("dummy");
        controller.nameTxt.setText("dummy");

        moveTo(controller.gender);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveBy(-50, 60);
        clickOn();

        moveTo(controller.ageTxt);
        clickOn();
        write("30");

        moveTo(controller.departmentTxt);
        clickOn();
        write("CSE");

        moveTo(controller.passwordTxt);
        clickOn();
        write("password!");

        moveTo(controller.passwordConfirmTxt);
        clickOn();
        write("password!");

        clickOn(controller.registerButton);

        assertFalse(controller.usernameWarning.isVisible());
        assertFalse(controller.nameWarning.isVisible());
        assertFalse(controller.genderMissingWarning.isVisible());
        assertFalse(controller.ageMissingWarning.isVisible());
        assertFalse(controller.ageNotInRangeWarning.isVisible());
        assertFalse(controller.departmentMissingWarning.isVisible());
        assertFalse(controller.departmentInvalidCharWarning.isVisible());
        assertFalse(controller.passwordMissingWarning.isVisible());
        assertFalse(controller.passwordNotSecureWarning.isVisible());
        assertFalse(controller.passwordConfirmMissingWarning.isVisible());
        assertFalse(controller.passwordConfirmNotMatchWarning.isVisible());

        //Remove new added entry
        ObjectMapper objectMapper = new ObjectMapper();
        File userInfoListFile = new File("src/test/testDatabase/userIntoTestForStudent.json");
        try{
            List<Student> userInfoList = objectMapper.readValue(userInfoListFile, new TypeReference<List<Student>>() {
            });

            userInfoList.removeLast();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(userInfoListFile, userInfoList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(19)
    void registerShowPw(){
        moveTo(controller.passwordTxt);
        clickOn();
        write("password!");

        clickOn(controller.showPasswordButton);
        assertTrue(controller.passwordTxtShow.isVisible());
        assertFalse(controller.passwordTxt.isVisible());
    }

    @Test
    @Order(20)
    void registerShowConfirmPw(){
        moveTo(controller.passwordConfirmTxt);
        clickOn();
        write("password!");

        clickOn(controller.showPasswordConfirmButton);
        assertTrue(controller.passwordConfirmTxtShow.isVisible());
        assertFalse(controller.passwordConfirmTxt.isVisible());
    }

}