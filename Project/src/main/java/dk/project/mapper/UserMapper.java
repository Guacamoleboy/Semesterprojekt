package dk.project.mapper;

import dk.project.entity.User;
import dk.project.DTO.UserDTO;
import dk.project.db.Database;
import dk.project.exception.DatabaseException;
import java.sql.*;

public class UserMapper {

    // Attributes

    // _____________________________________________________________________

    public void newUser(User user) throws DatabaseException {
        String sql = "INSERT INTO users (username, role_id, password_hash, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setInt(2, user.getRoleID());
            stmt.setString(3, user.getPassword_hash());
            stmt.setTimestamp(4, user.getCreatedAt());
            stmt.executeUpdate();

            // Gets the auto generated ID and adds it to our User Object
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved oprettelse af bruger", e);
        }
    }

    // _____________________________________________________________________

    public User getById(int id) throws DatabaseException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getInt("role_id"),
                            rs.getString("password_hash"),
                            rs.getTimestamp("created_at")
                    );
                } else {
                    return null;
                }
            }
            } catch (SQLException e) {
                throw new DatabaseException("Fejl ved hentning af bruger", e);
            }
    }

    // ________________________________________________________________________________

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRoleID(),
                user.getCreatedAt()
        );
    }

} // UserMapper end