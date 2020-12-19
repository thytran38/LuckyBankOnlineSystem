package com.example.myfirstapp.luckybankonlinesystem.Model;

public class Transactions {
    private String transactionID;
    private long transactionDateCreated;
    private String senderId;
    private String recipientId;
    private Double transactionAmount;


    public Transactions()
    {

    }

    public String getTransactionID(){
        return this.transactionID;
    }

    public long getTransactionTime(){
        return this.transactionDateCreated;
    }

    public String getSenderID()
    {
        return this.senderId;
    }
}
