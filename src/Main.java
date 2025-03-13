import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

/**
 * Main class for the Laboratory Management System.
 * Provides an interactive menu interface for managing laboratory inventory.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Laboratory laboratory;
    private static FileWriter writer;

    public static void main(String[] args) {
        try {
            laboratory = new Laboratory();
            writer = new FileWriter("Results");
            
            while (true) {
                displayMenu();
                int choice = getIntInput("Enter your choice (1-8): ");
                
                if (choice == 8) {
                    break;
                }
                
                processChoice(choice);
            }
            
            System.out.println("Thank you for using the Laboratory Management System!");
            writer.close();
            
        } catch (IOException e) {
            System.err.println("Error: Failed to write to Results file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Laboratory Management System ===");
        System.out.println("1. View Total Inventory Value");
        System.out.println("2. View Items Needing Supply");
        System.out.println("3. View Average Price of Materials");
        System.out.println("4. View Items Ordered by Price");
        System.out.println("5. View Most Expensive Item");
        System.out.println("6. Record Item Usage");
        System.out.println("7. View Usage History");
        System.out.println("8. Exit");
    }

    private static void processChoice(int choice) throws IOException {
        switch (choice) {
            case 1:
                displayTotalValue();
                break;
            case 2:
                displayItemsNeedingSupply();
                break;
            case 3:
                displayAveragePrice();
                break;
            case 4:
                displayItemsOrderedByPrice();
                break;
            case 5:
                displayMostExpensiveItem();
                break;
            case 6:
                recordItemUsage();
                break;
            case 7:
                displayUsageHistory();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void displayTotalValue() throws IOException {
        double totalValue = laboratory.getInventoryValue();
        String message = String.format("Total Inventory Value: %.2f", totalValue);
        System.out.println(message);
        writer.write(message + "\n\n");
    }

    private static void displayItemsNeedingSupply() throws IOException {
        List<Item> items = laboratory.getItemsThatNeedSupply();
        System.out.println("\nItems Needing Supply:");
        writer.write("Items Needing Supply:\n");
        items.forEach(item -> {
            String message = "- " + item.getName() + " (Quantity: " + item.getAvailableQuantity() + ")";
            System.out.println(message);
            try {
                writer.write(message + "\n");
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        });
        writer.write("\n");
    }

    private static void displayAveragePrice() throws IOException {
        double avgPrice = laboratory.calculateAveragePrice();
        String message = String.format("Average Price of Materials: %.2f", avgPrice);
        System.out.println(message);
        writer.write(message + "\n\n");
    }

    private static void displayItemsOrderedByPrice() throws IOException {
        List<Item> items = laboratory.getItemsOrderedByPrice();
        System.out.println("\nItems Ordered by Price:");
        writer.write("Items Ordered by Price:\n");
        items.forEach(item -> {
            String message = String.format("- %s: %.2f", item.getName(), item.getUnitPrice());
            System.out.println(message);
            try {
                writer.write(message + "\n");
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        });
        writer.write("\n");
    }

    private static void displayMostExpensiveItem() throws IOException {
        Item item = laboratory.getMostExpensiveItem();
        String message = String.format("Most Expensive Item: %s (Price: %.2f)", 
            item.getName(), item.getUnitPrice());
        System.out.println(message);
        writer.write(message + "\n\n");
    }

    private static void recordItemUsage() throws IOException {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        
        int quantity = getIntInput("Enter quantity used: ");
        
        try {
            laboratory.recordUsage(itemName, quantity);
            String message = String.format("Recorded usage of %d units for %s", quantity, itemName);
            System.out.println(message);
            writer.write(message + "\n\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayUsageHistory() throws IOException {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        
        int usageCount = laboratory.getUsageCount(itemName);
        String message = String.format("Usage count for %s: %d", itemName, usageCount);
        System.out.println(message);
        writer.write(message + "\n\n");
    }

    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
