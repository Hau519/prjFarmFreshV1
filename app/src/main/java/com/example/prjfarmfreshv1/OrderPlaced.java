package com.example.prjfarmfreshv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prjfarmfreshv1.models.User;

public class OrderPlaced extends AppCompatActivity implements View.OnClickListener{
    TextView tvMessage;
    Button btnHomePage, btnProfile, btnLogout;
    User user;
    String orderNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        initialize();

    }

    private void initialize() {
        tvMessage = findViewById(R.id.tvMessage);
        btnHomePage = findViewById(R.id.btnBackToHome);
        btnProfile = findViewById(R.id.btnBackToProfile);

        user = (User)getIntent().getExtras().getSerializable("user");
        orderNumber = getIntent().getExtras().getString("orderNumber");
        tvMessage.setText("Order number " + orderNumber.substring(0, 5));

        btnHomePage.setOnClickListener(this);
        btnProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnBackToHome:
                try{
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("user", user);
                    startActivity(i);
                }catch (Exception ex){
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.icLogOut:
                try{
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                }catch (Exception ex){
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnBackToProfile:
                try{
                    Intent i = new Intent(this, ClientActivity.class);
                    i.putExtra("user", user);
                    startActivity(i);
                }catch (Exception ex){
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}