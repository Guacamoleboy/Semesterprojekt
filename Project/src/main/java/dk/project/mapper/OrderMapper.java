// Package
package dk.project.mapper;

// Imports
import dk.project.db.Database;
import dk.project.entity.*;
import dk.project.exception.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Set;

public class OrderMapper {

    // Attributes

    // __________________________________________________________________

    public int newOrder(Order order) throws DatabaseException {
        String sql = "INSERT INTO orders (customer_id, carport_order_id, total_price, status) " +
                "VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order.getCustomer().getId());
            stmt.setInt(2, order.getCarportOrder().getId());
            stmt.setDouble(3, order.getTotalPrice());
            stmt.setString(4, order.getStatus());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                order.setId(id);
                return id;
            } else {
                throw new DatabaseException("Failed to create order");
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
    // TODO Test method | DO NOT REMOVE

    public Object getOrderFieldById(String field, int id) throws DatabaseException {

        // Only allow these
        Set<String> allowedFields = Set.of(
                "customer_id",
                "carport_order_id",
                "total_price",
                "status",
                "created_at"
        );

        // Checks
        if (!allowedFields.contains(field)) {
            throw new DatabaseException("Field not found: " + field);
        }

        // Query
        String sql = "SELECT " + field + " FROM orders WHERE id = ?";

        // Try-catch for that query
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            // Makes sure we only hit the field and type we want
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getObject(field);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error | " + field + " | "+ e);
        }
    }

    // __________________________________________________________________

    public void updateStatus(int id, String status) throws DatabaseException {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, id);

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Ingen ordre fundet med ID " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke opdatere status", e);
        }
    }

    // __________________________________________________________________

    public void updateTotalPrice(int id, double totalPrice) throws DatabaseException {
        String sql = "UPDATE orders SET total_price = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, totalPrice);
            stmt.setInt(2, id);

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Ingen ordre fundet med ID " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke opdatere total_price", e);
        }
    }

    // __________________________________________________________________

    /*public Order toOrder(ResultSet rs) throws SQLException {
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
    }*/

    // __________________________________________________________________

    public Order getByIdTilbud(int id) throws DatabaseException {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return toOrderTilbud(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af ordre", e);
        }
    }

    // __________________________________________________________________

    public Order toOrder(ResultSet rs) throws SQLException, DatabaseException {
        Timestamp createdAt = rs.getTimestamp("created_at");
        int carportOrderId = rs.getInt("carport_order_id");
        CarportOrder carportOrder = new CarportOrderMapper().getOrderByID(carportOrderId);

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

    // __________________________________________________________________

    public Order toOrderTilbud(ResultSet rs) throws SQLException, DatabaseException {

        // Initial
        Timestamp createdAt = rs.getTimestamp("created_at");
        int carportOrderId = rs.getInt("carport_order_id");
        int customerId = rs.getInt("customer_id");

        CarportOrder carportOrder = new CarportOrderMapper().getOrderByID(carportOrderId);

        Customer customer = null;

        if (customerId > 0) {
            customer = new CustomerMapper().getCustomerByID(customerId);
        }

        // Creates new order
        return new Order(
                rs.getInt("id"),
                customer,
                carportOrder,
                rs.getDouble("total_price"),
                rs.getString("status"),
                createdAt != null ? createdAt.toLocalDateTime() : null,
                null
        );

    }

} // OrderMapper end