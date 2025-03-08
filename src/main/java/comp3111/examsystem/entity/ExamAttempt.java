package comp3111.examsystem.entity;

/**
 * A class representing each exam attempt done by student
 */
public class ExamAttempt {
    private String studentName;
    private String course;
    private String examName;
    private int examAttemptScore;
    private int examTotalScore;
    private int examAttemptTimeSpend;

    /**
     * Default constructor for this class
     */
    public ExamAttempt(){

    }

    /**
     * Constructor for an ExamAttempt object
     * @param studentName           name of the student
     * @param courseName            course of this exam
     * @param examName              name of this course
     * @param examAttemptScore      the score student attained
     * @param examTotalScore        total score of this exam
     * @param examAttemptTimeSpend  total time student spent on this attempt
     */
    public ExamAttempt(String studentName, String courseName, String examName, int examAttemptScore, int examTotalScore, int examAttemptTimeSpend){
        this.studentName = studentName;
        this.course = courseName;
        this.examName = examName;
        this.examAttemptScore = examAttemptScore;
        this.examTotalScore = examTotalScore;
        this.examAttemptTimeSpend = examAttemptTimeSpend;
    }

    /**
     * A method to get the score student attained
     * @return  score attained in integer
     */
    public int getExamAttemptScore(){
        return this.examAttemptScore;
    }

    /**
     * A method to get the time student used in this attempt
     * @return  time spent in integer
     */
    public int getExamAttemptTimeSpend() {
        return examAttemptTimeSpend;
    }

    /**
     * A method to get name of the student
     * @return  name in String
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * A method to set the name of student attained the exam
     * @param studentName  the name of student in String
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * A method to get the name of the course of the exam
     * @return  course name in String
     */
    public String getCourseName() {
        return course;
    }

    /**
     * A method to set the name of the course of the exam
     * @param course    course name in String
     */
    public void setCourseName(String course) {
        this.course = course;
    }

    /**
     * A method to get the name of the exam
     * @return  name of the exam
     */
    public String getExamName() {
        return examName;
    }

    /**
     * A method to set the name of the exam
     * @param examName name of the exam
     */
    public void setExamName(String examName) {
        this.examName = examName;
    }

    /**
     * A method to get the total score of the exam
     * @return  total score in integer
     */
    public int getExamTotalScore() {
        return examTotalScore;
    }

    /**
     * A method to set the total score of the exam
     * @param examTotalScore total score in integer
     */
    public void setExamTotalScore(int examTotalScore) {
        this.examTotalScore = examTotalScore;
    }
}
