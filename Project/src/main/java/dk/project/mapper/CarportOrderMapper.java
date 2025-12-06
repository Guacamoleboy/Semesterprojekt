package dk.project.mapper;

import dk.project.db.Database;
import dk.project.entity.CarportOrder;
import dk.project.entity.Customer;
import dk.project.entity.CarportCategory;
import dk.project.exception.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarportOrderMapper {

    CustomerMapper customerMapper = new CustomerMapper();

    // _____________________________________________________________________

    public void newOrder(CarportOrder order) throws DatabaseException {
        String sql = "INSERT INTO carport_orders (customer_id, carport_category_id, width, length, height, angle, roof,"+
                "has_tool_shed, tool_shed_width, tool_shed_length, has_trapez) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            setNullableInt(stmt, 1, order.getCustomer().getId());
            stmt.setInt(2, order.getCategory().getId());
            stmt.setDouble(3, order.getWidth());
            stmt.setDouble(4, order.getLength());
            stmt.setDouble(5, order.getHeight());
            setNullableDouble(stmt, 6, order.getAngle());
            stmt.setString(7, order.getRoof());
            stmt.setBoolean(8, order.isHasToolShed());
            setNullableDouble(stmt, 9, order.getToolShedWidth());
            setNullableDouble(stmt, 10, order.getToolShedLength());
            stmt.setBoolean(11, order.isHasTrapez());

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    order.setId(rs.getInt("id"));

                }

            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved oprettelse af carport ordre", e);

        }
    }

    // _____________________________________________________________________

    public void deleteOrder(int id) throws DatabaseException {
        String sql = "DELETE FROM carport_orders WHERE id = ?";

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();

            if (rows == 0) {

                throw new SQLException("Ordrer ikke fundet med ID: " + id);

            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved sletning af carport ordre", e);
        }
    }

    // _____________________________________________________________________

    public void updateOrder(CarportOrder order) throws DatabaseException {
        String sql = "UPDATE carport_orders SET user_id = ?, carport_category_id = ?, width = ?, length = ?, height = ?, angle = ?, roof = ?,"+
                "has_tool_shed = ?, tool_shed_width = ?, tool_shed_length = ?, has_trapez = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order.getCustomer().getId());
            stmt.setInt(2, order.getCategory().getId());
            stmt.setDouble(3, order.getWidth());
            stmt.setDouble(4, order.getLength());
            stmt.setDouble(5, order.getHeight());

            setNullableDouble(stmt, 6, order.getAngle());

            stmt.setString(7, order.getRoof());
            stmt.setBoolean(8, order.isHasToolShed());

            setNullableDouble(stmt, 9, order.getToolShedWidth());

            setNullableDouble(stmt, 10, order.getToolShedLength());

            stmt.setBoolean(11, order.isHasTrapez());
            stmt.setInt(12, order.getId());

            int rows = stmt.executeUpdate();

            if (rows == 0) {

                throw new SQLException("Ordrer ikke fundet med ID: " + order.getId());

            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved opdatering af carport ordre", e);
        }
    }

    // _____________________________________________________________________

    public List<CarportOrder> getAllOrders() throws DatabaseException {

        List<CarportOrder> list = new ArrayList<>();

        String sql = "SELECT * FROM carport_orders ORDER BY id";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                list.add(toOrder(rs));

            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af carport ordre", e);
        }

        return list;
    }

    // _____________________________________________________________________

    public CarportOrder getOrderByID(int id) throws DatabaseException {

        String sql = "SELECT * FROM carport_orders WHERE id = ?";

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return toOrder(rs);

                }

            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved hentning af carport ordre", e);

        }

        return null;

    }

    // _____________________________________________________________________

    public List<CarportOrder> getOrdersByCustomerID(int userId) throws DatabaseException {

        List<CarportOrder> list = new ArrayList<>();
        String sql = "SELECT * FROM carport_orders WHERE user_id = ? ORDER BY id";

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next())  {

                    list.add(toOrder(rs));

                }

            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved hentning af carport ordre for kunde", e);

        }

        return list;
    }

    // _____________________________________________________________________

    private CarportOrder toOrder(ResultSet rs) throws SQLException, DatabaseException {
        Customer customer = customerMapper.getCustomerByID(rs.getInt("customer_id"));

        CarportCategory category = new CarportCategory();
        category.setId(rs.getInt("carport_category_id"));

        CarportOrder order = new CarportOrder(
                rs.getInt("id"),
                customer,
                category,
                rs.getDouble("width"),
                rs.getDouble("length"),
                rs.getDouble("height"),
                rs.getDouble("angle"),
                rs.getString("roof"),
                rs.getBoolean("has_tool_shed"),
                rs.getDouble("tool_shed_width"),
                rs.getDouble("tool_shed_length"),
                rs.getBoolean("has_trapez"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );

        return order;
    }

    // _____________________________________________________________________

    private void setNullableDouble(PreparedStatement stmt, int index, Double value) throws SQLException {
        if (value != null) {
            stmt.setDouble(index, value);
        } else {
            stmt.setNull(index, Types.DOUBLE);
        }
    }



    // ________________________________________________________________________________

    private void setNullableInt(PreparedStatement stmt, int index, Integer value) throws SQLException {

        if (value != null) {

            stmt.setInt(index, value);

        } else {

            stmt.setNull(index, java.sql.Types.INTEGER);

        }
    }

}