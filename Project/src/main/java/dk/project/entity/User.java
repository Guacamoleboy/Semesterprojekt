package dk.project.entity;

import java.sql.Timestamp;

public class User {

    // Attributes
    private int id;
    private String username;
    private int roleID;
    private String password_hash;
    private Timestamp createdAt;

    // __________________________________________________________

    public User (int id, String username, int roleID, String password_hash, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.roleID = roleID;
        this.password_hash = password_hash;
        this.createdAt = createdAt;
    }

    public User() {

    }

    // __________________________________________________________

    public int getId() {
        return id;
    }

    // __________________________________________________________

    public void setId(int id) {
        this.id = id;
    }

    // __________________________________________________________

    public String getUsername() {
        return username;
    }

    // __________________________________________________________

    public void setUsername(String username) {
        this.username = username;
    }

    // __________________________________________________________

    public int getRoleID() {
        return roleID;
    }

    // __________________________________________________________

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    // __________________________________________________________

    public String getPassword_hash() {
        return password_hash;
    }

    // __________________________________________________________

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    // __________________________________________________________

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // __________________________________________________________

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

} // User end