package comp3111.examsystem.entity;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;

/**
 * The class representing each question record.
 */
public class Question {
    String questionName;
    String questionType;    // Single | Multiple
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    List<Character> answer;
    int score;

    /**
     * Default Constructor.
     */
    public Question(){}


    /**
     * Constructor that initialize all attributes with the given parameters.
     * @param name the Question itself
     * @param type the type of the Question. Its either Single or Multiple.
     * @param optionA
     * @param optionB
     * @param optionC
     * @param optionD
     * @param answer the Upper-Case character that represents the correct option
     * @param score positive integer that determines the score of the question
     */
    public Question(String name, String type,
                    String optionA, String optionB, String optionC, String optionD,
                    List<Character> answer, int score){
        this.questionName = name;
        this.questionType = type;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.score = score;
    }

    /**
     * Getter for the attribute questionType
     * @return The type of the question (either Single or Multiple)
     */
    public String getQuestionType(){
        return questionType;
    }

    /**
     * Getter for the attribute questionName
     * @return The Question itself
     */
    public String getQuestionName(){
        return questionName;
    }

    /**
     * Getter for the attribute optionA
     * @return The Option A of the question
     */
    public String getOptionA(){
        return optionA;
    }
    /**
     * Getter for the attribute optionB
     * @return The Option B of the question
     */
    public String getOptionB(){
        return optionB;
    }
    /**
     * Getter for the attribute optionC
     * @return The Option C of the question
     */
    public String getOptionC(){
        return optionC;
    }
    /**
     * Getter for the attribute optionD
     * @return The Option D of the question
     */
    public String getOptionD(){
        return optionD;
    }

    /**
     * Getter for the attribute score
     * @return The Upper-Case character that represents the correct option
     */
    public List<Character> getAnswer(){
        return answer;
    }

    /**
     * Getter for the attribute score
     * @return A positive integer that determines the score of the question
     */
    public int getScore() { return score; }

    /**
     * Setter for the attribute questionName
     * @param questionName The Question itself
     */
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    /**
     * Setter for the attribute questionType
     * @param questionType The type of the question (either Single or Multiple)
     */
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    /**
     * Setter for the attribute optionA
     * @param optionA The Option A of the question
     */
    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    /**
     * Setter for the attribute optionB
     * @param optionB The Option B of the question
     */
    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    /**
     * Setter for the attribute optionC
     * @param optionC The Option C of the question
     */
    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    /**
     * Setter for the attribute optionD
     * @param optionD The Option D of the question
     */
    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    /**
     * Setter for the attribute answer
     * @param answer The Upper-Case character that represents the correct option
     */
    public void setAnswer(List<Character> answer) {
        this.answer = answer;
    }

    /**
     * Setter for the attribute score
     * @param score A positive integer that determines the score of the question
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns true if the parsed question has identical attributes to this question
     * If Object o is not {@link Question}, return false
     * If Question o has different attribute from this question, return false
     * Otherwise, returns true
     * @param o Object
     * @return boolean indicating the condition
     */
    public boolean equals(Object o){
        if (o.getClass() != Question.class) return false;
        return questionName.equals(((Question) o).questionName)
                && questionType.equals(((Question) o).questionType)
                && optionA.equals(((Question) o).optionA)
                && optionB.equals(((Question) o).optionB)
                && optionC.equals(((Question) o).optionC)
                && optionD.equals(((Question) o).optionD)
                && answer.equals(((Question) o).answer)
                && score == ((Question) o).score;
    }
}
