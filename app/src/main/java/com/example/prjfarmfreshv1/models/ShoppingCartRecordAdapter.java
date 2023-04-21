package com.example.prjfarmfreshv1.models;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.prjfarmfreshv1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShoppingCartRecordAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ShoppingCartRecord> scRecordList;

    private int type=0;

    public ShoppingCartRecordAdapter(Context context, ArrayList<ShoppingCartRecord> scRecordList) {
        this.context = context;
        this.scRecordList = scRecordList;
    }
    public ShoppingCartRecordAdapter(Context context, ArrayList<ShoppingCartRecord> scRecordList, int type) {
        this.context = context;
        this.scRecordList = scRecordList;
        this.type = type;
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
        Button btnEdit;
        ImageButton imPlus, imMinus;

        //1-inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        oneItem = inflater.inflate(R.layout.shopping_cart_item, parent, false);

        // 2-- Access and populate each widget of this view : OneItem
        imProduct = oneItem.findViewById(R.id.imProduct);
        tvProductName = oneItem.findViewById(R.id.tvProductName);
        tvProductPrice = oneItem.findViewById(R.id.tvProductPrice);
        tvProductTotal = oneItem.findViewById(R.id.tvProductTotal);

        edProductQuantity = oneItem.findViewById(R.id.edProductQuantity);
        ShoppingCartRecord scRecord= scRecordList.get(i);

        //find photo by url in cloud
        String urlPhoto = scRecord.getProductPhoto();
        Picasso.with(context).load(urlPhoto).into(imProduct);

        tvProductName.setText(scRecord.getProductName());
        int productQuantity = scRecord.getProductQuantity();
        edProductQuantity.setText(productQuantity+"");
        float price = scRecord.getProductPrice();
        tvProductPrice.setText(price+"");
        tvProductTotal.setText(scRecord.getProductTotal()+"");

        imPlus = oneItem.findViewById(R.id.imPlus);
        imPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.valueOf(edProductQuantity.getText().toString());
                edProductQuantity.setText(quantity+1+"");

            }
        });

        imMinus = oneItem.findViewById(R.id.imMinus);
        imMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.valueOf(edProductQuantity.getText().toString());
                if (quantity==1) {
                    Toast.makeText(context, "Quantity must be more than 0, cannot Minus!", Toast.LENGTH_SHORT).show();
                }else
                    edProductQuantity.setText(quantity-1+"");

            }
        });



        btnEdit = oneItem.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnEdit.getTag().toString().equalsIgnoreCase("edit")) {
                    edProductQuantity.setEnabled(true);

                    imMinus.setVisibility(View.VISIBLE);
                    imPlus.setVisibility(View.VISIBLE);
                    btnEdit.setTag("save");

                    btnEdit.setBackgroundResource(R.drawable.ic_shopping_cart_save);
                }else if (btnEdit.getTag().toString().equalsIgnoreCase("save")) {


                    edProductQuantity.setEnabled(false);
                    imMinus.setVisibility(View.INVISIBLE);
                    imPlus.setVisibility(View.INVISIBLE);
                    btnEdit.setTag("edit");
                    btnEdit.setBackgroundResource(R.drawable.edit_btn);
                    try{
                        int newQuantity = Integer.valueOf(edProductQuantity.getText().toString());
                        float newTotal = newQuantity * price;
                        tvProductTotal.setText(String.format("%.2f", newTotal));
                        scRecord.setProductQuantity(newQuantity);
                        scRecord.setProductTotal(newTotal);

                        Intent intent = new Intent("changedScRecordList");
                        intent.putExtra("newScRecordList", scRecordList);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                    }catch(Exception e){
                        Log.d("ShoppingCartRecordAdapter", e.toString());
                    }

                }

            }
        });

        if (this.type==1){
            btnEdit.setVisibility(View.GONE);
        }


/*
        btnRemove = oneItem.findViewById(R.id.btnRemoveProduct);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                scRecordList.remove(i);
                Intent intent = new Intent("selectedOneShoppingCartRecord");
                intent.putExtra("scRecord", scRecordList.get(i));
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
*/

        return oneItem;
    }
}
