package ru.dostavkamix.denis.dostavkamix;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by den on 06.02.2016.
 */
public class ReviewAdapter extends PagerAdapter{

    LayoutInflater mInflater;
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View item_view = null;
        return item_view;
    }
}
