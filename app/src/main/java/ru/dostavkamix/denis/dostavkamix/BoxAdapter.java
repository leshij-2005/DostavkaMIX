package ru.dostavkamix.denis.dostavkamix;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    Animation anim_emerging = null;
    private HashMap<Integer, Boolean> myChecked = new HashMap<Integer, Boolean>();

    public BoxAdapter(MainActivity mainActivity, ArrayList<Dish> object, FragmentTransaction ft) {
        this.ctx = mainActivity.getApplicationContext();
        this.mainActivity = mainActivity;
        this.object = object;
        this.linflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fontRub = Typeface.createFromAsset(ctx.getAssets(), "fonts/RUBSN.otf");
        fontReg = Typeface.createFromAsset(ctx.getAssets(), "fonts/GothaProReg.otf");
        descriptFragment = new descriptionFragment();
        this.ft = ft;
        anim_emerging = AnimationUtils.loadAnimation(mainActivity, R.anim.emerging_view);

        for (int i = 0; i < object.size(); i++) {
            myChecked.put(i, false);
        }
    }

    public void toggleChecked(int position) {
        if (myChecked.get(position)) {
            myChecked.put(position, false);
        } else {
            myChecked.put(position, true);
        }

        notifyDataSetChanged();
    }

    public List<Integer> getCheckedItemPositions() {
        List<Integer> checkedItemPositions = new ArrayList<Integer>();

        for (int i = 0; i < myChecked.size(); i++) {
            if (myChecked.get(i)) {
                (checkedItemPositions).add(i);
            }
        }

        return checkedItemPositions;
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

            SpannableStringBuilder result = new SpannableStringBuilder(s.substring(0, 1 + (s.length() - 4)) + " " + s.substring(1) + " Я");
            result.setSpan(new CustomTypefaceSpan("normal", fontReg), 0, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            result.setSpan(new CustomTypefaceSpan("sans", fontRub), s.length() + 1 + (s.length() - 4), s.length() + 3 + (s.length() - 4), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            return result;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = linflater.inflate(R.layout.dish_item, parent, false);
        }

        final Dish d = getDish(position);

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.dish_progress);
        final NetworkImageView dish_img = (NetworkImageView) view.findViewById(R.id.dish_img);
        priceButton checkBut = (priceButton) view.findViewById(R.id.dish_price);

        ((TextViewPlus) view.findViewById(R.id.dish_name)).setText(d.getNameDish());
        ((TextViewPlus) view.findViewById(R.id.dish_descript)).setText(d.getContent());

        checkBut.setText(addRuble(String.valueOf(d.getPriceDish())));




        dish_img.setDefaultImageResId(R.drawable.white_progress);
        dish_img.setImageUrl(d.getImjDish(), imageLoader);

        (view.findViewById(R.id.dish_item)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("json", "Click item");

                descriptFragment.setMainActivity(mainActivity);
                descriptFragment.setOnDish(d);
                descriptFragment.setDish_img_frag(d.getImjDish());
                descriptFragment.setDish_name_frag(d.getNameDish());
                descriptFragment.setDish_descript_frag(d.getContent());
                descriptFragment.setDish_weight_frag(d.getWeight());
                descriptFragment.setDish_price_frag(addRuble(String.valueOf(d.getPriceDish())));

                ft = mainActivity.getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.slide_in, R.animator.slide_out);
                ft.replace(R.id.frame_fragment, descriptFragment);
                mainActivity.setIsShowDescriptFrag(true);
                ft.commit();
            }
        });
         //////////////////////

        Boolean checked = myChecked.get(position);
        if(checked != null)
        {
            checkBut.setChecked(checked);
            if(checked) {
                d.setCountOrder(1);
                AppController.getInstance().addInBag(d);
                mainActivity.updateBagPrice();
            }else {
                d.setCountOrder(0);
                AppController.getInstance().removeInBad(d);
                mainActivity.updateBagPrice();
            }

        }

        if(AppController.getInstance().onBag(d))
        {
            //((Button) view.findViewById(R.id.dish_price)).setText("уже");
            //((TransitionDrawable)((Button) view.findViewById(R.id.dish_price)).getBackground()).startTransition(100);
            Log.d("json", "уже в корзине");
            checkBut.setChecked(true);
            mainActivity.updateBagPrice();
        }

        //final View finalView = view;
        (view.findViewById(R.id.dish_price)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toggleChecked(position);
                /*
                if (AppController.getInstance().onBag(d)) {
                    //TransitionDrawable td = (TransitionDrawable) ((Button) finalView.findViewById(R.id.dish_price)).getBackground();
                    Log.d("json", "удаляю из корзины");
                    d.setCountOrder(0);


                    //((Button) finalView.findViewById(R.id.dish_price)).setText(addRuble(String.valueOf(d.getPriceDish())));
                    /*
                    ObjectAnimator colorAnim = ObjectAnimator.ofInt(
                            ((Button) finalView.findViewById(R.id.dish_price)),
                            "textColor",
                            Color.TRANSPARENT,
                            AppController.getInstance().getResources().getColor(R.color.menu_catalog_color));
                    colorAnim.setDuration(100);
                    colorAnim.setEvaluator(new ArgbEvaluator());
                    colorAnim.start();
                    td.reverseTransition(100);

                    ((priceButton) finalView.findViewById(R.id.dish_price)).setChecked(false);
                    AppController.getInstance().removeInBad(d);
                    mainActivity.updateBagPrice();

                } else {
                    //TransitionDrawable td = (TransitionDrawable) ((Button) finalView.findViewById(R.id.dish_price)).getBackground();
                    d.setCountOrder(1);
                    Log.d("json", "в корзину");

                    //((Button) finalView.findViewById(R.id.dish_price)).setText("уже");
/*
                    ObjectAnimator colorAnim = ObjectAnimator.ofInt(
                            ((Button) finalView.findViewById(R.id.dish_price)),
                            "textColor",
                            AppController.getInstance().getResources().getColor(R.color.menu_catalog_color),
                            Color.TRANSPARENT);
                    colorAnim.setDuration(100);
                    colorAnim.setEvaluator(new ArgbEvaluator());
                    colorAnim.start();
                    td.startTransition(100);

                    ((priceButton) finalView.findViewById(R.id.dish_price)).setChecked(true);
                    AppController.getInstance().addInBag(d);
                    mainActivity.updateBagPrice();
                }
            */
            }
        });
        return view;
    }
}
