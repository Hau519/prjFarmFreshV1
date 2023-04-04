package com.example.prjfarmfreshv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.prjfarmfreshv1.models.Order;
import com.example.prjfarmfreshv1.models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    ListView lvOrders;
    ImageView ivAccount;
    Button btnShop;
    User user;
    ArrayList<Order> orderList;

    ArrayAdapter<Order> arrayAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initialize();
    }

    private void initialize() {
        lvOrders = findViewById(R.id.lvOrders);
        lvOrders.setOnItemClickListener(this);
        ivAccount = findViewById(R.id.ivAccount);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Orders");
        arrayAdapter = new ArrayAdapter<>(this, R.layout.one_item, orderList);
        user = (User)getIntent().getExtras().getSerializable("user");
        ivAccount.setOnClickListener(this);
        btnShop = findViewById(R.id.btnShop);
        readOrderData();
//        orderAdapter = new ArrayAdapter<>(this, R.layout.O, orderList)
    }

    private void readOrderData() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Order order = snapshot.getValue(Order.class);
                orderList.add(order);
                lvOrders.setAdapter(arrayAdapter);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Order order = (Order)orderList.get(i);
        Intent intent2 = new Intent(this, OrderActivity.class);
        intent2.putExtra("order", order);
        intent2.putExtra("user", user);
        startActivity(intent2);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnShop:
                Intent i = new Intent(this, MainActivity.class); //TODO : change to product activity when Luke's done
                i.putExtra("user", user);
                startActivity(i);
                break;
            case R.id.ivAccount:
                finish();
                break;
        }
    }
}