package com.example.myfirstapp.luckybankonlinesystem.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class AccountModel implements Parcelable {

    protected AccountModel(Parcel in) {
        accountNumber = in.readString();
        dateCreated = in.readLong();
        accountOwner = in.readString();
        currentBalance = in.readDouble();
        accountType = AccountType.valueOf(in.readString());
    }

    public static final Creator<AccountModel> CREATOR = new Creator<AccountModel>() {
        @Override
        public AccountModel createFromParcel(Parcel in) {
            return new AccountModel(in);
        }

        @Override
        public AccountModel[] newArray(int size) {
            return new AccountModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accountNumber);
        dest.writeLong(dateCreated);
        dest.writeString(accountOwner);
        dest.writeDouble(currentBalance);
        dest.writeString(accountType.name());
    }

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
