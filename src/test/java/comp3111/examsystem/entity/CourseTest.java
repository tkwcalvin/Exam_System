package comp3111.examsystem.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private Course course;

    @BeforeEach
    public void setUp() {
        course = new Course("COMP3111", "Software Engineering", "CSE");
    }


    @Test
    void getCourseID() {
        assertEquals("COMP3111", course.getCourseID());
    }


    @Test
    void getCourseName() {
        assertEquals("Software Engineering", course.getCourseName());
    }

    @Test
    void getDepartment() {
        assertEquals("CSE", course.getDepartment());
    }

    @Test
    void setCourseId() {
        course.setCourseId("COMP4111");
        assertEquals("COMP4111", course.getCourseID());
    }

    @Test
    void setCourseName() {
        course.setCourseName("Hardware Engineering");
        assertEquals("Hardware Engineering", course.getCourseName());
    }

    @Test
    void setDepartment() {
        course.setDepartment("ECE");
        assertEquals("ECE", course.getDepartment());
    }
}