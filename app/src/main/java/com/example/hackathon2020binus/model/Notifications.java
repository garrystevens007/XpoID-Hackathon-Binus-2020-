package com.example.hackathon2020binus.model;

public class Notifications {
    public Notifications(String description, String image, String senderID, String title) {
        this.description = description;
        this.image = image;
        this.senderID = senderID;
        this.title = title;
    }

    private String description;
    private String image;
    private String senderID;
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
