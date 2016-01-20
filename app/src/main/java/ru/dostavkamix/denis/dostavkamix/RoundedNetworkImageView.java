package ru.dostavkamix.denis.dostavkamix;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by den on 20.01.16.
 */
public class RoundedNetworkImageView extends NetworkImageView {
    private float mCornerRadius = 10;

    public RoundedNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RoundedNetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public RoundedNetworkImageView(Context context) {
        super(context, null);
    }


    public void setCornerRadius(float cornerRadius) {
        mCornerRadius = cornerRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Round some corners betch!
        super.onDraw(canvas);
    }
}
