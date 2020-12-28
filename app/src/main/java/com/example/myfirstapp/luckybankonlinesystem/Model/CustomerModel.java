package com.example.myfirstapp.luckybankonlinesystem.Model;

import com.example.myfirstapp.luckybankonlinesystem.Class.Date;
import com.google.firebase.firestore.Exclude;

import java.text.ParseException;
import java.util.ArrayList;

public class CustomerModel {

    @Exclude
    public static final int PASSWORD_MIN_LENGTH = 8;

    public enum CustomerGender {
        Male, Female;
    }

    private String customerId;
    private String fullName, phoneNumber, email, address;
    private ArrayList<AccountModel> accounts;

    private CustomerGender gender;

    private long birthDate;

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Exclude
    public String getCustomerId() {
        return customerId;
    }

    public String getBirthDate() {
        return Date.getInstance(birthDate).toString();
    }

    public void setBirthDate(String date) throws ParseException {
        birthDate = Date.getInstance(date, false).getEpochSecond();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(CustomerGender gender) {
        this.gender = gender;
    }

    public CustomerGender getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAccounts(ArrayList<AccountModel> accounts) {
        this.accounts = accounts;
    }

    public ArrayList<AccountModel> getAccounts() {
        return accounts;
    }
}
