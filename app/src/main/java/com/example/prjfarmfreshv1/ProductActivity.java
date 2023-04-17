package com.example.prjfarmfreshv1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import java.util.Objects;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener  {
    ListView lvProducts;
    Button btnSearchProductName, btnReset, btnGoToShoppingCart;
    EditText edSearchProductName;
    Spinner spinnerProductFilter;
    String[] categories = {"All","Vegetable","Meat","Fruit"};



    ArrayList<Product> fullProductList;
    ProductAdapter productAdapter;
    DatabaseReference productsDB;

    ActivityResultLauncher actResLauncher;

    ArrayList<ShoppingCartRecord> selectedProductRecords = new ArrayList<>();

    public BroadcastReceiver mMessageReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initialize();
    }

    private void initialize() {
        mMessageReceiver = getMessageReceiver();

        //transfer data from Adapter
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("selectedOneProduct"));

        edSearchProductName = (EditText) findViewById(R.id.edSearchProductName);


        //filter
        spinnerProductFilter = (Spinner) findViewById(R.id.spinnerProductFilter);
        spinnerProductFilter.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories));
        spinnerProductFilter.setOnItemSelectedListener(this);




        //listView
        lvProducts = findViewById(R.id.lvProducts);
        getFullProductList();
        productAdapter = new ProductAdapter(ProductActivity.this, fullProductList);
        lvProducts.setAdapter(productAdapter);

        //button
        btnSearchProductName = findViewById(R.id.btnSearchProductName);
        btnReset = findViewById(R.id.btnReset);
        btnGoToShoppingCart = findViewById(R.id.btnGoToShoppingCart);
        btnReset.setOnClickListener(this);
        btnSearchProductName.setOnClickListener(this);
        btnGoToShoppingCart.setOnClickListener(this);



        actResLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> updateSelectedList(result));
    }



    private void updateSelectedList(ActivityResult result) {

            if (result.getResultCode() == RESULT_OK && result.getData()!=null) {
                selectedProductRecords = (ArrayList<ShoppingCartRecord>) result.getData().getExtras().getSerializable("returnedScRecordList");
                if (selectedProductRecords.size()>0) {
                    for (int i = 0; i < fullProductList.size(); i++) {
                        TextView tvSelectedProductName = (TextView) lvProducts.getChildAt(i).findViewById(R.id.tvProductName);
                        EditText edSelectedProductQuantity = (EditText) lvProducts.getChildAt(i).findViewById(R.id.edQuantity);
                        for (ShoppingCartRecord scRecord : selectedProductRecords) {
                            if (tvSelectedProductName.getText().toString().equals(scRecord.getProductName())) {
                                edSelectedProductQuantity.setText(scRecord.getProductQuantity()+"");
                                break;
                            }else{
                                edSelectedProductQuantity.setText(null);
                            }
                        }
                    }
                }else
                    reset();



            }else{
                Toast.makeText(this, "Wrong way", Toast.LENGTH_SHORT).show();
            }

    }

    private BroadcastReceiver getMessageReceiver() {
       return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent
                int quantity = intent.getIntExtra("quantity", 0);
                Product selectedProduct = (Product) intent.getExtras().getSerializable("product");


                for (ShoppingCartRecord scRec : selectedProductRecords) {
                    if ((selectedProduct.getName()).equals(scRec.getProductName())) {
//                        int finalQuantity = quantity + scRec.getProductQuantity();
                        int finalQuantity = quantity;
                        scRec.setProductQuantity(finalQuantity);
                        scRec.setProductTotal(finalQuantity * scRec.getProductPrice());
                        return;
                    }
                }

                String productId = selectedProduct.getProductId();
                String productName = selectedProduct.getName();
                float productPrice = selectedProduct.getPrice();
                float total = quantity * productPrice;
                String productPhoto = selectedProduct.getPhoto();

                ShoppingCartRecord scRecord = new ShoppingCartRecord(productId, productName, productPrice, quantity, total,productPhoto);
                selectedProductRecords.add(scRecord);
                Toast.makeText(context, "Add to cart success!", Toast.LENGTH_SHORT).show();
            }


        };
    }




    private void getFullProductList() {

        fullProductList = new ArrayList<Product>();
        productsDB = FirebaseDatabase.getInstance().getReference("Products");
//        productsDB = FirebaseDatabase.getInstance().getReference(Product.class.getSimpleName()+"s");
        productsDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product product = snapshot.getValue(Product.class);
                fullProductList.add(product);
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
        productAdapter = new ProductAdapter(ProductActivity.this, fullProductList);
        lvProducts.setAdapter(productAdapter);
        edSearchProductName.setText(null);
        spinnerProductFilter.setSelection(0);
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

        for (Product product : fullProductList) {
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
        intent.putExtra("selectedProducts", selectedProductRecords);
        actResLauncher.launch(intent);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position >= 0 && position < categories.length) {
            getSelectedCategories(position);
        } else {
            Toast.makeText(this, "Selected Category not Exist", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void getSelectedCategories(int categoryID) {
        if (categoryID == 0) {
            productAdapter = new ProductAdapter(ProductActivity.this, fullProductList);
        } else {
            ArrayList<Product> categorizedProductList = new ArrayList<>();
            for (Product product : fullProductList) {
                if (product.getCategory().equals(categories[categoryID])) {
                    categorizedProductList.add(product);
                }
            }
            productAdapter = new ProductAdapter(this, categorizedProductList);
        }
        lvProducts.setAdapter(productAdapter);
    }


}