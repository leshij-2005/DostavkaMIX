package ru.dostavkamix.denis.dostavkamix;

import android.app.Application;
import android.app.Fragment;
import android.app.ListFragment;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import ru.dostavkamix.denis.dostavkamix.CustomView.CustomTypefaceSpan;
import ru.dostavkamix.denis.dostavkamix.CustomView.LruBitmapCache;
import ru.dostavkamix.denis.dostavkamix.Dish.Dish;

/**
 * Created by den on 20.01.16.
 */
public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    public ArrayList<Dish> inBag = new ArrayList<>();

    private int sale = 0;
    private int withoutSale = 0;
    private int withSale = 0;

    private MainActivity mainActivity = null;
    private ListFragment MenuFragment;


    Typeface fontRub = null;
    Typeface fontReg = null;

    boolean isShowDescriptFrag = false;
    boolean isShowMenuList = true;

    public void setSale(int sale) {
        this.sale = sale;
    }

    public void setWithoutSale(int withoutSale) {
        this.withoutSale = withoutSale;
    }

    public void setWithSale(int withSale) {
        this.withSale = withSale;
    }

    public int getSale() {
        return sale;
    }

    public int getWithoutSale() {
        return withoutSale;
    }

    public int getWithSale() {
        return withSale;
    }

    public boolean isShowDescriptFrag() {
        return isShowDescriptFrag;
    }

    public boolean isShowMenuList() {
        return isShowMenuList;
    }

    public void setIsShowDescriptFrag(boolean isShowDescriptFrag) {
        this.isShowDescriptFrag = isShowDescriptFrag;
    }

    public void setIsShowMenuList(boolean isShowMenuList) {
        this.isShowMenuList = isShowMenuList;
    }

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Log.d(TAG, "Start AppController");
        fontRub = Typeface.createFromAsset(getAssets(), "fonts/RUBSN.otf");
        fontReg = Typeface.createFromAsset(getAssets(), "fonts/GothaProReg.otf");
    }

    public void setMainActivity(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
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
                    mainActivity.updateBagPrice();
                    Log.d(TAG, "удаление из корзины");
                    Log.d(TAG, "в корзине " + getInBag().size() + " блюд");
                    return;
                }
            }
        } else
        {
            Log.d(TAG, "блюда в корзине нет");
            Log.d(TAG, "в корзине " + getInBag().size() + " блюд");
        }
    }

    public void addInBag(Dish dish)
    {

        for (int i = 0; i < getInBag().size(); i++) {
            if(getInBag().get(i).getIdDish() == dish.getIdDish())
            {
                Log.d(TAG, "блюдо уже есть в корзине  " + getInBag().get(i).getIdDish() + " = " + dish.getIdDish());

                    getInBag().get(i).setCountOrder(dish.getCountOrder());
                    mainActivity.updateBagPrice();
                    Log.d(TAG, "изменил порции на " + getInBag().get(i).getCountOrder());
                    Log.d(TAG, "в корзине " + getInBag().size() + " блюд");
                    return;
            }
        }

            this.getInBag().add(dish);
            mainActivity.updateBagPrice();
            Log.d(TAG, "добавил в карзину");
            Log.d(TAG, "в корзине " + getInBag().size() + " блюд");

    }

    public void updateBag()
    {
        withoutSale = getBagPrice();
        if(withoutSale > 500) sale = 5;
        else if(withoutSale > 1500) sale = 10;
        else if(withoutSale > 2500) sale = 15;
        withSale = withoutSale - ((withoutSale / 100) * sale);
        mainActivity.updateBagPrice();
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
            return false;
    }

    public int getBagPrice()
    {
        int result = 0;
        for (int i = 0; i < inBag.size(); i++) {
            result += inBag.get(i).getPriceDish() * inBag.get(i).getCountOrder();
        }
        return result;
    }
}
