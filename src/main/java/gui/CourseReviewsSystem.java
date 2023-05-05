package gui;
import reviews.CreateTables;

import java.util.List;


public class CourseReviewsSystem {
    private CreateTables database;
    private Student loggedInStudent;

    public CourseReviewsSystem() {
        database = new CreateTables();
        database.connect();
        database.createTables();
    }

    public void registerStudent(String username, String password) {
        Student newStudent = new Student(username, password);
        database.addStudent(newStudent);
    }

    public boolean login(String username, String password) {
        Student student = database.getStudent(new Student(username,password));
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
        if (database.hasReviewedCourse(loggedInStudent, course)) {
            System.out.println("You have already reviewed this course.");
        } else {
            Review newReview = new Review(course, loggedInStudent, reviewText, rating);
            database.addReview(newReview);
        }
    }

    public void addReviewBeginning(Course course, Student student, String reviewText, int rating) {
            Review newReview = new Review(course, student, reviewText, rating);
            database.addReview(newReview);
    }



    public List<Review> getReviews(Course course) {
        return database.getReviews(course);
    }

    public double getAverageRating(Course course) {
        List<Review> reviews = database.getReviews(course);
        if (reviews.isEmpty()) {
            return 0;
        } else {
            int total = 0;
            for (Review review : reviews) {
                total += review.getRating();
            }
            return (double) total / reviews.size();
        }
    }

    public List<Course> getAllCourses() {
        return database.getAllCourses();
    }
}
