package com.example.prjfarmfreshv1.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.prjfarmfreshv1.R;

import java.util.ArrayList;

public class ClientListAdapter extends BaseAdapter {
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

        //1-inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        oneItem = inflater.inflate(R.layout.order_details_item, parent, false);

        // 2-- Access and populate each widget of this view : OneItem
        tvName = oneItem.findViewById(R.id.tvName);
        tvEmail = oneItem.findViewById(R.id.tvEmail);
        user = clientList.get(position);
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        return oneItem;
    }
}
