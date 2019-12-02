package com.esri.android.nearbyplaces.Main;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.nearbyplaces.Common.IEntitySearcher;
import com.esri.android.nearbyplaces.Entities.Bar;
import com.esri.android.nearbyplaces.Entities.User;
import com.esri.android.nearbyplaces.R;
import com.esri.android.nearbyplaces.Services.EntityService;
import com.esri.android.nearbyplaces.Services.ServicesConfiguration;
import com.esri.android.nearbyplaces.map.MapActivity;
import com.esri.android.nearbyplaces.places.PlacesActivity;
import com.esri.android.nearbyplaces.places.PlacesContract;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private int j;


    private SliderAdapter sliderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        EntityService usersService = ServicesConfiguration.getUsersService();
        EntityService barsService = ServicesConfiguration.getBarsService();

        User user = new User("1", "testing", "super@mail.com",null);

        Bar bar = new Bar("super bar", "1354112312.45", "1");
        Bar bar1 = new Bar("Master bar", "1354112312.45", "2");
        Bar bar2 = new Bar("Hyper Bar", "1354112312.45", "3");
        ArrayList<Bar> bars = new ArrayList<>();
        bars.add(bar);

        user.setBars(bars);

        try {
            usersService.save(user);
            barsService.save(bar);
            barsService.save(bar1);
            barsService.save(bar2);
            bar.setName("Perchulo");
            barsService.save(bar);
        }
        catch (Exception ex) {
            Log.e("Main Activity", "Problema save() con entidad",  ex); // el EntityService tira exception si no tiene un mapper.
        }

        try {
            Log.i("Main Activity", "searching by id 1");
            User entity = usersService.searchById("1");

            Log.i("Main Activity", "Found entity! showing mail: " + entity.getEmail());
        }
        catch (Exception e) {
            Log.e("Main Activity", "Error searching for entity!", e);
        }
    }

    public void onClick(View v){
        Intent pub;
        switch(j){

            case 0:
                pub = new Intent(MainActivity.this, PlacesActivity.class);
                startActivity(pub);
                break;
            case 1:
                Toast.makeText(this, "Aca vendria la activity de favoritos", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                pub = new Intent(MainActivity.this, About.class);
                startActivity(pub);
                break;
        }


        }





//sigo aca



    public void addDotsIndicator(int position) {

        mDots = new TextView[3];
        mDotLayout.removeAllViews();
        for ( int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.gris));


            mDotLayout.addView(mDots[i]);

        }
        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorText));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int i, float v, int i1) {
           j = i;
        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

        }


        @Override
        public void onPageScrollStateChanged(int i) {

        }

    };
}
