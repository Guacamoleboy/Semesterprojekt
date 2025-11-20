package dk.project.mapper;

import dk.project.db.Database;
import dk.project.entity.CarportOrder;
import dk.project.entity.CarportCategory;
import dk.project.exception.DatabaseException;
import java.sql.*;
import java.time.LocalDateTime;

public class CarportOrderMapper {

    // Attributes

    // __________________________________________________________________

    public void newCarportOrder(CarportOrder carportOrder) throws DatabaseException {
        String sql = "INSERT INTO carport_orders (user_id, carport_category_id, width, length, height, angle, roof, has_tool_shed, tool_shed_width, tool_shed_length, has_trapez, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setObject(1, carportOrder.getUser() != null ? carportOrder.getUser().getId() : null, Types.INTEGER);
            stmt.setInt(2, carportOrder.getCategory().getId());
            stmt.setDouble(3, carportOrder.getWidth());
            stmt.setDouble(4, carportOrder.getLength());
            stmt.setDouble(5, carportOrder.getHeight());
            stmt.setObject(6, carportOrder.getAngle(), Types.DECIMAL);
            stmt.setString(7, carportOrder.getRoof());
            stmt.setBoolean(8, carportOrder.isHasToolShed());
            stmt.setObject(9, carportOrder.getToolShedWidth(), Types.DECIMAL);
            stmt.setObject(10, carportOrder.getToolShedLength(), Types.DECIMAL);
            stmt.setBoolean(11, carportOrder.isHasTrapez());
            stmt.setTimestamp(12, carportOrder.getCreatedAt() != null ? Timestamp.valueOf(carportOrder.getCreatedAt()) : Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    carportOrder.setId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved oprettelse af carport ordre", e);
        }
    }

    // __________________________________________________________________

    public CarportOrder toCarportOrder(ResultSet rs) throws SQLException {
        Timestamp createdAt = rs.getTimestamp("created_at");
        CarportCategory category = new CarportCategory();
        category.setId(rs.getInt("carport_category_id"));
        
        return new CarportOrder(
                rs.getInt("id"),
                null,
                category,
                rs.getDouble("width"),
                rs.getDouble("length"),
                rs.getDouble("height"),
                rs.getObject("angle", Double.class),
                rs.getString("roof"),
                rs.getBoolean("has_tool_shed"),
                rs.getObject("tool_shed_width", Double.class),
                rs.getObject("tool_shed_length", Double.class),
                rs.getBoolean("has_trapez"),
                createdAt != null ? createdAt.toLocalDateTime() : null
        );
    }

} // CarportOrderMapper end
