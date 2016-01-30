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
import ru.dostavkamix.denis.dostavkamix.R;


/**
 * Created by den on 29.01.2016.
 */
public class BagFragment extends Fragment {

    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bag_fragment, container, false);
        listView = (ListView) v.findViewById(R.id.list_bag);
        //listView.setAdapter(new BagAdapter(AppController.getInstance().getApplicationContext()));
        listView.setAdapter(new BagSwipeAdapter(AppController.getInstance().getApplicationContext()));

        return v;
    }
}
