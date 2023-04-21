package com.example.prjfarmfreshv1.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prjfarmfreshv1.R;
import com.example.prjfarmfreshv1.models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity implements View.OnClickListener, ValueEventListener {
    Button btnRegister, btnLogIn;
    EditText edName, edEmail, edPassword;
    DatabaseReference userDatabase;

    TextView tvError;


    User user;
    String email;
    boolean userAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
    }

    private void initialize(){
        tvError = findViewById(R.id.tvErrorRegister);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnRegister.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
        edName = findViewById(R.id.edFName);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        userDatabase= FirebaseDatabase.getInstance().getReference("Users");

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btnRegister:
                tvError.setText("");
                register(view);
                break;
            case R.id.btnLogIn:
                startActivity(new Intent(this, Login.class));

                break;
        }
    }


    private void register(View view) {
        try{
            String name = edName.getText().toString();
            String emailstr = edEmail.getText().toString();
            email = emailstr.replace(".","DOT");
            String password = edPassword.getText().toString();

            if(TextUtils.isEmpty(name)||TextUtils.isEmpty(emailstr)||TextUtils.isEmpty(password)){
                tvError.setText("Please fill all the fields!");
            }else{
                tvError.setText("");
                user = new User(name, emailstr, password);
                userDatabase.child(email).addValueEventListener(this);
            }
        }catch(Exception e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }


    }


    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        if(snapshot.exists()){
            if (!userAdded) {
                tvError.setText("This email already exist.");
            }
        }else{
            tvError.setText("");
            userDatabase.child(email).setValue(user);
            userAdded=true;
            Toast.makeText(this, "Register success!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Login.class);
            i.putExtra("user", user);
            setResult(RESULT_OK, i);
            userDatabase.child(email).removeEventListener(this);
            finish();

        }

    }


    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}