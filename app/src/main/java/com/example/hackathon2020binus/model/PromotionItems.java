package com.example.hackathon2020binus.model;

public class PromotionItems {
    public PromotionItems(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public PromotionItems(){

    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String imgUrl;
}
