package gui;

import reviews.CreateTables;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;


public class CourseReviewsSystemUI {
    private CourseReviewsSystem courseReviewsSystem;
    private Scanner scanner;


    public CourseReviewsSystemUI() {
        courseReviewsSystem = new CourseReviewsSystem();
        scanner = new Scanner(System.in);
    }

    public void run1() {
        boolean running = true;
        while (running) {
            System.out.println("Welcome to UVA Course Reviews System.");
            System.out.println("1. Register");
            System.out.println("2. Login");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    public void run2() {
        boolean running = true;
        while(running) {
            System.out.println("1. Add Review");
            System.out.println("2. Get Reviews");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addReview();
                    break;
                case 2:
                    getReviews();
                    break;
                case 3:
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
        System.out.println("Confirm password:");
        String password2 = scanner.nextLine();

        if(password.equals(password2)) {
            courseReviewsSystem.registerStudent(username, password);
            System.out.println("Student registered successfully.");
        }
        else {
            System.out.println("Password do not match");
        }
    }

    private void login() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        if (courseReviewsSystem.login(username, password)) {
            System.out.println("Login successful.");
            run2();
        } else {
            System.out.println("Invalid credentials.");
            run1();
        }
    }

    private void addCourse() {
        System.out.println("Enter course department:");
        String department = scanner.nextLine();
        System.out.println("Enter course catalog number:");
        String catalogNumber = scanner.nextLine();

        int intCatalogNumber = Integer.parseInt(catalogNumber);
        Course course = new Course(department, intCatalogNumber);

        courseReviewsSystem.addCourse(department, intCatalogNumber);
        System.out.println("Course added successfully.");
    }

    private void addReview() {
        System.out.println("Enter course name:");
        String courseName = scanner.nextLine();

        if (!courseName.matches("[A-Z]{1,4} \\d{4}")) { // https://stackoverflow.com/questions/28145881/how-does-d-work-in-java
            System.out.println("Invalid course name format.");
            return;
        }

        String[] parts = courseName.split(" ");
        String department = parts[0];
        int catalogNumber = Integer.parseInt(parts[1]);
        System.out.println("Enter review text:");
        String reviewText = scanner.nextLine();
        System.out.println("Enter rating (1-5):");
        int rating = Integer.parseInt(scanner.nextLine());
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Please enter a number between 1 and 5.");
        }

        Course course = new Course(department, catalogNumber);
        courseReviewsSystem.addReview(course, reviewText, rating);

        System.out.println("Review added successfully.");
    }

    private void getReviews() {
        System.out.println("Enter course name:");
        String courseName = scanner.nextLine();

        if (!courseName.matches("[A-Z]{4} \\d{4}")) { // https://stackoverflow.com/questions/28145881/how-does-d-work-in-java
            System.out.println("Invalid course name format.");
            return;
        }

        String[] parts = courseName.split(" ");
        String department = parts[0];
        int catalogNumber = Integer.parseInt(parts[1]);
        Course course = new Course(department,catalogNumber);
        List<Review> reviews = courseReviewsSystem.getReviews(course);
        if (reviews.isEmpty()) {
            System.out.println("No reviews found for this course.");
        } else {
            System.out.println("Reviews for " + department + " " + catalogNumber + ": ");
            for (Review review : reviews) {
                System.out.println(review.getReviewText());
            }
            double averageRating = courseReviewsSystem.getAverageRating(course);
            System.out.println("Average rating: " + averageRating);
        }
    }

    private void getAllCourses() {
        List<Course> courses = courseReviewsSystem.getAllCourses();
        for (Course course : courses) {
            System.out.println(course.getDepartment() + " - " + course.getCatalogNumber());
        }
    }

    private void generateJSONFile() {
        List<Course> courses = courseReviewsSystem.getAllCourses();

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            sb.append("{");
            sb.append("\"code\":\"").append(course.getDepartment()).append("\",");
            sb.append("\"title\":\"").append(course.getCatalogNumber()).append("\",");
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
        ui.run1();
    }
}
