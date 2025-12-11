/*

    Hikari Connection Pool
    ______________________

    File changed so our project has Github Automations included as a workflow
    It'll pass / fail our Unit Tests by default on Pull Requests to either "development"
    or "review" branch.

    If it's not being run by Github it'll use our local variables instead.

    - Guac

*/

// Package
package dk.project.db;

// Imports
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {

    // Attributes
    private static String databaseName = System.getenv().getOrDefault("DB_NAME", "fog");
    private static final String host = System.getenv().getOrDefault("DB_HOST", "localhost");
    private static final String port = System.getenv().getOrDefault("DB_PORT", "5432");
    private static final String user = System.getenv().getOrDefault("DB_USER", "postgres");
    private static final String password = System.getenv().getOrDefault("DB_PASSWORD", "postgres");
    private static final String URL_TEMPLATE = "jdbc:postgresql://%s:%s/%s";
    private static HikariDataSource hikariDataSource;

    // ___________________________________________________________

    private static synchronized void startPool() {

        // Important to avoid bugs with Unit Tests
        close();

        // Initial Config
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(String.format(URL_TEMPLATE, host, port, databaseName));
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);

        // Pool Settings
        hikariConfig.setMaximumPoolSize(10); // Pool limits | http-requests
        hikariConfig.setMinimumIdle(2); // Minimum active connections available for speed
        hikariConfig.setIdleTimeout(30000); // 30sec
        hikariConfig.setConnectionTimeout(10000); // 10sec delay -> Timeout
        hikariConfig.setPoolName("FogPool"); // Name for debugging

        // If all worked out
        hikariDataSource = new HikariDataSource(hikariConfig);

    }

    // ___________________________________________________________

    public static Connection getConnection() throws SQLException {

        // Initial connection
        if (hikariDataSource == null) {
            startPool();
        }

        return hikariDataSource.getConnection();

    }

    // ___________________________________________________________
    // Unit Tests | DO NOT REMOVE

    public static void setDatabaseName(String newName) {
        databaseName = newName;
        startPool();
    }

    // ___________________________________________________________

    public static void close() {

        if (hikariDataSource != null) {
            hikariDataSource.close();
        }

    }

} // Database end