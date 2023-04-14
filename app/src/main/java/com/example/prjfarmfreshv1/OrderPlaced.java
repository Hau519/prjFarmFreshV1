package com.example.prjfarmfreshv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.UUID;

public class OrderPlaced extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        String orderId = UUID.randomUUID().toString();
    }
}