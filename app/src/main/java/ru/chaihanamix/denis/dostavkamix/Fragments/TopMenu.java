package ru.chaihanamix.denis.dostavkamix.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.chaihanamix.denis.dostavkamix.R;

/**
 * Created by den on 26.01.16.
 */
public class TopMenu extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.top_menu, container, false);
        return v;
    }
}
