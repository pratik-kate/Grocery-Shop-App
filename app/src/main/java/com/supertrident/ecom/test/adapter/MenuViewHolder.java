package com.supertrident.ecom.test.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.supertrident.ecom.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageView image;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image);
        name = itemView.findViewById(R.id.name);

    }
}
