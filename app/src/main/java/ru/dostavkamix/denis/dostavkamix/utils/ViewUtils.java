package ru.dostavkamix.denis.dostavkamix.utils;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public class ViewUtils {

    private static final Animation animationFadeIn = AnimationUtils.loadAnimation(AppController.getInstance(), R.anim.fadein);
    private static final Animation animationFadeOut = AnimationUtils.loadAnimation(AppController.getInstance(), R.anim.fadeout);

    public static void focus(EditText View) {
        View.requestFocus();
        View.setSelection(View.getText().length());
    }

    public static void fade(View view, boolean enabled) {
        if(enabled) {
            if(view.getVisibility() == View.VISIBLE) return;
            view.startAnimation(animationFadeIn);
            view.setVisibility(View.VISIBLE);
        } else {
            if(view.getVisibility() == View.GONE) return;
            view.startAnimation(animationFadeOut);
            view.setVisibility(View.GONE);
        }
    }

    public static void setTextColorAnim(TextView v, int color) {
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(
                v,
                "textColor",
                v.getCurrentTextColor(),
                color);
        colorAnim.setDuration(100);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
    }
}
