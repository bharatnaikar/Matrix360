package com.example.navigation2;

public class User {
    String mail, username, password, userId,phonenumber;


    public User(String userId, String username, String mail, String password, String phonenumber){
        this.userId=userId;
        this.username=username;
        this.mail=mail;
        this.password=password;
        this.phonenumber = phonenumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhoneNumber() {
        return phonenumber; // Return phone number as integer
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phonenumber = phoneNumber; // Store phone number as integer
    }
}

