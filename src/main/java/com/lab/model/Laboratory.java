package com.lab.model;

import jakarta.persistence.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

@Entity
@Table(name = "laboratories")
public class Laboratory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "laboratory_id")
    private List<Item> inventory;

    @ElementCollection
    @CollectionTable(name = "usage_history", joinColumns = @JoinColumn(name = "laboratory_id"))
    private List<String> usageHistory;

    // Default constructor for JPA
    public Laboratory() {
        this.inventory = new ArrayList<>();
        this.usageHistory = new ArrayList<>();
    }

    // Constructor for initializing a laboratory
    public Laboratory(List<Item> initialInventory) {
        this.inventory = new ArrayList<>(initialInventory);
        this.usageHistory = new ArrayList<>();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public List<Item> getInventory() {
        return Collections.unmodifiableList(inventory);
    }

    public List<String> getUsageHistory() {
        return Collections.unmodifiableList(usageHistory);
    }

    // Method to add an item to inventory
    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        inventory.add(item);
    }

    // Method to remove an item from inventory
    public void removeItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        inventory.remove(item);
    }

    // Method to record item usage
    public void recordItemUsage(String usage) {
        if (usage == null || usage.trim().isEmpty()) {
            throw new IllegalArgumentException("Usage record cannot be null or empty");
        }
        usageHistory.add(usage);
    }

    // Method to calculate total inventory value
    public BigDecimal calculateTotalInventoryValue() {
        return inventory.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getAvailableQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Method to find items that need supply (quantity below threshold)
    public List<Item> findItemsNeedingSupply(int threshold) {
        return inventory.stream()
                .filter(item -> item.getAvailableQuantity() < threshold)
                .collect(Collectors.toList());
    }

    // Method to calculate average price of items
    public BigDecimal calculateAveragePrice() {
        if (inventory.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return calculateTotalInventoryValue()
                .divide(BigDecimal.valueOf(inventory.size()), 2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return String.format("Laboratory[id=%d, inventorySize=%d, historySize=%d]",
                id, inventory.size(), usageHistory.size());
    }
} 