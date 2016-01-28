package ru.dostavkamix.denis.dostavkamix;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.dostavkamix.denis.dostavkamix.CustomView.CustomTypefaceSpan;
import ru.dostavkamix.denis.dostavkamix.CustomView.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.CustomView.customNetworkImageView;
import ru.dostavkamix.denis.dostavkamix.CustomView.priceButton;
import ru.dostavkamix.denis.dostavkamix.Dish.Dish;
import ru.dostavkamix.denis.dostavkamix.Fragments.descriptionFragment;

import static ru.dostavkamix.denis.dostavkamix.R.drawable.white_progress;

/**
 * Created by den on 12.01.16.
 */
public class BoxAdapter extends BaseAdapter {

    private final String TAG = getClass().getSimpleName();
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

    public void setChecked(int position, boolean b)
    {
        myChecked.put(position, b);
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

        final customNetworkImageView dish_img = (customNetworkImageView) view.findViewById(R.id.dish_img);
        priceButton checkBut = (priceButton) view.findViewById(R.id.dish_price);

        ((TextViewPlus) view.findViewById(R.id.dish_name)).setText(d.getNameDish());
        ((TextViewPlus) view.findViewById(R.id.dish_descript)).setText(d.getContent());

        checkBut.setText(addRuble(String.valueOf(d.getPriceDish())));


        dish_img.setDefaultImageResId(white_progress);
        dish_img.setImageUrl(d.getImjDish(), imageLoader);

        (view.findViewById(R.id.dish_item)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Click item");

                descriptFragment.setMainActivity(mainActivity);
                descriptFragment.setOnDish(d);
                descriptFragment.setDish_img_frag(d.getImjDish());
                descriptFragment.setDish_name_frag(d.getNameDish());
                descriptFragment.setDish_descript_frag(d.getContent());
                descriptFragment.setDish_weight_frag(d.getWeight());
                descriptFragment.setDish_price_frag(addRuble(String.valueOf(d.getPriceDish())));

                ft = mainActivity.getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out);
                ft.replace(R.id.frame_fragment, descriptFragment);
                AppController.getInstance().setIsShowDescriptFrag(true);
                AppController.getInstance().setIsShowMenuList(false);
                ft.commit();
            }
        });
         //////////////////////
        if(AppController.getInstance().onBag(getDish(position)))
        {
            setChecked(position, true);
        }

        if(AppController.getInstance().onBag(d))
        {
            Log.d(TAG, "уже в корзине");
            checkBut.setChecked(true);
        }

        Boolean checked = myChecked.get(position);

        if(checked != null)
        {
            checkBut.setChecked(checked);
        }

        checkBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(AppController.getInstance().onBag(getDish(position)))
                {
                    Log.d(TAG, "Убираю из корзины");
                    setChecked(position, false);
                    getDish(position).setCountOrder(0);
                    AppController.getInstance().removeInBad(getDish(position));
                    notifyDataSetChanged();
                } else {
                    Log.d(TAG, "Добавил в корзину");
                    setChecked(position, true);
                    Dish result = getDish(position);
                    result.setCountOrder(1);
                    AppController.getInstance().addInBag(result);
                }

                notifyDataSetChanged();

            }
        });
        return view;
    }
}
