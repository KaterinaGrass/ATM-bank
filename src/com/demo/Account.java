package com.demo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Account {

    private String username;
    private String uuid;
    private User holder;
    private List<Transaction> transactions;

    public Account (String username, User holder, Bank bank){
        this.username = username;
        this.holder = holder;
        this.uuid = bank.getNewAccountUUID();
        this.transactions = new ArrayList<>();
    }

    public String getUUID(){
        return this.uuid;
    }

    public String getSummaryLine(){
        //get the balance
        double balance = this.getBalance();
        if (balance >= 0){
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.username);
        }else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.username);
        }
    }

    public double getBalance(){
        double balance = 0;
        for (Transaction transaction : this.transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public void addTransaction(double amount, String memo){
        Transaction newTransaction = new Transaction(amount, memo, this);
        this.transactions.add(newTransaction);
    }


}


