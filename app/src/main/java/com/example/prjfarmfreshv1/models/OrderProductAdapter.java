package com.example.prjfarmfreshv1.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.prjfarmfreshv1.R;

import java.util.ArrayList;

public class OrderProductAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<OrderProduct> orderProductList;
    OrderProduct orderProduct;

    public OrderProductAdapter(Context context, ArrayList<OrderProduct> orderProductList) {
        this.context = context;
        this.orderProductList = orderProductList;
    }

    @Override
    public int getCount() {
        return orderProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View oneItem = null;

        TextView tvName, tvUnitPrice, tvQuanity, tvTotal;

        //1-inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        oneItem = inflater.inflate(R.layout.order_details_item, parent, false);

        // 2-- Access and populate each widget of this view : OneItem
        tvName = oneItem.findViewById(R.id.tvName);
        tvUnitPrice = oneItem.findViewById(R.id.tvUnitPrice);
        tvQuanity = oneItem.findViewById(R.id.tvQuantity);
        tvTotal = oneItem.findViewById(R.id.tvTotal);
        orderProduct=orderProductList.get(position);
        tvName.setText(orderProduct.getProductName());
        tvUnitPrice.setText(String.valueOf(orderProduct.getUnitPrice()));
        tvQuanity.setText(String.valueOf(orderProduct.getQuantity()));

        tvTotal.setText(String.valueOf(orderProduct.getProductTotal()));
        return oneItem;

    }
}
