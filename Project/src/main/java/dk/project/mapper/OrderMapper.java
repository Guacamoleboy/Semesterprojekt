// Package
package dk.project.mapper;

// Imports
import dk.project.db.Database;
import dk.project.entity.Order;
import dk.project.entity.CarportOrder;
import dk.project.entity.CarportCategory;
import dk.project.entity.User;
import dk.project.exception.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;

public class OrderMapper {

    // Attributes

    // __________________________________________________________________

    public void newOrder(Order order) throws DatabaseException {
        String sql = "INSERT INTO orders (user_id, carport_order_id, total_price, status, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setObject(1, order.getUser() != null ? order.getUser().getId() : null, Types.INTEGER);
            stmt.setInt(2, order.getCarportOrder().getId());
            stmt.setDouble(3, order.getTotalPrice());
            stmt.setString(4, order.getStatus() != null ? order.getStatus() : "pending");
            stmt.setTimestamp(5, order.getCreatedAt() != null ? Timestamp.valueOf(order.getCreatedAt()) : Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    order.setId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved oprettelse af ordre", e);
        }
    }

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

    // __________________________________________________________________

    public Order getById(int id) throws DatabaseException {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return toOrder(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af ordre", e);
        }
    }

    // __________________________________________________________________

    public Order toOrder(ResultSet rs) throws SQLException {
        Timestamp createdAt = rs.getTimestamp("created_at");
        
        CarportCategory category = new CarportCategory();
        category.setId(1);
        CarportOrder carportOrder = new CarportOrder(
                rs.getInt("carport_order_id"),
                null,
                category,
                0.0, 0.0, 0.0,
                null,
                null,
                false,
                null, null,
                false,
                null
        );
        
        return new Order(
                rs.getInt("id"),
                null,
                carportOrder,
                rs.getDouble("total_price"),
                rs.getString("status"),
                createdAt != null ? createdAt.toLocalDateTime() : null,
                null
        );
    }

} // OrderMapper end