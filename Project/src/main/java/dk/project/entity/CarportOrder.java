package dk.project.entity;

import java.time.LocalDateTime;

public class CarportOrder {

    // Attributes
    private int id;
    private User user;
    private CarportCategory category;
    private double width;
    private double length;
    private double height;
    private Double angle;
    private String roof;
    private boolean hasToolShed;
    private Double toolShedWidth;
    private Double toolShedLength;
    private boolean hasTrapez;
    private LocalDateTime createdAt;

    // _________________________________________________________________

    public CarportOrder(int id, User user, CarportCategory category, double width, double length,
    double height, Double angle, String roof, boolean hasToolShed,
    Double toolShedWidth, Double toolShedLength, boolean hasTrapez, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.category = category;
        this.width = width;
        this.length = length;
        this.height = height;
        this.angle = angle;
        this.roof = roof;
        this.hasToolShed = hasToolShed;
        this.toolShedWidth = toolShedWidth;
        this.toolShedLength = toolShedLength;
        this.hasTrapez = hasTrapez;
        this.createdAt = createdAt;
    }

    // _________________________________________________________________

    public boolean isHasTrapez() {
        return hasTrapez;
    }

    // _________________________________________________________________

    public void setHasTrapez(boolean hasTrapez) {
        this.hasTrapez = hasTrapez;
    }

    // _________________________________________________________________

    public int getId() {
        return id;
    }

    // _________________________________________________________________

    public void setId(int id) {
        this.id = id;
    }

    // _________________________________________________________________

    public User getUser() {
        return user;
    }

    // _________________________________________________________________

    public void setUser(User user) {
        this.user = user;
    }

    // _________________________________________________________________

    public CarportCategory getCategory() {
        return category;
    }

    // _________________________________________________________________

    public void setCategory(CarportCategory category) {
        this.category = category;
    }

    // _________________________________________________________________

    public double getWidth() {
        return width;
    }

    // _________________________________________________________________

    public void setWidth(double width) {
        this.width = width;
    }

    // _________________________________________________________________

    public double getLength() {
        return length;
    }

    // _________________________________________________________________

    public void setLength(double length) {
        this.length = length;
    }

    // _________________________________________________________________

    public double getHeight() {
        return height;
    }

    // _________________________________________________________________

    public void setHeight(double height) {
        this.height = height;
    }

    // _________________________________________________________________

    public Double getAngle() {
        return angle;
    }

    // _________________________________________________________________

    public void setAngle(Double angle) {
        this.angle = angle;
    }

    // _________________________________________________________________

    public String getRoof() {
        return roof;
    }

    // _________________________________________________________________

    public void setRoof(String roof) {
        this.roof = roof;
    }

    // _________________________________________________________________

    public boolean isHasToolShed() {
        return hasToolShed;
    }

    // _________________________________________________________________

    public void setHasToolShed(boolean hasToolShed) {
        this.hasToolShed = hasToolShed;
    }

    // _________________________________________________________________

    public Double getToolShedWidth() {
        return toolShedWidth;
    }

    // _________________________________________________________________

    public void setToolShedWidth(Double toolShedWidth) {
        this.toolShedWidth = toolShedWidth;
    }

    // _________________________________________________________________

    public Double getToolShedLength() {
        return toolShedLength;
    }

    // _________________________________________________________________

    public void setToolShedLength(Double toolShedLength) {
        this.toolShedLength = toolShedLength;
    }

    // _________________________________________________________________

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // _________________________________________________________________

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

} // CarportOrder end