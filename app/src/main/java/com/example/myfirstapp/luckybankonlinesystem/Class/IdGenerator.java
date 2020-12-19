package com.example.myfirstapp.luckybankonlinesystem.Class;


public class IdGenerator {
    public String thisId;

    public IdGenerator()
    {
        this.thisId = "Initialized ID: 00041415555";
    }

    public String getThisId(String str1, String str2, String str3)
    {
        thisId= str1+ str2+ str3;
        return thisId;

    }

    public String idEncrypt(){
        return "";
    }

    public String idDecrypt(){
        return "";
    }


}
