package ru.dostavkamix.denis.dostavkamix.Fragments.Profile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dostavkamix.denis.dostavkamix.Adapters.ProfilePagerAdapter;
import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.Custom.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by Денис on 02.08.2016.
 */

public class ProfileFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private TabLayout tab;
    private ViewPager pager;
    private ProfilePagerAdapter adapter;

    private void inicializeUI(View view) {
        tab = (TabLayout) (view.findViewById(R.id.tab));
        pager = (ViewPager) (view.findViewById(R.id.pager));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        inicializeUI(v);

        adapter = new ProfilePagerAdapter(AppController.getInstance().getMainActivity().getSupportFragmentManager(), getActivity());
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);
        adapter.setupTab(tab);
        tab.setOnTabSelectedListener(this);

        return v;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        pager.setCurrentItem(tab.getPosition());

        TextViewPlus title = (TextViewPlus) tab.getCustomView().findViewById(R.id.title);
        title.setCustomFont(getActivity(), "fonts/GothaProMed.otf");
        title.setTextColor(getActivity().getResources().getColor(android.R.color.white));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        TextViewPlus title = (TextViewPlus) tab.getCustomView().findViewById(R.id.title);
        title.setCustomFont(getActivity(), "fonts/GothaProLig.otf");
        title.setTextColor(getActivity().getResources().getColor(R.color.color_hint));
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
