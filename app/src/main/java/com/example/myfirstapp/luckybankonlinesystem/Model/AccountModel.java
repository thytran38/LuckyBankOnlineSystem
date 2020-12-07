package com.example.myfirstapp.luckybankonlinesystem.Model;

import com.example.myfirstapp.luckybankonlinesystem.Class.DateTime;

public class AccountModel {
    private String accountNumber;
    private DateTime dateCreated;
    private String accountOwner;
    private String accountType;

    public AccountModel() { }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public DateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
