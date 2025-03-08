package comp3111.examsystem.entity;


/**
 * The course class with the courses information
 */
public class Course {
    private String courseID;
    private String courseName;
    private String department;

    /**
     * The constructor
     * @param courseID Course ID
     * @param courseName Course name
     * @param department Course department
     */
    public Course(String courseID, String courseName, String department) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.department = department;
    }


    /**
     * The default constructor
     */
    public Course(){}

    /**
     * The access function for getting the course ID
     * @return The course ID
     */
    public String getCourseID() {
        return courseID;
    }

    /**
     * The access function for getting the course name
     * @return The course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * The access function for getting the course department
     * @return The course department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * The set function for setting the course ID
     * @param courseID course ID
     */
    public void setCourseId(String courseID) {
        this.courseID = courseID;
    }

    /**
     * The set function for setting the course name
     * @param courseName course name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    /**
     * The set function for setting the course department
     * @param department course department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

}
