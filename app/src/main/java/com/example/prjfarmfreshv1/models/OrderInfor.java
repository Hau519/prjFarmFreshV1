package com.example.prjfarmfreshv1.models;

import java.io.Serializable;

public class OrderInfor implements Serializable {
    private String clientId;
    private String date;
    private float total;
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderInfor() {
    }

    public OrderInfor(String clientId, String date, float total, String orderId) {
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
