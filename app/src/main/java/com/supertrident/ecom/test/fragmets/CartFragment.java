package com.supertrident.ecom.test.fragmets;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.supertrident.ecom.R;
import com.supertrident.ecom.test.MainActivity;

import java.util.ArrayList;
import java.util.Set;


public class CartFragment extends Fragment {


    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();

    TextView textView;
    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);


        try {
            SharedPreferences pref = getActivity().getSharedPreferences(MainActivity.CART, Context.MODE_PRIVATE);
            for (int i = 1; i <= MainActivity.CARTCOUNTER; i++) {
                String s = MainActivity.PRODUCT+i;
                String im = MainActivity.PRODUCTIMAGE+i;
                String pr = MainActivity.PRODUCTPRICE+i;

                name.add(pref.getString(s, "empty"));
                price.add(pref.getString(pr, "empty"));
                image.add(pref.getString(im, "empty"));
                textView.append(image.get(i-1));
                textView.append("\n");

                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
           }

        }catch (Exception e){
            textView.setText("Cart Empty");
        }

        return view;
    }
}