package com.example.prjfarmfreshv1.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prjfarmfreshv1.ProfileActivity;
import com.example.prjfarmfreshv1.R;
import com.example.prjfarmfreshv1.activity_Register;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {
EditText edEmail,edPassword;
Button btnLogIn,btnRegister;
DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
    }

    private void initialize() {
        edEmail=findViewById(R.id.edEmail);
        edPassword=findViewById(R.id.edPassword);
        btnLogIn=findViewById(R.id.btnLogIn);
        btnRegister=findViewById(R.id.btnRegister);
        btnLogIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");

    }


    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btnLogIn:
                login();
                break;
            case R.id.btnRegister:
                register();
                break;
        }
    }

    private void register() {
        Intent i = new Intent(this, activity_Register.class);
       startActivity(i);
    }

    private void login() {
        String email=edEmail.getText().toString();
        String password=edPassword.getText().toString();
        if(email.isEmpty()){
            edEmail.setError("Email is required");
        }
        if(password.isEmpty()){
            edPassword.setError("Password is required");
        }
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild(email)){

                    String getPassword=snapshot.child(email).child("Password").getValue(String.class);
                    if(getPassword.equals(password)){
                        Toast.makeText(Login.this,"logged in",Toast.LENGTH_LONG).show();

                        Intent i=new Intent(Login.this, ProfileActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(Login.this,"Incorrect email or password",Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}