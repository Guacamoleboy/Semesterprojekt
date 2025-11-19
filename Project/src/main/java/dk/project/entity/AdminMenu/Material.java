package dk.project.entity.AdminMenu;

public class Material {

    private int id;
    private int category_id;
    private String name;
    private String description;
    private String unit;
    private Integer length;
    private Integer height;
    private Integer width;
    private double price;

    // _______________________________________________

    public Material(int category_id, String name, String description, String unit, Integer length, Integer width, Integer height, double price) {
        this.price = price;
        this.width = width;
        this.height = height;
        this.length = length;
        this.unit = unit;
        this.description = description;
        this.name = name;
        this.category_id = category_id;
    }

    public Material() {}

    // _______________________________________________

    public int getId() {
        return id;
    }

    // _______________________________________________

    public void setId(int id) {
        this.id = id;
    }

    // _______________________________________________

    public int getCategory_id() {
        return category_id;
    }

    // _______________________________________________

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    // _______________________________________________

    public String getName() {
        return name;
    }

    // _______________________________________________

    public void setName(String name) {
        this.name = name;
    }

    // _______________________________________________

    public String getDescription() {
        return description;
    }

    // _______________________________________________

    public void setDescription(String description) {
        this.description = description;
    }

    // _______________________________________________

    public String getUnit() {
        return unit;
    }

    // _______________________________________________

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // _______________________________________________

    public Integer getLength() {
        return length;
    }

    // _______________________________________________

    public void setLength(Integer length) {
        this.length = length;
    }

    // _______________________________________________

    public Integer getHeight() {
        return height;
    }

    // _______________________________________________

    public void setHeight(Integer height) {
        this.height = height;
    }

    // _______________________________________________

    public Integer getWidth() {
        return width;
    }

    // _______________________________________________

    public void setWidth(Integer width) {
        this.width = width;
    }

    // _______________________________________________

    public double getPrice() {
        return price;
    }

    // _______________________________________________

    public void setPrice(Double price) {
        this.price = price;
    }
}
