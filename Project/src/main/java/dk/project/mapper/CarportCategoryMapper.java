package dk.project.mapper;

import dk.project.db.Database;
import dk.project.entity.CarportCategory;
import dk.project.exception.DatabaseException;
import java.sql.*;

public class CarportCategoryMapper {

    // Attributes

    // __________________________________________________________________

    public CarportCategory getById(int id) throws DatabaseException {
        String sql = "SELECT * FROM carport_category WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CarportCategory category = new CarportCategory();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                    return category;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af carport kategori", e);
        }
    }

    // __________________________________________________________________

    public CarportCategory getFirstCategory() throws DatabaseException {
        String sql = "SELECT * FROM carport_category ORDER BY id LIMIT 1";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                CarportCategory category = new CarportCategory();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                return category;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af carport kategori", e);
        }
    }

} // CarportCategoryMapper end
