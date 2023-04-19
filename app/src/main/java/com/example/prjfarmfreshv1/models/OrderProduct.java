package com.example.prjfarmfreshv1.models;

import java.io.Serializable;

public class OrderProduct implements Serializable {
    private String orderId;
    private String productName;
    private float unitPrice;

    private float quantity;
    private float productTotal;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(float productTotal) {
        this.productTotal = productTotal;
    }

    public OrderProduct() {
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public OrderProduct(String orderId, String productName, float unitPrice, float productTotal, float quantity) {
        this.orderId = orderId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.productTotal = productTotal;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return
                "  " + productName +
                "            " + unitPrice +
                "                " + quantity +
                "              " + productTotal;
    }
}
