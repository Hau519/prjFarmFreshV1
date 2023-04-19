package com.example.prjfarmfreshv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.prjfarmfreshv1.models.ClientListAdapter;
import com.example.prjfarmfreshv1.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminClientActivity extends AppCompatActivity implements View.OnClickListener {

    Button orderList, btnReturn;
    ListView lvClient;
    DatabaseReference usersDatabase;
    User user;
    ArrayList<User> clientList;
    ClientListAdapter clientListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_client);
        initialize();
    }

    private void initialize() {
        orderList = findViewById(R.id.btnOrderList);
        btnReturn = findViewById(R.id.btnReturn);
        lvClient = findViewById(R.id.lvClients);

        usersDatabase= FirebaseDatabase.getInstance().getReference("Users");
        clientList = new ArrayList<>();
        clientListAdapter = new ClientListAdapter(this, clientList);
        lvClient.setAdapter(clientListAdapter);

        usersDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User value = (User)snapshot.getValue(User.class);
                clientList.add(value);
                clientListAdapter.notifyDataSetChanged();

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}