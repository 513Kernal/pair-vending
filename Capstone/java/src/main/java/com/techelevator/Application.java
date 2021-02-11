package com.techelevator;
import com.techelevator.view.MenuDrivenCLI;

public class Application {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "View Khajiit's wares.";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Buy something so that Elon leaves me alone for a while.";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit the application so that I can take a nap.";
	private static final String MAIN_MENU_OPTION_SALES_REPORT = "Check out the data that your personal FBI agent collected on you during this transaction.";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALES_REPORT};

	private final MenuDrivenCLI ui = new MenuDrivenCLI();
	private VendingMachine vm = new VendingMachine();

	public static void main(String[] args) {
		Application application = new Application();
		application.run();
	}

	public void run() {
		vm.importInventory();
		System.out.println();
		System.out.println("Welcome to Elon Musk's vending machine. It doesn't make sense, but he sold a flamethrower " +
				"for $500 so what do you expect? Pick a number and hit enter, let's see what happens.");
		while (true) {
			String selection = ui.promptForSelection(MAIN_MENU_OPTIONS);
			System.out.println();
			if (selection.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				vm.listItems();
				System.out.println("\nThese are the items are currently available, to make a purchase please choose option 2 for the purchase menu.");
			} else if (selection.equals(MAIN_MENU_OPTION_PURCHASE)) {
				PurchaseItemsMenu subMenu = new PurchaseItemsMenu();
				subMenu.display();
			} else if (selection.equals(MAIN_MENU_OPTION_EXIT)) {
				System.out.println("Heimdall is closing the Bifrost, back to Asgard with you!");
				System.exit(0);
			} else if (selection.equals(MAIN_MENU_OPTION_SALES_REPORT)) {
				System.out.println(vm.createSalesReport());
			}
		}
	}
}
