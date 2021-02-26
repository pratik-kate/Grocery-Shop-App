package com.supertrident.ecom.test.models;

public class OrderModel {
    String no,amount,name,phone,address,pincode,landmark,products;

    public OrderModel(){

    }
    public OrderModel(String no, String amount, String name, String phone, String address, String pincode, String landmark, String products) {
        this.no = no;
        this.amount = amount;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.pincode = pincode;
        this.landmark = landmark;
        this.products = products;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }
}
