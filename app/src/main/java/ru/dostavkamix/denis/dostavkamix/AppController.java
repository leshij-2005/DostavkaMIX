package ru.dostavkamix.denis.dostavkamix;

import android.Manifest;
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
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.ActivityCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import ru.dostavkamix.denis.dostavkamix.Activitys.MainActivity;
import ru.dostavkamix.denis.dostavkamix.Custom.CustomTypefaceSpan;
import ru.dostavkamix.denis.dostavkamix.Custom.LruBitmapCache;
import ru.dostavkamix.denis.dostavkamix.Custom.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.Custom.blurbehind.BlurBehind;
import ru.dostavkamix.denis.dostavkamix.Objects.Dish;
import ru.dostavkamix.denis.dostavkamix.Fragments.AboutFragment;
import ru.dostavkamix.denis.dostavkamix.Fragments.ActionListFragment;
import ru.dostavkamix.denis.dostavkamix.Fragments.ActionPagerFragment;
import ru.dostavkamix.denis.dostavkamix.Fragments.ConditionFragment;
import ru.dostavkamix.denis.dostavkamix.Fragments.InfoFragment;
import ru.dostavkamix.denis.dostavkamix.Fragments.ReviewListFragment;
import ru.dostavkamix.denis.dostavkamix.Fragments.ReviewPagerFragment;
import ru.dostavkamix.denis.dostavkamix.Objects.User;
import ru.dostavkamix.denis.dostavkamix.content.profile.ProfileFragment;
import ru.dostavkamix.denis.dostavkamix.login.LoginActivity;


/**
 * Created by den on 20.01.16.
 *
 */
public class AppController extends MultiDexApplication {

    private static AppComponent component;

    private static final String TAG = AppController.class.getSimpleName();

    private static User user = null;
    private static final String  TAG_USER_TOKEN = "user";

    private static final String prefName = "pref";
    public SharedPreferences preferences;
    public SharedPreferences.Editor editPref;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    public ArrayList<Dish> inBag = new ArrayList<>();

    private int sale = 0;
    private int withoutSale = 0;
    private int withSale = 0;

    private MainActivity mainActivity = null;


    private Typeface fontRub = null;
    private Typeface fontReg = null;

    private Dialog menu_logo;
    private TextViewPlus menu_item_1;
    private TextViewPlus menu_item_2;
    private TextViewPlus menu_item_3;
    private TextViewPlus menu_item_4;
    private TextViewPlus menu_item_5;
    private TextViewPlus menu_item_6;
    private TextViewPlus menu_item_7;
    private TextViewPlus menu_item_9;
    private TextViewPlus selectItem;

    private Fragment aboutFragment;
    private Fragment conditionFragment;
    private Fragment infoFragment;
    public ListFragment actionListFragment;
    public ListFragment reviewListFragment;
    public ActionPagerFragment actionFragment;
    public ReviewPagerFragment reviewFragment;

    public void setUser(final User user) {
        AppController.user = user;

        UserHelper.getUser(user.getToken(), this, new UserCallback() {
            @Override
            public void onSuccess(User result) {

                AppController.user = result;
                editPref.putString(TAG_USER_TOKEN, user.getToken());
                editPref.commit();
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, "onError: " + error);
            }
        });

    }

    public User getUser() {
        return user;
    }

    public String getUserToken() {
        return preferences.getString(TAG_USER_TOKEN, null);
    }

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

    private static AppController mInstance;

    private android.support.v4.app.Fragment current;

    private void inicializeMenu() {
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
        menu_item_9 = (TextViewPlus) menu_logo.findViewById(R.id.menu_item_9);
        RelativeLayout menu_item_8 = (RelativeLayout) menu_logo.findViewById(R.id.menu_item_8);

        clickMenu cm = new clickMenu();

        menu_item_1.setOnClickListener(cm);
        menu_item_2.setOnClickListener(cm);
        menu_item_3.setOnClickListener(cm);
        menu_item_4.setOnClickListener(cm);
        menu_item_5.setOnClickListener(cm);
        menu_item_6.setOnClickListener(cm);
        menu_item_7.setOnClickListener(cm);
        menu_item_8.setOnClickListener(cm);
        menu_item_9.setOnClickListener(cm);


        mainActivity.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.arrow_down_t.getVisibility() == View.VISIBLE) {
                    menu_logo.show();
                }
            }
        });


    }

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        component = buildComponent();
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

        if(preferences.getString(TAG_USER_TOKEN, null) != null) {
            UserHelper.getUser(preferences.getString(TAG_USER_TOKEN, null), this, new UserCallback() {
                @Override
                public void onSuccess(User user) {
                    setUser(user);
                    Log.d(TAG, "onSuccess: Авторизация пройдена!");
                }

                @Override
                public void onError(String error) {

                }
            });
        }

        current = new ProfileFragment();
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        inicializeMenu();

        if (preferences.getString("f", "yes").equals("yes")) {
            mainActivity.showShapeActivity();
        }

        //editPref.putString("f", "no");
        //editPref.commit();
        // =)

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

    private RequestQueue getRequestQueue() {
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

    public ArrayList<Dish> getInBag() {
        return inBag;
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

    public void updateBag() {
        withoutSale = getBagPrice();
        if (withoutSale >= 500) sale = 5;
        else if (withoutSale >= 1500) sale = 10;
        else if (withoutSale >= 2500) sale = 15;
        withSale = withoutSale - ((withoutSale / 100) * sale);
        mainActivity.updateBagPrice();
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
            result += inBag.get(i).getPriceDish() * inBag.get(i).getCountOrder();
        }
        return result;
    }

    private class clickMenu implements View.OnClickListener {

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
                mainActivity.getSupportFragmentManager().beginTransaction().remove(current).commit();
                selectMenu(menu_item_1);
                mainActivity.ft = mainActivity.getFragmentManager().beginTransaction();
                mainActivity.ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                mainActivity.ft.replace(R.id.frame_fragment, mainActivity.MenuFragment);
                mainActivity.ft.addToBackStack(null);
                mainActivity.ft.commit();
                break;
            case 2:
                mainActivity.getSupportFragmentManager().beginTransaction().remove(current).commit();

                selectMenu(menu_item_2);
                mainActivity.ft = mainActivity.getFragmentManager().beginTransaction();
                mainActivity.ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                mainActivity.ft.replace(R.id.frame_fragment, conditionFragment);
                mainActivity.ft.addToBackStack(null);
                mainActivity.ft.commit();
                break;
            case 3:
                mainActivity.getSupportFragmentManager().beginTransaction().remove(current).commit();

                selectMenu(menu_item_3);
                mainActivity.ft = mainActivity.getFragmentManager().beginTransaction();
                mainActivity.ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                mainActivity.ft.replace(R.id.frame_fragment, actionListFragment);
                mainActivity.ft.addToBackStack(null);
                mainActivity.ft.commit();
                break;
            case 4:
                mainActivity.getSupportFragmentManager().beginTransaction().remove(current).commit();

                selectMenu(menu_item_4);
                mainActivity.ft = mainActivity.getFragmentManager().beginTransaction();
                mainActivity.ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                mainActivity.ft.replace(R.id.frame_fragment, infoFragment);
                mainActivity.ft.addToBackStack(null);
                mainActivity.ft.commit();
                break;
            case 5:
                mainActivity.getSupportFragmentManager().beginTransaction().remove(current).commit();

                selectMenu(menu_item_5);
                mainActivity.ft = mainActivity.getFragmentManager().beginTransaction();
                mainActivity.ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                mainActivity.ft.replace(R.id.frame_fragment, reviewListFragment);
                mainActivity.ft.addToBackStack(null);
                mainActivity.ft.commit();
                break;
            case 6:
                mainActivity.getSupportFragmentManager().beginTransaction().remove(current).commit();

                selectMenu(menu_item_6);
                mainActivity.ft = mainActivity.getFragmentManager().beginTransaction();
                mainActivity.ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                mainActivity.ft.replace(R.id.frame_fragment, aboutFragment);
                mainActivity.ft.addToBackStack(null);
                mainActivity.ft.commit();
                break;
            case 7:

                mainActivity.getSupportFragmentManager().beginTransaction().remove(current).commit();

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
            case 9:
                if(getSharedPreferences(getString(R.string.preference_file_name), MODE_PRIVATE).getString(getString(R.string.token_key), null) != null) {
                    selectMenu(menu_item_9);
                    /*
                    mainActivity.ft = mainActivity.getFragmentManager().beginTransaction();
                    mainActivity.ft.setCustomAnimations(R.animator.fade_in, R.animator.slide_out_left, R.animator.fade_in, R.animator.slide_out_left);
                    mainActivity.ft.replace(R.id.frame_fragment, new ProfileFragment());
                    mainActivity.ft.addToBackStack(null);
                    mainActivity.ft.commit();
                    */
                    mainActivity.getFragmentManager().beginTransaction().remove(mainActivity.getFragmentManager().findFragmentById(R.id.frame_fragment)).commit();
                    mainActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_fragment, current)
                            .commitNow();
                } else {

                    BlurBehind.getInstance().execute(mainActivity, () -> {
                        Intent intent = new Intent(mainActivity, LoginActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                    }, 12);
                    //startActivity(new Intent(mainActivity, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }

                break;
            default:
                break;

        }
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

}
