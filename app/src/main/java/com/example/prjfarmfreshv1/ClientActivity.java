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

public class ClientActivity extends AppCompatActivity implements View.OnClickListener  {
    ImageView ivLogo;
    TextView tvName, tvEmail, tvTitle;
    Button btnUpdate, btnShop;

    ImageView icOrderList, iconLogout;
    User user;

    String admin="";
    DatabaseReference usersTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initialize();

    }

    private void fillInformation() {
        user = (User)getIntent().getExtras().getSerializable("user");
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        String welcome = "Welcome " + user.getName();
        tvTitle.setText(welcome);
    }

    private void initialize() {
        ivLogo = findViewById(R.id.ivLogo);
        tvName = findViewById(R.id.tvProductName);
        tvName.setEnabled(false);
        tvEmail = findViewById(R.id.tvEmail);
        tvEmail.setEnabled(false);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnShop = findViewById(R.id.btnShopNow);
        btnShop.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        usersTable= FirebaseDatabase.getInstance().getReference("Users");
        iconLogout = findViewById(R.id.icLogOutAdmin);
        icOrderList = findViewById(R.id.icOrderList);
        iconLogout.setOnClickListener(this);
        ivLogo.setOnClickListener(this);
        icOrderList.setOnClickListener(this);
        tvTitle = findViewById(R.id.tvTitle);
        fillInformation();
        Intent intent = getIntent();
        if (intent.hasExtra("admin")){
            admin = getIntent().getExtras().getString("admin");

        }
        if (!admin.isEmpty()){
            tvTitle.setText("Welcome admin!");
            iconLogout.setVisibility(View.INVISIBLE);
            btnShop.setText("Working station");
            user = (User) getIntent().getExtras().getSerializable("user");
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnShopNow:
                if (admin.isEmpty()){
                    Intent i = new Intent(this, ProductActivity.class);
                    i.putExtra("user", user);
                    startActivity(i);
                }else{
                    Intent i = new Intent(this, AdminClientActivity.class);
                    i.putExtra("admin", "admin");
                    startActivity(i);
                }
                break;
            case R.id.btnUpdate:
                try{
                    if (btnUpdate.getText().toString().equals("Update")){
//                        tvEmail.setEnabled(false);
                        tvName.setEnabled(true);
                        btnUpdate.setText("Save");
                    }else if(btnUpdate.getText().toString().equals("Save")){

                        String email = user.getEmail();
                        String name = tvName.getText().toString();
                        if (name.equals(user.getName())){
                            tvName.setEnabled(false);
                            btnUpdate.setText("Update");
                            Snackbar.make(view, "Nothing change",
                                    Snackbar.LENGTH_LONG).show();
                            break;
                        }

                        user.setName(name);
                        String emailKey = email.replace(".", "DOT");
                        usersTable.child(emailKey).setValue(user);
                        tvEmail.setEnabled(false);
                        if(admin.equalsIgnoreCase("")){
                            tvTitle.setText("Welcome " + name);
                            Snackbar.make(view, "Your information is updated successfully",
                                    Snackbar.LENGTH_LONG).show();
                        }else{
                            Snackbar.make(view, "The client information is updated successfully",
                                    Snackbar.LENGTH_LONG).show();
                        }
                        tvName.setEnabled(false);
                        btnUpdate.setText("Update");
                    }
                }catch(Exception ex){
                    Snackbar.make(view, ex.getMessage(),
                            Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.icLogOutAdmin:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.icOrderList:
                Intent intent1 = new Intent(this, OrderListActivity.class);
                intent1.putExtra("user", user);
                if (!admin.equalsIgnoreCase("")){
                    intent1.putExtra("admin", admin);
                }
                startActivity(intent1);
                break;
            case R.id.ivLogo:
                Intent intent2 =new Intent(this, MainActivity.class);
                intent2.putExtra("user", user);
                startActivity(intent2);
        }
    }
}