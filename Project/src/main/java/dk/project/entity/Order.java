package dk.project.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    // Attributes
    private int id;
    private User user;
    private CarportOrder carportOrder;
    private double totalPrice;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderMaterial> orderMaterials;

    // ________________________________________________________________

    public Order(int id, User user, CarportOrder carportOrder, double totalPrice, String status, LocalDateTime createdAt, List<OrderMaterial> orderMaterials) {
        this.id = id;
        this.user = user;
        this.carportOrder = carportOrder;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.orderMaterials = orderMaterials;
    }

    // ________________________________________________________________

    public List<OrderMaterial> getOrderMaterials() {
        return orderMaterials;
    }

    // ________________________________________________________________

    public void setOrderMaterials(List<OrderMaterial> orderMaterials) {
        this.orderMaterials = this.orderMaterials;
    }

    // ________________________________________________________________

    public int getId() {
        return id;
    }

    // ________________________________________________________________

    public void setId(int id) {
        this.id = id;
    }

    // ________________________________________________________________

    public User getUser() {
        return user;
    }

    // ________________________________________________________________

    public void setUser(User user) {
        this.user = user;
    }

    // ________________________________________________________________

    public CarportOrder getCarportOrder() {
        return carportOrder;
    }

    // ________________________________________________________________

    public void setCarportOrder(CarportOrder carportOrder) {
        this.carportOrder = carportOrder;
    }

    // ________________________________________________________________

    public double getTotalPrice() {
        return totalPrice;
    }

    // ________________________________________________________________

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // ________________________________________________________________

    public String getStatus() {
        return status;
    }

    // ________________________________________________________________

    public void setStatus(String status) {
        this.status = status;
    }

    // ________________________________________________________________

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ________________________________________________________________

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

} // Order end