package ru.dostavkamix.denis.dostavkamix;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by den on 04.02.2016.
 */
public class SwipeImageAdapter extends PagerAdapter {

    ArrayList<Integer> image_list = new ArrayList<>();
    LayoutInflater mInflater;
    int ItemLayout;
    ImageLoader mLoader = AppController.getInstance().getImageLoader();

    public SwipeImageAdapter(ArrayList<Integer> list, int item) {
        this.image_list = list;
        this.ItemLayout = item;
    }

    @Override
    public int getCount() {
        return image_list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View item_view = null;
        try {
            mInflater = (LayoutInflater) AppController.getInstance().getMainActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item_view = mInflater.inflate(ItemLayout, container, false);
            ImageView image_view = (ImageView) item_view.findViewById(R.id.image_view);
            //image_view.setImageDrawable(image_list.get(position));
            image_view.setImageResource(image_list.get(position));
            container.addView(item_view);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public Drawable getImageFromAssets(String patch)
    {
        try {
            InputStream ims = AppController.getInstance().getMainActivity().getAssets().open(patch);
            return Drawable.createFromStream(ims, null);
        } catch (Exception e)
        {
            e.printStackTrace();
            return new ColorDrawable(Color.BLACK);
        }
    }
}
