package ru.dostavkamix.denis.dostavkamix.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.BagAdapter;
import ru.dostavkamix.denis.dostavkamix.BagSwipeAdapter;
import ru.dostavkamix.denis.dostavkamix.CustomView.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.R;


/**
 * Created by den on 29.01.2016.
 */
public class BagFragment extends Fragment {

    ListView listView;
    TextViewPlus sale;
    TextViewPlus withoutSale;
    TextViewPlus withSale;
    TextViewPlus but_order;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bag_fragment, container, false);

        listView = (ListView) v.findViewById(R.id.list_bag);
        sale = (TextViewPlus) v.findViewById(R.id.sale);
        withoutSale = (TextViewPlus) v.findViewById(R.id.without_sale);
        withSale = (TextViewPlus) v.findViewById(R.id.with_sale);
        but_order = (TextViewPlus) v.findViewById(R.id.text_but_order);

        //listView.setAdapter(new BagAdapter(AppController.getInstance().getApplicationContext()));
        listView.setAdapter(new BagSwipeAdapter(AppController.getInstance().getApplicationContext()));

        updateFragPrie();

        return v;
    }

    public void updateFragPrie()
    {
        sale.setText(String.valueOf(AppController.getInstance().getSale()) + "%");

        withSale.setText(addR(String.valueOf(AppController.getInstance().getWithSale())));
        withoutSale.setText(addR(String.valueOf(AppController.getInstance().getWithoutSale())));

        but_order.setText("Оформить за " + addR(String.valueOf(AppController.getInstance().getWithSale())));
    }

    private String addR(String s)
    {
        String result = "";
        if(s.length() < 4) result = s + " руб.";
        else result = s.substring(0, 1 + (s.length() - 4)) + " " + s.substring(1 + (s.length() - 4)) + " руб.";

        return result;
    }
}
