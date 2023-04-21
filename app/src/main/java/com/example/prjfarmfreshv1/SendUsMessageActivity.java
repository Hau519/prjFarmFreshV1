package com.example.prjfarmfreshv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prjfarmfreshv1.models.MailSender;
import com.example.prjfarmfreshv1.models.User;
import com.google.android.material.snackbar.Snackbar;

public class SendUsMessageActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edName, edContent;

    TextView tvTitle;
    Button btnHome, btnSend;
    User user=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_us_message);
        initialize();
    }

    private void initialize() {
        edName = findViewById(R.id.edName);
        edContent = findViewById(R.id.edContent);
        btnSend = findViewById(R.id.btnSend);
        btnHome = findViewById(R.id.btnHome);
        tvTitle = findViewById(R.id.tvTitle);

        btnSend.setOnClickListener(this);
        btnHome.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent.hasExtra("user")){
            user = (User)getIntent().getExtras().getSerializable("user");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnSend:
                if (btnSend.getText().toString().equalsIgnoreCase("Send")){
                    String name = edName.getText().toString();
                    String content = edContent.getText().toString();
                    sendEmail(name, content);
                    edName.setVisibility(View.INVISIBLE);
                    edContent.setVisibility(View.INVISIBLE);
                    tvTitle.setText("Thank you! We got your message!");
                    btnSend.setText("Send another message");
                } else {
                    Intent intent1 = new Intent(this, SendUsMessageActivity.class);
                    intent1.putExtra("user", user);
                    startActivity(intent1);
                }
                break;
            case R.id.btnHome:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
        }
    }

    private void sendEmail(String name, String content) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String subject = "A message from " + name;
                    String body = "Client name/email: " + name + ".\nMessage: " + content;
                    MailSender sender = new MailSender();
                    sender.sendMail(subject, body,
                            "farmfresh.lasalle@gmail.com", "farmfresh.client@gmail.com");
                } catch (Exception e) {
                    Toast.makeText(SendUsMessageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }).start();
    }
}