package com.dating.blinddate.Model;

public class NotificationModel {
    String profile;
    String icon;
    String Name;

    String Notification;
    String Time;

    public NotificationModel(String profile, String icon, String name, String notification, String time) {
        this.profile = profile;
        this.icon = icon;
        Name = name;
        Notification = notification;
        Time = time;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNotification() {
        return Notification;
    }

    public void setNotification(String notification) {
        Notification = notification;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
