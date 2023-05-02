package reviews;
import reviews.Student;

import java.sql.*;
public class CreateTables{
    Connection conn;


    public void createTables() {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Open a connection to the database
            conn = DriverManager.getConnection("jdbc:sqlite:Reviews.sqlite3");

            // Create a Statement object
            Statement stmt = conn.createStatement();

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
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            }
        System.out.println("Tables created successfully");
    }

    public void addStudent(Student student) {
        try {
            String login = student.getLogin();
            String password = student.getPassword();
            Statement statement = conn.createStatement();
            String sql = "INSERT INTO Students (login_name, password) "
                    + "VALUES ('" + login + "', '" + password + "');";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new IllegalStateException("Error in adding to database");
        }
    }

    public void addCourse(Course course) {

    }
}