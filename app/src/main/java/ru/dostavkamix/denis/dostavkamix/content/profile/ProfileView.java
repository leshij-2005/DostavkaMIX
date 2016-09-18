package ru.dostavkamix.denis.dostavkamix.content.profile;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public interface ProfileView extends MvpView {

    void updatePointsCount(int pointsCount);
}
