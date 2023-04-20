package com.example.prjfarmfreshv1.models;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.prjfarmfreshv1.AdminClientActivity;
import com.example.prjfarmfreshv1.ClientActivity;
import com.example.prjfarmfreshv1.OrderActivity;
import com.example.prjfarmfreshv1.R;

import java.util.ArrayList;

public class ClientListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<User> clientList;
    User user;

    public ClientListAdapter(Context context, ArrayList<User> clientList) {
        this.context = context;
        this.clientList = clientList;
    }

    @Override
    public int getCount() {
        return clientList.size();
    }

    @Override
    public Object getItem(int position) {
        return clientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View oneItem = null;

        TextView tvName, tvEmail;
        Button btnDetails;

        //1-inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        oneItem = inflater.inflate(R.layout.admin_one_client, parent, false);

        // 2-- Access and populate each widget of this view : OneItem
        tvName = oneItem.findViewById(R.id.tvName);
        tvEmail = oneItem.findViewById(R.id.tvEmail);
        btnDetails = oneItem.findViewById(R.id.btnDetails);
        user = clientList.get(position);
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ClientActivity.class);
                user = clientList.get(position);
                i.putExtra("user",user);
                i.putExtra("admin", "admin");
                context.startActivity(i);
            }
        });

        return oneItem;
    }


}
