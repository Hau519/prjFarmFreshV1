package com.example.prjfarmfreshv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.prjfarmfreshv1.models.Product;
import com.example.prjfarmfreshv1.models.ProductAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    ListView lvProducts;
    ArrayList<Product> productList;
    ProductAdapter productAdapter;
    DatabaseReference productsDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initialize();
    }

    private void initialize() {
        lvProducts = findViewById(R.id.lvProducts);

//        playerList = FileManagement.readFile("players.txt",this );
        getPlayerList();
        productAdapter = new ProductAdapter(this, productList);
        lvProducts.setAdapter(productAdapter);
    }

    private void getPlayerList() {
//        productsDB = FirebaseDatabase.getInstance().getReference("Products");
        productsDB = FirebaseDatabase.getInstance().getReference(Product.class.getSimpleName()+"s");
        productsDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
}