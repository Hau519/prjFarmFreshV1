package com.example.prjfarmfreshv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prjfarmfreshv1.models.OrderInfor;
import com.example.prjfarmfreshv1.models.OrderProduct;
import com.example.prjfarmfreshv1.models.ShoppingCartRecord;
import com.example.prjfarmfreshv1.models.ShoppingCartRecordAdapter;
import com.example.prjfarmfreshv1.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Payment extends AppCompatActivity implements View.OnClickListener {

    TextView tvDate, tvTotal;
    ListView lvProducts;
    Button btnPlaceOrder;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA);
    User user;
    ShoppingCartRecordAdapter scAdapter;
    ArrayList<ShoppingCartRecord> shoppingCartList;
    String orderNumber;
    String dateOrder;
    Double total;

    OrderInfor orderInfor;
    OrderProduct orderProduct;

    DatabaseReference orderListDatabase;
    DatabaseReference orderProductDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        intialize();
    }

    private void intialize() {
        orderListDatabase = FirebaseDatabase.getInstance().getReference("OrderList");
        orderProductDatabase = FirebaseDatabase.getInstance().getReference("OrderProduct");

        tvDate = findViewById(R.id.tvDate);
        tvTotal = findViewById(R.id.tvTotal);
        lvProducts = findViewById(R.id.lvProducts);
        btnPlaceOrder = findViewById(R.id.btnOrder);

        btnPlaceOrder.setOnClickListener(this);

        user = (User)getIntent().getExtras().getSerializable("user");
        total = (Double)getIntent().getExtras().getDouble("total");
        total = (double) (Math.round(total * 100) / 100);

        tvTotal.setText(String.format("%.2f",total));
        Date date = new Date();
        dateOrder = String.valueOf(formatter.format(date));
        tvDate.setText(dateOrder);


        shoppingCartList = (ArrayList<ShoppingCartRecord>) getIntent().getExtras().getSerializable("shoppingCartList");
        scAdapter = new ShoppingCartRecordAdapter(this, shoppingCartList,1);
        lvProducts.setAdapter(scAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnOrder:
                try{
                    createOrderInfor();
                    createOrderProduct();
                    Intent i = new Intent(this, OrderPlaced.class);
                    i.putExtra("user", user);
                    i.putExtra("orderNumber", orderNumber);
                    startActivity(i);
                }catch (Exception ex){
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
//            case R.id.ivAccount:
//
//                break;
        }
    }

//    String orderId, String productName, float unitPrice, float productTotal, float quantity)
    private void createOrderProduct() {
        try{
            for (ShoppingCartRecord shoppingCart : shoppingCartList){
                String id = UUID.randomUUID().toString();
                String productName = shoppingCart.getProductName();
                float unitPrice = shoppingCart.getProductPrice();
                float productTotal = shoppingCart.getProductTotal();
                float quantity = (float)shoppingCart.getProductQuantity();

                orderProduct = new OrderProduct(orderNumber, productName, unitPrice, productTotal, quantity);

                orderProductDatabase.child(id).setValue(orderProduct);
//                orderProductDatabase.child(id).setValue("hello");
            }
        }catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void createOrderInfor() {
        try{
            orderNumber = "O" + UUID.randomUUID().toString();
            orderInfor = new OrderInfor(user.getEmail(), dateOrder, total, orderNumber);
            orderListDatabase.child(orderNumber).setValue(orderInfor);
        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}