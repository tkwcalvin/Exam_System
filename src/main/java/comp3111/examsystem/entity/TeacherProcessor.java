package comp3111.examsystem.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The class That controls teacher login, registration and account field validation
 */
public class TeacherProcessor {
    private static ObjectMapper objectMapper = new ObjectMapper();
    /**
     * A list of teacher in this software
     */
    public static List<Teacher> users;
    /**
     * File path to teacher database
     */
    public static String jsonFileLocation = "src/main/resources/database/teacherUserInfo.json";
    /**
     * A boolean to decide if writing to database is allowed
     */
    public static boolean blockFileWriting = false;

    /**
     * Default Constructor
     * This constructor loads the static variable user once
     */
    public TeacherProcessor(){
        try{
            File userListFile = new File(jsonFileLocation);
            users = objectMapper.readValue(userListFile, new TypeReference<List<Teacher>>() {});
        } catch (IOException e) {
            System.out.println("Failed to read json.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Provided the username, return the password  of the user
     * @param username The username of the user
     * @return The password String of the user
     */
    public static String getPassword(String username){
        for (Teacher user : users) {
            if(user.getUsername().equals(username)){
                return user.getPassword();
            }
        }
        return "";
    }

    /**
     * Provided the username, return the result
     * @param checkUsername The username of the user
     * @return The password String of the user
     */
    public static boolean checkIfUserNameExists(String checkUsername){
        for (Teacher user : users) {
            if(user.getUsername().equals(checkUsername)){
                return true;
            }
        }
        return false;
    }

    /**
     * Create a new Teacher record and write it into the teacher record database
     * @param username The username of the user
     * @param name The name of the user
     * @param gender THe gender of the user
     * @param age THe age of the user
     * @param position The position of the user
     * @param department The department of the user
     * @param password The password of the user
     * @return The result of creating the teacher record
     */
    public static boolean addUser(String username, String name, String gender,
                                  Integer age, String position, String department, String password){
           try{
                File userListFile = new File(jsonFileLocation);
                users = objectMapper.readValue(userListFile, new TypeReference<List<Teacher>>() {});
           } catch (IOException e) {
                System.out.println("Failed to read json.");
                throw new RuntimeException(e);
            }
            Teacher newUser = new Teacher();
            newUser.setUsername(username);
            newUser.setName(name);
            newUser.setGender(gender);
            newUser.setAge(age);
            newUser.setPosition(position);
            newUser.setDepartment(department);
            newUser.setPassword(password);

            // Add new user to the list
            users.add(newUser);

            if (blockFileWriting) return true;
            try{
                File userListFile = new File(jsonFileLocation);
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(userListFile, users);
            } catch (IOException e) {
                System.out.println("Failed to read json.");
                throw new RuntimeException(e);
            }


            return true;
    }

    /**
     * Check whether the login information provided is valid
     * @param username The username of the user
     * @param password The password of the user
     * @return The result of logging in
     */
    public static boolean loginUser(String username, String password){
        if(!checkIfUserNameExists(username)){
            return false;
        }
        String userCorrectPassword = getPassword(username);
        if(userCorrectPassword.isEmpty()){
            return false;
        }
        else{
            return userCorrectPassword.equals(password);
        }
    }

    /**
     * Check if the String contains Foul Language listed
     * @param text the String to be tested
     */
    public static boolean containsFoulLanguage(String text) {
        List<String> foulWords = List.of("fuck", "shit", "ass"); // Add more words if needed

        for (String word : foulWords) {
            if (text.toLowerCase().contains(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the String contains only English Characters and Arabian Numbers
     * @param text the String to be tested
     */
    public static boolean isAlphaNumeric(String text) {
        return Pattern.matches("[a-zA-Z0-9- ]+", text);
    }

    /**
     * Check if the String contains only English Characters
     * @param text the String to be tested
     */
    public static boolean isAlpha(String text) {
        return Pattern.matches("[a-zA-Z ]+", text);
    }

    /**
     * Check if the password is a strong password
     * @param password the String to be tested
     */
    public static boolean isSecurePassword(String password) {
        // Check for basic security measures: length and complexity
        return password.length() >= 8 && Pattern.matches(".*[!@#$%^&*].*", password);
    }
}
