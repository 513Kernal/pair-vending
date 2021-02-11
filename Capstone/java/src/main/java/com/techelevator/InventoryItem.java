package com.techelevator;

import java.math.BigDecimal;

public class InventoryItem {

    private String itemID;
    private String itemName;
    private BigDecimal purchasePrice;
    private String itemType;
    private int inventoryRemaining;
    private static final int STARTING_INVENTORY = 5;

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public String getItemType() {
        return itemType;
    }

    public int getInventoryRemaining() {
        return inventoryRemaining;
    }

    public void setInventoryRemaining(int inventoryRemaining) {
        this.inventoryRemaining = inventoryRemaining;
    }

    public InventoryItem(String itemID, String itemName, BigDecimal purchasePrice, String itemType) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.purchasePrice = purchasePrice;
        this.itemType = itemType;
        this.inventoryRemaining = STARTING_INVENTORY;
    }

    public String getTypeMessage() {
        String sound = "";
        if (itemType.equals("Chip")) {
            sound = "\nCrunch Crunch, Yum!";
        } else if (itemType.equals("Candy")) {
            sound = "\nMunch Munch, Yum!";
        } else if (itemType.equals("Drink")) {
            sound = "\nGlug Glug, Yum!";
        } else if (itemType.equals("Gum")) {
            sound = "\nChew Chew, Yum!";
        }
        return sound;
    }
}