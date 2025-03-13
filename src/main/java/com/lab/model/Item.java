package com.lab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type")
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Unit price cannot be null")
    @Min(value = 0, message = "Unit price must be non-negative")
    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Min(value = 0, message = "Available quantity must be non-negative")
    @Column(nullable = false)
    private int availableQuantity;

    @Column(name = "item_type", insertable = false, updatable = false)
    private String itemType;

    // Default constructor for JPA
    protected Item() {
    }

    // Constructor for initializing an item
    protected Item(String name, BigDecimal unitPrice, int availableQuantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.availableQuantity = availableQuantity;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public String getItemType() {
        return itemType;
    }

    // Method to update available quantity
    public void updateAvailableQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.availableQuantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%s - Price: $%s, Available: %d", name, unitPrice, availableQuantity);
    }
} 