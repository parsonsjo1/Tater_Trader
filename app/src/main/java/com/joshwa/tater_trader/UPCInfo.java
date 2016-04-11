package com.joshwa.tater_trader;

import java.net.URL;

/**
 * Created by Joshua on 4/10/2016.
 */
public class UPCInfo
{
    private String productname;
    private String imageurl;
    private String producturl;
    private String price;
    private String currency;
    private String saleprice;
    private String storename;

    public UPCInfo(String productname, String imageurl, String producturl, String price, String currency, String saleprice, String storename) {
        this.productname = productname;
        this.imageurl = imageurl;
        this.producturl = producturl;
        this.price = price;
        this.currency = currency;
        this.saleprice = saleprice;
        this.storename = storename;
    }

    public String getProductName() {
        return productname;
    }

    public void setProductName(String productName) {
        this.productname = productName;
    }

    public String getImageURL() {
        return imageurl;
    }

    public void setImageURL(String imageURL) {
        this.imageurl = imageURL;
    }

    public String getProductURL() {
        return producturl;
    }

    public void setProductURL(String productURL) {
        this.producturl = productURL;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    @Override
    public String toString() {
        return "UPCInfo{" +
                "productName='" + productname + '\'' +
                ", imageURL=" + imageurl +
                ", productURL=" + producturl +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", saleprice=" + saleprice +
                ", storename='" + storename + '\'' +
                '}';
    }
}
