package com.example.myfirstapp.luckybankonlinesystem.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TransactionModel implements Parcelable {
    private String transactionID;
    private long timestamp;
    private String senderUID;
    private String receiverUID;
    private String message;
    private double amount;

    public TransactionModel() {
        timestamp = System.currentTimeMillis();
    }

    protected TransactionModel(Parcel in) {
        transactionID = in.readString();
        timestamp = in.readLong();
        senderUID = in.readString();
        receiverUID = in.readString();
        amount = in.readDouble();
    }

    public static final Creator<TransactionModel> CREATOR = new Creator<TransactionModel>() {
        @Override
        public TransactionModel createFromParcel(Parcel in) {
            return new TransactionModel(in);
        }

        @Override
        public TransactionModel[] newArray(int size) {
            return new TransactionModel[size];
        }
    };

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTransactionDateCreated() {
        Date time = new Date(timestamp * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        return simpleDateFormat.format(time);
    }

    public String getSenderUID() {
        return senderUID;
    }

    public void setSenderUID(String senderUID) {
        this.senderUID = senderUID;
    }

    public String getReceiverUID() {
        return receiverUID;
    }

    public void setReceiverUID(String receiverUID) {
        this.receiverUID = receiverUID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String newMess){
        this.message = newMess;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(transactionID);
        dest.writeLong(timestamp);
        dest.writeString(senderUID);
        dest.writeString(receiverUID);
        dest.writeDouble(amount);
    }
}
