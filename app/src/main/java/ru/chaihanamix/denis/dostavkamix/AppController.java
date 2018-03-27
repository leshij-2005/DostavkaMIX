package ru.chaihanamix.denis.dostavkamix;

import android.Manifest;
import android.app.Application;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import me.drakeet.materialdialog.MaterialDialog;
import ru.chaihanamix.denis.dostavkamix.CustomView.CustomTypefaceSpan;
import ru.chaihanamix.denis.dostavkamix.CustomView.LruBitmapCache;
import ru.chaihanamix.denis.dostavkamix.CustomView.TextViewPlus;
import ru.chaihanamix.denis.dostavkamix.Dish.Dish;
import ru.chaihanamix.denis.dostavkamix.Fragments.AboutFragment;
import ru.chaihanamix.denis.dostavkamix.Fragments.ActionListFragment;
import ru.chaihanamix.denis.dostavkamix.Fragments.ActionPagerFragment;
import ru.chaihanamix.denis.dostavkamix.Fragments.ConditionFragment;
import ru.chaihanamix.denis.dostavkamix.Fragments.InfoFragment;
import ru.chaihanamix.denis.dostavkamix.Fragments.ReviewListFragment;
import ru.chaihanamix.denis.dostavkamix.Fragments.ReviewPagerFragment;


/**
 * Created by den on 20.01.16.
 */
public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    private static final String prefName = "pref";
    public SharedPreferences preferences;
    public SharedPreferences.Editor editPref;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    public ArrayList<Dish> inBag = new ArrayList<Dish>();

    private int sale = 0;
    private int withSale = 0;
    private String delivery = "courier";
    private String deliveryTime = "now";
    private Date deliveryDate = null;

    private MainActivity mainActivity = null;


    Typeface fontRub = null;
    Typeface fontReg = null;

    boolean isShowDescriptFrag = false;
    boolean isShowMenuList = true;

    Dialog menu_logo;
    TextViewPlus menu_item_1;
    TextViewPlus menu_item_2;
    TextViewPlus menu_item_3;
    TextViewPlus menu_item_4;
    TextViewPlus menu_item_5;
    TextViewPlus menu_item_6;
    TextViewPlus menu_item_7;
    RelativeLayout menu_item_8;
    TextViewPlus selectItem;

    Fragment aboutFragment;
    Fragment conditionFragment;
    Fragment infoFragment;
    ListFragment actionListFragment;
    ListFragment reviewListFragment;
    ActionPagerFragment actionFragment;
    ReviewPagerFragment reviewFragment;

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public void setWithSale(int withSale) {
        this.withSale = withSale;
    }

    public int getSale() {
        return sale;
    }

    public int getWithSale() {
        return withSale;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;

        this.calculateSale();
    }

    public String getDelivery() {
        return this.delivery;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;

        this.calculateSale();
    }

    public String getDeliveryTime() {
        return this.deliveryTime;
    }

    public void setDeliveryDate(String deliveryDate) {
        Locale locale = new Locale("ru");
        SimpleDateFormat format = new SimpleDateFormat ("dd.MM.yyyy HH:mm", locale);
        try {
            this.deliveryDate = format.parse(deliveryDate);
        } catch (ParseException e) {
            this.deliveryDate = null;
        }

        this.calculateSale();
    }

    public Date getDeliveryDate() {
        return this.deliveryDate;
    }

    public void setIsShowDescriptFrag(boolean isShowDescriptFrag) {
        this.isShowDescriptFrag = isShowDescriptFrag;
    }

    public void setIsShowMenuList(boolean isShowMenuList) {
        this.isShowMenuList = isShowMenuList;
    }

    private static AppController mInstance;

    public void initMenu() {
        menu_logo = new Dialog(mainActivity);
        menu_logo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        menu_logo.setContentView(R.layout.top_menu);
        menu_logo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowLay = menu_logo.getWindow().getAttributes();
        windowLay.gravity = Gravity.TOP;
        windowLay.dimAmount = 0.0f;
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
        menu_item_8 = (RelativeLayout) menu_logo.findViewById(R.id.menu_item_8);

        clickMenu cm = new clickMenu();

        menu_item_1.setOnClickListener(cm);
        menu_item_2.setOnClickListener(cm);
        menu_item_3.setOnClickListener(cm);
        menu_item_4.setOnClickListener(cm);
        menu_item_5.setOnClickListener(cm);
        menu_item_6.setOnClickListener(cm);
        menu_item_7.setOnClickListener(cm);
        menu_item_8.setOnClickListener(cm);


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
        preferences = getSharedPreferences(prefName, MODE_PRIVATE);
        editPref = preferences.edit();


        aboutFragment = new AboutFragment();
        conditionFragment = new ConditionFragment();
        infoFragment = new InfoFragment();
        actionListFragment = new ActionListFragment();
        reviewListFragment = new ReviewListFragment();
        actionFragment = new ActionPagerFragment();
        reviewFragment = new ReviewPagerFragment();

    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        initMenu();
    }

    public SpannableStringBuilder addRuble(String s) {
        if (s.length() < 4) {
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

    public void removeInBad(Dish dish) {
        if (getInBag() != null) {
            for (int i = 0; i < getInBag().size(); i++) {
                if (getInBag().get(i).getIdDish() == dish.getIdDish()) {
                    getInBag().remove(i);
                    mainActivity.updateBagPrice();
                    Log.d(TAG, "удаление из корзины");
                    Log.d(TAG, "в корзине " + getInBag().size() + " блюд");
                    return;
                }
            }
        } else {
            Log.d(TAG, "блюда в корзине нет");
            Log.d(TAG, "в корзине " + getInBag().size() + " блюд");
        }
    }

    public void addInBag(Dish dish) {

        for (int i = 0; i < getInBag().size(); i++) {
            if (getInBag().get(i).getIdDish() == dish.getIdDish()) {
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

    public boolean onBag(Dish dish) {
        for (int i = 0; i < getInBag().size(); i++) {
            if (getInBag().get(i).getIdDish() == dish.getIdDish()) {
                Log.d(TAG, "блюдо есть в корзине");
                return true;
            }
        }
        return false;
    }

    public int getBagPrice() {
        int result = 0;

        for (int i = 0; i < inBag.size(); i++) {
            Dish dish = inBag.get(i);

            result += dish.getPriceDish() * dish.getCountOrder();
        }

        return result;
    }

    public int getBagPriceWithoutPromo() {
        int result = 0;

        for (int i = 0; i < inBag.size(); i++) {
            Dish dish = inBag.get(i);

            result += dish.isPromo() ? 0 : (dish.getPriceDish() * dish.getCountOrder());
        }

        return result;
    }

    public void calculateSale() {
        int s = 0;
        String delivery = AppController.getInstance().getDelivery();
        String deliveryTime = AppController.getInstance().getDeliveryTime();
        Date deliveryDate = AppController.getInstance().getDeliveryDate();
        Date now = new Date();

        if (delivery == "self") {
            s = 10;
        }

        if (deliveryTime == "concrete" && deliveryDate != null) {
            long offset = (deliveryDate.getTime() - now.getTime()) / 3600000;

            if (offset >= 4) {
                s = s + 10;
            }
        }

        AppController.getInstance().setSale(s);
    }

    public class clickMenu implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            selectMenu(Integer.valueOf(String.valueOf(v.getTag())));
            menu_logo.dismiss();
        }
    }

    private void selectMenu(TextViewPlus v) {
        if (selectItem != v) {
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

    public void selectMenu(int position) {
        switch (position) {
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
                mainActivity.ft = mainActivity.getFragmentManager().beginTransaction();
                mainActivity.ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                mainActivity.ft.replace(R.id.frame_fragment, actionListFragment);
                mainActivity.ft.addToBackStack(null);
                mainActivity.ft.commit();
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
                mainActivity.ft = mainActivity.getFragmentManager().beginTransaction();
                mainActivity.ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                mainActivity.ft.replace(R.id.frame_fragment, reviewListFragment);
                mainActivity.ft.addToBackStack(null);
                mainActivity.ft.commit();
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
            case 8:
                Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "432222"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(call);
                break;
            default:
                break;

        }
    }

    public void selectMenu(int position, boolean is)
    {
        switch (position)
        {
            case 1:
                selectMenu(menu_item_1);
                break;
            case 2:
                selectMenu(menu_item_2);
                break;
            case 3:
                selectMenu(menu_item_3);
                break;
            case 4:
                selectMenu(menu_item_4);
                break;
            case 5:
                selectMenu(menu_item_5);
                break;
            case 6:
                selectMenu(menu_item_6);
                break;
            case 7:
                selectMenu(menu_item_7);
                break;
            default:
                break;

        }
    }
}
