package comp3111.examsystem.entity;

/**
 * A class for student object
 */
public class Student {
    private String username;
    private String name;
    private int age;
    private String gender;
    private String department;
    private String password;

    /**
     * Default constructor for student object
     */
    public Student(){}

    /**
     * Constructor for student object
     * @param username      username
     * @param name          name
     * @param age           age
     * @param gender        gender
     * @param department    department
     * @param password      password
     */
    public Student(String username, String name, int age, String gender, String department, String password) {
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.department = department;
        this.password = password;
    }

    /**
     * A method to get username of student object
     * @return  username in String
     */
    public String getUsername() { return username; }

    /**
     * A method to set username of student object
     * @param username username in String
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * A method to get name of student object
     * @return  name in String
     */
    public String getName() { return name; }

    /**
     * A method to set name of student object
     * @param name name in String
     */
    public void setName(String name) { this.name = name; }

    /**
     * A method to get gender of student object
     * @return  gender in String
     */
    public String getGender() { return gender; }

    /**
     * A method to set gender of student object
     * @param gender gender in String
     */
    public void setGender(String gender) { this.gender = gender; }

    /**
     * A method to get age of student object
     * @return  age in String
     */
    public int getAge() { return age; }

    /**
     * A method to set age of student object
     * @param age age in String
     */
    public void setAge(int age) { this.age = age; }

    /**
     * A method to get department of student object
     * @return  department in String
     */
    public String getDepartment() { return department; }

    /**
     * A method to set department of student object
     * @param department department in String
     */
    public void setDepartment(String department) { this.department = department; }

    /**
     * A method to get password of student object
     * @return  password in String
     */
    public String getPassword() { return password; }

    /**
     * A method to set password of student object
     * @param password  password in String
     */
    public void setPassword(String password) { this.password = password; }

}
