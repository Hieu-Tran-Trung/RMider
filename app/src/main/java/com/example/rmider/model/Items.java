package com.example.rmider.model;

public class Items {
    private String title;
    private int    image;
    private int    type;

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public int getType() {
        return type;
    }

    public Items(String title, int image, int type) {
        this.title = title;
        this.image = image;
        this.type = type;
    }
}
