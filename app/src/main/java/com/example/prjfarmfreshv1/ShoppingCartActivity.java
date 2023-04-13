package com.example.prjfarmfreshv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.prjfarmfreshv1.models.Product;
import com.example.prjfarmfreshv1.models.ShoppingCartRecord;
import com.example.prjfarmfreshv1.models.ShoppingCartRecordAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnCheckout, btnCS;
    ListView lvCart;
    ShoppingCartRecordAdapter scAdapter;
    ArrayList<ShoppingCartRecord> shoppingCartList;
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

        lvCart = findViewById(R.id.lvCart);

        shoppingCartList = (ArrayList<ShoppingCartRecord>) getIntent().getExtras().getSerializable("selectedProducts");
        scAdapter = new ShoppingCartRecordAdapter(this, shoppingCartList);
        lvCart.setAdapter(scAdapter);

    }

    @Override
    public void onClick(View view) {

    }
}