package com.dating.blinddate.Model;

public class User {
    boolean loged;
    String profilepic,userName,status,phoneNo,mail,password,userId,about,lastMessage,requestType,Device_Token;

    public String getStatus() {
        return status;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDevice_Token() {
        return Device_Token;
    }

    public void setDevice_Token(String device_Token) {
        Device_Token = device_Token;
    }

    public User(boolean loged, String profilepic, String userName, String status, String phoneNo, String mail, String password, String userId, String about, String lastMessage, String requestType, String device_Token) {
        this.loged = loged;
        this.profilepic = profilepic;
        this.userName = userName;
        this.status = status;
        this.phoneNo = phoneNo;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.about = about;
        this.lastMessage = lastMessage;
        this.requestType = requestType;
        Device_Token = device_Token;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getUserId() {
        return userId;
    }

    public User(){}

    public boolean isLoged() {
        return loged;
    }

    public void setLoged(boolean loged) {
        this.loged = loged;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    //SignUp Constructor ()
    public User(String userName,String phoneNo, String mail, String password) {
        this.userName = userName;
        this.phoneNo = phoneNo;
        this.mail = mail;
        this.password = password;
    }
    public User(String userName,String phoneNo, String mail) {
        this.userName = userName;
        this.phoneNo = phoneNo;
        this.mail = mail;
    }
    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUserId(String key) {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
