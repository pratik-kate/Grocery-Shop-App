package com.supertrident.ecom.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.supertrident.ecom.R;
import com.supertrident.ecom.test.models.CartModel;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    ArrayList<CartModel> list;
    Context context;

    public CartAdapter(ArrayList<CartModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_cart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CartModel model = list.get(position);
        holder.name.setText(model.getName());
        holder.quantity.setText(model.getQuantity());
        holder.price.setText(model.getPrice());
        Glide.with(context)
                .load(model.getImage())
                .override(250,250)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,price,quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image= itemView.findViewById(R.id.cimage);
            name = itemView.findViewById(R.id.cname);
            price = itemView.findViewById(R.id.cprice);
            quantity = itemView.findViewById(R.id.cquantity);

        }
    }
}
