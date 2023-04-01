package com.example.prjfarmfreshv1.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    private static int Id=0;

    private int clientId;
    private ArrayList<Product> productList;
    private LocalDateTime orderDate;
    private double total;

    public int getId() {
        return Id;
    }


    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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

    public Order(int clientId, ArrayList<Product> productList, LocalDateTime orderDate, double total) {
        this.clientId = clientId;
        this.productList = productList;
        this.orderDate = orderDate;
        this.total = total;
        Id++;
    }

    public Order(int id, int clientId, ArrayList<Product> productList, LocalDateTime orderDate, double total) {
        Id = id;
        this.clientId = clientId;
        this.productList = productList;
        this.orderDate = orderDate;
        this.total = total;
    }
}
