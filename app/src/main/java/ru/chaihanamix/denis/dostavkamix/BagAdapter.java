package ru.chaihanamix.denis.dostavkamix;

import android.app.FragmentTransaction;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import ru.chaihanamix.denis.dostavkamix.CustomView.CustomTypefaceSpan;
import ru.chaihanamix.denis.dostavkamix.CustomView.TextViewPlus;
import ru.chaihanamix.denis.dostavkamix.CustomView.customNetworkImageView;
import ru.chaihanamix.denis.dostavkamix.Dish.Dish;
import ru.chaihanamix.denis.dostavkamix.Fragments.BagFragment;

/**
 * Created by den on 29.01.2016.
 */
public class BagAdapter extends BaseAdapter {

    private final String TAG = getClass().getSimpleName();
    private BagFragment frag;
    private FragmentTransaction ft;
    Context ctx;
    //MainActivity mainActivity;
    LayoutInflater inflater;
    Typeface fontRub = null;
    Typeface fontReg = null;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Animation anim;

    public BagAdapter(Context ctx)
    {
        //this.mainActivity = mainActivity;
        this.ctx = ctx;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.frag = new BagFragment();
        fontRub = Typeface.createFromAsset(ctx.getAssets(), "fonts/RUBSN.otf");
        fontReg = Typeface.createFromAsset(ctx.getAssets(), "fonts/GothaProReg.otf");

        anim = AnimationUtils.loadAnimation(ctx, android.R.anim.slide_out_right);
        anim.setDuration(200);
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

    public Dish getDish(int position)
    {
        return ((Dish) getItem(position));
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = inflater.inflate(R.layout.item_bag, parent, false);
        }

        final Dish d = getDish(position);

        customNetworkImageView dish_img_bag = (customNetworkImageView) view.findViewById(R.id.dish_img_bag);
        TextViewPlus dish_name_bag = (TextViewPlus) view.findViewById(R.id.dish_name_bag);
        TextViewPlus dish_weight_bag = (TextViewPlus) view.findViewById(R.id.dish_weight_bag);
        TextViewPlus dish_price_bag = (TextViewPlus) view.findViewById(R.id.dish_price_bag);
        Button button_plus = (Button) view.findViewById(R.id.button_diah_plus);
        TextView dish_count = (TextView) view.findViewById(R.id.count_dish_bag);
        Button button_minus = (Button) view.findViewById(R.id.button_dish_minus);

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

        final View finalView = view;
        button_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.getInstance().inBag.get(position).setCountOrder(AppController.getInstance().inBag.get(position).getCountOrder() - 1);
                if(AppController.getInstance().inBag.get(position).getCountOrder() == 0) {
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

        return view;


    }
}
