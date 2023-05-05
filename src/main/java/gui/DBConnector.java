package gui;

import java.sql.*;

public class DBConnector {
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:Reviews.sqlite3";
    Connection connection;

    public void connect() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void createTables() {
        try {
            if (connection== null || connection.isClosed()) {
                throw new IllegalStateException("Manager is not connected.");
            }
            if (tableExists(connection, "Students")) {
                throw new IllegalStateException("Students Table already exists.");
            } else if (tableExists(connection, "Courses")) {
                throw new IllegalStateException("Courses Table already exists.");
            } else if (tableExists(connection, "Reviews")) {
                throw new IllegalStateException("Reviews Table already exists.");
            }

            String createStudentsTable = "CREATE TABLE Students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL UNIQUE,password TEXT NOT NULL);";
            String createCoursesTable = "CREATE TABLE Courses (id INTEGER PRIMARY KEY AUTOINCREMENT,department TEXT NOT NULL, catalog_number TEXT NOT NULL);";
            String createReviewsTable = "CREATE TABLE Reviews (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER NOT NULL, course_id INTEGER NOT NULL, message TEXT NOT NULL, rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5), FOREIGN KEY (student_id) REFERENCES Students(id) ON DELETE CASCADE, FOREIGN KEY (course_id) REFERENCES Courses(id) ON DELETE CASCADE);";

            Statement statementStudents = connection.createStatement();
            Statement statementCourses = connection.createStatement();
            Statement statementReviews = connection.createStatement();

            statementStudents.executeUpdate(createStudentsTable);
            statementCourses.executeUpdate(createCoursesTable);
            statementReviews.executeUpdate(createReviewsTable);

            statementStudents.close();
            statementCourses.close();
            statementReviews.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
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
}
