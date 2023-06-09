package com.example.prjfarmfreshv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prjfarmfreshv1.models.Order;
import com.example.prjfarmfreshv1.models.OrderInfor;
import com.example.prjfarmfreshv1.models.OrderProduct;
import com.example.prjfarmfreshv1.models.OrderProductAdapter;
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
    ArrayList<OrderProduct> orderProductList;
    OrderProductAdapter orderProductAdapter;
    OrderProduct orderProduct;
    DatabaseReference orderProductDatabase;

    String orderNumberDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initialize();
    }

    private void initialize() {
        try{
            tvId = findViewById(R.id.tvId);
            tvDate = findViewById(R.id.tvDateAdminOrderDetails);
            tvTotal = findViewById(R.id.tvTotalAdminDetailOrder);
            btnReturn = findViewById(R.id.btnReturnAdminClientList);
            lvProducts = findViewById(R.id.lvProducts);
            btnReturn.setOnClickListener(this);
            orderProductDatabase = FirebaseDatabase.getInstance().getReference("OrderProduct");
            user = (User)getIntent().getExtras().getSerializable("user");
            orderInfor = (OrderInfor) getIntent().getExtras().getSerializable("order");
            if (orderInfor.getOrderId().length()>5){
                orderNumberDisplay = orderInfor.getOrderId().substring(0,5);
            }
            tvId.setText(orderNumberDisplay);
            tvDate.setText(String.valueOf(orderInfor.getDate()));
            tvTotal.setText(String.format("%.2f", orderInfor.getTotal()));
            orderProductList = new ArrayList<>();
            orderProductAdapter = new OrderProductAdapter(this, orderProductList);
            lvProducts.setAdapter(orderProductAdapter);
            orderProductDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    OrderProduct value = (OrderProduct) snapshot.getValue(OrderProduct.class);
                    String orderId = value.getOrderId();
                    if (orderId.equals(orderInfor.getOrderId())){
                        orderProductList.add(value);
                        orderProductAdapter.notifyDataSetChanged();
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
        }catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}