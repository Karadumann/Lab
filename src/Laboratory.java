package com.lab.model;

import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Laboratory class manages the inventory of laboratory equipment and supplies.
 * Provides methods for inventory management, price calculations, and equipment tracking.
 */
@Entity
@Table(name = "laboratories")
public class Laboratory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "laboratory", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Item> inventory = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "usage_history", joinColumns = @JoinColumn(name = "laboratory_id"))
    @MapKeyColumn(name = "item_name")
    @Column(name = "usage_count")
    private final Map<String, Integer> usageHistory = new HashMap<>();

    /**
     * Default constructor for JPA
     */
    protected Laboratory() {
    }

    /**
     * Constructor initializes the laboratory with default inventory items.
     * @throws RuntimeException if there's an error initializing inventory
     */
    public Laboratory() {
        initializeInventory();
    }

    /**
     * Initialize the laboratory inventory with default items
     * @throws RuntimeException if there's an error adding items to inventory
     */
    private void initializeInventory() {
        try {
            // Microscopes
            inventory.add(new Microscope(32, 1, Microscope.Type.Electronic, "Zeiss"));
            inventory.add(new Microscope(15, 33, Microscope.Type.Optical, "Nikon"));
            inventory.add(new Microscope(22, 15, Microscope.Type.Optical, "Olympus"));
            inventory.add(new Microscope(18, 10, Microscope.Type.Electronic, "Oly"));

            // Test Tubes
            inventory.add(new TestTube(6, 30, 50, 3));
            inventory.add(new TestTube(3, 23, 25, 10));
            inventory.add(new TestTube(5, 27, 17, 7));

            // Volume Flasks
            inventory.add(new VolumeFlask(330, 15, 40));
            inventory.add(new VolumeFlask(220, 11, 30));
            inventory.add(new VolumeFlask(200, 9, 35));

            // Litmus Papers
            inventory.add(new LitmusPaper(50, 47, 10));
            inventory.add(new LitmusPaper(40, 30, 7));
            inventory.add(new LitmusPaper(33, 25, 5));

            // Litmus Solutions
            inventory.add(new LitmusSolution(400, 12, 3));
            inventory.add(new LitmusSolution(300, 10, 2));
            inventory.add(new LitmusSolution(600, 5, 1));
            inventory.add(new LitmusSolution(700, 8, 8));
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize laboratory inventory", e);
        }
    }

    /**
     * Get the unique identifier of the laboratory
     * @return laboratory id
     */
    public Long getId() {
        return id;
    }

    /**
     * Calculate total price of all available equipment
     * @return total price
     */
    public double calculateTotalPriceOfAvailableEquipment() {
        return inventory.stream()
                .mapToDouble(Item::getUnitPrice)
                .sum();
    }

    /**
     * Get items ordered by price
     * @return sorted list of items
     */
    public List<Item> getItemsOrderedByPrice() {
        return inventory.stream()
                .sorted(Comparator.comparingDouble(Item::getUnitPrice))
                .collect(Collectors.toList());
    }

    /**
     * Get items that need supply (low stock)
     * @return list of items needing supply
     */
    public List<Item> getItemsThatNeedSupply() {
        return inventory.stream()
                .filter(Item::isSupplyNeeded)
                .collect(Collectors.toList());
    }

    /**
     * Calculate average price of all items
     * @return average price
     */
    public double calculateAveragePrice() {
        return inventory.stream()
                .mapToDouble(Item::getUnitPrice)
                .average()
                .orElse(0.0);
    }

    /**
     * Get the most expensive item in inventory
     * @return most expensive item
     * @throws NoSuchElementException if inventory is empty
     */
    public Item getMostExpensiveItem() {
        return inventory.stream()
                .max(Comparator.comparingDouble(Item::getUnitPrice))
                .orElseThrow(() -> new NoSuchElementException("Inventory is empty"));
    }

    /**
     * Add a new item to inventory
     * @param item item to add
     * @throws IllegalArgumentException if item is null
     */
    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        item.setLaboratory(this);
        inventory.add(item);
    }

    /**
     * Remove an item from inventory
     * @param item item to remove
     * @return true if item was removed, false otherwise
     */
    public boolean removeItem(Item item) {
        if (inventory.remove(item)) {
            item.setLaboratory(null);
            return true;
        }
        return false;
    }

    /**
     * Record usage of an item
     * @param itemName name of the item
     * @param quantity quantity used
     * @throws IllegalArgumentException if itemName is null or quantity is negative
     */
    public void recordUsage(String itemName, int quantity) {
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        usageHistory.merge(itemName, quantity, Integer::sum);
    }

    /**
     * Get usage history for an item
     * @param itemName name of the item
     * @return usage count
     */
    public int getUsageCount(String itemName) {
        return usageHistory.getOrDefault(itemName, 0);
    }

    /**
     * Get total number of items in inventory
     * @return total count
     */
    public int getTotalItemCount() {
        return inventory.size();
    }

    /**
     * Get inventory value
     * @return total value of all items
     */
    public double getInventoryValue() {
        return inventory.stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getAvailableQuantity())
                .sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Laboratory Inventory:\n");
        sb.append("Total Items: ").append(getTotalItemCount()).append("\n");
        sb.append("Total Value: ").append(String.format("%.2f", getInventoryValue())).append("\n");
        sb.append("Items:\n");
        inventory.forEach(item -> sb.append("- ").append(item.toString()).append("\n"));
        return sb.toString();
    }
}

