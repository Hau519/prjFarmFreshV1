package com.example.prjfarmfreshv1.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    private int Id;
    private ArrayList<Product> productList;
    private LocalDateTime orderDate;
    private double total;

    public Order() {
    }

    public Order(ArrayList<Product> productList, LocalDateTime orderDate, double total) {
        this.productList = productList;
        this.orderDate = orderDate;
        this.total = total;
    }

    public Order(int id, ArrayList<Product> productList, LocalDateTime orderDate, double total) {
        Id = id;
        this.productList = productList;
        this.orderDate = orderDate;
        this.total = total;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
