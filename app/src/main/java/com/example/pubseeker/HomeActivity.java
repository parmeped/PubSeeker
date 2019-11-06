package com.example.pubseeker;

public class HomeActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btn = (Button) findViewById(R.id.beerButton);
        btn.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        ImageView animationTarget = (ImageView) this.findViewById(R.id.beerButton);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_around_center_point);
        animationTarget.startAnimation(animation);

    }


}
