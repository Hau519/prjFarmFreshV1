package com.example.prjfarmfreshv1.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String productId;
    private String name;
    private String category;
    private String description;
    private float price ;

    private String photo;
    private static int sequence=0;
    public Product() {}

    public Product( String name, String category, String description, float price, String photo) {
        this.productId = "p"+(++sequence);
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
    }

    public Product(String productId, String name, String category, String description, float price, String photo) {
        this.productId = productId;
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
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
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

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", photo='" + photo + '\'' +
                '}';
    }
}
