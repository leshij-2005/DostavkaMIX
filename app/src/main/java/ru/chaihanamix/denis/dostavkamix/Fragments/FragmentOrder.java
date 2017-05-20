package ru.chaihanamix.denis.dostavkamix.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.chaihanamix.denis.dostavkamix.R;

/**
 * Created by den on 23.01.16.
 */
public class FragmentOrder extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bag, container, false);
        return rootView;
    }
}
