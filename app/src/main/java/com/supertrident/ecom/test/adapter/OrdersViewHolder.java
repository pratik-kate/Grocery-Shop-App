package com.supertrident.ecom.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.supertrident.ecom.R;
import com.supertrident.ecom.test.models.OrderModel;

import java.util.ArrayList;


public class OrdersViewHolder extends RecyclerView.Adapter<OrdersViewHolder.ViewHolder> {

    private  static  final String tag = "Recycler";
    private Context context;
    private ArrayList<OrderModel> modelArrayList;
    String textproduct="";

    public OrdersViewHolder(Context context, ArrayList<OrderModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }


    @NonNull
    @Override
    public OrdersViewHolder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.design_orders,parent,false);


        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String add = modelArrayList.get(position).getAddress() + " " + modelArrayList.get(position).getPincode();

        String products = modelArrayList.get(position).getProducts();
        String[] arr = products.split(";");
        for(String w:arr){
                textproduct += w +"\n";
        }
        holder.no.setText(modelArrayList.get(position).getNo());
        holder.name.setText(modelArrayList.get(position).getName());
        holder.address.setText(add);
        holder.phone.setText(modelArrayList.get(position).getPhone());
        holder.landmark.setText(modelArrayList.get(position).getLandmark());
        holder.amount.setText(modelArrayList.get(position).getAmount());
        holder.products.setText(textproduct);
        textproduct="";
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView no,name,phone,address,landmark,amount,products;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.orderno);
            name = itemView.findViewById(R.id.cname);
            phone = itemView.findViewById(R.id.cphone);
            address = itemView.findViewById(R.id.caddress);
            landmark = itemView.findViewById(R.id.clandmark);
            amount = itemView.findViewById(R.id.amount);
            products = itemView.findViewById(R.id.products);


        }
    }



}
