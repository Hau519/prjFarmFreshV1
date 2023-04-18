package com.example.prjfarmfreshv1.ui.home;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prjfarmfreshv1.AdminProfileActivity;
import com.example.prjfarmfreshv1.ClientActivity;
import com.example.prjfarmfreshv1.ProfileActivity;
import com.example.prjfarmfreshv1.R;
import com.example.prjfarmfreshv1.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener, ValueEventListener {
    EditText edEmail,edPassword;
    Button btnLogIn,btnRegister;
    DatabaseReference databaseReference;
    ActivityResultLauncher activityResultLauncher;

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
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result -> {
            if (result.getResultCode() == RESULT_OK&&result.getData()!=null) {
               fillUserInfo(result);
            }
        });

    }

    private void fillUserInfo(ActivityResult result) {
        User user =(User) result.getData().getExtras().getSerializable("user");
        edEmail.setText(user.getEmail());
        edPassword.setText(user.getPassword());
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
        activityResultLauncher.launch(new Intent(this, Register.class));
    }


    private void login() {
        String emailstr=edEmail.getText().toString();
        String email = emailstr.replace(".","DOT");
        String password=edPassword.getText().toString();
        if(email.isEmpty()){
            edEmail.setError("Email is required");
        } else if (password.isEmpty()){
            edPassword.setError("Password is required");
        }else{
            databaseReference.child(email).addValueEventListener(this);
        }
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        String email = edEmail.getText().toString();
        String password=edPassword.getText().toString();
        if(email.equalsIgnoreCase("admin@farmfresh.com") && password.equalsIgnoreCase("123")){
            Toast.makeText(this,"Welcome back Admin!",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this, AdminProfileActivity.class);
            i.putExtra("user", "admin");
            startActivity(i);
        }

        if(snapshot.exists()){
            //Toast.makeText(this, "email correct", Toast.LENGTH_SHORT).show();
            String getPassword=snapshot.child("password").getValue().toString();
//            String userId = snapshot.child("id").getValue().toString();
            String name = snapshot.child("name").getValue().toString();

            if(getPassword.equals(password)){
                Toast.makeText(this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(this, ClientActivity.class);
                User user = new User(name, email, password);
                String emailKey = email.replace(".", "DOT");
                i.putExtra("user", user);
                databaseReference.child(emailKey).removeEventListener(this);

                startActivity(i);
            }else{
                Toast.makeText(this,"Incorrect email or password",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Email not valid", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}