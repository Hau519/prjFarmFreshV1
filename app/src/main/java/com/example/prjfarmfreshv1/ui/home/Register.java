package com.example.prjfarmfreshv1.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prjfarmfreshv1.R;
import com.example.prjfarmfreshv1.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity implements View.OnClickListener, ValueEventListener {
    Button btnRegister, btnLogIn;
    EditText edFName, edEmail, edPassword;
    DatabaseReference userDatabase;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
    }

    private void initialize(){
        btnRegister = findViewById(R.id.btnRegister);
        btnLogIn = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
        edFName = findViewById(R.id.edFName);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        userDatabase= FirebaseDatabase.getInstance().getReference("Users");

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btnRegister:
                register(view);
                break;
            case R.id.btnLogIn:
                startActivity(new Intent(this, Login.class));

                break;
        }
    }


    private void register(View view) {
        try{
            String name = edFName.getText().toString();
            String emailstr = edEmail.getText().toString();
            String newEmail = emailstr.substring(emailstr.indexOf("."), emailstr.length()-0);
            String email = emailstr.replace(newEmail,"");
            String password = edPassword.getText().toString();

            if(TextUtils.isEmpty(name)){
                Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
            }
            if(TextUtils.isEmpty(emailstr)){
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            }

            User user = new User(name, emailstr, password);
            userDatabase.child(email).setValue(user);

            Snackbar.make(view, "User has registered successfully", Snackbar.LENGTH_LONG).show();


        }catch(Exception e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }


    }


    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
            Toast.makeText(this, "This email already exist.", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}