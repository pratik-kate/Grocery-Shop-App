package com.supertrident.ecom.test.fragmets;

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
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.supertrident.ecom.R;
import com.supertrident.ecom.test.adapter.HomeAdapter;
import com.supertrident.ecom.test.models.HomeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    SliderLayout sliderLayout ;
    HashMap<String, String> HashMapForURL ;
    HashMap<String, Integer> HashMapForLocalRes ;
    RecyclerView list;
    public FirebaseDatabase database;
    public DatabaseReference category;

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



        //Init Firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("category");





        //products Grid
        list  = view.findViewById(R.id.list);
//        ArrayList<HomeModel> items = new ArrayList<>();
//
//        items.add(new HomeModel(R.drawable.demo,"product1"));
//        items.add(new HomeModel(R.drawable.demo,"product2"));
//        items.add(new HomeModel(R.drawable.demo,"product3"));
//        items.add(new HomeModel(R.drawable.demo,"product4"));
//        items.add(new HomeModel(R.drawable.demo,"product5"));
//        items.add(new HomeModel(R.drawable.demo,"product6"));
//        items.add(new HomeModel(R.drawable.demo,"product7"));
//
//
//        HomeAdapter adapter = new HomeAdapter(items,getContext());
//        list.setAdapter(adapter);

        GridLayoutManager layout = new GridLayoutManager(getContext(),2);
        list.setLayoutManager(layout);

        loadMenu();

        return view;
    }

    private void loadMenu() {
        Query query = category.child("category"); // Ph4 Reading chat
        FirebaseRecyclerOptions<HomeModel> options = new FirebaseRecyclerOptions.Builder<HomeModel>() //ph4
                .build();
        FirebaseRecyclerAdapter<HomeModel,HomeAdapter.viewHolder> adapter  = new FirebaseRecyclerAdapter<HomeModel, HomeAdapter.viewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HomeAdapter.viewHolder holder, int position, @NonNull HomeModel model) {

            }

            @NonNull
            @Override
            public HomeAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };
    }

    @Override
    public void onStop() {

        sliderLayout.stopAutoCycle();

        super.onStop();
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