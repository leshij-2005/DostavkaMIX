package ru.dostavkamix.denis.dostavkamix;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import ru.dostavkamix.denis.dostavkamix.Fragments.DeliveryOrderFragment;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    private Button OnSelect = null;

    private Button select_left;
    private Button select_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //final Toolbar toolbar_order = (Toolbar) findViewById(R.id.toolbar_order);
        //setSupportActionBar(toolbar_order);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        select_left = (Button) findViewById(R.id.select_left);
        select_right = (Button) findViewById(R.id.select_right);

        selectOnButton(select_left);

        select_left.setOnClickListener(this);
        select_right.setOnClickListener(this);
    }

    public void selectOnButton(Button button)
    {
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

        OnSelect = button;
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

    @Override
    public void onClick(View v) {
        Log.d("json", "click");
        if (OnSelect != v) {
            selectOffButton(OnSelect);
            selectOnButton((Button) v);

            if(v.getId() == R.id.select_right)
            {
                findViewById(R.id.address).setVisibility(View.GONE);
                findViewById(R.id.view_street).setVisibility(View.GONE);
                findViewById(R.id.house).setVisibility(View.GONE);
                findViewById(R.id.apartament).setVisibility(View.GONE);
                findViewById(R.id.scroll_l).requestLayout();

            } else
            {
                findViewById(R.id.address).setVisibility(View.VISIBLE);
                findViewById(R.id.view_street).setVisibility(View.VISIBLE);
                findViewById(R.id.house).setVisibility(View.VISIBLE);
                findViewById(R.id.apartament).setVisibility(View.VISIBLE);
                findViewById(R.id.scroll_l).requestLayout();
            }
        }
    }


}
