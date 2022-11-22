package com.demo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class Bank {

    private String title;
    private List<User> users;
    private List<Account> accounts;

    public Bank(String title){
        this.title = title;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<>();
    }

    public String getNewUserUUID(){
        String uuid;
        Random random = new Random();
        int len = 4;
        boolean nonUnique;
        do {
            uuid = "";
            for (int i = 0; i < len; i++){
                uuid += ((Integer)random.nextInt(10)).toString();
            }
            nonUnique = false;
            for (User user : this.users){
                if (uuid.compareTo(user.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        }while (nonUnique);

            return uuid;
    }

    public String getNewAccountUUID(){
        String uuid;
        Random random = new Random();
        int len = 12;
        boolean nonUnique;
        do {
            uuid = "";
            for (int i = 0; i < len; i++){
                uuid += ((Integer)random.nextInt(10)).toString();
            }
            nonUnique = false;
            for (Account account : this.accounts){
                if (uuid.compareTo(account.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        }while (nonUnique);

        return uuid;
    }

    public User addNewUser (String firstName, String lastName, String cardNumber, String pin){
        User newUser = new User(firstName, lastName, cardNumber, pin, this);
        this.users.add(newUser);
        Account newAccount = new Account("Saving", newUser, this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;
    }

    public User userLogin (String userId, String cardNumber, String pin){
        for (User user : this.users){
            if (user.getUUID().compareTo(userId) != 0 || !user.validateCardNumber(cardNumber) ||!user.validatePin(pin)) {
                return user;
            }

        }
        return null;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }
}
