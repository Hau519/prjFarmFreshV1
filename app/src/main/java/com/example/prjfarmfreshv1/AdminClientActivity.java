package com.example.prjfarmfreshv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class AdminClientActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, DialogInterface.OnClickListener {

    Button orderList, btnReturn;
    ListView lvClient;
    DatabaseReference usersDatabase;
    User user;
    ArrayList<User> clientList;
    ClientListAdapter clientListAdapter;
    AlertDialog.Builder alertD;
    int position=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_client);
        initialize();
    }

    private void initialize() {
        orderList = findViewById(R.id.btnOrderList);
        btnReturn = findViewById(R.id.btnReturnAdminClientList);
        lvClient = findViewById(R.id.lvClients);

        lvClient.setOnItemClickListener(this);
        setDeleteAlert();
        btnReturn.setOnClickListener(this);

        usersDatabase= FirebaseDatabase.getInstance().getReference("Users");
        clientList = new ArrayList<>();
        clientListAdapter = new ClientListAdapter(this, clientList);
        lvClient.setAdapter(clientListAdapter);
        clientListAdapter.notifyDataSetChanged();

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

        Intent i = new Intent(this, AdminProfileActivity.class);
        i.putExtra("admin", "admin");
        startActivity(i);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        position = i;
        alertD.create().show();
    }

    private void setDeleteAlert() {
        alertD = new AlertDialog.Builder(this);
        alertD.setTitle("Remove Client");
        alertD.setMessage("Do you want to remove (Yes/NO)?");
        alertD.setPositiveButton("Yes", this);
        alertD.setNegativeButton("No",this);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                clientListAdapter.notifyDataSetChanged();
                String email = clientList.get(position).getEmail();
                String emailKey = email.replace(".", "DOT");
                usersDatabase.child(emailKey).removeValue();
                clientList.remove(position);
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
        }
    }
}