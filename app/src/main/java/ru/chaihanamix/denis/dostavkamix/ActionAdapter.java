package ru.chaihanamix.denis.dostavkamix;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.toolbox.NetworkImageView;

import ru.chaihanamix.denis.dostavkamix.CustomView.TextViewPlus;

/**
 * Created by den on 06.02.2016.
 */
public class ActionAdapter extends PagerAdapter {


    int position = 0;
    MainActivity mActivity = AppController.getInstance().getMainActivity();
    LayoutInflater mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    @Override
    public int getCount() {
        return mActivity.actions.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = null;
        v = mInflater.inflate(R.layout.pager_action, container, false);

        Action action = mActivity.actions.get(position);

        NetworkImageView action_img = (NetworkImageView) v.findViewById(R.id.action_img);
        TextViewPlus action_title = (TextViewPlus) v.findViewById(R.id.action_title);
        TextViewPlus action_content = (TextViewPlus) v.findViewById(R.id.action_content);

        action_img.setImageUrl(action.url, AppController.getInstance().getImageLoader());
        action_title.setText(action.title);
        action_content.setText(action.content);

        container.addView(v);
        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
