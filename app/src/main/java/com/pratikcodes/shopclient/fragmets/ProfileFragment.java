package com.pratikcodes.shopclient.fragmets;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.pratikcodes.ecom.R;
import com.pratikcodes.shopclient.LoginActivity;
import com.pratikcodes.shopclient.MainActivity;
import com.pratikcodes.shopclient.MyOrdersActivity;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {


    TextView name,email;
    FirebaseAuth mFirebaseAuth;
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        name = view.findViewById(R.id.pUser);
        email = view.findViewById(R.id.pEmail);
        mFirebaseAuth = FirebaseAuth.getInstance();

        SharedPreferences preferences = getActivity().getSharedPreferences(MainActivity.INFO,MODE_PRIVATE);
        String uname = preferences.getString(MainActivity.USER,"user");
        String uemail = preferences.getString(MainActivity.EMAIL,"user@email.com");

        if(uname.contains("user")){
            String nname = mFirebaseAuth.getCurrentUser().getDisplayName();
            name.setText(nname);
            String eemail = mFirebaseAuth.getCurrentUser().getEmail();
            email.setText(eemail);

        }else {
            name.setText(uname);
            email.setText(uemail);
        }

        TextView v = view.findViewById(R.id.logout);
        TextView myorders = view.findViewById(R.id.myorders);
        myorders.setOnClickListener(v1 -> {
            startActivity(new Intent(getContext(), MyOrdersActivity.class));
        });


        v.setOnClickListener(v1 -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
        });
        return view;
    }
}