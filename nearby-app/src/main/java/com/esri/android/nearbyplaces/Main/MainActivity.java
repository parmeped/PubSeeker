package com.esri.android.nearbyplaces.Main;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.nearbyplaces.R;
import com.esri.android.nearbyplaces.Services.EntityService;
import com.esri.android.nearbyplaces.Services.ServicesConfiguration;
import com.esri.android.nearbyplaces.map.MapActivity;
import com.esri.android.nearbyplaces.places.PlacesActivity;
import com.esri.android.nearbyplaces.places.PlacesContract;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.esri.android.nearbyplaces.places.PlacesActivity;

public class MainActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private int j;


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









        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);
    }

    public void onClick(View v){
        Intent pub;
        switch(j){

            case 0:
                pub = new Intent(MainActivity.this, PlacesActivity.class);
                startActivity(pub);
                break;
            case 1:
                pub = new Intent(MainActivity.this, PlacesActivity.class);
                startActivity(pub);
                break;
            case 2:
                pub = new Intent(MainActivity.this, About.class);
                startActivity(pub);
                break;
        }


        }

        public void meVoy(View v){
        mGoogleSignClient.signOut();
            startActivity(new Intent(MainActivity.this,Login.class));
            Toast.makeText(this, "Successfully signed out", Toast.LENGTH_SHORT).show();

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
