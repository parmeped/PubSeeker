package com.example.pubseeker;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btn = findViewById(R.id.beerButton);
        btn.setOnClickListener(this::onClick);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        ImageView animationTarget = this.findViewById(R.id.beerButton);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_around_center_point);
        animationTarget.startAnimation(animation);

    }
}
