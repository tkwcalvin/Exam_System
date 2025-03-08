package comp3111.examsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * A program to create the window of exam result, to be popped
 * up after a student taken an exam
 * @author <a href=mailto:pakninlpn@gmail.com>Marco</a>
 * @version 2.0
 */
public class StudentExamPopUpResultController {

    @FXML
    private Label examPopUpResultNoOfCorrectLabel;

    @FXML
    private Label examPopUpResultPrecisionLabel;

    @FXML
    private Label examPopUpResultScoreLabel;

    @FXML
    private Button examPopUpResultOKButton;

    /**
     * Constructor for this controller
     */
    public StudentExamPopUpResultController(){}

    /**
     * Initialize this controller by setting the handler of the OK button
     */
    public void initialize(){
        examPopUpResultOKButton.setOnAction(e -> exitPopUpResult(e));
    }

    /**
     * A method to set the exam result displayed including correct proportion,
     * accuracy, and total score
     * @param correctProportion The proportion of question answering correct in the exam in string format "{number of correct / total number of questions}"
     * @param accuracy The precision of the student's attempt in string format "00.00"
     * @param score The total score the student get in this attempt in string format "{score the student get / total score}"
     */
    public void setExamResult(String correctProportion, String accuracy, String score) {
        examPopUpResultNoOfCorrectLabel.setText(correctProportion);
        examPopUpResultPrecisionLabel.setText(accuracy);
        examPopUpResultScoreLabel.setText(score);
    }

    /**
     * A method to handle exist of this controller
     * @param event Action event of the button
     */
    public void exitPopUpResult(ActionEvent event){
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

}
