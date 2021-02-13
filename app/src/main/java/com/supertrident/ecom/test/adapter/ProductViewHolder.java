package com.supertrident.ecom.test.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.supertrident.ecom.R;
import com.supertrident.ecom.test.MainActivity;
import com.supertrident.ecom.test.ProductDetailActivity;
import com.supertrident.ecom.test.ShopActivity;
import com.supertrident.ecom.test.models.HomeModel;
import com.supertrident.ecom.test.models.ProductModel;

import java.util.ArrayList;

public class ProductViewHolder extends RecyclerView.Adapter<ProductViewHolder.ViewHolder> {

    private  static  final String tag = "Recycler";
    private Context context;
    private ArrayList<ProductModel> modelArrayList;

    public ProductViewHolder(Context context, ArrayList<ProductModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }


    @NonNull
    @Override
    public ProductViewHolder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.design_home,parent,false);


        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(modelArrayList.get(position).getName());
        holder.price.setText(modelArrayList.get(position).getPrice());

        Glide.with(context)
                .load(modelArrayList.get(position).getImage())
                .override(250,250)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent product = new Intent(context, ProductDetailActivity.class);
                product.setType("text");
                product.putExtra(MainActivity.PID,modelArrayList.get(position).getName());
                product.putExtra(MainActivity.PDEC,modelArrayList.get(position).getDescription());
                product.putExtra(MainActivity.PIMG,modelArrayList.get(position).getImage());
                product.putExtra(MainActivity.PPRICE,modelArrayList.get(position).getPrice());
                context.startActivity(product);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        ImageView imageView;
        TextView textView,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            textView = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.price);

        }
    }



}
