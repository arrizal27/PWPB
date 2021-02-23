package com.me.warehouse.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.me.warehouse.Model.Product;
import com.me.warehouse.R;
import com.me.warehouse.db.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Product> listProduct;
    private ArrayList<Product> mArrayList;

    private DBHelper mDatabase;

    public ProductAdapter(Context context, ArrayList<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
        this.mArrayList=listProduct;
        mDatabase = new DBHelper(context);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product Product = listProduct.get(position);

        holder.name.setText(Product.getName());
        holder.desc.setText(Product.getDesc());
        holder.quan.setText(Product.getQuantity());
        holder.tanggal.setText(Product.getDate());

        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(Product);
            }
        });

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteContact(Product.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    listProduct = mArrayList;
                } else {

                    ArrayList<Product> filteredList = new ArrayList<>();

                    for (Product Product : mArrayList) {

                        if (Product.getName().toLowerCase().contains(charString)) {

                            filteredList.add(Product);
                        }
                    }

                    listProduct = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listProduct;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listProduct = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }


    private void editTaskDialog(final Product Product){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.form, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText descField = (EditText)subView.findViewById(R.id.enter_desc);
        final EditText quanField = (EditText)subView.findViewById(R.id.enter_quan);

        if(Product != null){
            nameField.setText(Product.getName());
            descField.setText(String.valueOf(Product.getDesc()));
            quanField.setText(String.valueOf(Product.getQuantity()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Product");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String desc = descField.getText().toString();
                final String quan = quanField.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(context, "Terjadi kesalahan. Mohon Periksa Kembali Data ", Toast.LENGTH_LONG).show();
                }
                else{
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    Date date = new Date();
                    final String tanggal = dateFormat.format(date);
                    mDatabase.updateProduct(new Product(Product.getId(), name, desc, quan,tanggal));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Dibatalkan", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
