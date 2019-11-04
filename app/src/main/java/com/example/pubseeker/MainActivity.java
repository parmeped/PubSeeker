package com.example.pubseeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Console;
import java.lang.reflect.GenericDeclaration;
import java.net.ConnectException;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private ViewPager mslideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mslideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(this);
        mslideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mslideViewPager.addOnPageChangeListener(viewListener);

        //firebase Testing
        DatabaseContext context = new DatabaseContext("MainActivity");
        User user = new User("8", "Sarasasa", "Testing@generics.com");
        context.dataWriter("Users", user, user.getKey());

        User user1 = new User("7", "SarasaGenerics", "Testing@generics.com");
        context.dataWriter("Users", user1, user1.getKey());


        //AnotherClassTesting testing = new AnotherClassTesting("5", "This is a string", "13");
        //DatabaseContext.dataWriter("AnotherCollection", testing, testing.getKey()); // esto registra una nueva collection en la db.
        dataReader();
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

    private void dataReader() {
        // Read from the database
        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference userValues = myRef.child("NewCollection");
        userValues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    AnotherClassTesting anotherTesting = singleSnapshot.getValue(AnotherClassTesting.class);
                    anotherTesting.setKey(singleSnapshot.getKey().toString());
                    Log.d(TAG, "Value is: " + anotherTesting.showDataAsJSON());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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
