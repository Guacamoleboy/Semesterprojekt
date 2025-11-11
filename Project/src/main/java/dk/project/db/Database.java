package dk.project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // Attributes
    private static String databaseName = System.getenv().getOrDefault("DB_NAME", "fog");
    private static String host = System.getenv().getOrDefault("DB_HOST", "localhost");
    private static String port = System.getenv().getOrDefault("DB_PORT", "5432");
    private static String user = System.getenv().getOrDefault("DB_USER", "postgres");
    private static String password = System.getenv().getOrDefault("DB_PASSWORD", "postgres");
    private static final String URL_TEMPLATE = "jdbc:postgresql://%s:%s/%s";

    // ________________________________________________________________

    public static Connection getConnection() throws SQLException {
        String url = String.format(URL_TEMPLATE, host, port, databaseName);
        return DriverManager.getConnection(url, user, password);
    }

    // ________________________________________________________________

    public static void setDatabaseName(String newDatabaseName) {
        databaseName = newDatabaseName;
    }

}