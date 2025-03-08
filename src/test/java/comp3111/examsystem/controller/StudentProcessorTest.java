package comp3111.examsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.entity.Student;
import comp3111.examsystem.entity.StudentProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentProcessorTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static List<Student> users;
    private static File userListFile;
    final String jsonFileLocation = "src/test/testDatabase/userIntoTestForStudent.json";
    private StudentProcessor controller;

    @BeforeEach
    public void setUp() {
        controller = new StudentProcessor(jsonFileLocation);
        System.setOut(new PrintStream(outContent));
        try{
            userListFile = new File(jsonFileLocation);
            users = objectMapper.readValue(userListFile, new TypeReference<List<Student>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to read json.");
        }
    }

    String getUserInformation(Student user){
        return "Username: " + user.getUsername() + "\n" +
                "Name: " + user.getName() + "\n" +
                "Gender: " + user.getGender() + "\n" +
                "Age: " + user.getAge() + "\n" +
                "Department: " + user.getDepartment() + "\n" +
                "Password: " + user.getPassword() + "\n" +
                "-----\n";
    }

    @Test
    void getAllUser() {
        // Act
        StudentProcessor.getAllUser();
        // Assert
        String expectedOutput =
                "Username: alreadyExist\n" +
                "Name: alreadyExist\n" +
                "Gender: Male\n" +
                "Age: 12\n" +
                "Department: CS\n" +
                "Password: password!\n" +
                "-----\n";

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void getPassword1() {
        String expected = "password!";
        String actual = StudentProcessor.getPassword("alreadyExist");

        assertEquals(expected, actual);
    }

    @Test
    void getPassword2() {
        String expected = "";
        String actual = StudentProcessor.getPassword("test4");

        assertEquals(expected, actual);
    }

    @Test
    void checkIfUserNameExists1() {
        boolean expected = true;
        boolean actual = StudentProcessor.checkIfUserNameExists("alreadyExist");

        assertEquals(expected, actual);
    }

    @Test
    void checkIfUserNameExists2() {
        boolean expected = false;
        boolean actual = StudentProcessor.checkIfUserNameExists("kjhgkfjhf");

        assertEquals(expected, actual);
    }

    @Test
    void addUser() {
    }

    @Test
    void loginUser1() {
        boolean expected = true;
        boolean actual = StudentProcessor.loginUser("alreadyExist", "password!");

        assertEquals(expected, actual);
    }

    @Test
    void loginUser2() {
        boolean expected = false;
        boolean actual = StudentProcessor.loginUser("test3", "password");

        assertEquals(expected, actual);
    }

    @Test
    void loginUser3() {
        boolean expected = false;
        boolean actual = StudentProcessor.loginUser("test3", "");

        assertEquals(expected, actual);
    }

    @Test
    void loginUser4() {
        boolean expected = false;
        boolean actual = StudentProcessor.loginUser("", "");

        assertEquals(expected, actual);
    }

    @Test
    void containsFoulLanguage1() {
        boolean expected = true;
        boolean actual = StudentProcessor.containsFoulLanguage("fuck");

        assertEquals(expected, actual);
    }

    @Test
    void containsFoulLanguage2() {
        boolean expected = false;
        boolean actual = StudentProcessor.containsFoulLanguage("hi");

        assertEquals(expected, actual);
    }

    @Test
    void isAlphaNumeric1() {
        assertEquals(true, StudentProcessor.isAlphaNumeric("abc"));
    }

    @Test
    void isAlphaNumeric2() {
        assertEquals(false, StudentProcessor.isAlphaNumeric("!@@"));
    }

    @Test
    void isAlpha1() {
        assertEquals(true, StudentProcessor.isAlpha("abc"));
    }

    @Test
    void isAlpha2() {
        assertEquals(false, StudentProcessor.isAlpha("!!@"));
    }

    @Test
    void isMaleFemale1() {
        assertEquals(true, StudentProcessor.isMaleFemale("Male"));
    }

    @Test
    void isMaleFemale2() {
        assertEquals(true, StudentProcessor.isMaleFemale("Female"));
    }

    @Test
    void isMaleFemale3() {
        assertEquals(false, StudentProcessor.isMaleFemale("Other"));
    }

    @Test
    void isSecurePassword1() {
        assertEquals(false, StudentProcessor.isSecurePassword("pass"));
    }

    @Test
    void isSecurePassword2() {
        assertEquals(false, StudentProcessor.isSecurePassword("password"));
    }

    @Test
    void isSecurePassword3() {
        assertEquals(true, StudentProcessor.isSecurePassword("password!"));
    }

    @Test
    void isSecurePassword4() {
        assertEquals(false, StudentProcessor.isSecurePassword("pass!"));
    }

    @Test
    void validateUserNameEqual1(){
        Student user = new Student("Kenneth", "Kenneth Leung", 40, "Male", "CSE", "kenneth!");
        assertTrue(StudentProcessor.checkUserNameEqual(user, "Kenneth"));
    }

    @Test
    void validateUserNameEqual2(){
        Student user = new Student("Kenneth", "Kenneth Leung", 40, "Male", "CSE", "kenneth!");
        assertFalse(StudentProcessor.checkUserNameEqual(user, "Leung"));
    }

    @Test
    void validatePasswordEmpty1(){
        assertTrue(StudentProcessor.checkPasswordEmpty(""));
    }

    @Test
    void validatePasswordEmpty2(){
        assertFalse(StudentProcessor.checkPasswordEmpty("password!"));
    }

    @Test
    void validatePasswordEqual1(){
        assertTrue(StudentProcessor.checkPasswordEqual("password!", "password!"));
    }

    @Test
    void validatePasswordEqual2(){
        assertFalse(StudentProcessor.checkPasswordEqual("password!", "password"));
    }

    @Test
    void validateLowerCaseContainWord1(){
        assertTrue(StudentProcessor.checkLowerCaseContainWord("low", "low"));
    }

    @Test
    void validateLowerCaseContainWord2(){
        assertFalse(StudentProcessor.checkLowerCaseContainWord("low", "high"));
    }
}