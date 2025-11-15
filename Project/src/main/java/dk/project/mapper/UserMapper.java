package dk.project.mapper;

import dk.project.entity.User;
import dk.project.DTO.UserDTO;
import dk.project.db.Database;
import dk.project.exception.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                    return toUser(rs);
                } else {
                    return null;
                }
            }
            } catch (SQLException e) {
                throw new DatabaseException("Fejl ved hentning af bruger", e);
            }
    }

    // _____________________________________________________________________

    public User getByUserName(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE LOWER(username) = LOWER(?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return toUser(rs);
            }
            return null;
        }
    }

    // ________________________________________________________________________________

    public List<User> getAll() throws DatabaseException {
        String sql = "SELECT * FROM users ORDER BY id";
        List<User> users = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(toUser(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af alle brugere", e);
        }

        return users;
    }

    // ________________________________________________________________________________

    public List<User> getByRole(int roleId) throws DatabaseException {
        String sql = "SELECT * FROM users WHERE role_id = ? ORDER BY id";
        List<User> users = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roleId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(toUser(rs));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af brugere med rolle ID: " + roleId, e);
        }

        return users;
    }

    // ________________________________________________________________________________

    public void updateUser(User user) {
        String sql = "UPDATE users SET username = ?, password_hash = ?, role_id = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword_hash());
            stmt.setInt(3, user.getRoleID());
            stmt.setInt(4, user.getId());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("User not found with ID: " + user.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ________________________________________________________________________________

    public void updateUser(int ID) throws DatabaseException {
        User user = getById(ID);
        String sql = "UPDATE users SET username = ?, password_hash = ?, role_id = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword_hash());
            stmt.setInt(3, user.getRoleID());
            stmt.setInt(4, user.getId());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("User not found with ID: " + user.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ________________________________________________________________________________

    public User toUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getInt("role_id"),
                rs.getString("password_hash"),
                rs.getTimestamp("created_at")
        );
    }

    // ________________________________________________________________________________

    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int rows = stmt.executeUpdate();

            if (rows == 0) {
                throw new SQLException("No user found with ID: " + userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user with ID " + userId, e);
        }
    }

    // ________________________________________________________________________________

    public void deleteUser(User user) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getId());
            int rows = stmt.executeUpdate();

            if (rows == 0) {
                throw new SQLException("No user found with ID: " + user.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user with ID " + user.getId(), e);
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