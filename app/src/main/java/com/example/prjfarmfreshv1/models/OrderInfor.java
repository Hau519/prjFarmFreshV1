package com.example.prjfarmfreshv1.models;

import java.io.Serializable;

public class OrderInfor implements Serializable {
    private String clientId;
    private String date;
    private double total;
    private String orderId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getOrderId() {
        if (orderId.length()>5){
            return orderId.substring(0,5);
        }
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderInfor() {
    }

    public OrderInfor(String clientId, String date, double total, String orderId) {
        this.clientId = clientId;
        this.date = date;
        this.total = total;
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "         " + orderId+
                "                   " + date +
                "        " + total
                ;
    }
}
