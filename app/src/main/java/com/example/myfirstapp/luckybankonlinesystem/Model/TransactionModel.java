package com.example.myfirstapp.luckybankonlinesystem.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class TransactionModel implements Parcelable {
    private String transactionID;
    private long timestamp;
    private String senderUID;
    private String receiverUID;
    private String message;
    private String senderName;
    private String receiverName;
    private double amount;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public TransactionModel() {
        timestamp = System.currentTimeMillis();
    }

    protected TransactionModel(Parcel in) {
        transactionID = in.readString();
        timestamp = in.readLong();
        senderUID = in.readString();
        receiverUID = in.readString();
        amount = in.readDouble();
        senderName = in.readString();
        receiverName = in.readString();
        message = in.readString();
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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
        dest.writeString(senderName);
        dest.writeString(receiverName);
    }
}
