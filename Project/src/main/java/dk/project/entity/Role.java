package dk.project.entity;

public class Role {

    // Attributes
    private int id;
    private String name;

    // ________________________________________

    public Role() {}

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // ________________________________________

    public int getId() {
        return id;
    }

    // ________________________________________

    public void setId(int id) {
        this.id = id;
    }

    // ________________________________________

    public String getName() {
        return name;
    }

    // ________________________________________

    public void setName(String name) {
        this.name = name;
    }

} // Role end