package com.demo;

import lombok.Data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


@Data
public class User {

    private String firstName;
    private String lastName;
    private String cardPin;
    private String cardNumber;
    //ID user
    private String uuid;
    //Stored Pin code, in a byte Array called pinHash (empty). Will be stored hashed in MD5.
    private byte pinHash[];
    private List<Account> accounts;

    public User(String firstName, String lastName, String cardNumber, String cardPin, Bank bank) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.cardNumber = cardNumber;
        this.cardPin = cardPin;
        MessageDigest messageDigest;
        {
            try {
                messageDigest = MessageDigest.getInstance("MD5");
                this.pinHash = messageDigest.digest(cardPin.getBytes());
            } catch (NoSuchAlgorithmException e) {
                System.err.println("NoSuchAlgorithmException");
                e.printStackTrace();
                System.exit(1);
            }
            this.uuid = bank.getNewUserUUID();
            this.accounts = new ArrayList<Account>();
            System.out.printf("New user %s, %s with ID %s created", firstName, lastName, this.uuid);

        }
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public String getUUID() {
        return this.uuid;
    }

    public boolean validatePin(String pin) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(messageDigest.digest(pin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public boolean validateCardNumber(String cardNumber){
        String cardNumberPattern = "([2-6]([0-9]{3}) ?)(([0-9]{4} ?){3})";
        if (cardNumberPattern.matches(cardNumberPattern)){
            System.out.println("Card number is correct");
        } else {
            System.out.println("Invalid card number");
        }
        return false;
    }

    public void printAccountSummary(){
        System.out.printf("\n\n%s accounts summary", this.firstName);
        for (int i = 0; i < this.accounts.size(); i++){
            System.out.printf("%d) %s\n", i+1,this.accounts.get(i).getSummaryLine());
        }
        System.out.println("");
    }

    public int numAccounts(){
        return  this.accounts.size();
    }

    public double getAccountBalance(int accountIndex){
        return this.accounts.get(accountIndex).getBalance();
    }

    public void addAccountTransaction(int accountIndex, double amount, String memo){
        this.accounts.get(accountIndex).addTransaction(amount, memo);
    }


}


