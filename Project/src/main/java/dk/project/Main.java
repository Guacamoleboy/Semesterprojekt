// Package
package dk.project;

// Imports
import dk.project.server.Server;
import java.sql.SQLException;

public class Main {

    // Attributes

    // _____________________________________________________

    public static void main(String[] args) throws SQLException {

        Server server = new Server();
        server.start(7000); // Standard Server for Javalin

    }

} // Main end