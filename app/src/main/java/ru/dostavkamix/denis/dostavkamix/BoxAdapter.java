package ru.dostavkamix.denis.dostavkamix;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import ru.dostavkamix.denis.dostavkamix.Dish.Dish;
import ru.dostavkamix.denis.dostavkamix.Fragments.descriptionFragment;

/**
 * Created by den on 12.01.16.
 */
public class BoxAdapter extends BaseAdapter {

    private descriptionFragment descriptFragment;
    private Fragment firstFragment;
    private FragmentTransaction ft;
    Context ctx;
    MainActivity mainActivity;
    LayoutInflater linflater;
    ArrayList<Dish> object;
    Typeface fontRub = null;
    Typeface fontReg = null;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public BoxAdapter(MainActivity mainActivity, ArrayList<Dish> object, FragmentTransaction ft) {
        this.ctx = mainActivity.getApplicationContext();
        this.mainActivity = mainActivity;
        this.object = object;
        this.linflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fontRub = Typeface.createFromAsset(ctx.getAssets(), "fonts/RUBSN.otf");
        fontReg = Typeface.createFromAsset(ctx.getAssets(), "fonts/GothaProReg.otf");
        descriptFragment = new descriptionFragment();
        this.ft = ft;
    }

    @Override
    public int getCount() {
        return object.size();
    }

    @Override
    public Object getItem(int position) {
        return object.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Dish getDish(int position)
    {
        return ((Dish) getItem(position));
    }

    public SpannableStringBuilder addRuble(String s)
    {
        if(s.length() < 4) {
            SpannableStringBuilder result = new SpannableStringBuilder(s + " Я");
            result.setSpan(new CustomTypefaceSpan("normal", fontReg), 0, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            result.setSpan(new CustomTypefaceSpan("sans", fontRub), s.length(), s.length() + 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            return result;
        } else {

            SpannableStringBuilder result = new SpannableStringBuilder(s.substring(0, 1) + " " + s.substring(1) + " Я");
            result.setSpan(new CustomTypefaceSpan("normal", fontReg), 0, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            result.setSpan(new CustomTypefaceSpan("sans", fontRub), s.length() + 1, s.length() + 3, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            return result;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = linflater.inflate(R.layout.dish_item, parent, false);
        }

        final Dish d = getDish(position);

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.dish_progress);
        final NetworkImageView dish_img = (NetworkImageView) view.findViewById(R.id.dish_img);

        ((TextViewPlus) view.findViewById(R.id.dish_name)).setText(d.getNameDish());
        ((TextViewPlus) view.findViewById(R.id.dish_descript)).setText(d.getContent());
        ((Button) view.findViewById(R.id.dish_price)).setText(addRuble(String.valueOf(d.getPriceDish())));

        dish_img.setDefaultImageResId(R.drawable.white_progress);
        dish_img.setImageUrl(d.getImjDish(), imageLoader);

        final View finalView = view;
        ((LinearLayout) view.findViewById(R.id.dish_item)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("json", "Click item");

                descriptFragment.setDish_img_frag(d.getImjDish());
                descriptFragment.setDish_name_frag(d.getNameDish());
                descriptFragment.setDish_descript_frag(d.getContent());
                descriptFragment.setDish_weight_frag(d.getWeight());
                descriptFragment.setDish_price_frag(addRuble(String.valueOf(d.getPriceDish())));

                ft = ((Activity) mainActivity).getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.slide_in, R.animator.slide_out);
                ft.add(R.id.frame_fragment, descriptFragment);
                mainActivity.setIsShowDescriptFrag(true);
                ft.commit();
            }
        });


        return view;
    }
}
