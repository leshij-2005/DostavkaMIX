package ru.dostavkamix.denis.dostavkamix.Activitys;

import ru.dostavkamix.denis.dostavkamix.Adapters.ActionListAdapter;
import ru.dostavkamix.denis.dostavkamix.Adapters.BoxAdapter;
import ru.dostavkamix.denis.dostavkamix.Adapters.ReviewListAdapter;
import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.Tasks.BuildActions;
import ru.dostavkamix.denis.dostavkamix.Objects.Action;
import ru.dostavkamix.denis.dostavkamix.Objects.Review;
import ru.dostavkamix.denis.dostavkamix.Push.SendToken;
import ru.dostavkamix.denis.dostavkamix.Adapters.SlideAdapter;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

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

import ru.dostavkamix.denis.dostavkamix.Custom.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.Objects.Catalog;
import ru.dostavkamix.denis.dostavkamix.Objects.Category;
import ru.dostavkamix.denis.dostavkamix.Objects.Dish;
import ru.dostavkamix.denis.dostavkamix.Fragments.BagFragment;
import ru.dostavkamix.denis.dostavkamix.Fragments.OrderFragment;
import ru.dostavkamix.denis.dostavkamix.Dialogs.InOrderDialog;
import ru.dostavkamix.denis.dostavkamix.Fragments.dishListFragment;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.Custom.blurbehind.BlurBehind;
import ru.dostavkamix.denis.dostavkamix.Custom.blurbehind.OnBlurCompleteListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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

    public dishListFragment MenuFragment;
    public BagFragment bagFrag;
    public FragmentTransaction ft;
    private Fragment OrderFragment;
    DialogFragment dlg1;
    MainActivity mAct;
    public ImageView arrow_down_t;
    public ImageView arrow_up_t;

    public LinearLayout frame;



    public void updateBagPrice() {
        AppController.getInstance().setWithoutSale(AppController.getInstance().getBagPrice());
        double s = 0;
        if (AppController.getInstance().getWithoutSale() >= 500 && AppController.getInstance().getWithoutSale() < 1500) {
            AppController.getInstance().setSale(5);
            s = 0.95;
        } else if (AppController.getInstance().getWithoutSale() >= 1500 && AppController.getInstance().getWithoutSale() < 2500) {
            AppController.getInstance().setSale(10);
            s = 0.90;
        } else if (AppController.getInstance().getWithoutSale() >= 2500) {
            AppController.getInstance().setSale(15);
            s = 0.85;
        } else {
            AppController.getInstance().setSale(0);
            s = 1;

        }

        try {
            AppController.getInstance().setWithSale((int) (AppController.getInstance().getWithoutSale() * s));
        } catch (Exception e) {
            e.printStackTrace();
        }

        bag_price.setText(AppController.getInstance().addRuble(String.valueOf(AppController.getInstance().getWithSale())));
        try {
            bagFrag.updateFragPrie();
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
        OrderFragment = new OrderFragment();
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

                if (AppController.getInstance().getWithoutSale() > 0) {
                    Log.d("json", String.valueOf(AppController.getInstance().getWithoutSale()));
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


    public void showShapeActivity() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isReadyDish) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BlurBehind.getInstance().execute(mAct, new OnBlurCompleteListener() {
                            @Override
                            public void onBlurComplete() {
                                Intent intent = new Intent(mAct, IntroActivity.class);
                                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                            }
                        }, 12);
                    }
                });
            }
        }).start();
    }
    private class ParseTask extends AsyncTask<Void, Void, String> {
        final String base_url_img = "http://chaihanamix.ru/ios_app/action_images/action_";
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonExport = "";

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url_export = new URL("http://chaihanamix.ru/server/export.json");

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
                //Document docActions = dBuilder.parse("http://chaihanamix.ru/ios_app/actions.xml");
                Document docReviews = dBuilder.parse("http://chaihanamix.ru/ios_app/reviews.xml");
                docReviews.getDocumentElement().normalize();
                //docActions.getDocumentElement().normalize();

                //Log.d("parse", " Root element: " + docActions.getDocumentElement().getNodeName());

                //NodeList actionList = docActions.getElementsByTagName("action");
                NodeList reviewsList = docReviews.getElementsByTagName("review");

                for (int i = 0; i < reviewsList.getLength(); i++) {
                    Node nNode = reviewsList.item(i);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        Log.d("parse", "title : " + eElement.getAttribute("title"));
                        Log.d("parse", "subtitle : " + eElement.getAttribute("subtitle"));
                        Log.d("parse", "content : " + eElement.getTextContent());

                        reviews.add(new Review(eElement.getAttribute("title"), eElement.getAttribute("subtitle"), eElement.getTextContent()));
                    }
                }
/*
                for (int temp = 0; temp < actionList.getLength(); temp++) {
                    Node nNode = actionList.item(temp);

                    Log.d("parse", "Current Element : " + nNode.getNodeName());

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;

                        Log.d("parse", "title : " + eElement.getAttribute("title"));
                        Log.d("parse", "img_id : " + eElement.getAttribute("img_id"));
                        Log.d("parse", "img_id : " + eElement.getTextContent());

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

            Log.d("json", "Start Parse");

            JSONObject dataJsonObj = null;


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
                                    dish.getString("title"),
                                    dish.getString("content"),
                                    dish.getString("image"),
                                    k


                            ));
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("json", "Exception Parse");
            }

            Log.d("json", "Stop Parse");
            Log.d("json", "Review : ");
            Log.d("json", "     title : " + reviews.get(1).title);
            Log.d("json", "     subtitle : " + reviews.get(1).subtitle);
            Log.d("json", "     content : " + reviews.get(1).content);
            //Log.d("json", "Action : " + actions.get(1));
            //Log.d("json", "     title : " + actions.get(1).title);
            //Log.d("json", "     url : " + actions.get(1).url);
            //Log.d("json", "     content : " + actions.get(1).content);

            isReadyDish = true;
            AppController.getInstance().actionListFragment.setListAdapter(new ActionListAdapter());
            AppController.getInstance().reviewListFragment.setListAdapter(new ReviewListAdapter());
            //AppController.getInstance().actionFragment.setPagerAdapter(new ActionAdapter());

            slide_data = new ArrayList<ListViewItem>();
            for(int i = 0; i < catalogs.size(); i++) {
                slide_data.add(new ListViewItem(catalogs.get(i).getNameCatalog(), SlideAdapter.TYPE_CATALOG, 0));
                for (int k = 0; k < categories.size(); k++) {
                    if(categories.get(k).getIdCatalog() == catalogs.get(i).getIdCatalog()) {
                        slide_data.add(new ListViewItem(categories.get(k).getNameCategory(), SlideAdapter.TYPE_SUBCATALOG, categories.get(k).getIdCategory()));
                        //Log.d(TAG, "onPostExecute: category id: " + categories.get(i).getIdCategory());
                    }
                }
            }
            // Нежно пихаем его в слайд
            arr_slide_data = slide_data.toArray(new ListViewItem[slide_data.size()]);
            slideAdapter = new SlideAdapter(AppController.getInstance().getMainActivity(), R.id.slide_text, arr_slide_data);
            listView.setAdapter(slideAdapter);

            AppController.getInstance().getMainActivity()
                    .updateListDish(
                            AppController.getInstance().getMainActivity().getDishOfCategory(AppController.getInstance().getMainActivity().getCategoryOfName("Суши"), AppController.getInstance().getMainActivity().dishs));
        }
    }

    public ArrayList<Dish> getDishOfCategory(Category category, ArrayList<Dish> dishL) {
        Log.d("json", "Начинаю сортировку...");
        Log.d("json", "Жду готовности списка...");
        Log.d("json", "Список готов! Сортирую...");
        ArrayList<Dish> result = new ArrayList<Dish>();
        for (int i = 0; i < dishL.size(); i++) {
            if (dishL.get(i).getIdCategory() == category.getIdCategory()) result.add(dishL.get(i));
        }
        return result;
    }

    public void updateListDish(ArrayList<Dish> aDish) {
        try {
            Log.d("json", "Готовлю фрагмент для обновления...");
            boxAdapter = new BoxAdapter(this, aDish, ft);
            //MenuFragment.setListAdapter(boxAdapter);
            MenuFragment.setAdapter(boxAdapter);
            Log.d("json", "back to Menu");
            ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.animator.fade_in, R.animator.slide_out_left);
            ft.replace(R.id.frame_fragment, MenuFragment);
            AppController.getInstance().selectMenu(1);
            ft.commit();
            Log.d("json", "Фрагмент обновлен!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Category getCategoryIdOfTag(String t) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getIdCategory() == Integer.valueOf(t)) {
                return categories.get(i);
            }
        }
        return null;
    }
    public Category getCategoryOfName(String name) {
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getNameCategory().equals(name)) {
                return categories.get(i);
            }
        }
        return null;
    }

    public Category getCategotyOfId(int id) {
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getIdCategory() == id) return categories.get(i);
        }
        return null;
    }

    public class ListViewItem {
        private String text;
        private int type;
        private int id;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {

            return id;
        }

        public ListViewItem(String text, int type, int id) {
            this.text = text;
            this.type = type;
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

    }
}
