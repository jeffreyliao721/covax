package com.example.covax.Models;

import android.media.Image;
import android.widget.ImageView;

import com.google.firebase.database.ServerValue;

public class Post {
    private String userID, userName, userIcon;
    private String title, address, image, description;
    private Object timeStamp;
    private String key;

    public Post() {
    }

    public Post(String userID, String userName, String userIcon, String title, String description) {
        this.userID = userID;
        this.userName = userName;
        this.userIcon = userIcon;
        this.title = title;
        this.description = description;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public Post(String userID, String userName, String userIcon, String title, String description, String image) {
        this.userID = userID;
        this.userName = userName;
        this.userIcon = userIcon;
        this.title = title;
        this.description = description;
        this.image = image;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public String getKey() {
        return key;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
