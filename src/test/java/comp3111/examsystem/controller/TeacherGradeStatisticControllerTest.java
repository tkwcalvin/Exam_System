
package comp3111.examsystem.controller;

import comp3111.examsystem.entity.ExamAttempt;

import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Question;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.plaf.IconUIResource;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TeacherGradeStatisticControllerTest extends ApplicationTest {

    TeacherGradeStatisticController controller;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("TeacherGradeStatistic.fxml"));
        Parent root = loader.load();
        stage.setTitle("Test");
        stage.setScene(new Scene(root));
        stage.show();
        controller = loader.<TeacherGradeStatisticController>getController();
    }

    @Test
    @Order(1)
    void filterGradeAndReset(){

        clickOn(controller.courseCombox);
        moveBy(0, 60);
        clickOn();
        String courseName = controller.courseCombox.getValue();
        clickOn(controller.filterBtn);
        for (ExamAttempt i: controller.gradeList){
            assertEquals(i.getCourseName(), courseName);
        }
        clickOn(controller.resetBtn);

        clickOn(controller.examCombox);
        moveBy(0, 60);
        clickOn();
        String examName = controller.examCombox.getValue();
        clickOn(controller.filterBtn);
        for (ExamAttempt i: controller.gradeList){
            assertEquals(i.getExamName(), examName);
        }
        clickOn(controller.resetBtn);

        clickOn(controller.studentCombox);
        moveBy(0, 60);
        clickOn();
        String studentName = controller.studentCombox.getValue();
        clickOn(controller.filterBtn);
        for (ExamAttempt i: controller.gradeList){
            assertEquals(i.getStudentName(), studentName);
        }
        clickOn(controller.resetBtn);

    }

    @Test
    @Order(2)
    void barChartTest(){
        for (XYChart.Series<String, Number> series : controller.barChart.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                double scores = 0.0;
                int num = 0;
                for (ExamAttempt attempt: controller.gradeList){
                    if (Objects.equals(attempt.getCourseName(), data.getXValue())){
                        scores += attempt.getExamAttemptScore();
                        num ++;
                    }

                }
                double expectedValue = num > 0 ? scores / num : 0.0; // Avoid division by zero
                // Now check if the calculated average matches the pie chart's value
                assertEquals(expectedValue, data.getYValue().doubleValue(), 0.001);

            }
        }

    }

    @Test
    @Order(3)
    void lineChartTest(){
        for (XYChart.Series<String, Number> series : controller.lineChart.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                double scores = 0.0;
                int num = 0;
                for (ExamAttempt attempt: controller.gradeList){
                    if (Objects.equals(attempt.getCourseName()+" "+attempt.getExamName(), data.getXValue())){
                        scores += attempt.getExamAttemptScore();
                        num ++;
                    }
                }
                double expectedValue = num > 0 ? scores / num : 0.0; // Avoid division by zero
                // Now check if the calculated average matches the pie chart's value
                assertEquals(expectedValue, data.getYValue().doubleValue(), 0.001); // Use a delta for floating-point comparison
            }
        }

    }

    @Test
    @Order(4)
    void pieChartTest(){
        // Assuming series is defined and populated with data elsewhere in the test
        for (PieChart.Data data : controller.pieChart.getData()) {
            int scores = 0;
            // Iterate over all grade attempts
            for (ExamAttempt attempt : controller.gradeList) {
                // Check if the attempt's course name matches the pie chart data's label
                if (Objects.equals(attempt.getStudentName(), data.getName())) {
                    scores += attempt.getExamAttemptScore();
                }
            }

            assertEquals(scores, data.getPieValue()); // Use a delta for floating-point comparison
        }

    }
}
