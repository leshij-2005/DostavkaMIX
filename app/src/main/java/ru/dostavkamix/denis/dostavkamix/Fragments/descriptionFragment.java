package ru.dostavkamix.denis.dostavkamix.Fragments;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.Dish.Dish;
import ru.dostavkamix.denis.dostavkamix.MainActivity;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.TextViewPlus;

/**
 * Created by den on 21.01.16.
 */
public class descriptionFragment extends Fragment implements View.OnClickListener {
    private Button selectDishCount = null;
    private Dish onDish;
    private MainActivity mainActivity;

    private String dish_img_frag = "";
    private String dish_name_frag = "";
    private String dish_descript_frag = "";
    private String dish_weight_frag = "";
    private SpannableStringBuilder dish_price_frag = null;

    private int dish_count = 0;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private NetworkImageView dish_img;
    private TextViewPlus dish_name;
    private TextViewPlus dish_descript;
    private TextViewPlus dish_price;
    private TextViewPlus dish_weight;
    private Button inBag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dish_descript_fragment, container, false);
        dish_img = (NetworkImageView) rootView.findViewById(R.id.dish_img_frag);
        dish_name = (TextViewPlus) rootView.findViewById(R.id.dish_name_frag);
        dish_descript = (TextViewPlus) rootView.findViewById(R.id.dish_descript_frag);
        dish_price = (TextViewPlus) rootView.findViewById(R.id.dish_price_frag);
        dish_weight = (TextViewPlus) rootView.findViewById(R.id.dish_weight_frag);
        inBag = (Button) rootView.findViewById(R.id.inBag);

        dish_img.setImageUrl(dish_img_frag, imageLoader);
        dish_name.setText(dish_name_frag);
        dish_descript.setText(dish_descript_frag);
        dish_price.setText(dish_price_frag);
        dish_weight.setText(dish_weight_frag + " гр.");

        rootView.findViewById(R.id.count_button1).setOnClickListener(this);
        rootView.findViewById(R.id.count_button2).setOnClickListener(this);
        rootView.findViewById(R.id.count_button3).setOnClickListener(this);
        rootView.findViewById(R.id.count_button4).setOnClickListener(this);
        rootView.findViewById(R.id.count_button5).setOnClickListener(this);
        rootView.findViewById(R.id.count_button6).setOnClickListener(this);
        rootView.findViewById(R.id.count_button7).setOnClickListener(this);
        rootView.findViewById(R.id.count_button8).setOnClickListener(this);
        rootView.findViewById(R.id.count_button9).setOnClickListener(this);
        rootView.findViewById(R.id.count_button10).setOnClickListener(this);
        inBag.setOnClickListener(this);

        selectOnButton((Button) rootView.findViewById(R.id.count_button1));
        return rootView;
    }

    public void setDish_img_frag(String dish_img_frag) {
        this.dish_img_frag = dish_img_frag;
    }

    public void setDish_name_frag(String dish_name_frag) {
        this.dish_name_frag = dish_name_frag;
    }

    public void setDish_descript_frag(String dish_descript_frag) {
        this.dish_descript_frag = dish_descript_frag;
    }

    public void setDish_weight_frag(String dish_weight_frag) {
        this.dish_weight_frag = dish_weight_frag;
    }

    public void setDish_price_frag(SpannableStringBuilder dish_price_frag) {
        this.dish_price_frag = dish_price_frag;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() != R.id.inBag) {
            if (selectDishCount != v) {
                selectOffButton(selectDishCount);
                selectOnButton((Button) v);
            }
        } else
        {
            onDish.setCountOrder(Integer.valueOf(String.valueOf(selectDishCount.getText())));
            AppController.getInstance().addInBag(onDish);
            mainActivity.updateBagPrice();
        }
    }

    public void selectOnButton(Button button)
    {
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            button.setBackground(getResources().getDrawable(R.drawable.rounded_button_on_select));
        }

        button.setTextColor(getResources().getColor(R.color.md_black_1000));
*/
        TransitionDrawable drawable = (TransitionDrawable) button.getBackground();
        drawable.startTransition(100);

        ObjectAnimator colorAnim = ObjectAnimator.ofInt(
                button,
                "textColor",
                Color.WHITE,
                Color.BLACK);
        colorAnim.setDuration(100);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();

        selectDishCount = button;
    }
    public void selectOffButton(Button button)
    {
        TransitionDrawable drawable = (TransitionDrawable) button.getBackground();
        drawable.reverseTransition(100);

        ObjectAnimator colorAnim = ObjectAnimator.ofInt(
                button,
                "textColor",
                Color.BLACK,
                Color.WHITE);
        colorAnim.setDuration(100);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
    }

    public void setOnDish(Dish onDish) {
        this.onDish = onDish;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
