package com.example.prjfarmfreshv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Payment extends AppCompatActivity implements View.OnClickListener {
EditText edName,edCardNumber,edExpDate,edCVV;
Button btnPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        intialize();
    }

    private void intialize() {

        edName=findViewById(R.id.edName);
        edCardNumber=findViewById(R.id.edcardNumber);
        edExpDate=findViewById(R.id.edExpDate);
        edCVV=findViewById(R.id.edCVV);
        btnPay=findViewById(R.id.btnPay);
        btnPay.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        String cardno=edCardNumber.getText().toString();
        String name=edName.getText().toString();
        String expDate=edExpDate.getText().toString();
        String edC=edCVV.getText().toString();

        if(TextUtils.isEmpty(cardno)||TextUtils.isEmpty(name)||TextUtils.isEmpty(expDate)||TextUtils.isEmpty(edC)){
            Toast.makeText(this,"please fill all the fields",Toast.LENGTH_SHORT).show();
        }else{
            Intent i=new Intent(this,OrderPlaced.class);
            startActivity(i);
        }



    }
}