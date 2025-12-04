// Package
package dk.project.mapper.AdminMenu;

// Imports
import dk.project.db.Database;
import dk.project.entity.AdminMenu.Material;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MaterialMapperTest {

    // Attributes
    private MaterialMapper materialMapper;

    // ______________________________________________________

    @BeforeAll
    static void beforeAll() {
        Database.setDatabaseName("fog_test");
    }

    // ______________________________________________________

    @BeforeEach
    void setUp() throws Exception {

        // Initial
        materialMapper = new MaterialMapper();

        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement()) {

            stmt.execute("TRUNCATE TABLE materials RESTART IDENTITY CASCADE");
            stmt.execute("""
                INSERT INTO materials
                    (category_id, name, description, unit, length, width, height, price)
                VALUES
                    (1, 'Regel 45x95 mm', 'Trykimprægneret træ', 'stk', 300, 45, 95, 29.95),
                    (1, 'Skruer 5x40 mm', 'Galvaniserede skruer', 'pakke', NULL, NULL, NULL, 49.95)
            """);
        }

    }

    // ______________________________________________________

    @Test
    void getMaterials() throws Exception {

        // Act
        List<Material> list = materialMapper.getMaterials();

        // Assert
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("Regel 45x95 mm", list.get(0).getName());
        assertEquals("Skruer 5x40 mm", list.get(1).getName());

    }

    // ______________________________________________________

    @Test
    void getMaterialById() throws Exception {

        // Act
        Material m = materialMapper.getMaterialByID(1);

        // Assert
        assertNotNull(m);
        assertEquals(1, m.getId());
        assertEquals("Regel 45x95 mm", m.getName());
        assertEquals(300, m.getLength());
        assertEquals(45, m.getWidth());
        assertEquals(95, m.getHeight());
        assertNull(materialMapper.getMaterialByID(3)); // Null checks

    }

    // ______________________________________________________

    @Test
    void newMaterial() throws Exception {

        // Arrange
        Material material = new Material(
                2,
                "Tagplade Plast",
                "Klar bølgeplast",
                "stk",
                600,
                100,
                null,
                129.95
        );

        // Act
        materialMapper.newMaterial(material);

        // Assert
        assertTrue(material.getId() > 0);
        Material m = materialMapper.getMaterialByID(material.getId());
        assertNotNull(m);
        assertEquals("Tagplade Plast", m.getName());
        assertEquals(600, m.getLength());
        assertNull(m.getHeight());

    }

    // ______________________________________________________

    @Test
    void updateMaterial() throws Exception {

        // Arrange
        Material m = materialMapper.getMaterialByID(1);
        m.setName("Opdateret Regel");
        m.setPrice(39.95);

        // Act
        materialMapper.updateMaterial(m);

        // Assert
        Material updated = materialMapper.getMaterialByID(1);
        assertEquals("Opdateret Regel", updated.getName());
        assertEquals(39.95, updated.getPrice());

    }

    // ______________________________________________________

    @Test
    void deleteMaterial() throws Exception {

        // Act
        materialMapper.deleteMaterial(1);

        // Assert
        assertNull(materialMapper.getMaterialByID(1));
        assertEquals(1, materialMapper.getMaterials().size());

    }

}