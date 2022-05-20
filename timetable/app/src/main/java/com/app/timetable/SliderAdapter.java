package com.app.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slide_images = {
            R.mipmap.calendar_img,
            R.mipmap.todo_img,
            R.mipmap.bkcalendar_img,
            R.mipmap.note_img
    };

    public int[] slide_headings ={
            R.string.calendar_pager_heading,
            R.string.todo_heading,
            R.string.bk_calendar_heading,
            R.string.note_heading
    };

    public int[] slide_texts ={
            R.string.calendar_pager,
            R.string.todo_pager,
            R.string.bk_calendar_pager,
            R.string.note_pager
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView imageView = view.findViewById(R.id.pager_img);
        TextView pager_txt = view.findViewById(R.id.pager_txt);
        TextView pager_heading = view.findViewById(R.id.pager_heading);

        imageView.setImageResource(slide_images[position]);
        pager_txt.setText(slide_texts[position]);
        pager_heading.setText(slide_headings[position]);

        container.addView(view);

        return view;
    };

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
