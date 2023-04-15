package com.example.prjfarmfreshv1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.prjfarmfreshv1.models.Product;
import com.example.prjfarmfreshv1.models.ProductAdapter;
import com.example.prjfarmfreshv1.models.ShoppingCartRecord;
import com.example.prjfarmfreshv1.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    ListView lvProducts;
    Button btnSearchProductName, btnReset, btnGoToShoppingCart;
    EditText edSearchProductName;
    ArrayList<Product> productList;
    ProductAdapter productAdapter;
    DatabaseReference productsDB;

//    ArrayList<HashMap<Product,Integer>> selectedProducts = new ArrayList<>();

    ArrayList<ShoppingCartRecord> selectedProducts = new ArrayList<>();
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int quantity = intent.getIntExtra("quantity", 0);
            Product selectedProduct = (Product) intent.getExtras().getSerializable("product");
//            HashMap<Product,Integer > oneProductQuantity  = new HashMap<Product,Integer>() {{
//                put(selectedProduct,quantity);
//            }};
            Toast.makeText(context, selectedProduct.toString(), Toast.LENGTH_SHORT).show();
            for (ShoppingCartRecord scRec : selectedProducts) {
                if ((selectedProduct.getName()).equals(scRec.getProductName())) {
                    int finalQuantity = quantity + scRec.getProductQuantity();
                    scRec.setProductQuantity(finalQuantity);
                    scRec.setProductTotal(finalQuantity * scRec.getProductPrice());
                    return;
                }
            }

            String productId = selectedProduct.getProductId();
            String productName = selectedProduct.getName();
            float productPrice = selectedProduct.getPrice();
            float total = quantity * productPrice;

            ShoppingCartRecord scRecord = new ShoppingCartRecord(productId, productName, productPrice, quantity, total);
            Toast.makeText(context, "total after for loop:" + total, Toast.LENGTH_SHORT).show();
            selectedProducts.add(scRecord);
            Toast.makeText(ProductActivity.this, quantity + " " + scRecord, Toast.LENGTH_SHORT).show();
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initialize();
    }

    private void initialize() {

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("selectedOneProduct"));
        lvProducts = findViewById(R.id.lvProducts);
        edSearchProductName = (EditText) findViewById(R.id.edSearchProductName);

        productList = new ArrayList<Product>();

        getProductList();
        btnSearchProductName = findViewById(R.id.btnSearchProductName);
        btnReset = findViewById(R.id.btnReset);
        btnGoToShoppingCart = findViewById(R.id.btnGoToShoppingCart);
        btnReset.setOnClickListener(this);
        btnSearchProductName.setOnClickListener(this);
        btnGoToShoppingCart.setOnClickListener(this);

//        productAdapter = new ProductAdapter(ProductActivity.this, productList);
//        lvProducts.setAdapter(productAdapter);
    }

    private void getProductList() {
        productList.clear();
        productsDB = FirebaseDatabase.getInstance().getReference("Products");
//        productsDB = FirebaseDatabase.getInstance().getReference(Product.class.getSimpleName()+"s");
        productsDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product product = snapshot.getValue(Product.class);
                productList.add(product);
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
        productAdapter = new ProductAdapter(ProductActivity.this, productList);
        lvProducts.setAdapter(productAdapter);
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();

        switch (vId) {
            case R.id.btnGoToShoppingCart:
                goToShoppingCart();
                break;
            case R.id.btnSearchProductName:
                searchProductName();
                break;
            case R.id.btnReset:
                reset();
                break;

        }
    }

    private void reset() {
        getProductList();
        edSearchProductName.setText(null);
    }

    private void searchProductName() {

/*
        //TODO: why this way is so buggy?(only use one productList)
        getProductList();
        String searchedName = edSearchProductName.getText().toString();
        ArrayList<Product> productListToRemove = new ArrayList<>();
        for (Product product : productList) {
            if (!(product.getName().toLowerCase()).contains(searchedName.toLowerCase())) {
                productListToRemove.add(product);
            }
        }

        for (Product product : productListToRemove) {
            productList.remove(product);
        }
        productAdapter.notifyDataSetChanged();*/


        ArrayList<Product> searchResultList = new ArrayList<Product>();
        String searchedName = edSearchProductName.getText().toString();

        for (Product product : productList) {
            if ((product.getName().toLowerCase()).contains(searchedName.toLowerCase())) {
                searchResultList.add(product);
            }
        }
        productAdapter = new ProductAdapter(ProductActivity.this, searchResultList);
        lvProducts.setAdapter(productAdapter);


    }

    private void goToShoppingCart() {
        User user = (User) getIntent().getExtras().getSerializable("user");
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("selectedProducts", selectedProducts);
        startActivity(intent);

    }
}