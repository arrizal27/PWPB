package com.me.warehouse.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.me.warehouse.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public TextView tanggal,name,desc,quan;
    public ImageView deleteProduct;
    public  ImageView editProduct;

    public ProductViewHolder(View itemView) {
        super(itemView);
        tanggal = (TextView)itemView.findViewById(R.id.date);
        name = (TextView)itemView.findViewById(R.id.name);
        desc = (TextView)itemView.findViewById(R.id.desc);
        quan = (TextView)itemView.findViewById(R.id.quan);
        deleteProduct = (ImageView)itemView.findViewById(R.id.img_delete);
        editProduct = (ImageView)itemView.findViewById(R.id.img_edit);
    }
}
