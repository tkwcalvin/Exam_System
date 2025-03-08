package comp3111.examsystem.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private Student user;

    @BeforeEach
    public void setUp() {
        user = new Student("Kenneth", "Kenneth", 40, "Male", "CSE", "password!");
    }
    @Test
    void getUsername() {
        assertEquals("Kenneth", user.getUsername());
    }

    @Test
    void setUsername() {
        user.setUsername("Leung");
        assertEquals("Leung", user.getUsername());
    }

    @Test
    void getName() {
        assertEquals("Kenneth", user.getName());
    }

    @Test
    void setName() {
        user.setName("Leung");
        assertEquals("Leung", user.getName());
    }

    @Test
    void getGender() {
        assertEquals("Male", user.getGender());
    }

    @Test
    void setGender() {
        user.setGender("Female");
        assertEquals("Female", user.getGender());
    }

    @Test
    void getAge() {
        assertEquals(40, user.getAge());
    }

    @Test
    void setAge() {
        user.setAge(50);
        assertEquals(50, user.getAge());
    }

    @Test
    void getDepartment() {
        assertEquals("CSE", user.getDepartment());
    }

    @Test
    void setDepartment() {
        user.setDepartment("ECE");
        assertEquals("ECE", user.getDepartment());
    }

    @Test
    void getPassword() {
        assertEquals("password!", user.getPassword());
    }

    @Test
    void setPassword() {
        user.setPassword("password!!!");
        assertEquals("password!!!", user.getPassword());
    }
}