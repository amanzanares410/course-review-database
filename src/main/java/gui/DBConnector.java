package gui;

import java.sql.*;
import java.util.List;

public class DBConnector {
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:Reviews.sqlite3";
    Connection connection;


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
}
