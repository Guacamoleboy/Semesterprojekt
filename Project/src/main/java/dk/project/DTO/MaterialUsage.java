// Package
package dk.project.DTO;

// Import
import dk.project.entity.AdminMenu.Material;

public class MaterialUsage {

    // Attributes
    private final Material material;
    private final int amount;

    // _______________________________________________

    public MaterialUsage(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    // _______________________________________________

    public Material getMaterial() {
        return material;
    }

    // _______________________________________________

    public int getAmount() {
        return amount;
    }

    // _______________________________________________

    public double getTotalPrice() {
        return Math.round(amount * material.getPrice());
    }

}