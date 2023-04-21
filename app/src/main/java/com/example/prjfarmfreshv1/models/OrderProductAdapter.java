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
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.prjfarmfreshv1.R;

import java.util.ArrayList;

public class OrderProductAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<OrderProduct> orderProductList;
    OrderProduct orderProduct;

    float totalChange=0;
    float originTotal;
    float newTotal;

    int state =0;

    public OrderProductAdapter(Context context, ArrayList<OrderProduct> orderProductList) {
        this.context = context;
        this.orderProductList = orderProductList;
    }

    public OrderProductAdapter(Context context, ArrayList<OrderProduct> orderProductList, int state) {
        this.context = context;
        this.orderProductList = orderProductList;
        this.state = state;
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
        Button btnEditOrderDetails;

        //1-inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        oneItem = inflater.inflate(R.layout.order_details_item, parent, false);

        // 2-- Access and populate each widget of this view : OneItem
        tvName = oneItem.findViewById(R.id.tvName);
        tvUnitPrice = oneItem.findViewById(R.id.tvUnitPrice);
        tvQuanity = oneItem.findViewById(R.id.tvQuantity);
        btnEditOrderDetails = oneItem.findViewById(R.id.btnEditOrderDetails);

        if (state!=1){
            btnEditOrderDetails.setVisibility(View.GONE);
        }

        tvTotal = oneItem.findViewById(R.id.tvTotalAdminDetailOrder);
        orderProduct=orderProductList.get(position);
        tvName.setText(orderProduct.getProductName());
        tvUnitPrice.setText(String.format("%.2f", orderProduct.getUnitPrice()));
        tvQuanity.setText(String.valueOf(orderProduct.getQuantity()));
        originTotal = Float.parseFloat(String.valueOf(orderProduct.getProductTotal()));
        tvTotal.setText(String.format("%.2f", originTotal));
        btnEditOrderDetails.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                    try{
                        if (btnEditOrderDetails.getText().toString().equalsIgnoreCase("edit")) {
                            tvQuanity.setEnabled(true);
                            tvQuanity.setClickable(true);
                            tvQuanity.setFocusableInTouchMode(true);
                            tvQuanity.setFocusable(true);

                            btnEditOrderDetails.setText("save");
                        }else if (btnEditOrderDetails.getText().toString().equalsIgnoreCase("save")) {
                            tvQuanity.setEnabled(false);
                            btnEditOrderDetails.setText("edit");
                            float newQuantity = Float.valueOf(tvQuanity.getText().toString());
                            float price = Float.parseFloat(tvUnitPrice.getText().toString());
                            newTotal = newQuantity * price;
                            tvTotal.setText(String.format("%.2f", newTotal));
                            orderProduct.setQuantity(newQuantity);
                            orderProduct.setProductTotal(newTotal);
                            tvQuanity.setClickable(false);
                            tvQuanity.setFocusableInTouchMode(false);
                            tvQuanity.setFocusable(false);
                            Intent intent = new Intent("changedOrderProductList");
                            intent.putExtra("newOrderProductList", orderProductList);
                            totalChange = totalChange + (newTotal - originTotal) *1.15f;
                            intent.putExtra("totalChange", totalChange);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }

                    }catch(Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

        });

        return oneItem;

    }
}
