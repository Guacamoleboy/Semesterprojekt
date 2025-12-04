// Package
package dk.project.mapper.AdminMenu;

// Imports
import dk.project.db.Database;
import dk.project.entity.AdminMenu.MaterialCategory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MaterialCategoryMapperTest {

    // Attributes
    private MaterialCategoryMapper materialCategoryMapper;

    // _______________________________________________________

    @BeforeAll
    static void beforeAll() {
        Database.setDatabaseName("fog_test");
    }

    // _______________________________________________________

    @BeforeEach
    void setUp() throws Exception {

        // Initial
        materialCategoryMapper = new MaterialCategoryMapper();

        // Clear and execute to DB
        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement()) {

            stmt.execute("TRUNCATE TABLE materials_category RESTART IDENTITY CASCADE");

            stmt.execute("""
                INSERT INTO materials_category (name) VALUES
                ('Beslag & Skruer'),
                ('Træ & Tagplader')
            """);

        }

    }

    // _______________________________________________________

    @Test
    void getCategories() throws Exception {

        // Act
        List<MaterialCategory> list = materialCategoryMapper.getCategories();

        // Assert
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("Beslag & Skruer", list.get(0).getName());
        assertEquals("Træ & Tagplader", list.get(1).getName());

    }

    // _______________________________________________________

    @Test
    void getCategoryById() throws Exception {

        // Act
        MaterialCategory materialCategory = materialCategoryMapper.getCategoryById(1);

        // Assert
        assertNotNull(materialCategory);
        assertEquals(1, materialCategory.getId());
        assertEquals("Beslag & Skruer", materialCategory.getName());
        assertNull(materialCategoryMapper.getCategoryById(3)); // Null

    }



}