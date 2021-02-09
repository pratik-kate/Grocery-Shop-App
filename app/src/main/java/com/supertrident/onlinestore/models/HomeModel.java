package com.supertrident.onlinestore.models;

public class HomeModel {
    int image;
    String name,price,shop;

    public HomeModel(){

    }

    public HomeModel(int image, String name, String price, String shop) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.shop = shop;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }
}
