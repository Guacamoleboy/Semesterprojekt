package dk.project.entity.AdminMenu;

/*

Jeg vidste ikke helt hvad vores produkter skulle indeholde, men har sat ind, så vi også kan angive antal. f.eks. 50 skruer
2 planker osv osv.

Size er mål - vidste ikke hvad jeg lige skulle kalde det på engelsk. Kom med et godt ord til det!!!

 */

public class Product {
    private int id;
    private String title;
    private String description;
    private String size;
    private int quantity;
    private double unitPrice;

    // _______________________________________________

    public Product() {}

    // _______________________________________________

    public Product(int id, String title, String description, String size, int quantity, double unitPrice) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.size = size;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
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

    public String getTitle() {
        return title;
    }

    // _______________________________________________

    public void setTitle(String title) {
        this.title = title;
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

    public String getSize() {
        return size;
    }

    // _______________________________________________

    public void setSize(String size) {
        this.size = size;
    }

    // _______________________________________________

    public int getQuantity() {
        return quantity;
    }

    // _______________________________________________

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // _______________________________________________

    public double getUnitPrice() {
        return unitPrice;
    }

    // _______________________________________________

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

}
