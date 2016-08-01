package ru.dostavkamix.denis.dostavkamix.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.Adapters.ReviewAdapter;
import ru.dostavkamix.denis.dostavkamix.Custom.viewpagerindicator.CirclePageIndicator;

/**
 * Created by den on 06.02.2016.
 */
public class ReviewPagerFragment extends Fragment {

    int mCurrentPage = 0;
    ViewPager mViewPager;
    ReviewAdapter mAdapter;

    private RelativeLayout but_to_review;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review, container, false);

        mViewPager = (ViewPager) v.findViewById(R.id.review_pager);
        mAdapter = new ReviewAdapter();
        mViewPager.setAdapter(mAdapter);

        CirclePageIndicator indicator = (CirclePageIndicator)v.findViewById(R.id.review_indicator);
        indicator.setViewPager(mViewPager);
        but_to_review = (RelativeLayout) v.findViewById(R.id.but_to_review);
        but_to_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intReview = new Intent(Intent.ACTION_SEND);
                intReview.setType("message/rfc822");
                intReview.putExtra(Intent.EXTRA_EMAIL, new String[]{"chaihana.mix@mail.ru"});
                intReview.putExtra(Intent.EXTRA_SUBJECT, "Отзыв о Чайхана MIX");
                AppController.getInstance().getMainActivity().startActivity(intReview);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        mAdapter = new ReviewAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurrentPage);
    }

    public void setCurrentItem(int currentItem)
    {
        mCurrentPage = currentItem;

        mViewPager.setCurrentItem(mCurrentPage);
    }
}
