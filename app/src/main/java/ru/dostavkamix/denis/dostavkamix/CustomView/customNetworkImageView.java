package ru.dostavkamix.denis.dostavkamix.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by den on 27.01.16.
 */
public class customNetworkImageView extends NetworkImageView {
    public customNetworkImageView(Context context) {
        super(context);
    }

    public customNetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public customNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        float radius = 15.0f;
        float padding = radius / 2;
        int w = this.getWidth();
        int h = this.getHeight();
        clipPath.addRoundRect(new RectF(padding, padding, w - padding, h - padding), radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
