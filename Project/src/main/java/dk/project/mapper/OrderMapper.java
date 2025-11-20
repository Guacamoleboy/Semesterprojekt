// Package
package dk.project.mapper;

// Imports
import dk.project.db.Database;
import dk.project.exception.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper {

    // Attributes

    // __________________________________________________________________

    public String getStatusById(int id) throws DatabaseException {
        String sql = "SELECT status FROM orders WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af ordre status", e);
        }
    }

} // OrderMapper end