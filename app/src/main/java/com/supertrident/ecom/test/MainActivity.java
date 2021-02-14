package com.supertrident.ecom.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.supertrident.ecom.R;
import com.supertrident.ecom.test.fragmets.CartFragment;
import com.supertrident.ecom.test.fragmets.HomeFragment;
import com.supertrident.ecom.test.fragmets.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    public static final String PPRICE ="PPRICE";
    public static final String CART = "CART";
    public static final String PRODUCT ="PRODUCT" ;
    public static final String PRODUCTIMAGE = "PRODUCTIMAGE";
    public static final String PRODUCTPRICE = "PRODUCTPRICE" ;
    public static int CARTCOUNTER= 0;
    LinearLayout screen;
    public static final String INFO ="INFO";
    public static final String USER ="USER";
    public static final String EMAIL ="EMAIL";
    public static final String CATID ="CATID";
    public static final String PID ="PID";
    public static final String PDEC ="PDEC";
    public static final String PIMG ="PIMG";

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