package com.pratikcodes.shopclient.fragmets;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pratikcodes.ecom.R;
import com.pratikcodes.shopclient.adapter.MenuViewHolder;
import com.pratikcodes.shopclient.models.HomeModel;

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
    private ShimmerFrameLayout shimmerFrameLayout;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        shimmerFrameLayout = view.findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();
        //banner
        sliderLayout = (SliderLayout)view.findViewById(R.id.slider);
        AddImagesUrlOnline();
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
//        HashMapForURL.put("CupCake", "https://github.com/pratik-kate/SampleData/blob/main/Farsan_dryfruits.jpg?raw=true");
//        HashMapForURL.put("Donut", "https://github.com/pratik-kate/SampleData/blob/main/Farsan_dryfruits.jpg?raw=true");
//        HashMapForURL.put("Eclair", "https://github.com/pratik-kate/SampleData/blob/main/Farsan_dryfruits.jpg?raw=true");


        FirebaseDatabase.getInstance().getReference().child("offers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
//                    images.add(snapshot.child("image").getValue().toString());
//                    names.add(snapshot.child("description").getValue().toString());
                    if(HashMapForURL.size()>=3){
                        HashMapForURL.clear();
                    }
                    HashMapForURL.put(snapshot.child("description").getValue().toString(),snapshot.child("image").getValue().toString());
                }

                for(String name : HashMapForURL.keySet()){

                    TextSliderView textSliderView = new TextSliderView(getContext());
                    textSliderView
                            .description(name)
                            .image(HashMapForURL.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit);

                    textSliderView.bundle(new Bundle());

                    textSliderView.getBundle()
                            .putString("extra",name);

                    sliderLayout.addSlider(textSliderView);
                }
                sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
                sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                sliderLayout.setCustomAnimation(new DescriptionAnimation());
                sliderLayout.setDuration(3000);

                HashMapForURL.clear();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}