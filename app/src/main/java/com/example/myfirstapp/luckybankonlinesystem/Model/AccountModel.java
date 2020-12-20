package com.example.myfirstapp.luckybankonlinesystem.Model;

public class AccountModel {

    public enum AccountType {
        Primary, Saving;
    }

    private String accountNumber;
    private long dateCreated;
    private String accountOwner;
    private AccountType accountType;
    private double currentBalance;

    public AccountModel() {
        dateCreated = System.currentTimeMillis() / 1000;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }
}
