package gui;

import reviews.CreateTables;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;


public class CourseReviewsSystemUI {
    private CourseReviewsSystem courseReviewsSystem;
    private Scanner scanner;
    private CreateTables createTables;

    public CourseReviewsSystemUI() {
        courseReviewsSystem = new CourseReviewsSystem();
        scanner = new Scanner(System.in);
    }

    public void generateTables() {
        createTables = new CreateTables();
        createTables.connect();
        createTables.createTables();
    }

    public void run() {
        generateTables();
        boolean running = true;
        while (running) {
            System.out.println("Welcome to UVA Course Reviews System.");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Add Course");
            System.out.println("4. Add Review");
            System.out.println("5. Get Reviews");
            System.out.println("6. Get All Courses");
            System.out.println("7. Generate Output JSON file");
            System.out.println("8. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    addCourse();
                    break;
                case 4:
                    addReview();
                    break;
                case 5:
                    getReviews();
                    break;
                case 6:
                    getAllCourses();
                    break;
                case 7:
                    generateJSONFile();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    private void register() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        courseReviewsSystem.registerStudent(username, password);
        System.out.println("Student registered successfully.");
    }

    private void login() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        if (courseReviewsSystem.login(username, password)) {
            System.out.println("Login successful.");
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void addCourse() {
        System.out.println("Enter course code:");
        String courseCode = scanner.nextLine();
        System.out.println("Enter course name:");
        String courseName = scanner.nextLine();

        courseReviewsSystem.addCourse(courseCode, courseName);
        System.out.println("Course added successfully.");
    }

    private void addReview() {
        System.out.println("Enter course code:");
        String courseCode = scanner.nextLine();
        System.out.println("Enter review text:");
        String reviewText = scanner.nextLine();

        courseReviewsSystem.addReview(courseCode, reviewText);
        System.out.println("Review added successfully.");
    }

    private void getReviews() {
        System.out.println("Enter course code:");
        String courseCode = scanner.nextLine();

        List<Review> reviews = courseReviewsSystem.getReviews(courseCode);
        if (reviews.isEmpty()) {
            System.out.println("No reviews found for this course.");
        } else {
            for (Review review : reviews) {
                System.out.println(review.getStudent().getUsername() + ": " + review.getReviewText());
            }
        }
    }

    private void getAllCourses() {
        List<Course> courses = courseReviewsSystem.getAllCourses();
        for (Course course : courses) {
            System.out.println(course.getCourseCode() + " - " + course.getCourseName());
        }
    }

    private void generateJSONFile() {
        List<Course> courses = courseReviewsSystem.getAllCourses();

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            sb.append("{");
            sb.append("\"code\":\"").append(course.getCourseCode()).append("\",");
            sb.append("\"title\":\"").append(course.getCourseName()).append("\",");
            sb.append("}");
            if (i < courses.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");

        try (FileWriter fileWriter = new FileWriter("courses.json")) {
            fileWriter.write(sb.toString());
            System.out.println("Output JSON file generated successfully.");
        } catch (IOException e) {
            System.out.println("Error generating output JSON file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CourseReviewsSystemUI ui = new CourseReviewsSystemUI();
        ui.run();
    }
}
