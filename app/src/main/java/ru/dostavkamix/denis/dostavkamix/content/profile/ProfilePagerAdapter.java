package ru.dostavkamix.denis.dostavkamix.content.profile;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.Custom.TextViewPlus;
import ru.dostavkamix.denis.dostavkamix.Fragments.Profile.OrdersFragment;
import ru.dostavkamix.denis.dostavkamix.Fragments.Profile.PointsFragment;
import ru.dostavkamix.denis.dostavkamix.Fragments.Profile.ProfileEditFragment;
import ru.dostavkamix.denis.dostavkamix.Objects.User;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.UserCallback;
import ru.dostavkamix.denis.dostavkamix.UserHelper;
import ru.dostavkamix.denis.dostavkamix.content.profile.edit.EditFragment;

/**
 * Created by Денис on 02.08.2016.
 */

public class ProfilePagerAdapter extends FragmentPagerAdapter {

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
                return new PointsFragment();
            case 1:
                return new OrdersFragment();
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
        final TextViewPlus points = (TextViewPlus) v.findViewById(R.id.points);
        UserHelper.getUser(AppController.getInstance().getUserToken(), AppController.getInstance(), new UserCallback() {
            @Override
            public void onSuccess(User user) {
                points.setText(String.valueOf(user.getPoints()));
            }

            @Override
            public void onError(String error) {

            }
        });

        switch (position) {
            case 1:
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
}