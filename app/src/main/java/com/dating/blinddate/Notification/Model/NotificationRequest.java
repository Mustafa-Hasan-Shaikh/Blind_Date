package com.dating.blinddate.Notification.Model;

import java.util.Map;

public class NotificationRequest {
    private String to;

    private Map<String,String> data;

    public NotificationRequest(String to, Map<String, String> data) {
        this.to = to;
        this.data = data;
    }

}
