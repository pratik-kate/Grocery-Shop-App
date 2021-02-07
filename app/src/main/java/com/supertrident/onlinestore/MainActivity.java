package com.supertrident.onlinestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.supertrident.onlinestore.fragmets.CartFragment;
import com.supertrident.onlinestore.fragmets.HomeFragment;
import com.supertrident.onlinestore.fragmets.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    LinearLayout screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        screen = findViewById(R.id.screen);
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.screen,fragment);
        transaction.commit();

        BubbleNavigationConstraintView bubbleNavigation;
        bubbleNavigation  = findViewById(R.id.bottom_navigation);
        bubbleNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                //navigation changed, do something
                switch (position){
                    case 0: {
                        HomeFragment fragment = new HomeFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen,fragment);
                        transaction.commit();
                        break;
                    }
                    case 1:{
                        CartFragment fragment = new CartFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen,fragment);
                        transaction.commit();
                        break;
                    }
                    case 2:{
                        ProfileFragment fragment = new ProfileFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen,fragment);
                        transaction.commit();
                        break;
                    }
                }
            }
        });
    }
}