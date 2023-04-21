package com.example.prjfarmfreshv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class AdminProfileActivity extends AppCompatActivity implements View.OnClickListener {
    Button clientList, orderList;
    ImageView icLogout;
    String admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        clientList = findViewById(R.id.btnClientList);
        orderList = findViewById(R.id.btnOrderList);
        icLogout = findViewById(R.id.icLogOutAdmin);

        clientList.setOnClickListener(this);
        orderList.setOnClickListener(this);
        icLogout.setOnClickListener(this);


        admin = getIntent().getExtras().getString("admin");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnClientList:
                try{
                    Intent i = new Intent(this, AdminClientActivity.class);
                    i.putExtra("user", admin);
                    startActivity(i);
                }catch (Exception ex){
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnOrderList:
                try{
                    Intent i = new Intent(this, OrderListActivity.class);
                    i.putExtra("admin", "adminAllUsers");
                    startActivity(i);
                }catch (Exception ex){
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.icLogOutAdmin:
                Intent i = new Intent(this, MainActivity.class);
                Toast.makeText(this, "You have successfully log out", Toast.LENGTH_SHORT).show();
                startActivity(i);
        }
    }


}