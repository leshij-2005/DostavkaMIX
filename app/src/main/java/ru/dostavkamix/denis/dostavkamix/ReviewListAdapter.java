package ru.dostavkamix.denis.dostavkamix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import ru.dostavkamix.denis.dostavkamix.CustomView.TextViewPlus;

/**
 * Created by den on 06.02.2016.
 */
public class ReviewListAdapter extends BaseAdapter implements View.OnClickListener {

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null) v = mInflater.inflate(R.layout.fragment_review_list_item, parent, false);

        final Review review = mActivity.reviews.get(position);
        RelativeLayout review_item = (RelativeLayout) v.findViewById(R.id.review_item);
        TextViewPlus review_title = (TextViewPlus) v.findViewById(R.id.review_title);
        TextViewPlus review_content = (TextViewPlus) v.findViewById(R.id.review_content);

        review_title.setText(review.title);
        review_content.setText(review.content);
        review_item.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

    }
}
