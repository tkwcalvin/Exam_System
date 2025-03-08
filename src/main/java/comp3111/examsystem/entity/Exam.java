package comp3111.examsystem.entity;

import java.util.List;

/**
 * The class representing each exam record
 */
public class Exam {
    String examName;
    int timeLimit;
    String course;
    boolean published;
    List<Question> examQuestions;
    int fullScore;

    /**
     * Default constructor
     */
    public Exam() {
    }

    /**
     * Constructor that initialize all attributes with the given parameters.
     * @param examName: The name of the exam
     * @param course: The course this exam belongs to
     * @param timeLimit: The time limit of the exam (in second)
     * @param published: The publishing state of the exam
     * @param examQuestions: The List of questions for them exam
     */
    public Exam(String examName, String course, int timeLimit, boolean published, List<Question> examQuestions) {
        this.examName = examName;
        this.course = course;
        this.timeLimit = timeLimit;
        this.published = published;
        this.examQuestions = examQuestions;
        this.fullScore = getFullScore();
    }

    /**
     * Getter for the attribute examQuestions
     * @return The List of questions for them exam
     */
    public List<Question> getExamQuestions() {
        return examQuestions;
    }

    /**
     * Getter for the attribute timeLimit
     * @return The time limit of the exam (in second)
     */
    public int getTimeLimit() {
        return timeLimit;
    }

    /**
     * Getter for the attribute course
     * @return The course this exam belongs to
     */
    public String getCourse() {
        return course;
    }

    /**
     * Getter for the attribute examName
     * @return The name of the exam
     */
    public String getExamName() {
        return examName;
    }

    /**
     * Getter for the attribute published
     * @return The publishing state of the exam
     */
    public boolean getPublished() {
        return published;
    }

    /**
     * Getter for the summed score of all question
     * @return The summed score of all questions
     */
    public int getFullScore(){
        return this.examQuestions.stream().mapToInt(Question::getScore).sum();
    }

    /**
     * Setter for the attribute examName
     * @param examName The name of the exam
     */
    public void setExamName(String examName) {
        this.examName = examName;
    }

    /**
     * Setter for the attribute timeLimit
     * @param timeLimit The time limit of the exam (in second)
     */
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    /**
     * Setter for the attribute course
     * @param course The course this exam belongs to
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * Setter for the attribute published
     * @param published The publishing state of the exam
     */
    public void setPublished(boolean published) {
        this.published = published;
    }

    /**
     * Setter for the attribute examQuestions
     * @param examQuestions The List of questions for them exam
     */
    public void setExamQuestions(List<Question> examQuestions) {
        this.examQuestions = examQuestions;
    }
}