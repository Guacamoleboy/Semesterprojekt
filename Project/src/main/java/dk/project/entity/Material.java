package dk.project.entity;

public class Material {

    // Attributes
    private int id;
    private MaterialCategory materialCategory;
    private String name;
    private String description;
    private String unit;
    private Double length;
    private Double width;
    private Double height;
    private double price;

    // _____________________________________________

    public Material(MaterialCategory materialCategory, int id, String name, String description, String unit, Double length, Double width, Double height, double price) {
        this.materialCategory = materialCategory;
        this.id = id;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.length = length;
        this.width = width;
        this.height = height;
        this.price = price;
    }

    // _____________________________________________

    public double getPrice() {
        return price;
    }

    // _____________________________________________

    public void setPrice(double price) {
        this.price = price;
    }

    // _____________________________________________

    public int getId() {
        return id;
    }

    // _____________________________________________

    public void setId(int id) {
        this.id = id;
    }

    // _____________________________________________

    public MaterialCategory getMaterialCategory() {
        return materialCategory;
    }

    // _____________________________________________

    public void setMaterialCategory(MaterialCategory materialCategory) {
        this.materialCategory = materialCategory;
    }

    // _____________________________________________

    public String getName() {
        return name;
    }

    // _____________________________________________

    public void setName(String name) {
        this.name = name;
    }

    // _____________________________________________

    public String getDescription() {
        return description;
    }

    // _____________________________________________

    public void setDescription(String description) {
        this.description = description;
    }

    // _____________________________________________

    public String getUnit() {
        return unit;
    }

    // _____________________________________________

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // _____________________________________________

    public Double getLength() {
        return length;
    }

    // _____________________________________________

    public void setLength(Double length) {
        this.length = length;
    }

    // _____________________________________________

    public Double getWidth() {
        return width;
    }

    // _____________________________________________

    public void setWidth(Double width) {
        this.width = width;
    }

    // _____________________________________________

    public Double getHeight() {
        return height;
    }

    // _____________________________________________

    public void setHeight(Double height) {
        this.height = height;
    }

} // Material end