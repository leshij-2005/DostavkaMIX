package ru.dostavkamix.denis.dostavkamix.content.profile;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ru.dostavkamix.denis.dostavkamix.Custom.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.content.profile.edit.EditFragment;
import ru.dostavkamix.denis.dostavkamix.content.profile.orders.OrdersFragment;
import ru.dostavkamix.denis.dostavkamix.content.profile.points.PointsFragment;

/**
 * Created by Денис on 02.08.2016.
 */

public class ProfilePagerAdapter extends FragmentPagerAdapter {

    TextView points;

    private final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] {"ЗАКАЗЫ", "БАЛЛЫ", "ПРОФИЛЬ"};
    private Context ctx;

    public ProfilePagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.ctx = ctx;


    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OrdersFragment();
            case 1:
                return new PointsFragment();
            case 2:
                return new EditFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    public View getTabView(int position, boolean selected) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.tab_view, null);
        TextViewPlus title = (TextViewPlus) v.findViewById(R.id.title);
        TextViewPlus points = (TextViewPlus) v.findViewById(R.id.points);

        switch (position) {
            case 1:
                this.points = points;
                points.setVisibility(View.VISIBLE);
                break;
            default:
                points.setVisibility(View.GONE);
                break;
        }
        title.setText(tabTitles[position]);
        return v;
    }

    public void setupTab(TabLayout tabLayout) {
        for (int i = 0; i < PAGE_COUNT; i++) {
            tabLayout.getTabAt(i).setCustomView(getTabView(i, false));
        }
    }

    void setPointsCount(int pointsCount) {
        if(points != null) points.setText(String.valueOf(pointsCount));
    }
}
