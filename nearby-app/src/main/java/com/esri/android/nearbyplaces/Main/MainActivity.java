package com.esri.android.nearbyplaces.Main;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.nearbyplaces.Entities.Bar;
import com.esri.android.nearbyplaces.Entities.User;
import com.esri.android.nearbyplaces.R;
import com.esri.android.nearbyplaces.Services.EntityService;
import com.esri.android.nearbyplaces.Services.ServicesConfiguration;
import com.esri.android.nearbyplaces.map.MapActivity;
import com.esri.android.nearbyplaces.places.PlacesActivity;
import com.esri.android.nearbyplaces.places.PlacesContract;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private int j;
    private ImageView i;
    GoogleSignInClient mGoogleSignClient;


    private SliderAdapter sliderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignClient = GoogleSignIn.getClient(this,gso);

        i = findViewById(R.id.exit);



        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        //EntityService usersService = ServicesConfiguration.getUsersService();
        //EntityService barsService = ServicesConfiguration.getBarsService();

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
