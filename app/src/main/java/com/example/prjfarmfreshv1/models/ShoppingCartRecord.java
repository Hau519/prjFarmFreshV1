package com.example.prjfarmfreshv1.models;

import java.io.Serializable;

public class ShoppingCartRecord implements Serializable {
    private String productId;
    private String productName;
    private float productPrice;
    private int productQuantity;
    private float productTotal;
    private String productPhoto;


    public ShoppingCartRecord() {
    }


    public ShoppingCartRecord(String productId, String productName, float productPrice, int productQuantity, float productTotal, String productPhoto) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productTotal = productTotal;
        this.productPhoto = productPhoto;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public float getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(float productTotal) {
        this.productTotal = productTotal;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }
}
