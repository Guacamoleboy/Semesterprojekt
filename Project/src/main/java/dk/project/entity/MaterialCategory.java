package dk.project.entity;

public class MaterialCategory {

    // Attributes
    private int id;
    private String name;

    // ____________________________________________________

    public MaterialCategory() {}

    public MaterialCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // ____________________________________________________

    public int getId() {
        return id;
    }

    // ____________________________________________________

    public void setId(int id) {
        this.id = id;
    }

    // ____________________________________________________

    public String getName() {
        return name;
    }

    // ____________________________________________________

    public void setName(String name) {
        this.name = name;
    }

} // MaterialCategory end