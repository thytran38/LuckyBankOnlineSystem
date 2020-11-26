package com.example.myfirstapp.luckybankonlinesystem.Model;

import com.example.myfirstapp.luckybankonlinesystem.Class.DateTime;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserModel {
    private String FullName;
    private String UserId;
    private DateTime DateOfBirth;
    private String EmailAddress;
    private String PhoneNumber;
    private Boolean Gender;
    private String UserAddress;

    private DateTime LastLogin;
    private String userToken;
    private FirebaseFirestore db;

    public UserModel(){

    }

    public String getUserFullName()
    {
        return this.FullName;
    }

    public String getUserId()
    {
        return this.UserId;
    }

    public DateTime getDateOfBirth(){
        return this.DateOfBirth;
    }

    public String getEmailAddress(){
        return this.EmailAddress;
    }

    public String getPhoneNumber()
    {
        return this.PhoneNumber;
    }

    public Boolean getGender(){
        return this.Gender;
    }


    public UserModel getThisUser(int id){
        UserModel um = new UserModel();




        return um;
    }




}
