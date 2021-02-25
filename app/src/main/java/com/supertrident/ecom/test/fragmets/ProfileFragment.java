package com.supertrident.ecom.test.fragmets;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.supertrident.ecom.R;
import com.supertrident.ecom.test.LoginActivity;
import com.supertrident.ecom.test.MainActivity;
import com.supertrident.ecom.test.SplashActivity;

import java.util.HashMap;
import java.util.List;

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

        SharedPreferences preferences = getActivity().getSharedPreferences(MainActivity.INFO,MODE_PRIVATE);
        String uname = preferences.getString(MainActivity.USER,"user");
        String uemail = preferences.getString(MainActivity.EMAIL,"user@email.com");

        name.setText(uname);
        email.setText(uemail);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.HASHMAP, MODE_PRIVATE);
        String defValue = new Gson().toJson(new HashMap<String, List<String>>());
        String json=sharedPreferences.getString(MainActivity.MAP,defValue);
        TypeToken<HashMap<String,List<String>>> token = new TypeToken<HashMap<String,List<String>>>() {};
        HashMap<String,List<String>> retrievedMap=new Gson().fromJson(json,token.getType());

        retrievedMap.get("name");
        TextView v = view.findViewById(R.id.logout);
        v.setText(retrievedMap.get("name").toString());

        v.setOnClickListener(v1 -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
        });
        return view;
    }
}