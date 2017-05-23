package ru.chaihanamix.denis.dostavkamix.CustomView;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.widget.Button;

import ru.chaihanamix.denis.dostavkamix.R;

/**
 * Created by den on 25.01.2016.
 */
public class priceButton extends Button {
    private boolean checked = false;
    private ObjectAnimator textInAnim = null;
    private ObjectAnimator textOutAnim = null;
    private TransitionDrawable trans = null;

    public priceButton(Context context) {
        super(context);
        textInAnim = ObjectAnimator.ofInt(
                this,
                "textColor",
                Color.TRANSPARENT,
                R.color.accent_color);
        textInAnim.setDuration(100);
        textInAnim.setEvaluator(new ArgbEvaluator());

        textOutAnim = ObjectAnimator.ofInt(
                this,
                "textColor",
                R.color.accent_color,
                Color.TRANSPARENT);
        textOutAnim.setDuration(100);
        textOutAnim.setEvaluator(new ArgbEvaluator());
        trans = (TransitionDrawable) this.getBackground();
    }

    public priceButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        textInAnim = ObjectAnimator.ofInt(
                this,
                "textColor",
                Color.TRANSPARENT,
                R.color.accent_color);
        textInAnim.setDuration(100);
        textInAnim.setEvaluator(new ArgbEvaluator());

        textOutAnim = ObjectAnimator.ofInt(
                this,
                "textColor",
                R.color.accent_color,
                Color.TRANSPARENT);
        textOutAnim.setDuration(100);
        textOutAnim.setEvaluator(new ArgbEvaluator());
        trans = (TransitionDrawable) this.getBackground();
    }

    public priceButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textInAnim = ObjectAnimator.ofInt(
                this,
                "textColor",
                Color.TRANSPARENT,
                R.color.accent_color);
        textInAnim.setDuration(100);
        textInAnim.setEvaluator(new ArgbEvaluator());

        textOutAnim = ObjectAnimator.ofInt(
                this,
                "textColor",
                R.color.accent_color,
                Color.TRANSPARENT);
        textOutAnim.setDuration(100);
        textOutAnim.setEvaluator(new ArgbEvaluator());
        trans = (TransitionDrawable) this.getBackground();
    }



    public void setChecked(boolean checked) {
        //
        if(this.checked != checked)
        {
            if(checked)
            {
                textOutAnim.start();
                trans.startTransition(100);
                this.checked = checked;
            } else
            {
                textInAnim.start();
                trans.reverseTransition(100);
                this.checked = checked;
            }
        }
    }
}
