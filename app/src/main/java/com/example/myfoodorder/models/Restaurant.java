package com.example.myfoodorder.models;

public class Restaurant {
    private String name;
    private String imageUrl;
    private String rating;

    public Restaurant() {
    }

    public Restaurant(String name, String imageUrl, String rating) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getRating() {
        return rating;
    }
}
