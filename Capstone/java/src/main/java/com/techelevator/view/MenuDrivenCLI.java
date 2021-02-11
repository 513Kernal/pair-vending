package com.techelevator.view;

import java.math.BigDecimal;
import java.util.Scanner;

public class MenuDrivenCLI {

    private final Scanner userInput = new Scanner(System.in);
    private final Menu menu = new Menu(System.in, System.out);

    public void output(String content) {
        System.out.println(); //Print blank line
        System.out.println(content);
    }

    public void pauseOutput() {
        System.out.println("(Press enter to continue)");
        userInput.nextLine();
    }

    public String promptForSelection(String[] options) {
        return (String) menu.getChoiceFromOptions(options);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return userInput.nextLine();
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        BigDecimal result = null;
        while (result == null) {
            try {
                System.out.print(prompt);
                String input = userInput.nextLine();
                int deposit = Integer.parseInt(input);
                result = new BigDecimal(deposit);
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid whole number dollar value! NO DOGE COINS!");
            }
        }
        return result;
    }
}
