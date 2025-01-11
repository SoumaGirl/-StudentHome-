package com.example.promobile;

public class Offer {
    private int imageResId;
    private String type;
    private String location;
    private String price;

    public Offer(int imageResId, String type, String location, String price) {
        this.imageResId = imageResId;
        this.type = type;
        this.location = location;
        this.price = price;
    }
    // Constructor for new offers without a custom image
    public Offer(String type, String location, String price) {
        this.imageResId = R.drawable.default_image; // Assuming 'default_image' is in your drawable folder
        this.type = type;
        this.location = location;
        this.price = price;
    }

    public Offer(String title, String price, String type, String location, String string) {
    }

    public int getImageResId() { return imageResId; }
    public String getType() { return type; }
    public String getLocation() { return location; }
    public String getPrice() { return price; }
}

