package ru.dostavkamix.denis.dostavkamix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import com.squareup.picasso.Callback;
import android.widget.ProgressBar;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.dostavkamix.denis.dostavkamix.Dish.Dish;

/**
 * Created by den on 12.01.16.
 */
public class BoxAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater linflater;
    ArrayList<Dish> object;
    Typeface fontRub = null;
    Typeface fontReg = null;

    public BoxAdapter(Context ctx, ArrayList<Dish> object) {
        this.ctx = ctx;
        this.object = object;
        this.linflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fontRub = Typeface.createFromAsset(ctx.getAssets(), "fonts/RUBSN.otf");
        fontReg = Typeface.createFromAsset(ctx.getAssets(), "fonts/GothaProReg.otf");
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
        SpannableStringBuilder result = new SpannableStringBuilder(s + " Я");
        result.setSpan(new CustomTypefaceSpan("normal", fontReg), 0, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        result.setSpan(new CustomTypefaceSpan("sans", fontRub), s.length(), s.length() + 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        return result;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = linflater.inflate(R.layout.dish_item, parent, false);
        }

        Dish d = getDish(position);

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.dish_progress);
        final RoundedImageView dish_img = (RoundedImageView) view.findViewById(R.id.dish_img);

                ((TextViewPlus) view.findViewById(R.id.dish_name)).setText(d.getNameDish());
        ((TextViewPlus) view.findViewById(R.id.dish_descript)).setText(d.getContent());
        ((Button) view.findViewById(R.id.dish_price)).setText(addRuble(String.valueOf(d.getPriceDish())));
        Picasso.with(ctx)
                .load(d.getImjDish())
                .into(dish_img, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                        dish_img.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {

                    }
                });

        return view;
    }


}
