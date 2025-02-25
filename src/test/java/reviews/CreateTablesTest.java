package reviews;

import gui.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateTablesTest {
    CreateTables createTables;

    @BeforeEach
    public void setup() {
        createTables = new CreateTables();
        createTables.connect();
        createTables.createTables();
    }

    @Test
    void createTables() {
        createTables.createTables();
    }

//    @Test
//    void addStudent() {
//        Student newStudent = new Student("jdoe", "password123");
//        createTables.addStudent(newStudent);
//        assertTrue(true);
//    }

    @Test
    void addCourse() {
        Course newCourse = new Course("CS",3140);
        createTables.addCourse(newCourse);
        assertTrue(true);
    }
}