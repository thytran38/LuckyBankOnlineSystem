package com.example.myfirstapp.luckybankonlinesystem.Model;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AccountModel {
    private String accountNumber;
    private final long dateCreated;
    private String accountOwner;
    private String accountType;

    public AccountModel() {
        dateCreated = System.currentTimeMillis() / 1000;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDateCreated() {
        Date time = new Date(dateCreated * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        return simpleDateFormat.format(time);
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
