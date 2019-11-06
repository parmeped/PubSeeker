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
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Console;
import java.net.ConnectException;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private ViewPager mslideViewPager;
    private LinearLayout mDotLayout;

<<<<<<< HEAD
    private TextView[] mDots;

    private SliderAdapter sliderAdapter;
<<<<<<< HEAD
=======
    private static final String TAG = "MainActivity";
>>>>>>> fdf4c402670fe6a0b06f03d8d02bf7a4690cb1a5
=======
    private ImageView i;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;
>>>>>>> 4b2e0e4cf531810dbd0b0da89fd665813224f882


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
=======

        i = findViewById(R.id.slide_image);

>>>>>>> 4b2e0e4cf531810dbd0b0da89fd665813224f882
        mslideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(this);
        mslideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mslideViewPager.addOnPageChangeListener(viewListener);
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 4b2e0e4cf531810dbd0b0da89fd665813224f882





    }
<<<<<<< HEAD
=======
        User user = new User("15","Pepote","Eai");
        AnotherClassTesting anotherClassTesting = new AnotherClassTesting("Hello","world");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseContext context = new DatabaseContext(database, "MainActivity");
        context.dataWriter(user, "Users");
        context.dataWriter(anotherClassTesting, "SomeCollection");

        dataReader();
    }

>>>>>>> fdf4c402670fe6a0b06f03d8d02bf7a4690cb1a5
=======

    public void onClick(View v) {


        Intent myIntent = new Intent(MainActivity.this, Maps.class);
        startActivity(myIntent);

//sigo aca
        }




>>>>>>> 4b2e0e4cf531810dbd0b0da89fd665813224f882
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

<<<<<<< HEAD
<<<<<<< HEAD
=======
    private void dataWriter() {
        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        String userId = "16";
        User user = new User(userId, "Another User", "another@userDomain.com");
        DatabaseReference mRef = database.getReference().child("Users").child(userId);
        mRef.setValue(user);
    }

    private void dataReader() {
        // Read from the database
        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference userValues = myRef.child("Users");
        userValues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    User user = singleSnapshot.getValue(User.class);
                    user.setKey(singleSnapshot.getKey().toString());
                    Log.d(TAG, "Value is: " + user.getAttributes());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

>>>>>>> fdf4c402670fe6a0b06f03d8d02bf7a4690cb1a5
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener(){
      @Override
=======
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener(){





        @Override
>>>>>>> 4b2e0e4cf531810dbd0b0da89fd665813224f882
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
<<<<<<< HEAD
=======


>>>>>>> 4b2e0e4cf531810dbd0b0da89fd665813224f882
}
