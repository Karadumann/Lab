package com.lab;

import com.lab.model.Laboratory;
import com.lab.util.DatabaseManager;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Initialize laboratory from database or create new one
            List<Laboratory> laboratories = DatabaseManager.findAll(Laboratory.class);
            Laboratory laboratory = laboratories.isEmpty() ? new Laboratory() : laboratories.get(0);

            // Create FileWriter for logging results
            try (FileWriter writer = new FileWriter("Results")) {
                while (true) {
                    displayMenu();
                    int choice = getIntInput("Enter your choice: ");

                    switch (choice) {
                        case 1:
                            displayTotalInventoryValue(laboratory, writer);
                            break;
                        case 2:
                            displayItemsNeedingSupply(laboratory, writer);
                            break;
                        case 3:
                            displayAveragePrice(laboratory, writer);
                            break;
                        case 4:
                            displayUsageHistory(laboratory, writer);
                            break;
                        case 5:
                            recordItemUsage(laboratory, writer);
                            break;
                        case 6:
                            System.out.println("Goodbye!");
                            DatabaseManager.close();
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseManager.close();
        }
    }

    private static void displayMenu() {
        System.out.println("\nLaboratory Management System");
        System.out.println("1. Display Total Inventory Value");
        System.out.println("2. Display Items Needing Supply");
        System.out.println("3. Display Average Price");
        System.out.println("4. Display Usage History");
        System.out.println("5. Record Item Usage");
        System.out.println("6. Exit");
    }

    private static void displayTotalInventoryValue(Laboratory laboratory, FileWriter writer) throws IOException {
        BigDecimal totalValue = laboratory.calculateTotalInventoryValue();
        String result = String.format("Total Inventory Value: $%s", totalValue);
        System.out.println(result);
        writer.write(result + "\n");
        DatabaseManager.saveOrUpdate(laboratory);
    }

    private static void displayItemsNeedingSupply(Laboratory laboratory, FileWriter writer) throws IOException {
        int threshold = getIntInput("Enter supply threshold: ");
        List<Item> itemsNeedingSupply = laboratory.findItemsNeedingSupply(threshold);
        String result = "Items needing supply:";
        System.out.println(result);
        writer.write(result + "\n");
        for (Item item : itemsNeedingSupply) {
            result = String.format("- %s", item);
            System.out.println(result);
            writer.write(result + "\n");
        }
        DatabaseManager.saveOrUpdate(laboratory);
    }

    private static void displayAveragePrice(Laboratory laboratory, FileWriter writer) throws IOException {
        BigDecimal averagePrice = laboratory.calculateAveragePrice();
        String result = String.format("Average Price: $%s", averagePrice);
        System.out.println(result);
        writer.write(result + "\n");
        DatabaseManager.saveOrUpdate(laboratory);
    }

    private static void displayUsageHistory(Laboratory laboratory, FileWriter writer) throws IOException {
        List<String> history = laboratory.getUsageHistory();
        String result = "Usage History:";
        System.out.println(result);
        writer.write(result + "\n");
        for (String usage : history) {
            result = String.format("- %s", usage);
            System.out.println(result);
            writer.write(result + "\n");
        }
        DatabaseManager.saveOrUpdate(laboratory);
    }

    private static void recordItemUsage(Laboratory laboratory, FileWriter writer) throws IOException {
        System.out.print("Enter usage description: ");
        String usage = scanner.nextLine();
        laboratory.recordItemUsage(usage);
        String result = String.format("Recorded usage: %s", usage);
        System.out.println(result);
        writer.write(result + "\n");
        DatabaseManager.saveOrUpdate(laboratory);
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
} 