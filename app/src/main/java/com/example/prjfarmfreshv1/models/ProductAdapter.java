package com.example.prjfarmfreshv1.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.prjfarmfreshv1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Product> productList;
    Product product;

    public ProductAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
    }
    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View oneItem = null;
        ImageView imPhoto;
        TextView tvName, tvCategory, tvPrice,tvDesc;
        EditText edQuantity;
        Button btnAddToCart;


        //1-inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        oneItem = inflater.inflate(R.layout.product_one_item, parent, false);

        // 2-- Access and populate each widget of this view : OneItem
        tvName = oneItem.findViewById(R.id.tvProductName);
        tvCategory = oneItem.findViewById(R.id.tvCategory);
        tvDesc = oneItem.findViewById(R.id.tvDesc);
        tvPrice = oneItem.findViewById(R.id.tvPrice);
        imPhoto = oneItem.findViewById(R.id.imPhoto);
        edQuantity = oneItem.findViewById(R.id.edQuantity);
        product = productList.get(i);
        tvName.setText(product.getName());
        tvCategory.setText(product.getCategory());
        tvDesc.setText(product.getDescription());
        tvPrice.setText(product.getPrice()+"");
        tvCategory.setText(product.getCategory());

        String urlPhoto = product.getPhoto();


        //find photo by url in cloud
        Picasso.with(context).load(urlPhoto).into(imPhoto);

        btnAddToCart = oneItem.findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("selectedOneProduct");
                intent.putExtra("product", productList.get(i));
                intent.putExtra("quantity", Integer.valueOf(edQuantity.getText().toString()));
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);



            }
        });

        return oneItem;
    }
}
