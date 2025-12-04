// Package
package dk.project.mapper;

// Imports
import dk.project.db.Database;
import dk.project.entity.CarportCategory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.*;

class CarportCategoryMapperTest {

    // Attributes
    private CarportCategoryMapper carportCategoryMapper;

    // _________________________________________________________

    @BeforeAll
    static void beforeAll() {
        Database.setDatabaseName("fog_test");
    }

    // _________________________________________________________

    @BeforeEach
    void setUp() throws Exception {

        // Initial
        carportCategoryMapper = new CarportCategoryMapper();

        // Clear and Setup DB
        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement()) {

            stmt.execute("TRUNCATE TABLE carport_category RESTART IDENTITY CASCADE");
            stmt.execute("""
                INSERT INTO carport_category (name) VALUES
                ('Fladt tag'),
                ('Høj rejsning')
                """);


        }

    }

    // _________________________________________________________

    @Test
    void getById() throws Exception {

        // Arrange
        // N/A

        // Act
        CarportCategory carportCategory = carportCategoryMapper.getById(1);

        // Assert
        assertNotNull(carportCategory);
        assertEquals(1, carportCategory.getId());
        assertEquals("Fladt tag", carportCategory.getName());
        assertNull(carportCategoryMapper.getById(3)); // Null check

    }

} // CarportCategoryMapperTest end