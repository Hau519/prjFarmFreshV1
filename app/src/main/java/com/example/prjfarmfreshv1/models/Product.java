package com.example.prjfarmfreshv1.models;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String category;
    private String description;
    private float price ;

    public Product() {}

    public Product(int id, String name, String category, String description, float price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
