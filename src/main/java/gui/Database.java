package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private Map<String, Student> students;
    private Map<String, Course> courses;
    private List<Review> reviews;

    public Database() {
        students = new HashMap<>();
        courses = new HashMap<>();
        reviews = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.put(student.getLogin(), student);
    }

    public Student getStudent(String username) {
        return students.get(username);
    }

    public void addCourse(Course course) {
        courses.put(course.getDepartment(), course);
    }

    public Course getCourse(String courseCode) {
        return courses.get(courseCode);
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public List<Review> getReviews(Course course) {
        List<Review> filteredReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getCourse().equals(course)) {
                filteredReviews.add(review);
            }
        }
        return filteredReviews;
    }

    public List<Course> getAllCourses() {
        List<Course> allCourses = new ArrayList<>();
        for (Course course : courses.values()) {
            allCourses.add(course);
        }
        return allCourses;
    }
}
