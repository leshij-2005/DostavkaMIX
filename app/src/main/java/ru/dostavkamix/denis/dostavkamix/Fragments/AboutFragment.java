package ru.dostavkamix.denis.dostavkamix.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.SwipeImageAdapter;

/**
 * Created by den on 04.02.2016.
 */
public class AboutFragment extends Fragment {

    ViewPager pager_view;
    SwipeImageAdapter mAdapter;
    ArrayList<Integer> image_list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        //TextView about_text = (TextView) v.findViewById(R.id.about_text);
        //about_text.setMovementMethod(new ScrollingMovementMethod());

        image_list.add(R.drawable.image1);
        image_list.add(R.drawable.image2);
        image_list.add(R.drawable.image3);
        image_list.add(R.drawable.image4);
        image_list.add(R.drawable.image5);
        image_list.add(R.drawable.image6);
        image_list.add(R.drawable.image7);
        image_list.add(R.drawable.image8);
        image_list.add(R.drawable.image9);
        image_list.add(R.drawable.image10);


        pager_view = (ViewPager) v.findViewById(R.id.pager_view);
        mAdapter = new SwipeImageAdapter(image_list, R.layout.about_swipe_layout);
        pager_view.setAdapter(mAdapter);

        return v;
    }
}
