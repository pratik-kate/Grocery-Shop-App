package com.supertrident.ecom.test.fragmets;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.supertrident.ecom.R;
import com.supertrident.ecom.test.adapter.MenuViewHolder;
import com.supertrident.ecom.test.models.HomeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    SliderLayout sliderLayout ;
    HashMap<String, String> HashMapForURL ;
    HashMap<String, Integer> HashMapForLocalRes ;
    RecyclerView list;
    private DatabaseReference myref;
    private ArrayList<HomeModel> modelArrayList;
    private MenuViewHolder menuViewHolder;
    private Context mcontext;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //banner
        sliderLayout = (SliderLayout)view.findViewById(R.id.slider);
        AddImageUrlFormLocalRes();
        for(String name : HashMapForLocalRes.keySet()){

            TextSliderView textSliderView = new TextSliderView(getContext());

            textSliderView
                    .description(name)
                    .image(HashMapForLocalRes.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
                        }
                    });

            textSliderView.bundle(new Bundle());

            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);

        //Showing Categories
        list = view.findViewById(R.id.list);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);

        myref = FirebaseDatabase.getInstance().getReference();

        modelArrayList = new ArrayList<>();
        clearAll();
        getDataFromFirebase();



        return view;
    }

    private void getDataFromFirebase() {

        Query query = myref.child("category");
       query.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               clearAll();
               for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                   HomeModel mode = new HomeModel();
                   mode.setImageUrl(snapshot.child("image").getValue().toString());
                   mode.setName(snapshot.child("name").getValue().toString());

                   modelArrayList.add(mode);
               }
               menuViewHolder = new MenuViewHolder(getContext(),modelArrayList);
               list.setAdapter(menuViewHolder);
               menuViewHolder.notifyDataSetChanged();
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


    @Override
    public void onStart()
    {
        super.onStart();
        //adapter.startListening();
    }
    @Override
    public void onStop()
    {
        super.onStop();
        sliderLayout.stopAutoCycle();
        //adapter.stopListening();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);

    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public void AddImagesUrlOnline(){

        HashMapForURL = new HashMap<String, String>();

        HashMapForURL.put("CupCake", "http://androidblog.esy.es/images/cupcake-1.png");
        HashMapForURL.put("Donut", "http://androidblog.esy.es/images/donut-2.png");
        HashMapForURL.put("Eclair", "http://androidblog.esy.es/images/eclair-3.png");
        HashMapForURL.put("Froyo", "http://androidblog.esy.es/images/froyo-4.png");
        HashMapForURL.put("GingerBread", "http://androidblog.esy.es/images/gingerbread-5.png");
    }
    public void AddImageUrlFormLocalRes(){

        HashMapForLocalRes = new HashMap<String, Integer>();

        HashMapForLocalRes.put("product1", R.drawable.demo3);
        HashMapForLocalRes.put("product2", R.drawable.demo3);
        HashMapForLocalRes.put("product3", R.drawable.demo3);
    }
    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}