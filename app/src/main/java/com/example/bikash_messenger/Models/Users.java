package com.example.bikash_messenger.Models;

public class Users {
    String profilepic,username,mail,password,userId,lastmessage,mobileno;

    public Users(String profilepic, String username, String mail, String password, String userId, String lastmessage) {
        this.profilepic = profilepic;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastmessage = lastmessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Users(){ }

    //signup constructor
    public Users(String username,String mobileno ,String mail, String password) {
        this.username = username;
        this.mobileno = mobileno;
        this.mail = mail;
        this.password = password;
    }
    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
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



    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }
}

