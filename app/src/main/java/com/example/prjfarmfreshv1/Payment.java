package com.example.prjfarmfreshv1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prjfarmfreshv1.models.MailSender;
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
    Button btnPlaceOrder, btnReturn;
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

    @SuppressLint("DefaultLocale")
    private void intialize() {
        orderListDatabase = FirebaseDatabase.getInstance().getReference("OrderList");
        orderProductDatabase = FirebaseDatabase.getInstance().getReference("OrderProduct");

        tvDate = findViewById(R.id.tvDate);
        tvTotal = findViewById(R.id.tvTotal);
        lvProducts = findViewById(R.id.lvProducts);
        btnPlaceOrder = findViewById(R.id.btnOrder);
        btnReturn = findViewById(R.id.btnReturn);

        btnPlaceOrder.setOnClickListener(this);
        btnReturn.setOnClickListener(this);

        user = (User)getIntent().getExtras().getSerializable("user");
        total = (Double)getIntent().getExtras().getDouble("total");
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
                    sendEmail();
                    Intent i = new Intent(this, OrderPlaced.class);
                    i.putExtra("user", user);
                    i.putExtra("orderNumber", orderNumber);
                    startActivity(i);
                }catch (Exception ex){
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnReturn:
                continueShopping();
                break;
        }
    }

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

    private void continueShopping() {
        Intent i = new Intent();
        i.putExtra("returnedScRecordList", shoppingCartList);
        i.putExtra("user", user);
        setResult(RESULT_OK,i);
        finish();
    }

    protected void sendEmail() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String subject = "Thank you for your order " + orderNumber.substring(0,5);
                    String body = buildConfirmMessage();
                    MailSender sender = new MailSender();
                    sender.sendMail(subject, body,
                            "farmfresh.lasalle@gmail.com", user.getEmail());
                } catch (Exception e) {
                    Toast.makeText(Payment.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }).start();
    }

        public String buildConfirmMessage(){
        String greeting = "Dear " + user.getName() + " !\n\n";
        String thankMessage = "Thank you for shopping with us!"+ "\n\nYour order details are as follow: \n";
        @SuppressLint("DefaultLocale") String orderDetails = "Order number: " + orderInfor.getOrderId() + ".\nTotal amount: $"+ String.format("%.2f",total) + ".\nOrder date: "+ dateOrder;
        String goodbye = "\n\nYour order will be deliver within 24 hours. \n\nKindest regards, Thank you!\n From FarmFresh with love.";
        return greeting + thankMessage + orderDetails + goodbye;
    }


}