package com.example.pubseeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.ImageDecoder;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private ViewPager mslideViewPager;
    private LinearLayout mDotLayout;

    private ImageView i;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        i = findViewById(R.id.slide_image);

        mslideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(this);
        mslideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mslideViewPager.addOnPageChangeListener(viewListener);





    }

    public void onClick(View v) {


        Intent myIntent = new Intent(MainActivity.this, Maps.class);
        startActivity(myIntent);

//sigo aca
        }




    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for(int i = 0; i< mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener(){





        @Override
      public void onPageScrolled(int i, float v, int j){

      }

      @Override
        public void onPageSelected(int i){
          addDotsIndicator(i);
      }

      @Override
        public void onPageScrollStateChanged(int i){

      }
    };


}
