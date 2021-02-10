package com.supertrident.ecom.test.fragmets;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supertrident.ecom.R;
import com.supertrident.ecom.test.MainActivity;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {


    TextView name,email;
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
        return view;
    }
}