package com.dating.blinddate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.dating.blinddate.Model.Theam;
import com.dating.blinddate.R;
import pl.droidsonroids.gif.GifImageView;

public class OnBoardAdapter extends PagerAdapter {

    public OnBoardAdapter(Context context) {
        this.context = context;
    }

    Context context;
    public int[] ico = {R.drawable.logo,R.drawable.vibes,R.drawable.locationgif};
    public String[] title = {"Welcome","Match Vibe's","Easy Search"};

    public String[] decriptionlist = {"Happpy to See You. \nwe will help you to find your better Half",
                                "Find the people on the basis on your vibes",
                                "Meet your nearby People"};

    TextView heading ;
    TextView decription;
    GifImageView logo;
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

                View view = LayoutInflater.from(container.getContext()).inflate(R.layout.on_bord_design, container, false);

                logo = view.findViewById(R.id.logo);
                heading = view.findViewById(R.id.heading);
                decription = view.findViewById(R.id.decription);
                logo.setImageResource(ico[position]);
                heading.setText(title[position]);
                heading.setTextColor(new Theam().getTextHeadingD());

                decription.setText(decriptionlist[position]);
                decription.setTextColor(new Theam().getDescriptionD());

                container.addView(view);
                return view;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}

