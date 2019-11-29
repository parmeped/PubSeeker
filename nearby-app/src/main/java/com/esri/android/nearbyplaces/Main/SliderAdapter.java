package com.esri.android.nearbyplaces.Main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.nearbyplaces.R;

import org.w3c.dom.Text;

public class SliderAdapter extends PagerAdapter {
    public int posicion;
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter (Context context){
        this.context = context;
    }

    public int [] slide_images ={
            R.drawable.pint,
            R.drawable.clipboards,
            R.drawable.group

    };

    public String [] slide_headings = {
            "Birra",
            "Favoritos",
            "About"
    };

    public String[] slide_descs ={
        "Dale a la Birra y a disfrutar!",
         "Bares favoritos",
          "Dev Team"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);
        posicion = position;
        slideImageView.setImageResource(slide_images[position]);
        slideDescription.setText((slide_descs[position]));

        container.addView(view);



        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout) object);
    }

    public int getPosicion(){return posicion;}
}
