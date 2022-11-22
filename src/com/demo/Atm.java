package com.demo;



import java.util.Scanner;

public class Atm {

    public static void main(String[] args) {



        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank("SBERBank");
        User user = bank.addNewUser("Grasevish", "Katerina", "2111-1111-1111-1111", "1111");
        Account newAccount = new Account("Checking", user, bank);
        user.addAccount(newAccount);
        bank.addAccount(newAccount);

        User curUser;
        while (true) {
            curUser = Atm.mainMenuPrompt(bank, scanner);
            Atm.printUserMenu(curUser, scanner);
        }
    }

    public static User mainMenuPrompt(Bank bank, Scanner scanner) {
        String userId;
        String cardPin;
        User authUser;
        String cardNumberPattern;
        int count = 0;
        do {
            System.out.printf(" Welcome to %s", bank.getTitle());
            System.out.print(" Enter User Id: ");
            userId = scanner.nextLine();
            System.out.println("Enter cardNumber (XXXX-XXXX-XXXX-XXXX): ");
            cardNumberPattern = scanner.nextLine();
            System.out.println("Enter pin (XXXX): ");
            cardPin = scanner.nextLine();
            authUser = bank.userLogin(userId, cardNumberPattern, cardPin);
            if (authUser == null) {
                System.out.println("Incorrect login or pin! Please, try again!");
            }
        } while (authUser == null);
        return authUser;
    }

    public static void printUserMenu(User user, Scanner scanner) {
        user.printAccountSummary();
        int choice;
        //user menu
        do {
            System.out.printf("Welcome %s select operation", user.getFirstName());
            System.out.println(" 1) Check Balance");
            System.out.println(" 2) Withdraw");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) EXIT");
            System.out.println("_______________________");
            System.out.print("Enter choice");
            choice = scanner.nextInt();
            if (choice < 1 || choice > 4) {
                System.out.println("Invalid choice. Please choose 1-4");
            }

        } while (choice < 1 || choice > 4);
        //process the choice
        switch (choice) {
              case 1:
              Atm.showCardBalance(user, scanner);
             break;
            case 2:
                Atm.withdrawCash(user, scanner);
                break;
            case 3:
                Atm.depositBalance(user, scanner);
                break;
        }
        if (choice != 4) {
            Atm.printUserMenu(user, scanner);

        }
    }

    public static void showCardBalance(User user, Scanner scanner) {
        int account;
        double balance;
        do {
            System.out.printf("Enter the number (1-%d) of the account to withdraw from", user.numAccounts());
            account = scanner.nextInt() - 1;
            if (account < 0 || account >= user.numAccounts()) {
                System.out.println("Invalid account! Please, try again");

            }
        } while (account < 0 || account >= user.numAccounts());
        balance = user.getAccountBalance(account);

    }

    public static void withdrawCash(User user, Scanner scanner) {
        int fromAccount;
        double amount;
        double balance;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the account to withdraw from", user.numAccounts());
            fromAccount = scanner.nextInt() - 1;
            if (fromAccount < 0 || fromAccount >= user.numAccounts()) {
                System.out.println("Invalid account! Please, try again");

            }
        } while (fromAccount < 0 || fromAccount >= user.numAccounts());
        balance = user.getAccountBalance(fromAccount);

        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", balance);
            amount = scanner.nextDouble();
            if (amount < 0) {
                System.out.println("Amount mast be greater than 0");
            } else if (amount > balance) {
                System.out.printf("Amount mast be greater than 0 balance of $%.02f", balance);
            }
        } while (amount < 0 || amount > balance);
        scanner.nextLine();
        System.out.print("Enter a memo: ");
        memo = scanner.nextLine();
        user.addAccountTransaction(fromAccount, -1 * amount, memo);
    }


    public static void depositBalance(User user, Scanner scanner) {
        int toAccount;
        double amount;
        double balance;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the account to deposit in : ", user.numAccounts());
            toAccount = scanner.nextInt() - 1;
            if (toAccount < 0 || toAccount >= user.numAccounts()) {
                System.out.println("Invalid account! Please, try again");

            }
        } while (toAccount < 0 || toAccount >= user.numAccounts());
        balance = user.getAccountBalance(toAccount);

        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", balance);
            amount = scanner.nextDouble();
            if (amount < 0 || amount > 1000000) {
                System.out.println("Amount mast be greater than 0 or the deposit amount should not exceed 1,000,000");

            }
        } while (amount < 0) ;
            scanner.nextLine();
            System.out.print("Enter a memo: ");
            memo = scanner.nextLine();
            user.addAccountTransaction(toAccount, amount, memo);

    }



}



