package com.example.demo.model;

public class Product {
    private String productName;
    private int price, amountAvailable;

    public Product() {
        super();
    }

    public Product(String productName, int price, int amountAvailable) {
        this.productName = productName;
        this.price = price;
        this.amountAvailable = amountAvailable;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }

    public int setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
        return amountAvailable;
    }
}
