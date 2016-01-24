package ru.dostavkamix.denis.dostavkamix;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import ru.dostavkamix.denis.dostavkamix.Dish.Dish;

/**
 * Created by den on 20.01.16.
 */
public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private ArrayList<Dish> inBag = new ArrayList<>();

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Log.d("json", "Start AppController");
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public ArrayList<Dish> getInBag() {
        return inBag;
    }

    public void setInBag(ArrayList<Dish> inBag) {
        this.inBag = inBag;
    }

    public void removeInBad(Dish dish)
    {
        if(getInBag() != null)
        {
            for (int i = 0; i < getInBag().size(); i++) {
                if(getInBag().get(i).getIdDish() == dish.getIdDish())
                {
                    getInBag().remove(i);
                    Log.d(TAG, "удаление из корзины");
                    return;
                }
            }
        } else Log.d(TAG, "блюда в корзине нет");
    }

    public void addInBag(Dish dish)
    {
        this.inBag.add(dish);
        Log.d(TAG, "добавил в карзину");
    }

    public boolean onBag(Dish dish)
    {
            for (int i = 0; i < getInBag().size(); i++) {
                if(getInBag().get(i).getIdDish() == dish.getIdDish())
                {
                    Log.d(TAG, "блюдо есть в корзине");
                    return true;
                }
            }
            Log.d(TAG, "блюда нет в корзине");
            return false;
    }
}
