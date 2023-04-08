package com.example.prjfarmfreshv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShoppingCart extends AppCompatActivity implements View.OnClickListener {
    Button btnCheckout, btnCS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initialize();
    }

    public void initialize(){
        btnCheckout = findViewById(R.id.btnCheckout);
        btnCS = findViewById(R.id.btnCS);
        btnCheckout.setOnClickListener(this);
        btnCS.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}