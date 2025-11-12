package dk.project.DTO;

import java.sql.Timestamp;

public class UserDTO {

    // Attributes
    private int id;
    private String username;
    private int roleID;
    private Timestamp createdAt;

    // _______________________________________________________________________

    public UserDTO(int id, String username, int roleID, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.roleID = roleID;
        this.createdAt = createdAt;
    }

    // _______________________________________________________________________

    public int getId() {
        return id;
    }

    // _______________________________________________________________________

    public String getUsername() {
        return username;
    }

    // _______________________________________________________________________

    public int getRoleID() {
        return roleID;
    }

    // _______________________________________________________________________

    public Timestamp getCreatedAt() {
        return createdAt;
    }

} // UserDTO end