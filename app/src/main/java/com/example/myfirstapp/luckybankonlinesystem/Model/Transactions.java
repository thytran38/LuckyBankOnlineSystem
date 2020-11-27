package com.example.myfirstapp.luckybankonlinesystem.Model;

import com.example.myfirstapp.luckybankonlinesystem.Class.DateTime;

public class Transactions {
    private String transactionID;
    private DateTime transactionDateCreated;
    private String senderId;
    private String recipientId;
    private Double transactionAmount;


    public Transactions()
    {

    }

    public String getTransactionID(){
        return this.transactionID;
    }

    public DateTime getTransactionTime(){
        return this.transactionDateCreated;
    }

    public String getSenderID()
    {
        return this.senderId;
    }
}
