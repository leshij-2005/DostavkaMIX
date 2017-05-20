package ru.chaihanamix.denis.dostavkamix;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import ru.chaihanamix.denis.dostavkamix.CustomView.TextViewPlus;

/**
 * Created by den on 06.02.2016.
 */
public class ReviewAdapter extends PagerAdapter{

    MainActivity mActivity = AppController.getInstance().getMainActivity();
    LayoutInflater mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    @Override
    public int getCount() {
        return mActivity.reviews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = null;
        v = mInflater.inflate(R.layout.pager_review, container, false);

        Review review = mActivity.reviews.get(position);

        TextViewPlus review_title = (TextViewPlus) v.findViewById(R.id.review_title);
        TextViewPlus review_content = (TextViewPlus) v.findViewById(R.id.review_content);
        TextViewPlus review_subtitle = (TextViewPlus) v.findViewById(R.id.review_subtitle);

        review_title.setText(review.title);
        review_content.setText(review.content);
        review_subtitle.setText(review.subtitle);

        container.addView(v);
        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
