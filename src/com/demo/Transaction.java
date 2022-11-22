package com.demo;

import lombok.Data;

import java.util.Date;
@Data
public class Transaction {

    private double amount;
    private Date timestamp;
    private String memo;
    private Account inAccount;

    public Transaction(double amount, Account account){
        this.amount = amount;
        this.inAccount = account;
        this.timestamp = new Date();
        this.memo = "";

    }

    public Transaction(double amount, String memo, Account account){
        this(amount, account);
        this.memo = memo;
    }



}
