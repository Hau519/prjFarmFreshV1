package com.example.prjfarmfreshv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class activity_Register extends AppCompatActivity {
    Button btnLogIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnLogIn = findViewById(R.id.btnRegister);
    }
}