package reviews;
import gui.Course;
import gui.Review;
import gui.Student;

import java.sql.*;
import java.util.*;
public class CreateTables{
    Connection connection;

    public void connect() {
        if (connection != null) {
            throw new IllegalStateException("Manager is already connected");
        }
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Open a connection to the database
            connection = DriverManager.getConnection("jdbc:sqlite:Reviews.sqlite3");
            connection.setAutoCommit(false);
            connection.commit();
        } catch(ClassNotFoundException | SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void createTables() {
        try {
            // Create a Statement object
            Statement stmt = connection.createStatement();

            // Execute the SQL statement to create the Students table
            String sql1 = "CREATE TABLE IF NOT EXISTS Students (\n"
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                        + "login_name TEXT UNIQUE NOT NULL,\n"
                        + "password TEXT NOT NULL\n"
                        + ");";
            stmt.execute(sql1);

            // Execute the SQL statement to create the Courses table
            String sql2 = "CREATE TABLE IF NOT EXISTS Courses (\n"
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                        + "department TEXT NOT NULL,\n"
                        + "catalog_number TEXT NOT NULL\n"
                        + ");";
            stmt.execute(sql2);

            // Execute the SQL statement to create the Reviews table
            String sql3 = "CREATE TABLE IF NOT EXISTS Reviews (\n"
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                        + "student_id INTEGER NOT NULL,\n"
                        + "course_id INTEGER NOT NULL,\n"
                        + "review_text TEXT,\n"
                        + "rating INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),\n"
                        + "FOREIGN KEY (student_id) REFERENCES Students(id) ON DELETE CASCADE,\n"
                        + "FOREIGN KEY (course_id) REFERENCES Courses(id) ON DELETE CASCADE\n"
                        + ");";
            stmt.execute(sql3);

            // Close the Statement and Connection objects
            stmt.close();
//            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
     //   System.out.println("Tables created successfully");
    }
    public boolean tableExists(Connection connection, String tableName) {
        try {
            tableName = tableName.toUpperCase();
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, tableName, new String[] {"TABLE"});
            boolean x = rs.next();
            rs.close();
            return x;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    public void clear() {
        try {
            if (connection== null || connection.isClosed()) {
                throw new IllegalStateException("Manager is not connected.");
            }
            if (!tableExists(connection, "Students")) {
                throw new IllegalStateException("Students Table does not exist.");
            } else if (!tableExists(connection, "Courses")) {
                throw new IllegalStateException("Courses Table does not exist.");
            } else if (!tableExists(connection, "Reviews")) {
                throw new IllegalStateException("Reviews Table does not exist.");
            }
            String clearStudentsTable = "DELETE FROM Students;";
            String clearCoursesTable = "DELETE FROM Courses;";
            String clearReviewsTable = "DELETE FROM Reviews;";

            Statement statementStudents = connection.createStatement();
            Statement statementCourses = connection.createStatement();
            Statement statementReviews = connection.createStatement();

            statementStudents.executeUpdate(clearStudentsTable);
            statementCourses.executeUpdate(clearCoursesTable);
            statementReviews.executeUpdate(clearReviewsTable);

            statementStudents.close();
            statementCourses.close();
            statementReviews.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    public void deleteTables() {
        try {
            if (connection == null || connection.isClosed()) {
                throw new IllegalStateException("Manager is not connected.");
            }
            if (!tableExists(connection, "Students")) {
                throw new IllegalStateException("Students Table does not exist.");
            } else if (!tableExists(connection, "Courses")) {
                throw new IllegalStateException("Courses Table does not exist.");
            } else if (!tableExists(connection, "Reviews")) {
                throw new IllegalStateException("Reviews Table does not exist.");
            }
            String deleteStudentsTable = "DROP TABLE IF EXISTS Students;";
            String deleteCoursesTable = "DROP TABLE IF EXISTS Courses;";
            String deleteReviewsTable = "DROP TABLE IF EXISTS Reviews;";

            Statement statementStudents = connection.createStatement();
            statementStudents.executeUpdate(deleteStudentsTable);
            statementStudents.close();

            Statement statementCourses = connection.createStatement();
            statementCourses.executeUpdate(deleteCoursesTable);
            statementCourses.close();

            Statement statementReviews = connection.createStatement();
            statementReviews.executeUpdate(deleteReviewsTable);
            statementReviews.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    public void disconnect() {
        try {
            if (connection.isClosed()) {
                throw new IllegalStateException("Connection is already closed.");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    //Resource used to create prepared statement: https://stackoverflow.com/questions/370818/cleanest-way-to-build-an-sql-string-in-java
    public void addStudent(Student student) {
        try {
            if (connection== null || connection.isClosed()) {
                throw new IllegalStateException("Manager is not connected.");
            }
            String login = student.getLogin();
            String password = student.getPassword();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Students (login_name, password) VALUES (?,?)");
            statement.setString(1, login);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error in adding to database");
        }
    }

    public Student getStudent(Student student) {
        try{
            if (connection== null || connection.isClosed()) {
                throw new IllegalStateException("Manager is not connected.");
            }
            //Parse Student records into Student objects
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Students WHERE login_name = ?");
            statement.setString(1, student.getLogin());
            ResultSet resultSet = statement.executeQuery();

            //Create new student from result set
            String login = resultSet.getString("login_name");
            String password = resultSet.getString("password");
            Student newStudent = new Student(login, password);
            return newStudent;
        } catch (SQLException e) {
            throw new IllegalStateException("Error in reading from the database");
        }
    }

    public void addCourse(Course course) {
        try {
            if (connection== null || connection.isClosed()) {
                throw new IllegalStateException("Manager is not connected.");
            }
            String department = course.getDepartment();
            int catalogNumber = course.getCatalogNumber();
            if ((catalogNumber) <= 0){
                throw new IllegalArgumentException("Catalog number should be positive");
            }
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Courses (department, catalog_number) VALUES (?,?)");
            statement.setString(1, department);
            statement.setInt(2, catalogNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error in adding to database");
        }
    }
    public Course getCourse(Course course) {
        try{
            if (connection== null || connection.isClosed()) {
                throw new IllegalStateException("Manager is not connected.");
            }
            PreparedStatement statement = connection.prepareStatement("SELECT FROM Courses WHERE department = ?");
            statement.setString(1, course.getDepartment());
            ResultSet resultSet = statement.executeQuery();

            String department = resultSet.getString("department");
            int catalogNumber = resultSet.getInt("catalog_number");
            Course newCourse = new Course(department, catalogNumber);
            return newCourse;
        } catch (SQLException e) {
            throw new IllegalStateException("Error in reading from the database");
        }
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        try {
            if (connection== null || connection.isClosed()) {
                throw new IllegalStateException("Manager is not connected.");
            }
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Courses";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                String department = resultSet.getString("department");
                int catalogNumber = resultSet.getInt("catalog_number");
                Course course = new Course(department, catalogNumber);
                courses.add(course);
            }
            return courses;
        } catch(SQLException e) {
            throw new IllegalStateException("Error in reading from database");
        }
    }
    public void addReview(Review review) {
        try {
            if (connection== null || connection.isClosed()) {
                throw new IllegalStateException("Manager is not connected.");
            }
            Course course = review.getCourse();
            PreparedStatement statement1 = connection.prepareStatement("SELECT id from Courses WHERE department = ? AND catalog_number = ?");
            statement1.setString(1,course.getDepartment());
            statement1.setInt(2, course.getCatalogNumber());
            ResultSet resultSet1 = statement1.executeQuery();
            int courseID = resultSet1.getInt("id");
            Student student = review.getStudent();
            PreparedStatement statement2 = connection.prepareStatement("SELECT id from Students WHERE login_name = ? AND password = ?");
            statement2.setString(1,student.getLogin());
            statement2.setString(2, student.getPassword());
            ResultSet resultSet2 = statement2.executeQuery();
            int studentID = resultSet2.getInt("id");
            String reviewText = review.getReviewText();
            int rating = review.getRating();
            if ((rating) <= 0 || (rating)>5){
                throw new IllegalArgumentException("Rating number should be between 1 and 5");
            }
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Reviews (student_id, course_id, review_text, rating) VALUES (?,?,?,?)");
            statement.setInt(1,studentID);
            statement.setInt(2, courseID);
            statement.setString(3, reviewText);
            statement.setInt(4, rating);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error in adding to database");
        }
    }
    public List<Review> getReviews(Course course) {
        List<Review> reviews = new ArrayList<>();
        try{
            if (connection== null || connection.isClosed()) {
                throw new IllegalStateException("Manager is not connected.");
            }

            PreparedStatement statement1 = connection.prepareStatement("SELECT id from Courses WHERE department = ? AND catalog_number = ?");
            statement1.setString(1,course.getDepartment());
            statement1.setInt(2, course.getCatalogNumber());
            ResultSet resultSet1 = statement1.executeQuery();
            int courseID = resultSet1.getInt("id");

            PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM Reviews WHERE course_id = ?");
            statement2.setInt(1, courseID);
            ResultSet resultSet2 = statement2.executeQuery();


            while(resultSet2.next()) {
                int studentID = resultSet2.getInt("student_id");
                String reviewText = resultSet2.getString("review_text");
                int rating = resultSet2.getInt("rating");
                PreparedStatement statement3 = connection.prepareStatement("SELECT * FROM Students WHERE id = ?");
                statement3.setInt(1, studentID);
                ResultSet resultSet3 = statement3.executeQuery();
                String username = resultSet3.getString("login_name");
                String password = resultSet3.getString("password");
                Student student = new Student(username,password);
                reviews.add(new Review(course, student, reviewText, rating));
            }
            return reviews;
        } catch (SQLException e) {
            throw new IllegalStateException("Error in reading from the database");
        }
    }
}