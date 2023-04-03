package com.example.prjfarmfreshv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prjfarmfreshv1.models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvName, tvEmail;
    Button btnUpdate, btnShop;

    ImageView icOrderList, iconLogout;
    User user;

    DatabaseReference usersTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initialize();
        fillInformation();
    }

    private void fillInformation() {
        user = (User)getIntent().getExtras().getSerializable("user");
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
    }

    private void initialize() {
        tvName = findViewById(R.id.tvName);
        tvName.setEnabled(false);
        tvEmail = findViewById(R.id.tvEmail);
        tvEmail.setEnabled(false);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnShop = findViewById(R.id.btnShopNow);
        btnShop.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        usersTable= FirebaseDatabase.getInstance().getReference("Users");
        iconLogout = findViewById(R.id.icLogOut);
        icOrderList = findViewById(R.id.icOrderList);
        iconLogout.setOnClickListener(this);
        icOrderList.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnShopNow:
                Intent i = new Intent(this, MainActivity.class); //TODO : change to product activity when Luke's done
                i.putExtra("user", user);
                startActivity(i);
                break;
            case R.id.btnUpdate:
                try{
                    if (btnUpdate.getText().toString().equals("Update")){
                        tvEmail.setEnabled(true);
                        tvName.setEnabled(true);
                        btnUpdate.setText("Save");
                    }else if(btnUpdate.getText().toString().equals("Save")){
                        String email = tvEmail.getText().toString();
                        String name = tvName.getText().toString();
                        User updateUser = new User(user.getId(), name, email, user.getPassword());
                        usersTable.child(String.valueOf(user.getId())).setValue(updateUser);
                        Snackbar.make(view, "Your information is updated succesffully",
                                Snackbar.LENGTH_LONG).show();
                        tvEmail.setEnabled(false);
                        tvName.setEnabled(false);
                        btnUpdate.setText("Update");
                    }
                }catch(Exception ex){
                    Snackbar.make(view, ex.getMessage(),
                            Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.icLogOut:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.icOrderList:
                Intent intent1 = new Intent(this, OrderListActivity.class);
                intent1.putExtra("user", user);
                startActivity(intent1);
                break;

        }
    }
}