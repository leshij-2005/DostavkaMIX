package ru.dostavkamix.denis.dostavkamix.Fragments.Profile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by Денис on 02.08.2016.
 */

public class PointsFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_points, container, false);


        return v;
    }
}
