package com.techelevator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine {

    private Map<String, InventoryItem> machineInventory = new TreeMap<>();
    private BigDecimal currentBalance = BigDecimal.ZERO;
    private BigDecimal totalSales = new BigDecimal(0);

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }
    public Map getInventory() {
        return machineInventory;
    }

    public void importInventory() {
        Path myPath = Path.of("inventory.txt");
        try (Scanner fileScanner = new Scanner(myPath)) {
            while (fileScanner.hasNextLine()) {
                String inventoryFileLine = fileScanner.nextLine();
                String[] fileLineArray = inventoryFileLine.split("\\|");
                String itemID = fileLineArray[0];
                String itemName = fileLineArray[1];
                String itemPriceString = fileLineArray[2];
                BigDecimal itemPrice = new BigDecimal(itemPriceString);
                String itemType = fileLineArray[3];

                InventoryItem inventoryItem = new InventoryItem(itemID, itemName, itemPrice, itemType);
                machineInventory.put(itemID, inventoryItem);
            }
        } catch (IOException e) {
            System.out.println("Unable to read from source file!");
        }
    }

    public String makeChange() {
        int quarters;
        int dimes;
        int nickels;
        String result;
        BigDecimal quarter = new BigDecimal("0.25");
        BigDecimal dime = new BigDecimal("0.10");
        BigDecimal nickel = new BigDecimal("0.05");

        BigDecimal[] quarterCalculation = currentBalance.divideAndRemainder(quarter);
        quarters = quarterCalculation[0].intValue();
        BigDecimal[] dimeCalculation = quarterCalculation[1].divideAndRemainder(dime);
        dimes = dimeCalculation[0].intValue();
        BigDecimal[] nickelCalculation = dimeCalculation[1].divideAndRemainder(nickel);
        nickels = nickelCalculation[0].intValue();

        result = "\nYou received " + quarters + " quarter(s), " + dimes + " dime(s), and " + nickels + " nickel(s) in change.";
        LocalDateTime timeStamp = LocalDateTime.now();
        try (FileOutputStream stream = new FileOutputStream("audit.txt", true);
             PrintWriter auditWriter = new PrintWriter(stream)) {
            auditWriter.println(timeStamp + " GIVE CHANGE " + currentBalance + " " + BigDecimal.ZERO);
        } catch (FileNotFoundException e) {
            System.out.println("\nCould not find the file!");
        } catch (IOException ex) {
            System.out.println("\nCould not append to the file!");
        }
        currentBalance = BigDecimal.ZERO;
        return result;
    }

    public void listItems() {
        for (Map.Entry<String, InventoryItem> entry : machineInventory.entrySet()) {
            if (entry.getValue().getInventoryRemaining() > 0) {
                System.out.println(entry.getValue().getItemName() + " " + entry.getKey() + " " + entry.getValue().getPurchasePrice());
            } else {
                System.out.println("Sold Out " + entry.getValue().getItemName() + " " + entry.getKey() + " " + entry.getValue().getPurchasePrice());
            }
        }
    }

    public void depositMoney(BigDecimal amount) {
        currentBalance = currentBalance.add(amount);
    }

    public void buyItems(String itemID) {
            if (machineInventory.containsKey(itemID)) {
                InventoryItem itemToBeSold = machineInventory.get(itemID);
                String itemName = itemToBeSold.getItemName();
                BigDecimal purchasePrice = itemToBeSold.getPurchasePrice();
                String itemType = itemToBeSold.getItemType();
                int availableInventory = itemToBeSold.getInventoryRemaining();

                if (availableInventory == 0) {
                    System.out.println("\nThis item is sold out! Please try again!");
                } else if (currentBalance.compareTo(purchasePrice) == -1) {
                    System.out.println("\nYou do not have enough money available! Please deposit more to continue!");
                } else {
                    totalSales = totalSales.add(itemToBeSold.getPurchasePrice());
                    currentBalance = currentBalance.subtract(purchasePrice);
                    itemToBeSold.setInventoryRemaining(availableInventory - 1);
                    System.out.println(itemToBeSold.getTypeMessage());
                    System.out.println("\nYour remaining balance is $" + currentBalance + ".");
                    LocalDateTime timeStamp = LocalDateTime.now();
                    try (FileOutputStream stream = new FileOutputStream("audit.txt", true);
                         PrintWriter auditWriter = new PrintWriter(stream)){
                        auditWriter.println(timeStamp + " " + itemName + " " + itemID + " " + purchasePrice + " " + currentBalance);
                    } catch (FileNotFoundException e) {
                        System.out.println("\nCould not find the file!");
                    } catch (IOException ex) {
                        System.out.println("\nCould not append to the file!");
                    }
                }
            }
        }
    public String createSalesReport() {
        StringBuilder sales = new StringBuilder();
        for (Map.Entry<String, InventoryItem> item : machineInventory.entrySet()) {
            sales.append(item.getValue().getItemName()).append(" | ").append(item.getValue().getInventoryRemaining()).append("\n");
        }
        sales.append("\n**TOTAL SALES** $").append(totalSales);
        return sales.toString();
    }
}