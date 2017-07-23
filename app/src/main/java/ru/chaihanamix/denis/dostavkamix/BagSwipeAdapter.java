package ru.chaihanamix.denis.dostavkamix;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import ru.chaihanamix.denis.dostavkamix.CustomView.CustomTypefaceSpan;
import ru.chaihanamix.denis.dostavkamix.CustomView.TextViewPlus;
import ru.chaihanamix.denis.dostavkamix.CustomView.customNetworkImageView;
import ru.chaihanamix.denis.dostavkamix.Dish.Dish;

/**
 * Created by den on 30.01.2016.
 */
public class BagSwipeAdapter extends BaseSwipeAdapter {

    private final String TAG = getClass().getSimpleName();
    Typeface fontRub = null;
    Typeface fontReg = null;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Animation anim;
    private Context mContext;

    public BagSwipeAdapter(Context mContext)
    {
        this.mContext = mContext;
        fontRub = Typeface.createFromAsset(mContext.getAssets(), "fonts/RUBSN.otf");
        fontReg = Typeface.createFromAsset(mContext.getAssets(), "fonts/GothaProReg.otf");
        anim = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_out_right);
        anim.setDuration(200);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        final View v = LayoutInflater.from(mContext).inflate(R.layout.item_bag_swipe, null);
        final SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));

        v.findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeLayout.close();
                v.startAnimation(anim);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppController.getInstance().removeInBad(getDish(position));
                        notifyDataSetChanged();
                    }
                }, anim.getDuration());
            }
        });
        return v;
    }

    public Dish getDish(int position)
    {
        return ((Dish) getItem(position));
    }
    @Override
    public void fillValues(final int position, View convertView) {
        final Dish d = getDish(position);

        customNetworkImageView dish_img_bag = (customNetworkImageView) convertView.findViewById(R.id.dish_img_bag);
        TextViewPlus dish_name_bag = (TextViewPlus) convertView.findViewById(R.id.dish_name_bag);
        TextViewPlus dish_weight_bag = (TextViewPlus) convertView.findViewById(R.id.dish_weight_bag);
        TextViewPlus dish_price_bag = (TextViewPlus) convertView.findViewById(R.id.dish_price_bag);
        Button button_plus = (Button) convertView.findViewById(R.id.button_diah_plus);
        TextView dish_count = (TextView) convertView.findViewById(R.id.count_dish_bag);
        Button button_minus = (Button) convertView.findViewById(R.id.button_dish_minus);

        dish_img_bag.setDefaultImageResId(R.drawable.white_progress);
        dish_img_bag.setImageUrl(d.getImjDish(), imageLoader);
        dish_name_bag.setText(d.getNameDish());
        dish_price_bag.setText(addRuble(String.valueOf(d.getPriceDish())));
        dish_weight_bag.setText(d.getWeight() + " " + d.getMeasure());
        dish_count.setText(String.valueOf(d.getCountOrder()));

        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.getInstance().inBag.get(position).setCountOrder(AppController.getInstance().inBag.get(position).getCountOrder() + 1);
                notifyDataSetChanged();
            }
        });

        final View finalView = convertView;
        button_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.getInstance().inBag.get(position).setCountOrder(AppController.getInstance().inBag.get(position).getCountOrder() - 1);
                if (AppController.getInstance().inBag.get(position).getCountOrder() == 0) {
                    finalView.startAnimation(anim);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AppController.getInstance().removeInBad(d);
                            notifyDataSetChanged();
                        }
                    }, anim.getDuration());
                }
                notifyDataSetChanged();
            }
        });

        AppController.getInstance().getMainActivity().updateBagPrice();
    }

    @Override
    public int getCount() {
        return AppController.getInstance().inBag.size();
    }

    @Override
    public Object getItem(int position) {
        return AppController.getInstance().inBag.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public SpannableStringBuilder addRuble(String s)
    {
        if(s.length() < 4) {
            SpannableStringBuilder result = new SpannableStringBuilder(s + " Я");
            result.setSpan(new CustomTypefaceSpan("normal", fontReg), 0, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            result.setSpan(new CustomTypefaceSpan("sans", fontRub), s.length(), s.length() + 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            return result;
        } else {

            SpannableStringBuilder result = new SpannableStringBuilder(s.substring(0, 1 + (s.length() - 4)) + " " + s.substring(1) + " Я");
            result.setSpan(new CustomTypefaceSpan("normal", fontReg), 0, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            result.setSpan(new CustomTypefaceSpan("sans", fontRub), s.length() + 1 + (s.length() - 4), s.length() + 3 + (s.length() - 4), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            return result;
        }
    }
}
