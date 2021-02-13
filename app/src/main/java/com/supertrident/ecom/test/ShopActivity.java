package com.supertrident.ecom.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.supertrident.ecom.R;
import com.supertrident.ecom.test.adapter.MenuViewHolder;
import com.supertrident.ecom.test.adapter.ProductViewHolder;
import com.supertrident.ecom.test.models.HomeModel;
import com.supertrident.ecom.test.models.ProductModel;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    int catId;
    String categoryId;
    RecyclerView recyclerView;
    private DatabaseReference myref;
    private ArrayList<ProductModel> modelArrayList;
    private ProductViewHolder menuViewHolder;
    private ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        getSupportActionBar().setTitle("Products");
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();


        Intent intent = getIntent();
        catId = intent.getIntExtra(MainActivity.CATID,0);
        categoryId = String.valueOf(catId+1);

        myref = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        modelArrayList = new ArrayList<>();
        clearAll();
        getDataFromFirebase();

    }


    private void getDataFromFirebase() {

        Query query = myref.child("products")
                .orderByChild("catId").equalTo(categoryId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearAll();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ProductModel mode = new ProductModel();

                    mode.setName(snapshot.child("name").getValue().toString());
                    mode.setPrice(snapshot.child("price").getValue().toString());
                    mode.setDescription(snapshot.child("description").getValue().toString());
                    mode.setImage(snapshot.child("image").getValue().toString());

                    modelArrayList.add(mode);
                }
                menuViewHolder = new ProductViewHolder(ShopActivity.this,modelArrayList);
                recyclerView.setAdapter(menuViewHolder);
                menuViewHolder.notifyDataSetChanged();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private  void clearAll(){
        if(modelArrayList != null){
            modelArrayList.clear();

            if(menuViewHolder != null){
                menuViewHolder.notifyDataSetChanged();
            }
        }else{
            modelArrayList = new ArrayList<>();
        }
    }

}