package com.example.prjfarmfreshv1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.Toast;

import com.example.prjfarmfreshv1.models.Product;
import com.example.prjfarmfreshv1.models.ShoppingCartRecord;
import com.example.prjfarmfreshv1.models.ShoppingCartRecordAdapter;
import com.example.prjfarmfreshv1.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener, DialogInterface.OnClickListener {
    Button btnCheckout, btnCS;
    ListView lvCart;
    ShoppingCartRecordAdapter scAdapter;
    ArrayList<ShoppingCartRecord> shoppingCartList;
    TextView tvSubtotal,tvQST,tvGST, tvTotal;

    AlertDialog.Builder alertD;
    int position=-1;
    double total=0;

    ActivityResultLauncher arl;

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            shoppingCartList = ( ArrayList<ShoppingCartRecord>) intent.getExtras().getSerializable("newScRecordList");
            setCartTotalTable(shoppingCartList);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initialize();
    }

    public void initialize(){
        //transfer data from Adapter
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("changedScRecordList"));


        btnCheckout = findViewById(R.id.btnCheckout);
        btnCS = findViewById(R.id.btnCS);
        btnCheckout.setOnClickListener(this);
        btnCS.setOnClickListener(this);

        lvCart = findViewById(R.id.lvCart);





        shoppingCartList = (ArrayList<ShoppingCartRecord>) getIntent().getExtras().getSerializable("selectedProducts");
        scAdapter = new ShoppingCartRecordAdapter(this, shoppingCartList);
        lvCart.setAdapter(scAdapter);

        lvCart.setOnItemLongClickListener(this);


        setCartTotalTable(shoppingCartList);
        setDeleteAlert();
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {


        });

    }

    private void setCartTotalTable( ArrayList<ShoppingCartRecord> shoppingCartList) {
        tvSubtotal= (TextView) findViewById(R.id.tvCartSubtotal);
        tvQST= (TextView) findViewById(R.id.tvQST);
        tvGST= (TextView) findViewById(R.id.tvGST);
        tvTotal= (TextView) findViewById(R.id.tvTotal);

        double subtotal = getSubtotal(shoppingCartList);
        tvSubtotal.setText(String.format("%.2f",subtotal));
        double QST = subtotal * 0.10;
        tvQST.setText(String.format("%.2f",QST));
        double GST = subtotal * 0.05;
        tvGST.setText(String.format("%.2f",GST));
        total = subtotal + QST + GST;
        tvTotal.setText(String.format("%.2f",total));



    }

    private float getSubtotal(ArrayList<ShoppingCartRecord> shoppingCartList) {
        float subtotal = 0;
        for (ShoppingCartRecord shoppingCartRecord : shoppingCartList) {
            subtotal += shoppingCartRecord.getProductTotal();
        }
        return subtotal;
    }
    private void setDeleteAlert() {
        alertD = new AlertDialog.Builder(this);
        alertD.setTitle("Remove Product");
        alertD.setMessage("Do you want to remove (Yes/NO)?");
        alertD.setPositiveButton("Yes", this);
        alertD.setNegativeButton("No",this);
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {

        position = i;
        alertD.create().show();
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                shoppingCartList.remove(position);
                scAdapter.notifyDataSetChanged();
                setCartTotalTable(shoppingCartList);
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        int vId = view.getId();
        switch (vId) {
            case R.id.btnCS:
                continueShopping();
                break;
            case R.id.btnCheckout:
                checkOut();
                break;
        }
    }



    private void continueShopping() {
        Intent i = new Intent();
        i.putExtra("returnedScRecordList", shoppingCartList);
        setResult(RESULT_OK,i);
        finish();
    }

    private void checkOut() {
        Intent intent = new Intent(this, Payment.class);
        User user = (User) getIntent().getExtras().getSerializable("user");
        intent.putExtra("user", user);
        intent.putExtra("total", total);
        intent.putExtra("shoppingCartList",shoppingCartList);
        Toast.makeText(this, user.toString()+"\n"+shoppingCartList.toString(), Toast.LENGTH_SHORT).show();
       arl.launch(intent);
    }



}