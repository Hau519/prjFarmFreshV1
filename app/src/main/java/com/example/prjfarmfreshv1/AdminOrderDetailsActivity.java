package com.example.prjfarmfreshv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prjfarmfreshv1.models.OrderInfor;
import com.example.prjfarmfreshv1.models.OrderProduct;
import com.example.prjfarmfreshv1.models.OrderProductAdapter;
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

public class AdminOrderDetailsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener, DialogInterface.OnClickListener {

    TextView tvDate, tvTotal, tvClientId;
    ListView lvProducts;
    Button btnWorkStation, btnReturn;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA);
    User user;

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

    float totalChange=0;

    AlertDialog.Builder alertD;
    int position=-1;

    int state = 1;

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            orderProductList = ( ArrayList<OrderProduct>) intent.getExtras().getSerializable("newOrderProductList");
            totalChange = intent.getExtras().getFloat("totalChange");

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_details);
        initialize();
    }

    private void initialize() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("changedOrderProductList"));
        orderListDatabase = FirebaseDatabase.getInstance().getReference("OrderList");
        orderProductDatabase = FirebaseDatabase.getInstance().getReference("OrderProduct");

        tvDate = findViewById(R.id.tvDateAdminOrderDetails);
        tvTotal = findViewById(R.id.tvTotalAdminDetailOrder);
        lvProducts = findViewById(R.id.lvProductAdinOrderDetails);
        lvProducts.setOnItemLongClickListener(this);
        tvClientId = findViewById(R.id.tvClientIdAdminOrderDetail);

        setDeleteAlert();
        btnWorkStation = findViewById(R.id.btnWorkingStation);
        btnReturn = findViewById(R.id.btnReturnAdminClientList);
        btnWorkStation.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
        clientId = getIntent().getExtras().getString("clientId");
        user = (User)getIntent().getExtras().getSerializable("user");
        tvClientId.setText(clientId);
        orderInfor = (OrderInfor )getIntent().getExtras().getSerializable("order");

        total = orderInfor.getTotal()+totalChange;
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
    }

    private void setDeleteAlert() {
        alertD = new AlertDialog.Builder(this);
        alertD.setTitle("Remove Product");
        alertD.setMessage("Do you want to remove (Yes/NO)?");
        alertD.setPositiveButton("Yes", this);
        alertD.setNegativeButton("No",this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnReturnAdminClientList:
                finish();
                break;
            case R.id.btnWorkingStation:
                Intent intent = new Intent(this, AdminProfileActivity.class);
                intent.putExtra("admin", "admin");
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                orderProductList.remove(position);
                orderProductAdapter.notifyDataSetChanged();
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
        position = i;
        alertD.create().show();
        return true;
    }
}