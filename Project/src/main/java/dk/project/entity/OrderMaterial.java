package dk.project.entity;

public class OrderMaterial {

    // Attributes
    private int id;
    private Order order;
    private Material material;
    private double quantity;
    private double unitPrice; /* The price when the order was placed */
    private double totalPrice;

    // ___________________________________________________________

    public OrderMaterial(int id, Order order, Material material, double quantity, double unitPrice, double totalPrice) {
        this.id = id;
        this.order = order;
        this.material = material;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    // ___________________________________________________________

    public int getId() {
        return id;
    }

    // ___________________________________________________________

    public void setId(int id) {
        this.id = id;
    }

    // ___________________________________________________________

    public Order getOrder() {
        return order;
    }

    // ___________________________________________________________

    public void setOrder(Order order) {
        this.order = order;
    }

    // ___________________________________________________________

    public Material getMaterial() {
        return material;
    }

    // ___________________________________________________________

    public void setMaterial(Material material) {
        this.material = material;
    }

    // ___________________________________________________________

    public double getQuantity() {
        return quantity;
    }

    // ___________________________________________________________

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    // ___________________________________________________________

    public double getTotalPrice() {
        return totalPrice;
    }

    // ___________________________________________________________

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // ___________________________________________________________

    public double getUnitPrice() {
        return unitPrice;
    }

    // ___________________________________________________________

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

} // OrderMaterial end