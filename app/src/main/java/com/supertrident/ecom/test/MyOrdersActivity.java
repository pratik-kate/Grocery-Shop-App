package com.supertrident.ecom.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.supertrident.ecom.R;
import com.supertrident.ecom.test.adapter.OrdersViewHolder;
import com.supertrident.ecom.test.models.OrderModel;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {

    String uName;
    RecyclerView list;
    private DatabaseReference myref;
    private ArrayList<OrderModel> modelArrayList;
    private OrdersViewHolder menuViewHolder;
    String customer;
    TextView empty;
    ArrayList<String> s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        getSupportActionBar().hide();

        SharedPreferences pref = getSharedPreferences(MainActivity.HASHMAP,MODE_PRIVATE);
        uName=pref.getString(MainActivity.MAP,"user");

        empty = findViewById(R.id.empty);
        list=findViewById(R.id.orderList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);

        myref = FirebaseDatabase.getInstance().getReference();

            s = new ArrayList<>();
            modelArrayList = new ArrayList<>();
            if (modelArrayList.isEmpty()) {
                empty.setVisibility(View.VISIBLE);
            }
            clearAll();
            getDataFromFirebase();

    }

    private void getDataFromFirebase() {


        Query query = myref.child("orders").orderByChild("name").equalTo(uName);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearAll();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    OrderModel mode = new OrderModel();
                    mode.setName(snapshot.child("name").getValue().toString());
                    mode.setAddress(snapshot.child("address").getValue().toString());
                    mode.setPincode(snapshot.child("pincode").getValue().toString());
                    mode.setPhone(snapshot.child("phone").getValue().toString());
                    mode.setLandmark(snapshot.child("landmark").getValue().toString());
                    mode.setNo(snapshot.child("paymentId").getValue().toString());
                    mode.setAmount(snapshot.child("amount").getValue().toString());
                    mode.setProducts(snapshot.child("products").getValue().toString());


                    modelArrayList.add(mode);
                }
                try{
                menuViewHolder = new OrdersViewHolder(MyOrdersActivity.this,modelArrayList);
                list.setAdapter(menuViewHolder);
                menuViewHolder.notifyDataSetChanged();
                empty.setVisibility(View.INVISIBLE);
                }catch (Exception e){
                    empty.setText("No Orders");
                }

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