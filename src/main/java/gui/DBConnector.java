package gui;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:/Users/priscillatehrani/IdeaProjects/login?client_id=58566862bd2a5ff748fb&return_to=/login/oauth/login?client_id=58566862bd2a5ff748fb&return_to=/login/oauth/hw7-coursereview-qvp8hy-yvp9pc-fnv2vx/Reviews.sqlite3";

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
