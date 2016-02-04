package ru.dostavkamix.denis.dostavkamix;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by den on 04.02.2016.
 */
public class SwipeAdapter extends PagerAdapter {

    ArrayList<Drawable> image_list = new ArrayList<>();
    LayoutInflater mInflater;

    public SwipeAdapter() {
        image_list.add(getImageFromAssets("slider/image1.jpg"));
        image_list.add(getImageFromAssets("slider/image2.jpg"));
        image_list.add(getImageFromAssets("slider/image3.jpg"));
        image_list.add(getImageFromAssets("slider/image4.jpg"));
        image_list.add(getImageFromAssets("slider/image5.jpg"));
        image_list.add(getImageFromAssets("slider/image6.jpg"));
        image_list.add(getImageFromAssets("slider/image7.jpg"));
        image_list.add(getImageFromAssets("slider/image8.jpg"));
        image_list.add(getImageFromAssets("slider/image9.jpg"));
        image_list.add(getImageFromAssets("slider/image10.jpg"));
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
        mInflater = (LayoutInflater) AppController.getInstance().getMainActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = mInflater.inflate(R.layout.swipe_lay, container, false);
        ImageView image_view = (ImageView) item_view.findViewById(R.id.image_view);
        image_view.setImageDrawable(image_list.get(position));
        container.addView(item_view);

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
