package com.example.prjfarmfreshv1.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.prjfarmfreshv1.R;

import java.util.ArrayList;

public class OrderListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<OrderInfor> orderInforList;
    OrderInfor orderInfor;
    String orderNumberDisplay;
    public OrderListAdapter(Context context, ArrayList<OrderInfor> orderInforList) {
        this.context = context;
        this.orderInforList = orderInforList;
    }

    @Override
    public int getCount() {
        return orderInforList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderInforList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View oneItem = null;

        TextView tvOrderNumber, tvDate, tvTotal;

        //1-inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        oneItem = inflater.inflate(R.layout.one_item, parent, false);

        // 2-- Access and populate each widget of this view : OneItem
        tvOrderNumber = oneItem.findViewById(R.id.tvOrderNumber);
        tvDate = oneItem.findViewById(R.id.tvDateAdminOrderDetails);
        tvTotal = oneItem.findViewById(R.id.tvTotalAdminDetailOrder);

        orderInfor = orderInforList.get(position);
        if (orderInfor.getOrderId().length()>5){
            orderNumberDisplay = orderInfor.getOrderId().substring(0,5);
        }
        tvOrderNumber.setText(orderNumberDisplay);
        tvDate.setText(orderInfor.getDate());
        tvTotal.setText(String.format("%.2f", orderInfor.getTotal()));
        return oneItem;

    }
}
