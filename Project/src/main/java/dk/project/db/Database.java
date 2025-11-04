/*

    Database.java | Sat op så Unit Test kan køres fra ekstern database.

    Sidst opdateret af: Guacamoleboy
    Dato: 01/11-2025

*/

// Package
package dk.project.db;

// Imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // Attributes
    private static String databaseName = "Project";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    // ________________________________________________

    public static Connection getConnection() throws SQLException {
        String url = String.format(URL, databaseName);
        return DriverManager.getConnection(url, USER, PASSWORD);
    }

    // ________________________________________________

    public static void setDatabaseName(String newDatabaseName) {
        databaseName = newDatabaseName;
    }

} // Database end