package com.supertrident.ecom.test.fragmets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.supertrident.ecom.R;
import com.supertrident.ecom.test.CheckOutActivity;
import com.supertrident.ecom.test.MainActivity;
import com.supertrident.ecom.test.adapter.CartAdapter;
import com.supertrident.ecom.test.models.CartModel;

import java.util.ArrayList;


public class CartFragment extends Fragment {

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    ArrayList<CartModel> items;
    RecyclerView list;
    TextView textView,placeOrder;
    CartAdapter adapter;
    SharedPreferences pref;

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
            pref = getActivity().getSharedPreferences(MainActivity.CART, Context.MODE_PRIVATE);

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
            if(name.get(0).contains("empty")){
                textView.setVisibility(View.VISIBLE);
            }else{
                for(int i = 0 ; i< MainActivity.CARTCOUNTER; i++) {
                    items.add(new CartModel(name.get(i), image.get(i), price.get(i), quantity.get(i)));
                }
                adapter = new CartAdapter(items,getContext());
                list.setAdapter(adapter);
                list.hasFixedSize();
                adapter.notifyDataSetChanged();
                LinearLayoutManager layout = new LinearLayoutManager(getContext());
                list.setLayoutManager(layout);
            }

        }catch (Exception e){
            textView.setText("Cart Empty");
        }
        placeOrder = view.findViewById(R.id.placeOrder);
        placeOrder.setOnClickListener(v -> {
            if(!name.isEmpty()) {
                //Placing Order

                Intent checkout = new Intent(getContext(), CheckOutActivity.class);
                checkout.putExtra(MainActivity.PRODUCT,name);
                checkout.putExtra(MainActivity.PRODUCTPRICE,price);
                checkout.putExtra(MainActivity.PRODUCTQUANTITY,quantity);
                startActivity(checkout);


                items.clear();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(MainActivity.CART,Context.MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                editor.commit();
                adapter.notifyItemRangeRemoved(0, items.size());
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Your Order has Been Placed", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Please Add Items To Cart First", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}