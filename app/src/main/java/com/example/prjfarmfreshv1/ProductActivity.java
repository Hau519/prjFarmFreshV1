package com.example.prjfarmfreshv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prjfarmfreshv1.models.Product;
import com.example.prjfarmfreshv1.models.ProductAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    ListView lvProducts;
    Button btnSearchProductName,btnSearchProductCategory,btnGoToShoppingCart;
    ArrayList<Product> productList;
    ProductAdapter productAdapter;
    DatabaseReference productsDB;

    ArrayList<HashMap<Product,Integer>> selectedProducts = new ArrayList<>();

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int quantity = intent.getIntExtra("quantity",0);
            Product selectedProduct= (Product)intent.getExtras().getSerializable("product");
            HashMap<Product,Integer > oneProductQuantity  = new HashMap<Product,Integer>() {{
                put(selectedProduct,quantity);
            }};
            selectedProducts.add(oneProductQuantity);
            Toast.makeText(ProductActivity.this, quantity +" "+selectedProduct.toString() ,Toast.LENGTH_SHORT).show();
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initialize();
    }

    private void initialize() {

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("selectedOneProduct"));
        lvProducts = findViewById(R.id.lvProducts);
        productList = new ArrayList<Product>();
//        playerList = FileManagement.readFile("players.txt",this );
        getPlayerList();
        btnSearchProductName = findViewById(R.id.btnSearchProductName);
        btnSearchProductCategory = findViewById(R.id.btnSearchProductCategory);
        btnGoToShoppingCart = findViewById(R.id.btnGoToShoppingCart);
        btnSearchProductCategory.setOnClickListener(this);
        btnSearchProductName.setOnClickListener(this);
        btnGoToShoppingCart.setOnClickListener(this);



    }

    private void getPlayerList() {
        productsDB = FirebaseDatabase.getInstance().getReference("Products");
//        productsDB = FirebaseDatabase.getInstance().getReference(Product.class.getSimpleName()+"s");
        productsDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product product = snapshot.getValue(Product.class);
                productList.add(product);
                productAdapter = new ProductAdapter(ProductActivity.this, productList);
                lvProducts.setAdapter(productAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}