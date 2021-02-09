package com.supertrident.onlinestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.supertrident.onlinestore.models.HomeModel;
import com.supertrident.onlinestore.R;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.viewHolder> {

    ArrayList<HomeModel> list;
    Context context;

    public HomeAdapter(ArrayList<HomeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.design_home,
                parent,
                false
        );



        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        //we sets the data here
        HomeModel model = list.get(position);

        holder.image.setImageResource(model.getImage());
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());

        switch(position)
        {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            {
               holder.itemView.setOnClickListener(v -> {
                   Toast.makeText(context, "item Clicked"+position, Toast.LENGTH_SHORT).show();
               });
                break;
            }
            default:
            {
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,price,shop;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            shop = itemView.findViewById(R.id.shop);
        }
    }
}
