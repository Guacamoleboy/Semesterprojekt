package dk.project.entity.AdminMenu;

public class MaterialCategory {

    private int id;
    private String name;

    // _______________________________________________

    public MaterialCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // _______________________________________________

    public int getId() {
        return id;
    }

    // _______________________________________________

    public String getName() {
        return name;
    }
}
