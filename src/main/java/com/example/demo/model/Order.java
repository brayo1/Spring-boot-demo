package com.example.demo.model;

public class Order {
    private String productName, orderedBy;
    private int amountOrdered;

    public Order() {
        super();
    }

    public Order(String productName, String orderedBy, int amountOrdered) {
        this.productName = productName;
        this.orderedBy = orderedBy;
        this.amountOrdered = amountOrdered;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public int getAmountOrdered() {
        return amountOrdered;
    }

    public void setAmountOrdered(int amountOrdered) {
        this.amountOrdered = amountOrdered;
    }
}
