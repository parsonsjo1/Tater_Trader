package com.joshwa.tater_trader;

import android.graphics.Bitmap;

public class ImageItem {
    //private Bitmap image;
    private String image;
    private String title;
    private double price;

    public ImageItem(String image, String title, double price) {
        super();
        this.image = image;
        this.title = title;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }
}
