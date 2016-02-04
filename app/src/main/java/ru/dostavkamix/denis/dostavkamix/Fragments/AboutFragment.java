package ru.dostavkamix.denis.dostavkamix.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.SwipeAdapter;

/**
 * Created by den on 04.02.2016.
 */
public class AboutFragment extends Fragment {

    ViewPager pager_view;
    SwipeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        TextView about_text = (TextView) v.findViewById(R.id.about_text);
        about_text.setMovementMethod(new ScrollingMovementMethod());
        pager_view = (ViewPager) v.findViewById(R.id.pager_view);
        mAdapter = new SwipeAdapter();
        pager_view.setAdapter(mAdapter);

        return v;
    }
}
