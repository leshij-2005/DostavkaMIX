package ru.chaihanamix.denis.dostavkamix;

import me.drakeet.materialdialog.MaterialDialog;
import ru.chaihanamix.denis.dostavkamix.Push.SendToken;
import ru.chaihanamix.denis.dostavkamix.SlideMenu.SlideAdapter;

import java.util.*;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import ru.chaihanamix.denis.dostavkamix.CustomView.TextViewPlus;
import ru.chaihanamix.denis.dostavkamix.Dish.Catalog;
import ru.chaihanamix.denis.dostavkamix.Dish.Category;
import ru.chaihanamix.denis.dostavkamix.Dish.Dish;
import ru.chaihanamix.denis.dostavkamix.Fragments.BagFragment;
import ru.chaihanamix.denis.dostavkamix.Fragments.FragmentOrder;
import ru.chaihanamix.denis.dostavkamix.Fragments.InOrderDialog;
import ru.chaihanamix.denis.dostavkamix.Fragments.dishListFragment;
import ru.chaihanamix.denis.dostavkamix.SlideMenu.ListViewItem;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<ListViewItem> slide_data;
    ListViewItem[] arr_slide_data;
    public SlideAdapter slideAdapter;

    boolean isReadyDish = false;

    public ArrayList<Review> reviews = new ArrayList<>();
    public ArrayList<Action> actions = new ArrayList<>();
    public ArrayList<Dish> dishs = new ArrayList<Dish>();
    public ArrayList<Category> categories = new ArrayList<Category>();
    public ArrayList<Catalog> catalogs = new ArrayList<Catalog>();

    public Drawer slideMuneDrawer = null;
    ImageView logo = null;
    TextViewPlus bag_price = null;

    BoxAdapter boxAdapter = null;

    //Мне очень стыдно...
    ArrayList<TextViewPlus> menuCategoryText = new ArrayList<TextViewPlus>();


    TextViewPlus selectText = null;

    public dishListFragment MenuFragment;
    public BagFragment bagFrag;
    public FragmentTransaction ft;
    private Fragment OrderFragment;
    DialogFragment dlg1;
    MainActivity mAct;
    ImageView arrow_down_t;
    ImageView arrow_up_t;

    LinearLayout frame;


    public void updateBagPrice() {
        int total = AppController.getInstance().getBagPrice();
        int totalWithoutPromo = AppController.getInstance().getBagPriceWithoutPromo();

        AppController.getInstance().calculateSale();

        double s = 1 - (double)AppController.getInstance().getSale() / 100;

        try {
            AppController.getInstance().setWithSale((int) (total - totalWithoutPromo + totalWithoutPromo * s));
        } catch (Exception e) {
            e.printStackTrace();
        }

        bag_price.setText(AppController.getInstance().addRuble(String.valueOf(total)));

        try {
            bagFrag.updateFragPrice();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);

        ft.replace(R.id.frame_fragment, MenuFragment);
        ft.commit();


        logo = (ImageView) findViewById(R.id.logo);
        bag_price = (TextViewPlus) findViewById(R.id.toolbar_price);
        arrow_down_t = (ImageView) findViewById(R.id.arrow_down_t);
        arrow_up_t = (ImageView) findViewById(R.id.arrow_up_t);
        frame = (LinearLayout) findViewById(R.id.arrow_cont);
        //topMenu = new TopMenu();
        mAct = this;


        findViewById(R.id.toolbar_lay_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppController.getInstance().getBagPrice() > 0) {
                    ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.animator.slide_in_right, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_left);
                    ft.replace(R.id.frame_fragment, bagFrag);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });

        new ParseTask().execute();
        new SendToken().execute("");
        new BuildActions().execute();



        //slideAdapter = new SlideAdapter(this);
        slideMuneDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withRootView(R.id.drawer_container)
                .withActionBarDrawerToggleAnimated(true)
                .withSliderBackgroundColorRes(R.color.base_color)
                .withCustomView(View.inflate(this, R.layout.slide_root, null))
                .withSavedInstance(savedInstanceState)
                .build();



        listView = (ListView) findViewById(R.id.slide_listview);


        //selectCategory(menuCategoryText.get(0));
        dlg1 = new InOrderDialog();


        AppController.getInstance().setMainActivity(this);
        AppController.getInstance().selectMenu(1);

        new Promo().execute();
    }

    private class Promo extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String response = "";

        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(Constants.getBase_url() + "server/promo/");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                response = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        protected synchronized void onPostExecute(String promo) {
            if (!promo.trim().isEmpty()) {
                showImage(Constants.getBase_url() + promo);
            }
        }
    }

    public void showImage(String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ImageLoader loader = AppController.getInstance().getImageLoader();
        ImageView imageView = new ImageView(this);

        loader.get(url, ImageLoader.getImageListener(imageView, R.drawable.progress_image, 0));

        builder.setView(imageView);

        builder.setTitle("Акция дня");

        builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

        alert.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = slideMuneDrawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        Log.d("json", "click Back");

        if (getFragmentManager().getBackStackEntryCount() > 1) {

            try {
                getFragmentManager().popBackStack();
                Log.d("json", "В стеке что-то есть  " + getFragmentManager().getBackStackEntryCount());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            super.onBackPressed();
        }
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {
        final String base_url_img = Constants.getBase_url() + "ios_app/action_images/action_";
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonExport = "";

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url_export = new URL(Constants.getBase_url() + "server/export.json");

                urlConnection = (HttpURLConnection) url_export.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                jsonExport = buffer.toString();


                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                //Document docActions = dBuilder.parse(Constants.getBase_url() + "ios_app/actions.xml");
                Document docReviews = dBuilder.parse(Constants.getBase_url() + "ios_app/reviews.xml");
                docReviews.getDocumentElement().normalize();
                //docActions.getDocumentElement().normalize();

                //Log.d("parse", " Root element: " + docActions.getDocumentElement().getNodeName());

                //NodeList actionList = docActions.getElementsByTagName("action");
                NodeList reviewsList = docReviews.getElementsByTagName("review");

                for (int i = 0; i < reviewsList.getLength(); i++) {
                    Node nNode = reviewsList.item(i);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        reviews.add(new Review(eElement.getAttribute("title"), eElement.getAttribute("subtitle"), eElement.getTextContent()));
                    }
                }
/*
                for (int temp = 0; temp < actionList.getLength(); temp++) {
                    Node nNode = actionList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        actions.add(new Action(eElement.getAttribute("title"), base_url_img + eElement.getAttribute("img_id") + ".png", eElement.getTextContent()));
                    }
                }
                */
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonExport;
        }

        @Override
        protected synchronized void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            JSONObject dataJsonObj = null;

            MainActivity mainActivity = AppController.getInstance().getMainActivity();


            try {
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
                                    dish.getString("measure"),
                                    dish.getString("title"),
                                    dish.getString("content"),
                                    dish.getString("image"),
                                    dish.getBoolean("isNew"),
                                    dish.getBoolean("isPromo"),
                                    k
                            ));
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            isReadyDish = true;
            AppController.getInstance().actionListFragment.setListAdapter(new ActionListAdapter());
            AppController.getInstance().reviewListFragment.setListAdapter(new ReviewListAdapter());

            slide_data = new ArrayList<ListViewItem>();
            for(int i = 0; i < catalogs.size(); i++) {
                slide_data.add(new ListViewItem(catalogs.get(i).getNameCatalog(), SlideAdapter.TYPE_CATALOG, 0));
                for (int k = 0; k < categories.size(); k++) {
                    if(categories.get(k).getIdCatalog() == catalogs.get(i).getIdCatalog()) {
                        slide_data.add(new ListViewItem(categories.get(k).getNameCategory(), SlideAdapter.TYPE_SUBCATALOG, categories.get(i).getIdCategory()));
                    }
                }
            }

            arr_slide_data = slide_data.toArray(new ListViewItem[slide_data.size()]);
            slideAdapter = new SlideAdapter(mainActivity, R.id.slide_text, arr_slide_data);
            listView.setAdapter(slideAdapter);

            mainActivity.updateListDish(mainActivity.getDishOfCategory(mainActivity.getCategoryOfName("Суши"), mainActivity.dishs));

        }
    }

    public ArrayList<Dish> getDishOfCategory(Category category, ArrayList<Dish> dishL) {
        ArrayList<Dish> result = new ArrayList<Dish>();

        Collections.sort(dishL, new Comparator<Dish>() {
            public int compare(Dish a, Dish b)
            {
                Boolean isNewA = a.isNew();
                Boolean isNewB = b.isNew();

                return isNewA.compareTo(isNewB) * -1;
            }
        });

        for (int i = 0; i < dishL.size(); i++) {
            if (dishL.get(i).getIdCategory() == category.getIdCategory()) {
                result.add(dishL.get(i));
            }
        }
        return result;
    }

    public void updateListDish(ArrayList<Dish> aDish) {
        try {
            boxAdapter = new BoxAdapter(this, aDish, ft);

            MenuFragment.setAdapter(boxAdapter);
            ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.animator.fade_in, R.animator.slide_out_left);
            ft.replace(R.id.frame_fragment, MenuFragment);

            AppController.getInstance().setIsShowMenuList(true);
            AppController.getInstance().setIsShowDescriptFrag(false);
            AppController.getInstance().selectMenu(1, true);

            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Category getCategoryOfName(String name) {
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getNameCategory().equals(name)) {
                return categories.get(i);
            }
        }
        return null;
    }

}
