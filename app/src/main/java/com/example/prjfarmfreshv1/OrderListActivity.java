package com.example.prjfarmfreshv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prjfarmfreshv1.models.Order;
import com.example.prjfarmfreshv1.models.OrderInfor;
import com.example.prjfarmfreshv1.models.OrderListAdapter;
import com.example.prjfarmfreshv1.models.User;
import com.example.prjfarmfreshv1.ui.home.Login;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener, DialogInterface.OnClickListener {

    ListView lvOrders;
    ImageView ivAccount;
    Button btnShop;
    User user;
    OrderInfor orderInfor;
    ArrayList<OrderInfor> orderInforList;
    OrderListAdapter orderListAdapter;
    DatabaseReference orderListDatabase;

    AlertDialog.Builder alertD;
    int position=-1;

    String admin="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initialize();
    }

    private void initialize() {
        try{
            lvOrders = findViewById(R.id.lvOrders);
            lvOrders.setOnItemClickListener(this);
            lvOrders.setOnItemLongClickListener(this);
            ivAccount = findViewById(R.id.ivAccount);

            orderListDatabase = FirebaseDatabase.getInstance().getReference("OrderList");
            orderInforList = new ArrayList<OrderInfor>();

            user = (User)getIntent().getExtras().getSerializable("user");

            ivAccount.setOnClickListener(this);
            btnShop = findViewById(R.id.btnShop);
            btnShop.setOnClickListener(this);
            orderInfor = new OrderInfor();
            orderListAdapter = new OrderListAdapter(this, orderInforList);
            lvOrders.setAdapter(orderListAdapter);
            Intent intent = getIntent();
            if (intent.hasExtra("admin")){
                admin = getIntent().getExtras().getString("admin");
                btnShop.setText("Return");
                setDeleteAlert();
                ivAccount.setVisibility(View.INVISIBLE);
            }

            orderListDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    OrderInfor value = (OrderInfor) snapshot.getValue(OrderInfor.class);
                    if (admin.equalsIgnoreCase("adminAllUsers")){
                        orderInforList.add(value);
                        orderListAdapter.notifyDataSetChanged();

                    }else{
                        String email = value.getClientId();

                        if (email.equals(user.getEmail())){
                            orderInforList.add(value);
                            orderListAdapter.notifyDataSetChanged();
                        }
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

    private void setDeleteAlert() {
        alertD = new AlertDialog.Builder(this);
        alertD.setTitle("Remove Product");
        alertD.setMessage("Do you want to remove (Yes/NO)?");
        alertD.setPositiveButton("Yes", this);
        alertD.setNegativeButton("No",this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try{
            if (admin.isEmpty()){
                OrderInfor orderInfo = orderInforList.get(i);
                Intent intent2 = new Intent(this, OrderActivity.class);
                intent2.putExtra("order", orderInfo);
                intent2.putExtra("user", user);
                startActivity(intent2);
            }else if(admin.equalsIgnoreCase("admin") || admin.equalsIgnoreCase("adminAllUsers")){
                OrderInfor orderInfo = orderInforList.get(i);
                Intent intent2 = new Intent(this, AdminOrderDetailsActivity.class);
                intent2.putExtra("order", orderInfo);
                intent2.putExtra("user", user);
                intent2.putExtra("admin", "admin");
                startActivity(intent2);
            }

        }catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnShop:
                try{
                    if(btnShop.getText().toString().equalsIgnoreCase("SHOP NOW")){
                        Intent i = new Intent(this, ProductActivity.class);
                        i.putExtra("user", user);
                        startActivity(i);
                    }else if(btnShop.getText().toString().equalsIgnoreCase("Return")){
                        finish();
                    }

                }catch (Exception ex){
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ivAccount:
                finish();
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
        if(!admin.isEmpty()){
            position = i;
            alertD.create().show();
            return true;
        }
        return false;

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                orderListDatabase.child(orderInforList.get(position).getOrderId()).removeValue();
                orderInforList.remove(position);
                orderListAdapter.notifyDataSetChanged();
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
        }
    }
}