package dk.project.DTO;

import dk.project.entity.AdminMenu.Material;

public class MaterialDTO {

    public int id;
    public String name;
    public String description;
    public String unit;
    public Integer length;
    public Integer width;
    public Integer height;
    public double price;
    public int category_id;
    public String category_name;

    // _______________________________________________

    public MaterialDTO(Material m, String categoryName) {
        this.id = m.getId();
        this.name = m.getName();
        this.description = m.getDescription();
        this.unit = m.getUnit();
        this.length = m.getLength();
        this.width = m.getWidth();
        this.height = m.getHeight();
        this.price = m.getPrice();
        this.category_id = m.getCategory_id();
        this.category_name = categoryName;
    }

    // _______________________________________________

    public int getId() {
        return id;
    }

    // _______________________________________________

    public void setId(int id) {
        this.id = id;
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

    public Integer getWidth() {
        return width;
    }

    // _______________________________________________

    public void setWidth(Integer width) {
        this.width = width;
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

    public double getPrice() {
        return price;
    }

    // _______________________________________________

    public void setPrice(double price) {
        this.price = price;
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

    public String getCategory_name() {
        return category_name;
    }

    // _______________________________________________

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}

