// Package
package dk.project.mapper.AdminMenu;

// Imports
import dk.project.db.Database;
import dk.project.entity.AdminMenu.MaterialCategory;
import dk.project.exception.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialCategoryMapper {

    // Attributes

    // _______________________________________________

    public List<MaterialCategory> getCategories() throws DatabaseException {

        List<MaterialCategory> categories = new ArrayList<>();
        String sql = "SELECT * FROM materials_category ORDER BY name";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                categories.add(new MaterialCategory(rs.getInt("id"), rs.getString("name")));

            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved hentning af materiale kategorier", e);

        }

        return categories;
    }

    // _______________________________________________

    public MaterialCategory getCategoryById(int id) throws DatabaseException {

        String sql = "SELECT * FROM materials_category WHERE id = ?";
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return new MaterialCategory(rs.getInt("id"), rs.getString("name"));

                }

            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved hentning af materiale kategori", e);

        }
        return null;
    }

}