package ru.dostavkamix.denis.dostavkamix.content.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import ru.dostavkamix.denis.dostavkamix.Custom.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.base.BaseMvpFragment;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public class ProfileFragment extends BaseMvpFragment<ProfileView, ProfilePresenter>
        implements ProfileView, TabLayout.OnTabSelectedListener {

    @BindView(R.id.tab) TabLayout tab;
    @BindView(R.id.pager) ViewPager pager;

    private ProfilePagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ProfilePagerAdapter(getFragmentManager(), getContext());
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);
        adapter.setupTab(tab);
        tab.setOnTabSelectedListener(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_profile;
    }

    @NonNull
    @Override
    public ProfilePresenter createPresenter() {
        return new ProfilePresenter();
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

    @Override
    public void updatePointsCount(int pointsCount) {
        Log.d("ProfileFragment", "updatePointsCount: " + pointsCount);
        adapter.setPointsCount(pointsCount);
    }
}
