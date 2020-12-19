package com.example.myfirstapp.luckybankonlinesystem.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TransactionModel {
    private String transactionID;
    private long transactionDateCreated;
    private String senderId;
    private String recipientId;
    private Double transactionAmount;
    private String transactionType;

    public TransactionModel() {
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTransactionDateCreated() {
        Date time = new Date(transactionDateCreated * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        return simpleDateFormat.format(time);
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
