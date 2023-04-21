package com.example.prjfarmfreshv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prjfarmfreshv1.models.OrderInfor;
import com.example.prjfarmfreshv1.models.OrderProduct;
import com.example.prjfarmfreshv1.models.OrderProductAdapter;
import com.example.prjfarmfreshv1.models.ShoppingCartRecord;
import com.example.prjfarmfreshv1.models.ShoppingCartRecordAdapter;
import com.example.prjfarmfreshv1.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdminOrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvDate, tvTotal, tvClientId;
    ListView lvProducts;
    Button btnWorkStation, btnReturn;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA);
    User user;
    ShoppingCartRecordAdapter scAdapter;
    ArrayList<ShoppingCartRecord> shoppingCartList;
    String orderNumber;
    String dateOrder;
    Double total;
    String clientId;

    OrderInfor orderInfor;
    OrderProduct orderProduct;
    ArrayList<OrderProduct> orderProductList;
    OrderProductAdapter orderProductAdapter;

    DatabaseReference orderProductDatabase;

    String orderNumberDisplay;
    DatabaseReference orderListDatabase;

    int state = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_details);
        initialize();
    }

    private void initialize() {
        orderListDatabase = FirebaseDatabase.getInstance().getReference("OrderList");
        orderProductDatabase = FirebaseDatabase.getInstance().getReference("OrderProduct");

        tvDate = findViewById(R.id.tvDateAdminOrderDetails);
        tvTotal = findViewById(R.id.tvTotalAdminDetailOrder);
        lvProducts = findViewById(R.id.lvProductAdinOrderDetails);
        tvClientId = findViewById(R.id.tvClientIdAdminOrderDetail);


        btnWorkStation = findViewById(R.id.btnWorkingStation);
        btnReturn = findViewById(R.id.btnReturn);
        btnWorkStation.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
        clientId = getIntent().getExtras().getString("clientId");
        user = (User)getIntent().getExtras().getSerializable("user");
        tvClientId.setText(clientId);
        orderInfor = (OrderInfor )getIntent().getExtras().getSerializable("order");

        total = orderInfor.getTotal();
        tvTotal.setText(String.format("%.2f",total));
        Date date = new Date();
        dateOrder = orderInfor.getDate();
        tvDate.setText(dateOrder);
        orderProductDatabase = FirebaseDatabase.getInstance().getReference("OrderProduct");
        orderProductList = new ArrayList<>();
        orderProductAdapter = new OrderProductAdapter(this, orderProductList, state);
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
      /*  shoppingCartList = (ArrayList<ShoppingCartRecord>) getIntent().getExtras().getSerializable("shoppingCartList");
        scAdapter = new ShoppingCartRecordAdapter(this, shoppingCartList);
        lvProducts.setAdapter(scAdapter);*/
    }

    @Override
    public void onClick(View v) {

    }
}