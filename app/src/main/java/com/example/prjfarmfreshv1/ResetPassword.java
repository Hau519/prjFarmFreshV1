package com.example.prjfarmfreshv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prjfarmfreshv1.models.MailSender;
import com.example.prjfarmfreshv1.models.OrderProduct;
import com.example.prjfarmfreshv1.models.User;
import com.example.prjfarmfreshv1.ui.home.Login;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener, ValueEventListener {
    EditText edEmail, edCode;
    Button btnClick, btnLogin;
    TextView tvError;
    User user=null;

    int code, inputCode;
    String email, emailKey, newPassword;
    DatabaseReference usersTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initialize();
    }

    private void initialize() {
        edEmail = findViewById(R.id.edEmail);
        edCode = findViewById(R.id.edCodeAndPassword);
        tvError = findViewById(R.id.tvError);
        btnClick = findViewById(R.id.btnClick);
        btnLogin = findViewById(R.id.btnLogin);
//        user = (User) getIntent().getExtras().getSerializable("user");
        usersTable= FirebaseDatabase.getInstance().getReference("Users");
        btnClick.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnClick:

                try {
                    if (btnClick.getText().toString().equalsIgnoreCase("Get code")) {
                        email = edEmail.getText().toString();
                        emailKey = email.replace(".", "DOT");
                        if (email.isEmpty()){
                            tvError.setText("Please fill in your email");
                        }else{
                            tvError.setText("");
                            usersTable.child(emailKey).addValueEventListener(this);
                        }
                    }else if(btnClick.getText().toString().equalsIgnoreCase("Confirm code")){
                        inputCode = Integer.valueOf(edCode.getText().toString());
                        usersTable.child(emailKey).removeEventListener(this);
                        tvError.setText("");
                        if (inputCode != code){
                            tvError.setText("Wrong code, please try again");
                        }else{
                            edEmail.setText(email);
                            edCode.setText("");
                            tvError.setText("");
                            edCode.setHint("Enter your new password");
                            btnClick.setText("Reset");
                        }
                    }else if(btnClick.getText().toString().equalsIgnoreCase("Reset")) {
                        newPassword = edCode.getText().toString();
                        user.setPassword(newPassword);
                        usersTable.child(emailKey).setValue(user);
                        tvError.setText("");
                        Snackbar.make(v, "Your password is reset successfully",
                                Snackbar.LENGTH_LONG).show();
                        Intent i = new Intent(this, Login.class);
                        i.putExtra("user", user);
                        setResult(RESULT_OK, i);
                        finish();

                    }
                } catch (Exception ex) {
                    Snackbar.make(v, ex.getMessage(),
                            Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.btnLogin:
                finish();
                break;
        }
    }

    private void sendCode(String email) {
        code = (int) Math.floor(Math.random() * (9999 - 1111 + 1) + 1111);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String subject = "FarmFresh - Your code to reset password";
                    String body = "Your one time reset password code is " + String.valueOf(code);
                    MailSender sender = new MailSender();
                    sender.sendMail(subject, body,
                            "farmfresh.lasalle@gmail.com", email);
                } catch (Exception e) {
                    Toast.makeText(ResetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }).start();

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
            user = (User) snapshot.getValue(User.class);
            sendCode(email);
            edCode.setVisibility(View.VISIBLE);
            btnClick.setText("Confirm Code");
            }else{
                tvError.setText("Email is not registered yet");
            }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}

