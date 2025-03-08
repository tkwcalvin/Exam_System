package comp3111.examsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.ExamAttempt;
import comp3111.examsystem.entity.Question;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentTakeExamControllerTest extends ApplicationTest  {

    @Test
    void validateSelectedExam1(){
        assertEquals(true, StudentTakeExamController.checkExamEmpty(null));
    }

    @Test
    void validateSelectedExam2(){
        assertEquals(false, StudentTakeExamController.checkExamEmpty(new Exam()));
    }

    @Test
    void validateItem1(){
        assertEquals(true, StudentTakeExamController.checkItemEmpty(null));
    }

    @Test
    void validateItem2(){
        assertEquals(false, StudentTakeExamController.checkItemEmpty("null"));
    }

    @Test
    void validateIndex1(){
        assertEquals(true, StudentTakeExamController.checkIndex(1));
    }

    @Test
    void validateIndex2(){
        assertEquals(false, StudentTakeExamController.checkIndex(-1));
    }
    @Test
    void validateRemainingTime1(){
        assertEquals(true, StudentTakeExamController.checkRemainingTime(1));
    }

    @Test
    void validateRemainingTime2(){
        assertEquals(false, StudentTakeExamController.checkRemainingTime(-1));
    }

    @Test
    void validateMultipleQuestion1(){
        List<Question> questionList = Arrays.asList(new Question("Q1", "Multiple", "A", "B", "C", "D", List.of('A', 'B'), 10));
        assertEquals(true, StudentTakeExamController.checkMultipleQuestion(questionList, 0));
    }

    @Test
    void validateMultipleQuestion2(){
        List<Question> questionList = Arrays.asList(new Question("Q1", "Single", "A", "B", "C", "D", List.of('A', 'B'), 10));
        assertEquals(false, StudentTakeExamController.checkMultipleQuestion(questionList, 0));
    }

    @Test
    void validateQuestionNumber1(){
        assertEquals(true, StudentTakeExamController.checkQuestionNotStartNotEnd(1, 3));
    }

    @Test
    void validateQuestionNumber2(){
        assertEquals(false, StudentTakeExamController.checkQuestionNotStartNotEnd(-1, 3));
    }

    @Test
    void validateQuestionNumber3(){
        assertEquals(false, StudentTakeExamController.checkQuestionNotStartNotEnd(3, 3));
    }

    @Test
    void validateQuestionNumber4(){
        assertEquals(false, StudentTakeExamController.checkQuestionIsLast(0, 2));
    }

    @Test
    void validateQuestionNumber5(){
        assertEquals(true, StudentTakeExamController.checkQuestionIsLast(1, 2));
    }

    @Test
    void validateQuestionNumber6(){
        assertEquals(false, StudentTakeExamController.checkQuestionIsLast(0, 2));
    }

    @Test
    void validateQuestionNumber7(){
        assertEquals(true, StudentTakeExamController.checkQuestionOnlyOne(1));
    }

    @Test
    void validateQuestionNumber8(){
        assertEquals(false, StudentTakeExamController.checkQuestionOnlyOne(2));
    }

    @Test
    void validateExamAttemptRecord1(){
        ArrayList<List<Character>> examAttemptRecord = new ArrayList<>();
        examAttemptRecord.add(Arrays.asList('A', 'B', 'C', 'D'));
        assertEquals(true, StudentTakeExamController.checkExamRecordEmpty(examAttemptRecord));
    }

    @Test
    void validateExamAttemptRecord2(){
        ArrayList<List<Character>> examAttemptRecord = new ArrayList<>();
        assertEquals(false, StudentTakeExamController.checkExamRecordEmpty(examAttemptRecord));
    }

    @Test
    void validateQuestionAnsweredYet1(){
        ArrayList<List<Character>> examAttemptRecord = new ArrayList<>();
        examAttemptRecord.add(Arrays.asList('A', 'B', 'C', 'D'));
        assertEquals(true, StudentTakeExamController.checkIfAnswered(0, examAttemptRecord));
    }

    @Test
    void validateQuestionAnsweredYet2(){
        ArrayList<List<Character>> examAttemptRecord = new ArrayList<>();
        examAttemptRecord.add(Arrays.asList('A', 'B', 'C', 'D'));
        assertEquals(false, StudentTakeExamController.checkIfAnswered(3, examAttemptRecord));
    }

    @Test
    void validateAnswerContainsA1(){
        List<Character> answers = List.of('A');
        assertEquals(true, StudentTakeExamController.checkIfContainsA(answers));
    }

    @Test
    void validateAnswerContainsA2(){
        List<Character> answers = List.of('B');
        assertEquals(false, StudentTakeExamController.checkIfContainsA(answers));
    }

    @Test
    void validateAnswerContainsB1(){
        List<Character> answers = List.of('B');
        assertEquals(true, StudentTakeExamController.checkIfContainsB(answers));
    }

    @Test
    void validateAnswerContainsB2(){
        List<Character> answers = List.of('C');
        assertEquals(false, StudentTakeExamController.checkIfContainsB(answers));
    }

    @Test
    void validateAnswerContainsC1(){
        List<Character> answers = List.of('C');
        assertEquals(true, StudentTakeExamController.checkIfContainsC(answers));
    }

    @Test
    void validateAnswerContainsC2(){
        List<Character> answers = List.of('D');
        assertEquals(false, StudentTakeExamController.checkIfContainsC(answers));
    }

    @Test
    void validateAnswerContainsD1(){
        List<Character> answers = List.of('D');
        assertEquals(true, StudentTakeExamController.checkIfContainsD(answers));
    }

    @Test
    void validateAnswerContainsAD(){
        List<Character> answers = List.of('A');
        assertEquals(false, StudentTakeExamController.checkIfContainsD(answers));
    }

    @Test
    void validateNextQuestion1(){
        assertEquals(true, StudentTakeExamController.checkNextQuestion(0, 2));
    }

    @Test
    void validateNextQuestion2(){
        assertEquals(false, StudentTakeExamController.checkNextQuestion(0, 1));
    }

    @Test
    void validatePrevQuestion1(){
        assertEquals(true, StudentTakeExamController.checkPrevQuestion(1));
    }

    @Test
    void validatePrevQuestion2(){
        assertEquals(false, StudentTakeExamController.checkPrevQuestion(0));
    }

    @Test
    void validateSelectedA1(){
        assertTrue(StudentTakeExamController.checkQuestionSelectedA(true));
    }

    @Test
    void validateSelectedA2(){
        assertFalse(StudentTakeExamController.checkQuestionSelectedA(false));
    }

    @Test
    void validateSelectedB1(){
        assertTrue(StudentTakeExamController.checkQuestionSelectedB(true));
    }

    @Test
    void validateSelectedB2(){
        assertFalse(StudentTakeExamController.checkQuestionSelectedB(false));
    }


    @Test
    void validateSelectedC1(){
        assertTrue(StudentTakeExamController.checkQuestionSelectedC(true));
    }

    @Test
    void validateSelectedC2(){
        assertFalse(StudentTakeExamController.checkQuestionSelectedC(false));
    }

    @Test
    void validateSelectedD1(){
        assertTrue(StudentTakeExamController.checkQuestionSelectedD(true));
    }

    @Test
    void validateSelectedD2(){
        assertFalse(StudentTakeExamController.checkQuestionSelectedD(false));
    }

    @Test
    void validateStudentAnswer1(){
        List<Character> correctAnswer = List.of('A');
        List<Character> studentAnswer = List.of('A');

        assertTrue(StudentTakeExamController.checkAnswer(correctAnswer, studentAnswer));
    }

    @Test
    void validateStudentAnswer2(){
        List<Character> correctAnswer = List.of('A');
        List<Character> studentAnswer = List.of('B');

        assertFalse(StudentTakeExamController.checkAnswer(correctAnswer, studentAnswer));
    }

    @Test
    void validateStudentAttemptRecord1(){
        ExamAttempt attempt = new ExamAttempt("Kenneth", "COMP3111", "Quiz", 10, 10, 60);
        Exam selectedExam = new Exam("Quiz", "COMP3111", 60, true, List.of(new Question()));
        assertTrue(StudentTakeExamController.checkStudentExamAttemptReacord(attempt, selectedExam, "Kenneth"));
    }

    @Test
    void validateStudentAttemptRecord2(){
        ExamAttempt attempt = new ExamAttempt("Leung", "COMP3111", "Quiz", 10, 10, 60);
        Exam selectedExam = new Exam("Quiz", "COMP3111", 60, true, List.of(new Question()));
        assertFalse(StudentTakeExamController.checkStudentExamAttemptReacord(attempt, selectedExam, "Kenneth"));
    }

    @Test
    void validateFound1(){
        assertFalse(StudentTakeExamController.checkFound(true));
    }


    @Test
    void validateFound2(){
        assertTrue(StudentTakeExamController.checkFound(false));
    }


    /////////////////////////////////////////////////////////////////////////////////

    StudentTakeExamController controller;
    Exam dummyExam;

    private static final ObjectMapper mapper = new ObjectMapper();
    public static List<Exam> readExamListFile(File examsListFile){
        try{
            return mapper.readValue(examsListFile, new TypeReference<List<Exam>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean stageClosed = false;
    public String examAttemptJsonFileLocation = "src/test/testDatabase/examAttemptTest.json";

    @Override
    public void start(Stage stage) throws IOException {

        List<Exam> examLists = readExamListFile(new File("src/test/testDatabase/examBankTestForStudent.json"));
        dummyExam = examLists.getFirst();


        FXMLLoader loader = new FXMLLoader(Main.class.getResource("StudentTakeExam.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));
        StudentTakeExamController controllerInit = loader.getController();
        controllerInit.setExamDetails(dummyExam, "dummy");
        controllerInit.setExamAttemptJsonFile(examAttemptJsonFileLocation);
        controllerInit.initialize();
        stage.show();


        controller = loader.<StudentTakeExamController>getController();
        controller.setExamDetails(dummyExam, "dummy");
        controller.setExamAttemptJsonFile(examAttemptJsonFileLocation);

    }

    @Test
    @Order(0)
    void testCountDown(){
       int timeLimit = dummyExam.getTimeLimit();
       WaitForAsyncUtils.sleep(timeLimit, TimeUnit.SECONDS);

       WaitForAsyncUtils.sleep(3, TimeUnit.SECONDS);

       assertTrue(controller.examSubmitWarning.isVisible());
       assertTrue(controller.examSubmitOkButton.isVisible());
       assertEquals("Time Is Up", controller.examSubmitWarningLabel.getText());
    }

    @Test
    @Order(1)
    void testFirstQuestionDisplay(){
       int questionScore = dummyExam.getExamQuestions().getFirst().getScore();
       String multipleAnswerWarning = " ";

       if(dummyExam.getExamQuestions().getFirst().getQuestionType().equals("Multiple"))
            multipleAnswerWarning = " (Multiple Answers)";

       int totalQuestion = dummyExam.getExamQuestions().size();

       assertEquals(String.valueOf(totalQuestion), controller.examTotalQuestion.getText());
       assertEquals("1", controller.examQuestionNumber.getText());
       assertEquals(String.valueOf(questionScore), controller.examQuestionScore.getText());
       assertEquals(multipleAnswerWarning, controller.examQuestionMultipleAnswerWarning.getText());
       assertEquals(dummyExam.getExamQuestions().getFirst().getQuestionName(), controller.examQuestionContent.getText());
       assertEquals(dummyExam.getExamQuestions().getFirst().getOptionA(), controller.examQuestionChoiceA.getText());
       assertEquals(dummyExam.getExamQuestions().getFirst().getOptionB(), controller.examQuestionChoiceB.getText());
       assertEquals(dummyExam.getExamQuestions().getFirst().getOptionC(), controller.examQuestionChoiceC.getText());
       assertEquals(dummyExam.getExamQuestions().getFirst().getOptionD(), controller.examQuestionChoiceD.getText());

       assertTrue(controller.examNextButton_1.isVisible());
       assertFalse(controller.examNextButton.isVisible());
       assertFalse(controller.examPrevButton.isVisible());
       assertFalse(controller.examSubmitButton.isVisible());
    }

    @Test
    @Order(2)
    void testMiddleQuestionDisplay(){
       //Move to second question
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
       clickOn(controller.examNextButton_1);
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);


       Question questionToTest = dummyExam.getExamQuestions().get(1);
       int questionScore = questionToTest.getScore();
       String multipleAnswerWarning = " ";

       if(questionToTest.getQuestionType().equals("Multiple"))
           multipleAnswerWarning = " (Multiple Answers)";

       int totalQuestion = dummyExam.getExamQuestions().size();

       assertEquals(String.valueOf(totalQuestion), controller.examTotalQuestion.getText());
       assertEquals("2", controller.examQuestionNumber.getText());
       assertEquals(String.valueOf(questionScore), controller.examQuestionScore.getText());
       assertEquals(multipleAnswerWarning, controller.examQuestionMultipleAnswerWarning.getText());
       assertEquals(questionToTest.getQuestionName(), controller.examQuestionContent.getText());
       assertEquals(questionToTest.getOptionA(), controller.examQuestionChoiceA.getText());
       assertEquals(questionToTest.getOptionB(), controller.examQuestionChoiceB.getText());
       assertEquals(questionToTest.getOptionC(), controller.examQuestionChoiceC.getText());
       assertEquals(questionToTest.getOptionD(), controller.examQuestionChoiceD.getText());

       assertFalse(controller.examNextButton_1.isVisible());
       assertTrue(controller.examNextButton.isVisible());
       assertTrue(controller.examPrevButton.isVisible());
       assertFalse(controller.examSubmitButton.isVisible());
    }

    @Test
    @Order(3)
    void testPrevQuestionDisplay(){
       //Move to second question
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
       clickOn(controller.examNextButton_1);
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
       clickOn(controller.examPrevButton);


       int questionScore = dummyExam.getExamQuestions().getFirst().getScore();
       String multipleAnswerWarning = " ";

       if(dummyExam.getExamQuestions().getFirst().getQuestionType().equals("Multiple"))
           multipleAnswerWarning = " (Multiple Answers)";

       int totalQuestion = dummyExam.getExamQuestions().size();

       assertEquals(String.valueOf(totalQuestion), controller.examTotalQuestion.getText());
       assertEquals("1", controller.examQuestionNumber.getText());
       assertEquals(String.valueOf(questionScore), controller.examQuestionScore.getText());
       assertEquals(multipleAnswerWarning, controller.examQuestionMultipleAnswerWarning.getText());
       assertEquals(dummyExam.getExamQuestions().getFirst().getQuestionName(), controller.examQuestionContent.getText());
       assertEquals(dummyExam.getExamQuestions().getFirst().getOptionA(), controller.examQuestionChoiceA.getText());
       assertEquals(dummyExam.getExamQuestions().getFirst().getOptionB(), controller.examQuestionChoiceB.getText());
       assertEquals(dummyExam.getExamQuestions().getFirst().getOptionC(), controller.examQuestionChoiceC.getText());
       assertEquals(dummyExam.getExamQuestions().getFirst().getOptionD(), controller.examQuestionChoiceD.getText());

       assertTrue(controller.examNextButton_1.isVisible());
       assertFalse(controller.examNextButton.isVisible());
       assertFalse(controller.examPrevButton.isVisible());
       assertFalse(controller.examSubmitButton.isVisible());
    }

    @Test
    @Order(4)
    void testLastQuestionDisplay(){
       //Move to second question
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
       clickOn(controller.examNextButton_1);
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
       clickOn(controller.examNextButton);
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
       clickOn(controller.examNextButton);


       Question questionToTest = dummyExam.getExamQuestions().getLast();
       int questionScore = questionToTest.getScore();
       String multipleAnswerWarning = " ";

       if(questionToTest.getQuestionType().equals("Multiple"))
           multipleAnswerWarning = " (Multiple Answers)";

       int totalQuestion = dummyExam.getExamQuestions().size();

       assertEquals(String.valueOf(totalQuestion), controller.examTotalQuestion.getText());
       assertEquals("3", controller.examQuestionNumber.getText());
       assertEquals(String.valueOf(questionScore), controller.examQuestionScore.getText());
       assertEquals(multipleAnswerWarning, controller.examQuestionMultipleAnswerWarning.getText());
       assertEquals(questionToTest.getQuestionName(), controller.examQuestionContent.getText());
       assertEquals(questionToTest.getOptionA(), controller.examQuestionChoiceA.getText());
       assertEquals(questionToTest.getOptionB(), controller.examQuestionChoiceB.getText());
       assertEquals(questionToTest.getOptionC(), controller.examQuestionChoiceC.getText());
       assertEquals(questionToTest.getOptionD(), controller.examQuestionChoiceD.getText());

       assertFalse(controller.examNextButton_1.isVisible());
       assertFalse(controller.examNextButton.isVisible());
       assertTrue(controller.examPrevButton.isVisible());
       assertTrue(controller.examSubmitButton.isVisible());
    }

    @Test
    @Order(5)
    void testSubmit(){
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
       clickOn(controller.examNextButton_1);
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
       clickOn(controller.examNextButton);
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
       clickOn(controller.examSubmitButton);
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);

       assertTrue(controller.examSubmitWarning.isVisible());
       assertTrue(controller.examSubmitWarning.isVisible());
       assertTrue(controller.examNextButton.isDisable());
       assertTrue(controller.examNextButton_1.isDisable());
       assertTrue(controller.examPrevButton.isDisable());
       assertTrue(controller.examSubmitButton.isDisable());
       assertTrue(controller.examQuestionChoiceA.isDisable());
       assertTrue(controller.examQuestionChoiceB.isDisable());
       assertTrue(controller.examQuestionChoiceC.isDisable());
       assertTrue(controller.examQuestionChoiceD.isDisable());
       assertTrue(controller.examQuestionScrollPane_Pane.isDisable());
    }

    @Test
    @Order(6)
    void testSubmitCancel(){
       //Move to second question
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
       clickOn(controller.examNextButton_1);
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
       clickOn(controller.examNextButton);
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
       clickOn(controller.examSubmitButton);
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
       clickOn(controller.examSubmitCancelButton);
       WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);

       assertFalse(controller.examSubmitWarning.isVisible());
       assertFalse(controller.examNextButton.isDisable());
       assertFalse(controller.examNextButton_1.isDisable());
       assertFalse(controller.examPrevButton.isDisable());
       assertFalse(controller.examSubmitButton.isDisable());
       assertFalse(controller.examQuestionChoiceA.isDisable());
       assertFalse(controller.examQuestionChoiceB.isDisable());
       assertFalse(controller.examQuestionChoiceC.isDisable());
       assertFalse(controller.examQuestionChoiceD.isDisable());
       assertFalse(controller.examQuestionScrollPane_Pane.isDisable());

    }

    @Test
    @Order(7)
    void testSubmitConfirm(){
        //Move to second question
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        clickOn(controller.examNextButton_1);
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        clickOn(controller.examNextButton);
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        clickOn(controller.examSubmitButton);
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        clickOn(controller.examSubmitConfirmButton);
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);

        assertTrue(controller.checkIfStageClose());

        //Remove new added entry
        ObjectMapper objectMapper = new ObjectMapper();
        File examAttemptFile = new File(examAttemptJsonFileLocation);
        try{
            List<ExamAttempt> userexammAttempt = objectMapper.readValue(examAttemptFile, new TypeReference<List<ExamAttempt>>() {
            });

            userexammAttempt.removeLast();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(examAttemptFile, userexammAttempt);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(8)
    void testQuestionMenu(){
        //Move to second question
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        moveTo(controller.examQuestionScrollPane_Pane);
        //Move to 1st question
        moveBy(0, -220);

        //Move to 2nd question
        moveBy(0, 20);
        clickOn();

        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);

        Question questionToTest = dummyExam.getExamQuestions().get(1);
        int questionScore = questionToTest.getScore();
        String multipleAnswerWarning = " ";

        if(questionToTest.getQuestionType().equals("Multiple"))
            multipleAnswerWarning = " (Multiple Answers)";

        int totalQuestion = dummyExam.getExamQuestions().size();

        assertEquals(String.valueOf(totalQuestion), controller.examTotalQuestion.getText());
        assertEquals("2", controller.examQuestionNumber.getText());
        assertEquals(String.valueOf(questionScore), controller.examQuestionScore.getText());
        assertEquals(multipleAnswerWarning, controller.examQuestionMultipleAnswerWarning.getText());
        assertEquals(questionToTest.getQuestionName(), controller.examQuestionContent.getText());
        assertEquals(questionToTest.getOptionA(), controller.examQuestionChoiceA.getText());
        assertEquals(questionToTest.getOptionB(), controller.examQuestionChoiceB.getText());
        assertEquals(questionToTest.getOptionC(), controller.examQuestionChoiceC.getText());
        assertEquals(questionToTest.getOptionD(), controller.examQuestionChoiceD.getText());

        assertFalse(controller.examNextButton_1.isVisible());
        assertTrue(controller.examNextButton.isVisible());
        assertTrue(controller.examPrevButton.isVisible());
        assertFalse(controller.examSubmitButton.isVisible());
    }

    @Test
    @Order(9)
    void testEvaluationWrongAnswer(){
        //moveTo(controller.examQuestionChoiceA);
        //clickOn();
        //WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);

        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        clickOn(controller.examNextButton_1);
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        clickOn(controller.examNextButton);
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);
        clickOn(controller.examSubmitButton);
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);

        controller.examEvaluation();

        List<String> examResult = controller.examAttemptResult;
        assertEquals("0/3", examResult.get(0));
        assertEquals("0.00", examResult.get(1));
        assertEquals("0/70", examResult.get(2));
    }


    @Test
    @Order(10)
    void testEvaluationCorrectAnswer(){

        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);

        moveTo(controller.examQuestionChoiceC);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);

        clickOn(controller.examNextButton_1);

        moveTo(controller.examQuestionChoiceA);
        clickOn();
        moveTo(controller.examQuestionChoiceB);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);

        clickOn(controller.examNextButton);

        moveTo(controller.examQuestionChoiceC);
        clickOn();
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);

        clickOn(controller.examSubmitButton);
        WaitForAsyncUtils.sleep(50, TimeUnit.MILLISECONDS);

        controller.examEvaluation();

        List<String> examResult = controller.examAttemptResult;
        assertEquals("3/3", examResult.get(0));
        assertEquals("100.00", examResult.get(1));
        assertEquals("70/70", examResult.get(2));

        //Remove new added entry
        ObjectMapper objectMapper = new ObjectMapper();
        File examAttemptFile = new File(examAttemptJsonFileLocation);
        try{
            List<ExamAttempt> userexammAttempt = objectMapper.readValue(examAttemptFile, new TypeReference<List<ExamAttempt>>() {
            });

            userexammAttempt.removeLast();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(examAttemptFile, userexammAttempt);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Test display
}

