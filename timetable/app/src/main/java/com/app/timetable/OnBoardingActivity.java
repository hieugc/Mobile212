package com.app.timetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoardingActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private SliderAdapter adapter;
    private ImageView[] slide_indicators;
    private LinearLayout skipLayout;
    private TextView skip_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        viewPager = findViewById(R.id.viewPager);
        linearLayout = findViewById(R.id.pager);
        skipLayout = findViewById(R.id.skip_on_boarding);

        skip_txt = findViewById(R.id.skip_txt);

        adapter = new SliderAdapter(this);

        viewPager.setAdapter(adapter);

        addSlideIndicators(0);

        viewPager.addOnPageChangeListener(listener);

        skipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void addSlideIndicators(int index){
        slide_indicators = new ImageView[adapter.getCount()];
        linearLayout.removeAllViews();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParams.setMargins(8,0,8,0);

        for(int i = 0; i < slide_indicators.length; i++)
        {
            slide_indicators[i] = new ImageView(this);
            slide_indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            slide_indicators[i].setLayoutParams(layoutParams);
            linearLayout.addView(slide_indicators[i]);
        }

        if(slide_indicators.length > 0)
        {
            slide_indicators[index].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_active
            ));
            if(index == slide_indicators.length -1)
            {
                skip_txt.setText(R.string.finish);
            }
        }
    }

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addSlideIndicators(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}