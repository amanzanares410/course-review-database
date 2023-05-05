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

    public void run1() {
        generateTables();
        boolean running = true;
        while (running) {
            System.out.println("Welcome to UVA Course Reviews System.");
            System.out.println("1. Register");
            System.out.println("2. Login");
//            System.out.println("3. Add Course");
//            System.out.println("4. Add Review");
//            System.out.println("5. Get Reviews");
//            System.out.println("6. Get All Courses");
//            System.out.println("7. Generate Output JSON file");
//            System.out.println("8. Exit");

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

    public void run2() {
        boolean running = true;
        System.out.println("1. Add Course");
        System.out.println("2. Add Review");
        System.out.println("3. Get Reviews");
        System.out.println("4. Get All Courses");
        System.out.println("5. Generate Output JSON file");
        System.out.println("6. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addCourse();
                break;
            case 2:
                addReview();
                break;
            case 3:
                getReviews();
                break;
            case 4:
                getAllCourses();
                break;
            case 5:
                generateJSONFile();
                break;
            case 6:
                running = false;
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    private void register() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        Student student = new Student(username,password);
        createTables.addStudent(student);

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
        run2();
    }

    private void addCourse() {
        System.out.println("Enter course department:");
        String department = scanner.nextLine();
        System.out.println("Enter course catalog number:");
        String catalogNumber = scanner.nextLine();

        int intCatalogNumber = Integer.parseInt(catalogNumber);
        Course course = new Course(department, intCatalogNumber);
        createTables.addCourse(course);

        courseReviewsSystem.addCourse(department, intCatalogNumber);
        System.out.println("Course added successfully.");
    }

    private void addReview() {
        System.out.println("Enter course department:");
        String department = scanner.nextLine();
        System.out.println("Enter course catalog number:");
        int catalogNumber = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter review text:");
        String reviewText = scanner.nextLine();
        System.out.println("Enter rating:");
        int rating = Integer.parseInt(scanner.nextLine());

        Course course = new Course(department, catalogNumber);
        courseReviewsSystem.addReview(course, reviewText, rating);
        System.out.println("Review added successfully.");
    }

    private void getReviews() {
        System.out.println("Enter course department:");
        String department = scanner.nextLine();
        System.out.println("Enter course catalog number");
        int catalogNumber = Integer.parseInt(scanner.nextLine());

        Course course = new Course(department,catalogNumber);
        List<Review> reviews = courseReviewsSystem.getReviews(course);
        if (reviews.isEmpty()) {
            System.out.println("No reviews found for this course.");
        } else {
            for (Review review : reviews) {
                System.out.println(review.getStudent().getLogin() + ": " + review.getReviewText());
            }
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
