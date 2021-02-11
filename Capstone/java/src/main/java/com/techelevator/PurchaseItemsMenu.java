package com.techelevator;
import com.techelevator.view.MenuDrivenCLI;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseItemsMenu {

    private static final String PURCHASE_MENU_OPTION_DEPOSIT_FUNDS = "Toss a coin to your witcher. Or just... deposit a few dollars so that you can buy a snack.";
    private static final String PURCHASE_MENU_SELECT_PRODUCT = "Spend an absurd amount of time choosing a snack or drink that you will finish in less than a minute.";
    private static final String PURCHASE_MENU_FINISH_TRANSACTION = "Choose this to make sure your credit card is no longer being charged, otherwise Fred from accounting will " +
            "rack up 50 bucks worth of junk food courtesy of bad code.";
    private static final String[] PURCHASE_MENU_MAIN = {PURCHASE_MENU_OPTION_DEPOSIT_FUNDS, PURCHASE_MENU_SELECT_PRODUCT, PURCHASE_MENU_FINISH_TRANSACTION};

    private final MenuDrivenCLI ui = new MenuDrivenCLI();
    private VendingMachine vm = new VendingMachine();

    public void display() {
        vm.importInventory();
        while (true) {
            String selection = ui.promptForSelection(PURCHASE_MENU_MAIN);
            if (selection.equals(PURCHASE_MENU_OPTION_DEPOSIT_FUNDS)) {
                BigDecimal depositAmount = ui.promptForBigDecimal("\nHow many Doge coi... Dollars... do you want to deposit? Whole numbers only. >>> ");
                if (depositAmount.intValue() > 0) {
                    vm.depositMoney(depositAmount);
                    //add if currentBalance
                    //call method to add item
                    //adds flamethrower to map
                    System.out.println("\nThank you! Your current balance is $" + vm.getCurrentBalance() + ".");
                    LocalDateTime timeStamp = LocalDateTime.now();
                    try (FileOutputStream stream = new FileOutputStream("audit.txt", true);
                         PrintWriter auditWriter = new PrintWriter(stream)){
                         auditWriter.println(timeStamp + " FEED MONEY " + depositAmount + " " + vm.getCurrentBalance());
                    } catch (FileNotFoundException e) {
                        System.out.println("\nCould not find the file!");
                    } catch (IOException ex) {
                        System.out.println("\nCould not append to the file!");
                    }
                } else if (depositAmount.intValue() == 0) {
                    System.out.println("\nOnly scrubs try to insert zero dollars. And no, I don't want no scrubs! Try again!");
                } else {
                    System.out.println("\nCan not deposit a negative value! Try again!");
                }
            } else if (selection.equals(PURCHASE_MENU_SELECT_PRODUCT)) {
                if (vm.getCurrentBalance().equals(BigDecimal.ZERO)) {
                    System.out.println("\nYou need to deposit money first! Go see the witcher!");
                } else {
                    System.out.println();
                    vm.listItems();
                    String chosenItem = ui.promptForString("\nPlease enter the alphanumeric 2 digit code of the item you would like to purchase. >>> ");
                    if (vm.getInventory().containsKey(chosenItem)) {
                        vm.buyItems(chosenItem);
                    } else {
                        System.out.println("\nInvalid Selection! Try again!");
                    }
                }
            } else if (selection.equals(PURCHASE_MENU_FINISH_TRANSACTION)) {
                String backToMainMenu = ui.promptForString("\nAre you finished making purchases? Please indicate yes or no. >>> ");
                if (backToMainMenu.equalsIgnoreCase("yes")) {
                    System.out.println(vm.makeChange());
                    Application mainMenu = new Application();
                    mainMenu.run();
                }
            }
        }
    }
}
