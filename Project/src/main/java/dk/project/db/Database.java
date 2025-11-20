/*

    File changed so our project has Github Automations included as a workflow
    It'll pass / fail our Unit Tests by default on Pull Requests to either "development"
    or "review" branch.

    If it's not being run by Github it'll use our local variables instead.

    - Guac

*/

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

} // Database end