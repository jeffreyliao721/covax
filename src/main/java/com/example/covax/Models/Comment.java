package com.example.covax.Models;

import com.google.firebase.database.ServerValue;

public class Comment {
    private String cUserID, cUserName, cUserIcon, cText;
    private Object timeStamp;

    public Comment() {
    }

    public Comment(String cUserID, String cUserName, String cUserIcon, String cText) {
        this.cUserID = cUserID;
        this.cUserName = cUserName;
        this.cUserIcon = cUserIcon;
        this.cText = cText;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public Comment(String cUserID, String cUserName, String cUserIcon, String cText, Object timeStamp) {
        this.cUserID = cUserID;
        this.cUserName = cUserName;
        this.cUserIcon = cUserIcon;
        this.cText = cText;
        this.timeStamp = timeStamp;
    }

    public String getcUserID() {
        return cUserID;
    }

    public String getcUserName() {
        return cUserName;
    }

    public String getcUserIcon() {
        return cUserIcon;
    }

    public String getcText() {
        return cText;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setcUserID(String cUserID) {
        this.cUserID = cUserID;
    }

    public void setcUserName(String cUserName) {
        this.cUserName = cUserName;
    }

    public void setcUserIcon(String cUserIcon) {
        this.cUserIcon = cUserIcon;
    }

    public void setcText(String text) {
        this.cText = text;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
