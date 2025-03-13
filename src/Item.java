package com.lab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Abstract base class for all laboratory items.
 * Implements Supplier interface for inventory management.
 */
@Entity
@Table(name = "items")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "item_type")
public abstract class Item implements Supplier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Column(nullable = false)
    protected String name;

    @NotNull
    @Min(value = 0, message = "Unit price cannot be negative")
    @Column(name = "unit_price", nullable = false)
    protected double unitPrice;

    @NotNull
    @Min(value = 0, message = "Available quantity cannot be negative")
    @Column(name = "available_quantity", nullable = false)
    protected int availableQuantity;

    @Column(name = "item_type", insertable = false, updatable = false)
    private String itemType;

    /**
     * Default constructor for JPA
     */
    protected Item() {
    }

    /**
     * Constructor for Item class
     * @param name Name of the item
     * @param unitPrice Price per unit
     * @param availableQuantity Available quantity in stock
     * @throws IllegalArgumentException if unitPrice is negative or availableQuantity is less than 0
     */
    public Item(String name, double unitPrice, int availableQuantity) {
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
        if (availableQuantity < 0) {
            throw new IllegalArgumentException("Available quantity cannot be negative");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        
        this.name = name;
        this.unitPrice = unitPrice;
        this.availableQuantity = availableQuantity;
    }

    /**
     * Get the unique identifier of the item
     * @return item id
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the unit price of the item
     * @return unit price
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Get the available quantity of the item
     * @return available quantity
     */
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    /**
     * Get the name of the item
     * @return item name
     */
    public String getName() {
        return name;
    }

    /**
     * Update the available quantity
     * @param quantity new quantity
     * @throws IllegalArgumentException if quantity is negative
     */
    public void setAvailableQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.availableQuantity = quantity;
    }

    @Override
    public boolean isSupplyNeeded() {
        return availableQuantity < 10; // Threshold for low stock
    }

    @Override
    public String toString() {
        return String.format("Item: %s%nPrice: %.2f%nAvailable Quantity: %d%nSupply Needed: %b",
                name, unitPrice, availableQuantity, isSupplyNeeded());
    }
}
