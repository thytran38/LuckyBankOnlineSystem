package com.example.myfirstapp.luckybankonlinesystem.Model;
//import com.example.myfirstapp.luckybankonlinesystem.Class.DateTime;

import com.google.type.DateTime;

import java.util.HashSet;

public class Accounts {
    private String AccountNumber;
    private DateTime DateCreated;
    HashSet<Accounts> accountList;
    private int Account_Type;
    private String Account_Owner;

    public Accounts()
    {

    }

//    public HashSet<Accounts> getAccountList(){
//
//        HashSet<Accounts> thisList ;
//        return thisList;
//    }

    public String getAccountNumber(){

        return this.AccountNumber;
    }

    public DateTime getDateCreated()
    {
        return this.DateCreated;
    }

    public int getAccountType()
    {
        return this.Account_Type;
    }

    public String getAccountOwner()
    {
        return this.Account_Owner;
    }

}
