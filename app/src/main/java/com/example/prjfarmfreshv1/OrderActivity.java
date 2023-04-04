package com.example.prjfarmfreshv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prjfarmfreshv1.models.Order;
import com.example.prjfarmfreshv1.models.Product;
import com.example.prjfarmfreshv1.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvId, tvDate, tvTotal;
    Button btnReturn;

    Order order;
    ListView lvProducts;
    User user;
    ArrayList<Product> productList;

    ArrayAdapter<Product> arrayAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initialize();
    }

    private void initialize() {
        tvId = findViewById(R.id.tvId);
        tvDate = findViewById(R.id.tvDate);
        tvTotal = findViewById(R.id.tvTotal);
        btnReturn = findViewById(R.id.btnReturn);
        lvProducts = findViewById(R.id.lvProducts);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Order");
        user = (User)getIntent().getExtras().getSerializable("user");
        order = (Order)getIntent().getExtras().getSerializable("order");
        tvId.setText(String.valueOf(order.getId()));
        tvDate.setText(String.valueOf(order.getOrderDate()));
        tvTotal.setText(String.valueOf(order.getTotal()));
        productList = order.getProductList();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.one_item, productList);
        lvProducts.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}