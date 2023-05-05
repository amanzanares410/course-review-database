package gui;
import java.util.List;


public class CourseReviewsSystem {
    private Database database;
    private Student loggedInStudent;

    public CourseReviewsSystem() {
        database = new Database();
    }

    public void registerStudent(String username, String password) {
        Student newStudent = new Student(username, password);
        database.addStudent(newStudent);
    }

    public boolean login(String username, String password) {
        Student student = database.getStudent(username);
        if (student != null && student.getPassword().equals(password)) {
            loggedInStudent = student;
            return true;
        }
        return false;
    }

    public void addCourse(String department, int catalogNumber) {
        Course newCourse = new Course(department, catalogNumber);
        database.addCourse(newCourse);
    }

    public void addReview(Course course, String reviewText, int rating) {
        Review newReview = new Review(course, loggedInStudent, reviewText, rating);
        database.addReview(newReview);
    }

    public List<Review> getReviews(String courseCode) {
        return database.getReviews(courseCode);
    }

    public List<Course> getAllCourses() {
        return database.getAllCourses();
    }
}
