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
import com.supertrident.ecom.test.models.HomeModel;

import java.util.ArrayList;

public class MenuViewHolder extends RecyclerView.Adapter<MenuViewHolder.ViewHolder> {

    private  static  final String tag = "Recycler";
    private Context context;
    private ArrayList<HomeModel>  modelArrayList;

    public MenuViewHolder(Context context, ArrayList<HomeModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }


    @NonNull
    @Override
    public MenuViewHolder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.design_categories,parent,false);


        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(modelArrayList.get(position).getName());

        Glide.with(context)
                .load(modelArrayList.get(position).getImageUrl())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.catImage);
            textView = itemView.findViewById(R.id.catName);

        }
    }



}
