package ru.dostavkamix.denis.dostavkamix;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.NetworkImageView;

import ru.dostavkamix.denis.dostavkamix.CustomView.TextViewPlus;

/**
 * Created by den on 06.02.2016.
 */
public class ActionListAdapter extends BaseAdapter {

    MainActivity mActivity = AppController.getInstance().getMainActivity();
    LayoutInflater mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    @Override
    public int getCount() {
        return mActivity.actions.size();
    }

    @Override
    public Object getItem(int position) {
        return mActivity.actions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) v = mInflater.inflate(R.layout.fragment_action_list_item, parent, false);

        final Action action = mActivity.actions.get(position);

        RelativeLayout action_item = (RelativeLayout) v.findViewById(R.id.action_item);
        NetworkImageView action_img = (NetworkImageView) v.findViewById(R.id.action_img);
        TextViewPlus action_title = (TextViewPlus) v.findViewById(R.id.action_title);
        TextViewPlus action_content = (TextViewPlus) v.findViewById(R.id.action_content);


        action_img.setImageUrl(action.url, AppController.getInstance().getImageLoader());
        action_title.setText(action.title);
        action_content.setText(action.content);

        action_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mActivity.ft = mActivity.getFragmentManager().beginTransaction();
                mActivity.ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                mActivity.ft.replace(R.id.frame_fragment, AppController.getInstance().actionFragment);
                mActivity.ft.addToBackStack(null);
                mActivity.ft.commit();

                try {
                    AppController.getInstance().actionFragment.setCurrentItem(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }

}
