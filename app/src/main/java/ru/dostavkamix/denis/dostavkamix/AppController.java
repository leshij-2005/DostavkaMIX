package ru.dostavkamix.denis.dostavkamix;

import android.app.Application;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;
import ru.dostavkamix.denis.dostavkamix.CustomView.CustomTypefaceSpan;
import ru.dostavkamix.denis.dostavkamix.CustomView.LruBitmapCache;
import ru.dostavkamix.denis.dostavkamix.CustomView.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.Dish.Dish;
import ru.dostavkamix.denis.dostavkamix.Fragments.AboutFragment;
import ru.dostavkamix.denis.dostavkamix.Fragments.ConditionFragment;
import ru.dostavkamix.denis.dostavkamix.Fragments.InfoFragment;


/**
 * Created by den on 20.01.16.
 */
public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    public ArrayList<Dish> inBag = new ArrayList<Dish>();

    private int sale = 0;
    private int withoutSale = 0;
    private int withSale = 0;

    private MainActivity mainActivity = null;
    private ListFragment MenuFragment;

    private ArrayList<Fragment> stackFragment = new ArrayList<Fragment>();


    Typeface fontRub = null;
    Typeface fontReg = null;

    boolean isShowDescriptFrag = false;
    boolean isShowMenuList = true;

    public MaterialDialog inposOrder;

    Dialog menu_logo;
    TextViewPlus menu_item_1;
    TextViewPlus menu_item_2;
    TextViewPlus menu_item_3;
    TextViewPlus menu_item_4;
    TextViewPlus menu_item_5;
    TextViewPlus menu_item_6;
    TextViewPlus menu_item_7;
    TextViewPlus selectItem;

    Fragment aboutFragment;
    Fragment conditionFragment;
    Fragment infoFragment;

    public MainActivity getMainActivity() {
        return mainActivity;
    }

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

    public void inicialiseMunu()
    {
        menu_logo = new Dialog(mainActivity);
        menu_logo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        menu_logo.setContentView(R.layout.top_menu);
        menu_logo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowLay = menu_logo.getWindow().getAttributes();
        windowLay.gravity = Gravity.TOP;
        windowLay.width = WindowManager.LayoutParams.FILL_PARENT;
        menu_logo.getWindow().getAttributes().verticalMargin = 0.1F;
        menu_logo.getWindow().getAttributes().horizontalMargin = 0.02F;
        menu_logo.getWindow().setAttributes(windowLay);
        menu_logo.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mainActivity.arrow_up_t.setVisibility(View.INVISIBLE);
                mainActivity.arrow_down_t.setVisibility(View.VISIBLE);
            }
        });
        menu_logo.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                mainActivity.arrow_down_t.setVisibility(View.INVISIBLE);
                mainActivity.arrow_up_t.setVisibility(View.VISIBLE);
            }
        });

        menu_item_1 = (TextViewPlus) menu_logo.findViewById(R.id.menu_item_1);
        menu_item_2 = (TextViewPlus) menu_logo.findViewById(R.id.menu_item_2);
        menu_item_3 = (TextViewPlus) menu_logo.findViewById(R.id.menu_item_3);
        menu_item_4 = (TextViewPlus) menu_logo.findViewById(R.id.menu_item_4);
        menu_item_5 = (TextViewPlus) menu_logo.findViewById(R.id.menu_item_5);
        menu_item_6 = (TextViewPlus) menu_logo.findViewById(R.id.menu_item_6);
        menu_item_7 = (TextViewPlus) menu_logo.findViewById(R.id.menu_item_7);

        clickMenu cm = new clickMenu();

        menu_item_1.setOnClickListener(cm);
        menu_item_2.setOnClickListener(cm);
        menu_item_3.setOnClickListener(cm);
        menu_item_4.setOnClickListener(cm);
        menu_item_5.setOnClickListener(cm);
        menu_item_6.setOnClickListener(cm);
        menu_item_7.setOnClickListener(cm);


        mainActivity.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.arrow_down_t.getVisibility() == View.VISIBLE) {
                    menu_logo.show();
                }
            }
        });

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Log.d(TAG, "Start AppController");
        fontRub = Typeface.createFromAsset(getAssets(), "fonts/RUBSN.otf");
        fontReg = Typeface.createFromAsset(getAssets(), "fonts/GothaProReg.otf");

        aboutFragment = new AboutFragment();
        conditionFragment = new ConditionFragment();
        infoFragment = new InfoFragment();

    }

    public void setMainActivity(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        inicialiseMunu();
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

    public class clickMenu implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            selectMenu(Integer.valueOf(String.valueOf(v.getTag())));
            menu_logo.dismiss();
        }
    }

    private void selectMenu(TextViewPlus v)
    {
        if(selectItem != v)
        {
            try {
                selectItem.setCustomFont(getApplicationContext(), "fonts/GothaProReg.otf");

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("json", "Новая кнопка");

            selectItem = v;
            selectItem.setCustomFont(getApplicationContext(), "fonts/GothaProBol.otf");
        }
    }

    public void selectMenu(int position)
    {
        switch (position)
        {
            case 1:
                selectMenu(menu_item_1);
                mainActivity.ft = mainActivity.getFragmentManager().beginTransaction();
                mainActivity.ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                mainActivity.ft.replace(R.id.frame_fragment, mainActivity.MenuFragment);
                mainActivity.ft.addToBackStack(null);
                mainActivity.ft.commit();
                break;
            case 2:
                selectMenu(menu_item_2);
                mainActivity.ft = mainActivity.getFragmentManager().beginTransaction();
                mainActivity.ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                mainActivity.ft.replace(R.id.frame_fragment, conditionFragment);
                mainActivity.ft.addToBackStack(null);
                mainActivity.ft.commit();
                break;
            case 3:
                selectMenu(menu_item_3);
                break;
            case 4:
                selectMenu(menu_item_4);
                mainActivity.ft = mainActivity.getFragmentManager().beginTransaction();
                mainActivity.ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                mainActivity.ft.replace(R.id.frame_fragment, infoFragment);
                mainActivity.ft.addToBackStack(null);
                mainActivity.ft.commit();
                break;
            case 5:
                selectMenu(menu_item_5);
                break;
            case 6:
                selectMenu(menu_item_6);
                mainActivity.ft = mainActivity.getFragmentManager().beginTransaction();
                mainActivity.ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                mainActivity.ft.replace(R.id.frame_fragment, aboutFragment);
                mainActivity.ft.addToBackStack(null);
                mainActivity.ft.commit();
                break;
            case 7:
                selectMenu(menu_item_7);
                mainActivity.ft = mainActivity.getFragmentManager().beginTransaction();
                mainActivity.ft.setCustomAnimations(R.animator.fade_in, R.animator.slide_out_left, R.animator.fade_in, R.animator.slide_out_left);
                mainActivity.ft.replace(R.id.frame_fragment, mainActivity.bagFrag);
                mainActivity.ft.addToBackStack(null);
                mainActivity.ft.commit();
                break;
            default:
                break;

        }
    }
}
