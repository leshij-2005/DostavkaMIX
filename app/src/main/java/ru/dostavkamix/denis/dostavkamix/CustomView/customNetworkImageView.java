package ru.dostavkamix.denis.dostavkamix.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by den on 27.01.16.
 */
public class customNetworkImageView extends NetworkImageView {

    private float mRadius = 0.0f;

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

        /*
        Path clipPath = new Path();
        float radius = 10.0f;
        float padding = radius / 2;
        int w = this.getWidth();
        int h = this.getHeight();
        clipPath.addRoundRect(new RectF(padding, padding, w - padding, h - padding), radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        Log.d("custom", String.valueOf(this.getWidth()));
        Log.d("custom", String.valueOf(this.getHeight()));
        */

        try {
            Drawable drawable = getDrawable();

            if (drawable == null) {
                return;
            }

            if (getWidth() == 0 || getHeight() == 0) {
                return;
            }

            Bitmap b = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                    && drawable instanceof VectorDrawable) {
                ((VectorDrawable) drawable).draw(canvas);
                b = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas();
                c.setBitmap(b);
                drawable.draw(c);
            }

            else

                b = ((BitmapDrawable) drawable).getBitmap();


            Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

            int w = getWidth(), h = getHeight();

            Bitmap roundBitmap =  getCroppedBitmap(bitmap, w, 10.0f);
            canvas.drawBitmap(roundBitmap, 0,0, null);
        } catch (Exception e)
        {
            Log.d("custom", "Exception   " + e.getClass().getSimpleName());
            super.onDraw(canvas);
        }

    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int width, float radius) {
        Bitmap sbmp;
        if(bmp.getWidth() != width || bmp.getHeight() != width)
            sbmp = Bitmap.createScaledBitmap(bmp, width, width, false);
        else
            sbmp = bmp;
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
                sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());
        final RectF rectF = new RectF(0.0f, 0.0f, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        //canvas.drawCircle(sbmp.getWidth() / 2+0.7f, sbmp.getHeight() / 2+0.7f,
        //        sbmp.getWidth() / 2+0.1f, paint);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);


        return output;
    }

    private void setRadius(Context ctx, AttributeSet attr)
    {
        TypedArray a = ctx.obtainStyledAttributes(attr, R.styleable.customNetworkImageView);
        float radius = a.getFloat(R.styleable.customNetworkImageView_custom_radius, 0.0f);
        this.mRadius = radius;
        a.recycle();
    }

    public void setRadius(float radius)
    {
        this.mRadius = radius;
    }
}
