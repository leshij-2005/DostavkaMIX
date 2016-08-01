package ru.dostavkamix.denis.dostavkamix.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dostavkamix.denis.dostavkamix.Adapters.ActionAdapter;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.Custom.viewpagerindicator.CirclePageIndicator;

/**
 * Created by den on 06.02.2016.
 */
public class ActionPagerFragment extends Fragment {


    int mCurrentPage = 0;
    ViewPager mViewPager;
    ActionAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_action, container, false);

        mViewPager = (ViewPager) v.findViewById(R.id.action_pager);
        mAdapter = new ActionAdapter();
        mViewPager.setAdapter(mAdapter);

        CirclePageIndicator indicator = (CirclePageIndicator)v.findViewById(R.id.action_indicator);
        indicator.setViewPager(mViewPager);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        mAdapter = new ActionAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurrentPage);
    }

    public void setCurrentItem(int currentItem)
    {
        mCurrentPage = currentItem;

        mViewPager.setCurrentItem(mCurrentPage);
    }
}
