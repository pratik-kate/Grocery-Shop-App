package com.supertrident.ecom.test.fragmets;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.supertrident.ecom.R;
import com.supertrident.ecom.test.MainActivity;
import com.supertrident.ecom.test.adapter.CartAdapter;
import com.supertrident.ecom.test.models.CartModel;

import java.util.ArrayList;
import java.util.Set;


public class CartFragment extends Fragment {

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    ArrayList<CartModel> items;
    RecyclerView list;
    TextView textView,placeOrder;
    CartAdapter adapter;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);
       textView = view.findViewById(R.id.textView3);

        try {
            SharedPreferences pref = getActivity().getSharedPreferences(MainActivity.CART, Context.MODE_PRIVATE);
            for (int i = 1; i <= MainActivity.CARTCOUNTER; i++) {
                String s = MainActivity.PRODUCT+i;
                String im = MainActivity.PRODUCTIMAGE+i;
                String pr = MainActivity.PRODUCTPRICE+i;
                String q = MainActivity.PRODUCTQUANTITY+i;

                name.add(pref.getString(s, "empty"));
                price.add(String.valueOf(Integer.parseInt(pref.getString(pr, "0"))*Integer.parseInt(pref.getString(q,"0"))));
                image.add(pref.getString(im, "empty"));
                quantity.add(pref.getString(q, "empty"));
               // Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
           }
            list = view.findViewById(R.id.cartlist);
            items = new ArrayList<>();
            for(int i = 0 ; i< MainActivity.CARTCOUNTER; i++) {
                items.add(new CartModel(name.get(i), image.get(i), price.get(i), quantity.get(i)));
            }
            if(name.isEmpty()){
                textView.setVisibility(View.VISIBLE);
            }
            adapter = new CartAdapter(items,getContext());
            list.setAdapter(adapter);
            LinearLayoutManager layout = new LinearLayoutManager(getContext());
            list.setLayoutManager(layout);
        }catch (Exception e){
            textView.setText("Cart Empty");
        }
        placeOrder = view.findViewById(R.id.placeOrder);
        placeOrder.setOnClickListener(v -> {
            if(!name.isEmpty()) {
                items.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Your Order has Been Placed", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Please Add Items To Cart First", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}