package com.example.myfirstapp.luckybankonlinesystem.Model;

import android.view.animation.TranslateAnimation;

import com.example.myfirstapp.luckybankonlinesystem.Class.DateTime;

public class TransactionModel {
    private String transactionID;
    private DateTime transactionDateCreated;
    private String senderId;
    private String recipientId;
    private Double transactionAmount;
    private String transactionType;

    public TransactionModel() { }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public DateTime getTransactionDateCreated() {
        return transactionDateCreated;
    }

    public void setTransactionDateCreated(DateTime transactionDateCreated) {
        this.transactionDateCreated = transactionDateCreated;
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
