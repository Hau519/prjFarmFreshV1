package com.example.prjfarmfreshv1.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prjfarmfreshv1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShoppingCartRecordAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ShoppingCartRecord> scRecordList;
    ShoppingCartRecord scRecord;


    public ShoppingCartRecordAdapter(Context context, ArrayList<ShoppingCartRecord> scRecordList) {
        this.context = context;
        this.scRecordList = scRecordList;

    }

    @Override
    public int getCount() {
        return scRecordList.size();
    }

    @Override
    public Object getItem(int i) {
        return scRecordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View oneItem = null;

        TextView tvProductName, tvProductPrice,tvProductTotal;
        ImageView imProduct;
        EditText edProductQuantity;
        Button btnRemove;


        //1-inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        oneItem = inflater.inflate(R.layout.shopping_cart_item, parent, false);

        // 2-- Access and populate each widget of this view : OneItem
        imProduct = oneItem.findViewById(R.id.imProduct);
        tvProductName = oneItem.findViewById(R.id.tvProductName);
        tvProductPrice = oneItem.findViewById(R.id.tvProductPrice);
        tvProductTotal = oneItem.findViewById(R.id.tvProductTotal);

        edProductQuantity = oneItem.findViewById(R.id.edProductQuantity);
        scRecord = scRecordList.get(i);

        String urlPhoto = scRecord.getProductPhoto();


        //find photo by url in cloud
        Picasso.with(context).load(urlPhoto).into(imProduct);

        tvProductName.setText(scRecord.getProductName());
        int productQuantity = scRecord.getProductQuantity();
        edProductQuantity.setText(productQuantity+"");
        float price = scRecord.getProductPrice();
        tvProductPrice.setText(price+"");
        tvProductTotal.setText(scRecord.getProductTotal()+"");



        btnRemove = oneItem.findViewById(R.id.btnRemoveProduct);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scRecordList.remove(i);

            }
        });

        return oneItem;
    }
}
