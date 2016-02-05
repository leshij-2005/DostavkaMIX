package ru.dostavkamix.denis.dostavkamix.Fragments;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.SwipeImageAdapter;

/**
 * Created by den on 04.02.2016.
 */
public class InfoFragment extends Fragment implements OnClickListener {

    ViewPager pager_view;
    SwipeImageAdapter mAdapter;
    ArrayList<Integer> image_list = new ArrayList<>();

    //Select button
    private Button select_left;
    private Button select_right;
    private Button OnSelect;

    //Info
    private RelativeLayout lay_map;
    private RelativeLayout lay_web;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);

        // Select button
        select_left = (Button) v.findViewById(R.id.select_left);
        select_right = (Button) v.findViewById(R.id.select_right);
        OnSelect = null;
        select_left.setOnClickListener(this);
        select_right.setOnClickListener(this);
        selectOnButton(select_left);

        //Info
        lay_map = (RelativeLayout) v.findViewById(R.id.relative_1);
        lay_web = (RelativeLayout) v.findViewById(R.id.relative_4);
        lay_map.setOnClickListener(this);
        lay_web.setOnClickListener(this);

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
        mAdapter = new SwipeImageAdapter(image_list, R.layout.info_swipe_layout);
        pager_view.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.select_left:
                if(OnSelect != v)
                {
                    selectOffButton(OnSelect);
                    selectOnButton((Button) v);
                }
                break;
            case R.id.select_right:
                if(OnSelect != v)
                {
                    selectOffButton(OnSelect);
                    selectOnButton((Button) v);
                }
                break;
            case R.id.relative_1:

                break;
            case R.id.relative_4:

                break;
        }
    }

    public void selectOnButton(Button button)
    {
        TransitionDrawable drawable = (TransitionDrawable) button.getBackground();
        drawable.startTransition(100);

        ObjectAnimator colorAnim = ObjectAnimator.ofInt(
                button,
                "textColor",
                Color.WHITE,
                Color.BLACK);
        colorAnim.setDuration(100);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();

        OnSelect = button;
    }
    public void selectOffButton(Button button)
    {
        TransitionDrawable drawable = (TransitionDrawable) button.getBackground();
        drawable.reverseTransition(100);

        ObjectAnimator colorAnim = ObjectAnimator.ofInt(
                button,
                "textColor",
                Color.BLACK,
                Color.WHITE);
        colorAnim.setDuration(100);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
    }
}
