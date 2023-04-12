package com.example.prjfarmfreshv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.prjfarmfreshv1.models.OrderInfor;
import com.example.prjfarmfreshv1.models.OrderProduct;
import com.example.prjfarmfreshv1.models.Product;
import com.example.prjfarmfreshv1.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvId, tvDate, tvTotal;
    Button btnReturn;

    Order order;
    OrderInfor orderInfor;
    ListView lvProducts;
    User user;
    ArrayList<String> orderProductList;

    ArrayAdapter<String> arrayAdapter;

    DatabaseReference orderProductDatabase;
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
        btnReturn.setOnClickListener(this);
        orderProductDatabase = FirebaseDatabase.getInstance().getReference("OrderProduct");

        user = (User)getIntent().getExtras().getSerializable("user");
        orderInfor = (OrderInfor) getIntent().getExtras().getSerializable("order");

        tvId.setText(orderInfor.getOrderId());

        tvDate.setText(String.valueOf(orderInfor.getDate()));
        tvTotal.setText(String.valueOf(orderInfor.getTotal()));
        orderProductList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.one_item, orderProductList);
        lvProducts.setAdapter(arrayAdapter);
        orderProductDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                OrderProduct value = (OrderProduct) snapshot.getValue(OrderProduct.class);
                String orderId = value.getOrderId();
                if (orderId.equals(orderInfor.getOrderId())){
                    orderProductList.add(value.toString());
                    arrayAdapter.notifyDataSetChanged();
                }
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
        finish();
    }
}