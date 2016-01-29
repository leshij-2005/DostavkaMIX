package ru.dostavkamix.denis.dostavkamix;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ru.dostavkamix.denis.dostavkamix.CustomView.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.Dish.Catalog;
import ru.dostavkamix.denis.dostavkamix.Dish.Category;
import ru.dostavkamix.denis.dostavkamix.Dish.Dish;
import ru.dostavkamix.denis.dostavkamix.Fragments.BagFragment;
import ru.dostavkamix.denis.dostavkamix.Fragments.FragmentOrder;
import ru.dostavkamix.denis.dostavkamix.Fragments.InOrderDialog;
import ru.dostavkamix.denis.dostavkamix.Fragments.TopMenu;
import ru.dostavkamix.denis.dostavkamix.Fragments.dishListFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    boolean isReadyDish = false;


    ArrayList<Dish> dishs = new ArrayList<>();
    ArrayList<Category> categories = new ArrayList<>();
    ArrayList<Catalog> catalogs = new ArrayList<>();

    private Drawer slideMuneDrawer = null;
    AppCompatImageView logo = null;
    AppCompatImageView icon_menu_up = null;
    AppCompatImageView icon_menu_down = null;
    TextViewPlus bag_price = null;
    //ListFragment dishList = null;
    //Fragment topMenu = null;
    //ListView listMain = null;

    BoxAdapter boxAdapter = null;
    BagAdapter bagAdapter = null;

    //Мне очень стыдно...
    ArrayList<TextViewPlus> menuCategoryText = new ArrayList<>();


    TextViewPlus selectText = null;

    private ListFragment MenuFragment;
    private BagFragment bagFrag;
    private FragmentTransaction ft;
    private Fragment OrderFragment;
    DialogFragment dlg1;
    MainActivity mAct;


    public void updateBagPrice()
    {
        bag_price.setText(AppController.getInstance().addRuble(String.valueOf(AppController.getInstance().getBagPrice())));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        MenuFragment = new dishListFragment();
        OrderFragment = new FragmentOrder();
        bagFrag = new BagFragment();

        ft = getFragmentManager().beginTransaction();
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out);

        ft.replace(R.id.frame_fragment, MenuFragment);
        ft.addToBackStack(null);
        ft.commit();

        logo = (AppCompatImageView) findViewById(R.id.logo);
        bag_price = (TextViewPlus) findViewById(R.id.toolbar_price);
        //topMenu = new TopMenu();
        mAct = this;
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("json", "open bag");
                ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.fade_in, R.animator.slide_out_left);
                ft.replace(R.id.frame_fragment, bagFrag);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        new ParseTask().execute();

        slideMuneDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDisplayBelowToolbar(true)
                .withSliderBackgroundColorRes(R.color.slide_background_color)
                .withCustomView(View.inflate(this, R.layout.slide_menu, null))
                .build();

        //Очень очень стыдно...


        menuCategoryText.add((TextViewPlus) findViewById(R.id.category0_0));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category0_1));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category0_2));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category0_3));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category0_4));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category0_5));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category0_6));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category1_0));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category1_1));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category1_2));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category1_3));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category1_4));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category1_5));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category1_6));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category1_7));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category1_8));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category2_0));
        menuCategoryText.add((TextViewPlus) findViewById(R.id.category2_1));
        for (int i = 0; i < menuCategoryText.size(); i++) {
            menuCategoryText.get(i).setOnClickListener(this);
        }

        selectCategory(menuCategoryText.get(0));
        dlg1 = new InOrderDialog();

        AppController.getInstance().setMainActivity(this);

    }


    @Override
    public void onBackPressed() {
        Log.d("json", "click Back");
        if (slideMuneDrawer != null && slideMuneDrawer.isDrawerOpen()) {
            slideMuneDrawer.closeDrawer();
        } else
        if(AppController.getInstance().isShowDescriptFrag()) {
            Log.d("json", "back to Menu");
            ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.animator.fade_in, R.animator.slide_out_left);
            ft.replace(R.id.frame_fragment, MenuFragment);
            AppController.getInstance().setIsShowMenuList(true);
            ft.addToBackStack(null);
            AppController.getInstance().setIsShowDescriptFrag(false);
            ft.commit();
        }
        else
        {
            Log.d("json", "super back");
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {


        Log.d("json", "click in onClick");

        selectCategory((TextViewPlus) v);
        slideMuneDrawer.closeDrawer();
    }

    private void selectCategory(final TextViewPlus v)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!isReadyDish) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(selectText != v)
                        {
                            try {
                                selectText.setCustomFont(getApplicationContext(), "fonts/GothaProReg.otf");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.d("json", "Новая кнопка");

                            selectText = (TextViewPlus) v;
                            selectText.setCustomFont(getApplicationContext(), "fonts/GothaProBol.otf");
                            Category categ = getCategoryIdOfTag(String.valueOf(selectText.getTag()));
                            if(categ != null)
                            {
                                Log.d("json", "Category ID: " + categ.getIdCategory());
                            } else Log.d("json", "Not found category =(");
                            updateListDish(getDishOfCategory(categ, dishs));

                        } else Log.d("json", "Та же кнопка");
                    }
                });
            }
        }).start();

    }


    private class ParseTask extends AsyncTask<Void, Void, String>
    {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL("http://chaihanamix.ru/server/export.json");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }
                resultJson = buffer.toString();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected synchronized void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            Log.d("json", "Start Parse");

            JSONObject dataJsonObj = null;


            try
            {
                dataJsonObj = new JSONObject(strJson);
                JSONArray catal = dataJsonObj.getJSONArray("catalogs");

                for (int i = 0; i < catal.length(); i++) {
                    JSONObject catalog = catal.getJSONObject(i);

                    catalogs.add(new Catalog(
                            catalog.getInt("id"),
                            catalog.getString("title"),
                            catalog.getString("image")
                    ));

                    JSONArray categors = catalog.getJSONArray("categories");
                    for (int j = 0; j < categors.length(); j++) {
                        JSONObject category = categors.getJSONObject(j);

                        categories.add(new Category(
                                catalog.getInt("id"),
                                catalog.getString("title"),
                                catalog.getString("image"),
                                category.getInt("id"),
                                category.getString("title"),
                                category.getString("image")
                        ));

                        JSONArray Jdishs = category.getJSONArray("items");
                        for (int k = 0; k < Jdishs.length(); k++) {
                            JSONObject dish = Jdishs.getJSONObject(k);

                            dishs.add(new Dish(
                                    catalog.getInt("id"),
                                    catalog.getString("title"),
                                    catalog.getString("image"),
                                    category.getInt("id"),
                                    category.getString("title"),
                                    category.getString("image"),
                                    dish.getInt("id"),
                                    dish.getInt("price"),
                                    dish.getString("weight"),
                                    dish.getString("title"),
                                    dish.getString("content"),
                                    dish.getString("image"),
                                    k

                            ));
                        }
                    }
                }

            } catch (Exception e)
            {
                e.printStackTrace();
                Log.d("json", "Exception Parse");
            }

            Log.d("json", "Stop Parse");
            isReadyDish = true;

        }
    }

    public ArrayList<Dish> getDishOfCategory(Category category, ArrayList<Dish> dishL) {
        Log.d("json", "Начинаю сортировку...");
        Log.d("json", "Жду готовности списка...");
        Log.d("json", "Список готов! Сортирую...");
        ArrayList<Dish> result = new ArrayList<>();
        for (int i = 0; i < dishL.size(); i++) {
            if(dishL.get(i).getIdCategory() == category.getIdCategory()) result.add(dishL.get(i));
        }
        return result;
    }

    private void updateListDish(ArrayList<Dish> aDish) {
        Log.d("json", "Готовлю фрагмент для обновления...");
        boxAdapter = new BoxAdapter(this, aDish, ft);
        MenuFragment.setListAdapter(boxAdapter);
        if(!AppController.getInstance().isShowMenuList()) {
            Log.d("json", "back to Menu");
            ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.animator.fade_in, R.animator.slide_out_left);
            ft.replace(R.id.frame_fragment, MenuFragment);
            AppController.getInstance().setIsShowMenuList(true);
            ft.addToBackStack(null);
            AppController.getInstance().setIsShowDescriptFrag(false);
            ft.commit();
        }
        Log.d("json", "Фрагмент обновлен!");
    }

    public Category getCategoryIdOfTag(String t)
    {
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getIdCategory() == Integer.valueOf(t))
            {
                return categories.get(i);
            }
        }
        return null;
    }

}
