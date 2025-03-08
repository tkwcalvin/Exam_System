package comp3111.examsystem.entity;

/**
 * The class representing each teacher record
 */
public class Teacher {
    private String username;
    private String name;
    private String gender;
    private int age;
    private String position;
    private String department;
    private String password;

    /**
     * Default Constructor
     */
    public Teacher() {}

    /**
     * Constructor that initialize all attributes with the given parameters.
     * @param username The username of the teacher account
     * @param name The name of the teacher
     * @param gender The gender of the teacher
     * @param age The age of the teacher
     * @param position The position of the teacher
     * @param department The department of the teacher
     * @param password The password of the teacher account
     */
    public Teacher(String username, String name, String gender, int
            age, String position, String department, String password) {
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.position = position;
        this.department = department;
        this.password = password;
    }

    /**
     * Getter for the attribute userName
     * @return The username of the teacher account
     */
    public String getUsername() { return username; }
    /**
     * Setter for the attribute userName
     * @param username The username of the teacher account
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Getter for the attribute name
     * @return The name of the teacher
     */
    public String getName() { return name; }
    /**
     * Setter for the attribute name
     * @param name The name of the teacher
     */
    public void setName(String name) { this.name = name; }

    /**
     * Getter for the attribute gender
     * @return The gender of the teacher
     */
    public String getGender() { return gender; }
    /**
     * Setter for the attribute gender
     * @param gender The gender of the teacher
     */
    public void setGender(String gender) { this.gender = gender; }

    /**
     * Getter for the attribute age
     * @return The age of the teacher
     */
    public int getAge() { return age; }
    /**
     * Setter for the attribute age
     * @param age The age of the teacher
     */
    public void setAge(int age) { this.age = age; }

    /**
     * Getter for the attribute position
     * @return The position of the teacher
     */
    public String getPosition() {
        return position;
    }
    /**
     * Setter for the attribute position
     * @param position The position of the teacher
     */
    public void setPosition(String position) { this.position = position; }

    /**
     * Getter for the attribute department
     * @return The department of the teacher
     */
    public String getDepartment() { return department; }
    /**
     * Setter for the attribute department
     * @param department The department of the teacher
     */
    public void setDepartment(String department) { this.department = department; }

    /**
     * Getter for the attribute password
     * @return The password of the teacher account
     */
    public String getPassword() { return password; }
    /**
     * Setter for the attribute password
     * @param password The password of the teacher account
     */
    public void setPassword(String password) { this.password = password; }

}
