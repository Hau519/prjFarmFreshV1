package com.example.prjfarmfreshv1.models;

import java.io.Serializable;

public class Product implements Serializable {
    private int productId;
    private String name;
    private String category;
    private String description;
    private float price ;

    private String photo;
    public Product() {}



    public Product(int id, String name, String category, String description, float price, String photo) {
        this.productId = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
