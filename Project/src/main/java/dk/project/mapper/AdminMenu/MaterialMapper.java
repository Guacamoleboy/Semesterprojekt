package dk.project.mapper.AdminMenu;

import dk.project.entity.AdminMenu.Material;
import dk.project.exception.DatabaseException;
import java.util.ArrayList;
import java.util.List;
import dk.project.db.Database;

import javax.annotation.Nullable;
import java.sql.*;

public class MaterialMapper {

    // Attributes

    // _____________________________________________________________________

    public void newMaterial(Material material) throws DatabaseException {

        String sql = "INSERT INTO materials  (category_id, name, description, unit, length, width, height, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, material.getCategory_id());
            stmt.setString(2, material.getName());
            stmt.setString(3, material.getDescription());
            stmt.setString(4, material.getUnit());

            setNullableInt(stmt, 5, material.getLength());
            setNullableInt(stmt, 6, material.getWidth());
            setNullableInt(stmt, 7, material.getHeight());

            stmt.setDouble(8, material.getPrice());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {

                if (keys.next()) {

                    material.setId(keys.getInt(1));

                }

            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved oprettelse af nyt materiale", e);

        }
    }

    // _____________________________________________________________________

    public List<Material> getMaterials() throws DatabaseException {

        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM materials";

        try (Connection conn = Database.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Material m = toMaterial(rs);
                materials.add(m);

            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved hentning af materialer", e);

        }

        return materials;
    }

    // ________________________________________________________________________________

    public Material getMaterialByID(int id) throws DatabaseException {

        String sql = "SELECT * FROM materials WHERE id = ?";

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return toMaterial(rs);

                }

            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved hentning af materialer", e);

        }

        return null;
    }

    // ________________________________________________________________________________

    public void updateMaterial(Material material) throws DatabaseException {
        String sql = "UPDATE materials SET category_id = ?, name = ?, description = ?, unit = ?, length = ?, width = ?, height = ?, price = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, material.getCategory_id());
            stmt.setString(2, material.getName());
            stmt.setString(3, material.getDescription());
            stmt.setString(4, material.getUnit());

            setNullableInt(stmt, 5, material.getLength());
            setNullableInt(stmt, 6, material.getWidth());
            setNullableInt(stmt, 7, material.getHeight());

            stmt.setDouble(8, material.getPrice());
            stmt.setInt(9, material.getId());

            int rows = stmt.executeUpdate();

            if (rows == 0) {

                throw new SQLException("Materiale ikke fundet med ID: " + material.getId());

            }

        } catch (SQLException e) {

            throw new DatabaseException("Fejl ved opdatering af materiale", e);

        }
    }

    // ________________________________________________________________________________

    public void deleteMaterial(int id) {

        String sql = "DELETE FROM materials WHERE id = ?";

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows == 0) {

                throw new SQLException("Intet materiale fundet med ID: " + id);

            }
        } catch (SQLException e) {

            throw new RuntimeException("Fejl ved sletning af materiale med ID " + id, e);

        }
    }

    // ________________________________________________________________________________

    private Material toMaterial(ResultSet rs) throws SQLException {
        Integer length = getNullableInt(rs, "length");
        Integer width = getNullableInt(rs, "width");
        Integer height = getNullableInt(rs, "height");

        Material m = new Material(
                rs.getInt("category_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("unit"),
                length,
                width,
                height,
                rs.getDouble("price")
        );
        m.setId(rs.getInt("id"));

        return m;
    }

    // ________________________________________________________________________________

    private void setNullableInt(PreparedStatement stmt, int index, Integer value) throws SQLException {

        if (value != null) {

            stmt.setInt(index, value);

        } else {

            stmt.setNull(index, java.sql.Types.INTEGER);

        }
    }

    // ________________________________________________________________________________

    private Integer getNullableInt(ResultSet rs, String columnName) throws SQLException {

        int value = rs.getInt(columnName);
        return rs.wasNull() ? null : value;

    }

}