package dk.project.mapper;

import dk.project.db.Database;
import dk.project.entity.User;
import dk.project.exception.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    // Attributes
    private UserMapper usermapper;

    // ______________________________________________________________________

    @BeforeAll
    static void beforeAll() {
        Database.setDatabaseName("fog_test");
    }

    // ______________________________________________________________________

    @BeforeEach
    void setUp() throws Exception {

        usermapper = new UserMapper();

        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE users RESTART IDENTITY CASCADE");
            stmt.execute("TRUNCATE TABLE roles RESTART IDENTITY CASCADE");
            stmt.execute("INSERT INTO roles (name) VALUES ('employee'), ('admin')");
        }

    }

    // ______________________________________________________________________

    // Workflow test review

    @Test
    void shouldInsertAndRetrieveUser() throws SQLException, DatabaseException {

        // Arange
        User u = new User();
        u.setUsername("unittest");
        u.setPassword_hash("guacamoleboy123!");
        u.setRoleID(1);
        u.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        // Act
        usermapper.newUser(u);
        User unitTestOne = usermapper.getById(u.getId());

        // Assert
        assertNotNull(unitTestOne);
        assertTrue(u.getId() > 0);
        assertEquals("unittest", unitTestOne.getUsername());
        assertEquals(1, unitTestOne.getRoleID());
        assertEquals("guacamoleboy123!", unitTestOne.getPassword_hash());
        assertNotNull(unitTestOne.getCreatedAt());

    }



} // UserMapperTest end