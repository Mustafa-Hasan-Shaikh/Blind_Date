package com.dating.blinddate.Model;

public class MessageModel {
    public MessageModel(String messageId, String messageText, Long timestamp) {
        this.messageId = messageId;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    public MessageModel(String messageId, String messageText) {
        this.messageId = messageId;
        this.messageText = messageText;
    }
    public MessageModel(){
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {

        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    String messageId;
    String messageText;

    String messageid;
    Long timestamp;
}
