package com.example.prjfarmfreshv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

    OrderInfor orderInfor;
    OrderProduct orderProduct;

    DatabaseReference orderListDatabase;
    DatabaseReference orderProductDatabase;

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

        user = (User)getIntent().getExtras().getSerializable("user");
        orderInfor = (OrderInfor )getIntent().getExtras().getSerializable("order");

        total = orderInfor.getTotal();
        tvTotal.setText(String.format("%.2f",total));
        Date date = new Date();
        dateOrder = orderInfor.getDate();
        tvDate.setText(dateOrder);

      /*  shoppingCartList = (ArrayList<ShoppingCartRecord>) getIntent().getExtras().getSerializable("shoppingCartList");
        scAdapter = new ShoppingCartRecordAdapter(this, shoppingCartList);
        lvProducts.setAdapter(scAdapter);*/
    }

    @Override
    public void onClick(View v) {

    }
}