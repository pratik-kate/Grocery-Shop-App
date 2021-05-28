package com.pratikcodes.shopclient.models;

public class ProductModel {
    String catId, description, image, name, price;

    public ProductModel(){

    }

    public ProductModel(String catId, String description, String image, String name, String price) {
        this.catId = catId;
        this.description = description;
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

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
}
