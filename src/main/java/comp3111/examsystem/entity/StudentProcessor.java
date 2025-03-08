package comp3111.examsystem.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A controller to handle Student user information
 * @author <a href=mailto:pakninlpn@gmail.com>Marco</a>
 * @version 1.0
 */
public class StudentProcessor {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static List<Student> users;
    private static File userListFile;

    /**
     * A constructor to set the file path of Student user database
     * @param jsonFileLocation file path
     */
    public StudentProcessor(String jsonFileLocation){
        try{
            userListFile = new File(jsonFileLocation);
            users = objectMapper.readValue(userListFile, new TypeReference<List<Student>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to read json.");
        }
    }

    /**
     * A method to print all user information
     */
    public static void getAllUser(){
        for (Student user : users) {
            System.out.println("Username: " + user.getUsername());
            System.out.println("Name: " + user.getName());
            System.out.println("Gender: " + user.getGender());
            System.out.println("Age: " + user.getAge());
            System.out.println("Department: " + user.getDepartment());
            System.out.println("Password: " + user.getPassword());
            System.out.println("-----");
        }
    }

    /**
     * A method to check if a user record match with a given username
     * @param user      a user object
     * @param username  username
     * @return          <code>true</code> if matches
     *                  <code>false</code> otherwise
     */
    public static boolean checkUserNameEqual(Student user, String username){
        return user.getUsername().equals(username);
    }

    /**
     * A method to get the password of a given username
     * @param username  the username
     * @return          the password of user with this username
     */
    public static String getPassword(String username){
        for (Student user : users) {
            //if(user.getUsername().equals(username)){
            if(checkUserNameEqual(user, username)){
                return user.getPassword();
            }
        }
        return "";
    }


    /**
     * A method to check is a username exists already
     * @param checkUsername the username to be checked
     * @return              <code>true</code> if the username exists in database
     *                      <code>false</code> otherwise
     */
    public static boolean checkIfUserNameExists(String checkUsername){
        for (Student user : users) {
            //if(user.getUsername().equals(checkUsername)){
            if(checkUserNameEqual(user, checkUsername)){
                return true;
            }
        }
        return false;
    }

    /**
     * A method to add new user to database
     * @param userInfo a list of user information
     * @return         <code>true</code> if successfully added
     *                 <code>false</code> otherwise
     */
    public static boolean addUser(List<String> userInfo){
        try {
            users = objectMapper.readValue(userListFile, new TypeReference<List<Student>>() {
            });
            Student newUser = new Student();
            newUser.setUsername(userInfo.get(0));
            newUser.setName(userInfo.get(1));
            newUser.setGender(userInfo.get(2));
            newUser.setAge(Integer.valueOf(userInfo.get(3)));
            newUser.setDepartment(userInfo.get(4));
            newUser.setPassword(userInfo.get(5));

            // Add new user to the list
            users.add(newUser);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(userListFile, users);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to add user.");
            return false;
        }
    }

    /**
     * A method to check if the password is empty
     * @param userCorrectPassword   password
     * @return                      <code>true</code> if password is empty
     *                              <code>false</code> otherwise
     */
    public static boolean checkPasswordEmpty(String userCorrectPassword){
        return userCorrectPassword.equals("");
    }

    /**
     * A method to check if the inputted password matches with the correct password
     * @param userCorrectPassword   correct password
     * @param password              inputted password
     * @return                      <code>true</code> if matches
     *                              <code>false</code> otherwise
     */
    public static boolean checkPasswordEqual(String userCorrectPassword, String password){
        return userCorrectPassword.equals(password);
    }

    /**
     * A method to perform login
     * @param username  username
     * @param password  password
     * @return          <code>true</code> if successfully login
     *                  <code>false</code> otherwise
     */
    public static boolean loginUser(String username, String password){
        if(!checkIfUserNameExists(username)){
            return false;
        }
        String userCorrectPassword = getPassword(username);
        //if(userCorrectPassword.equals("")){
        if(checkPasswordEmpty(userCorrectPassword)){
            return false;
        }
        else{
            //if(userCorrectPassword.equals(password)){
            if(checkPasswordEqual(userCorrectPassword, password)){
                return true;
            }
            else{
                return false;
            }
        }
    }

    /**
     * A method to check if a text contains given words
     * @param text  text to be tested
     * @param word  woeds to be tested
     * @return      <code>true</code> if contains
     *              <code>false</code> otherwise
     */
    public static boolean checkLowerCaseContainWord(String text, String word){
        return text.toLowerCase().contains(word);
    }

    /**
     * A method to check if the text contains foul languages
     * @param text  the text to be checked
     * @return      <code>true</code> if contains
     *              <code>false</code> otherwise
     */
    public static boolean containsFoulLanguage(String text) {
        List<String> foulWords = List.of("fuck", "shit", "ass"); // Add more words if needed

        for (String word : foulWords) {
            //if (text.toLowerCase().contains(word)) {
            if(checkLowerCaseContainWord(text, word)){
                return true;
            }
        }
        return false;
    }

    /**
     * A method to check if the text only consist of alphanumeric characters
     * @param text  text to be checked
     * @return      <code>true</code> yes
     *              <code>false</code> no
     */
    public static boolean isAlphaNumeric(String text) {
        return Pattern.matches("[a-zA-Z0-9 ]+", text);
    }

    /**
     * A method to check if the text only consist of alphabetical characters
     * @param text  text to be checked
     * @return      <code>true</code> yes
     *              <code>false</code> no
     */
    public static boolean isAlpha(String text) {
        return Pattern.matches("[a-zA-Z ]+", text);
    }

    /**
     * A method to check if the gender is valid
     * @param gender    gender input to be checked
     * @return          <code>true</code> if gender either "Male" or "Female"
     *                  <code>false</code> otherwise
     */
    public static boolean isMaleFemale(String gender) {
        // Check for basic security measures: length and complexity
        return gender.equals("Male") || gender.equals("Female");
    }

    /**
     * A method to check if the password is secure
     * @param password  the password to be tested
     * @return          <code>true</code> if the password has more than 8 characters
     *                                    and contains at least one special characters
     *                  <code>false</code> otherwise
     */
    public static boolean isSecurePassword(String password) {
        // Check for basic security measures: length and complexity
        return password.length() >= 8 && Pattern.matches(".*[!@#$%^&*].*", password);
    }


}
