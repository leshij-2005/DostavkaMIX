package ru.chaihanamix.denis.dostavkamix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import ru.chaihanamix.denis.dostavkamix.CustomView.TextViewPlus;

/**
 * Created by den on 06.02.2016.
 */
public class ReviewListAdapter extends BaseAdapter {

    MainActivity mActivity = AppController.getInstance().getMainActivity();
    LayoutInflater mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    @Override
    public int getCount() {
        return mActivity.reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return mActivity.reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null) v = mInflater.inflate(R.layout.fragment_review_list_item, parent, false);

        final Review review = mActivity.reviews.get(position);
        RelativeLayout review_item = (RelativeLayout) v.findViewById(R.id.review_item);
        TextViewPlus review_title = (TextViewPlus) v.findViewById(R.id.review_title);
        TextViewPlus review_content = (TextViewPlus) v.findViewById(R.id.review_content);

        review_title.setText(review.title);
        review_content.setText(review.content);
        review_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.ft = mActivity.getFragmentManager().beginTransaction();
                mActivity.ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                mActivity.ft.replace(R.id.frame_fragment, AppController.getInstance().reviewFragment);
                mActivity.ft.addToBackStack(null);
                mActivity.ft.commit();

                try {
                    AppController.getInstance().reviewFragment.setCurrentItem(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return v;
    }
}
